/*
 * DataMunch by Markus Ressel
 * Copyright (c) 2018.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.markusressel.datamunch.view.fragment.base

import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.*
import android.widget.Toast
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.github.ajalt.timberkt.Timber
import com.github.nitrico.lastadapter.LastAdapter
import com.jakewharton.rxbinding2.view.RxView
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.freebsd.FreeBSDServerManager
import de.markusressel.datamunch.data.persistence.LastUpdateFromSourcePersistenceManager
import de.markusressel.datamunch.data.persistence.SortOptionPersistenceHandler
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.event.SortOptionSelectionDialogDismissedEvent
import de.markusressel.datamunch.view.component.LoadingComponent
import de.markusressel.datamunch.view.component.OptionsMenuComponent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import kotlinx.android.synthetic.main.layout_empty_list.*
import java.util.*
import java.util.concurrent.CancellationException
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject


/**
 * Created by Markus on 29.01.2018.
 */
abstract class ListFragmentBase<ModelType : Any, EntityType : Any> : DaggerSupportFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.fragment_recyclerview

    protected open val fabConfig: FabConfig = FabConfig(left = mutableListOf(),
                                                        right = mutableListOf())
    private val fabButtonViews = mutableListOf<FloatingActionButton>()

    protected val listValues: MutableList<EntityType> = ArrayList()
    private lateinit var recyclerViewAdapter: LastAdapter

    @Inject
    lateinit var frittenbudeServerManager: FreeBSDServerManager

    @Inject
    lateinit var lastUpdatedManager: LastUpdateFromSourcePersistenceManager

    private val persistenceLoaderId = loaderIdCounter
            .getAndIncrement()

    @Inject
    lateinit var sortOptionPersistenceHandler: SortOptionPersistenceHandler

    protected abstract val entityTypeId: Long

    protected val loadingComponent by lazy {
        LoadingComponent(this, onShowContent = {
            updateFabVisibility(View.VISIBLE)
        }, onShowError = { message: String, throwable: Throwable? ->
            layoutEmpty
                    .visibility = View
                    .GONE
            updateFabVisibility(View.INVISIBLE)
        })
    }

    private val optionsMenuComponent: OptionsMenuComponent by lazy {
        OptionsMenuComponent(
                hostFragment = this,
                optionsMenuRes = R.menu.options_menu_list,
                onCreateOptionsMenu = { menu: Menu?, menuInflater: MenuInflater? ->
                    // set refresh icon
                    val refreshIcon = iconHandler
                            .getOptionsMenuIcon(
                                    MaterialDesignIconic.Icon.gmi_refresh)
                    menu
                            ?.findItem(R.id.refresh)
                            ?.icon = refreshIcon

                    val sortIcon = iconHandler
                            .getOptionsMenuIcon(
                                    MaterialDesignIconic.Icon.gmi_sort)
                    menu
                            ?.findItem(R.id.sortOrder)
                            ?.icon = sortIcon
                }, onOptionsMenuItemClicked = {
            when {
                it.itemId == R.id.refresh -> {
                    reloadDataFromSource()
                    true
                }
                it.itemId == R.id.sortOrder -> {
                    openSortSelection()
                    true
                }
                else -> false
            }
        })
    }

    override fun initComponents(context: Context) {
        super
                .initComponents(context)
        loadingComponent
        optionsMenuComponent
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super
                .onCreateOptionsMenu(menu, inflater)
        optionsMenuComponent
                .onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (super.onOptionsItemSelected(item)) {
            return true
        }
        return optionsMenuComponent
                .onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val parent = super.onCreateView(inflater, container, savedInstanceState) as ViewGroup
        return loadingComponent
                .onCreateView(inflater, parent, savedInstanceState)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        recyclerViewAdapter = createAdapter()

        recyclerView
                .adapter = recyclerViewAdapter
        val layoutManager = StaggeredGridLayoutManager(
                resources.getInteger(R.integer.list_column_count),
                StaggeredGridLayoutManager.VERTICAL)
        recyclerView
                .layoutManager = layoutManager

        setupFabs()

        frittenbudeServerManager
                .setSSHConnectionConfig(connectionManager.getSSHProxy(),
                                        connectionManager.getMainSSHConnection())
    }

    override fun onStart() {
        super
                .onStart()

        Bus
                .observe<SortOptionSelectionDialogDismissedEvent>()
                .subscribe {
                    // reload list with current sort options
                    fillListFromPersistence()
                }
                .registerInBus(this)
    }

    override fun onResume() {
        super
                .onResume()

        if (System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(
                        5) > getLastUpdatedFromSource()) {
            Timber
                    .d { "Persisted list data is old, refreshing from source" }
            //            reloadDataFromSource()
        } else {
            Timber
                    .d { "Persisted list data is probably still valid, just loading from persistence" }
            fillListFromPersistence()
        }

        fillListFromPersistence()
    }

    private fun setupFabs() {
        fabConfig
                .left
                .addAll(getLeftFabs())
        fabConfig
                .right
                .addAll(getRightFabs())

        // setup fabs
        fabConfig
                .left
                .forEach {
                    addFab(true, it)
                }
        fabConfig
                .right
                .forEach {
                    addFab(false, it)
                }

        updateFabVisibility(View.VISIBLE)
    }

    protected open fun getLeftFabs(): List<FabConfig.Fab> {
        return emptyList()
    }

    protected open fun getRightFabs(): List<FabConfig.Fab> {
        return emptyList()
    }

    private fun addFab(isLeft: Boolean, fab: FabConfig.Fab) {
        val inflater = LayoutInflater
                .from(context)

        val layout = when (isLeft) {
            true -> R.layout.view_fab_left
            false -> R.layout.view_fab_right
        }

        val fabView: FloatingActionButton = inflater.inflate(layout,
                                                             recyclerView.parent as ViewGroup,
                                                             false) as FloatingActionButton

        // icon
        fabView
                .setImageDrawable(iconHandler.getFabIcon(fab.icon))
        // fab color
        fab
                .color
                ?.let {
                    fabView
                            .backgroundTintList = ContextCompat
                            .getColorStateList(context as Context, it)
                }

        // behaviour
        val fabBehavior = ScrollAwareFABBehavior()
        val params = fabView.layoutParams as CoordinatorLayout.LayoutParams
        params
                .behavior = fabBehavior

        // listeners
        RxView
                .clicks(fabView)
                .bindToLifecycle(fabView)
                .subscribe {
                    Toast
                            .makeText(context as Context, "Fab '${fab.description}' clicked",
                                      Toast.LENGTH_LONG)
                            .show()

                    // execute defined action if it exists
                    fab
                            .onClick
                            ?.let {
                                it()
                            }
                }

        RxView
                .longClicks(fabView)
                .bindToLifecycle(fabView)
                .subscribe {
                    Toast
                            .makeText(context as Context, "Fab '${fab.description}' long clicked",
                                      Toast.LENGTH_LONG)
                            .show()

                    // execute defined action if it exists
                    fab
                            .onLongClick
                            ?.let {
                                it()
                            }
                }


        fabButtonViews
                .add(fabView)
        val parent = recyclerView.parent as ViewGroup
        parent
                .addView(fabView)
    }

    /**
     * Create the adapter used for the recyclerview
     */
    abstract fun createAdapter(): LastAdapter

    /**
     * Loads the data using {@link loadListDataFromPersistence()}
     */
    private fun fillListFromPersistence() {
        loadingComponent
                .showLoading()

        Single
                .fromCallable {
                    var listData = loadListDataFromPersistence()

                    // sort list data according to current selection
                    listData = sortByCurrentOptions(listData)

                    listData
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .bindUntilEvent(this, Lifecycle.Event.ON_STOP)
                .subscribeBy(onSuccess = {
                    listValues
                            .clear()

                    if (it.isEmpty()) {
                        showEmpty()
                    } else {
                        hideEmpty()
                        listValues
                                .addAll(it)
                    }
                    loadingComponent
                            .showContent()

                    recyclerViewAdapter
                            .notifyDataSetChanged()
                }, onError = {
                    if (it is CancellationException) {
                        Timber
                                .d { "reload from persistence cancelled" }
                    } else {
                        loadingComponent
                                .showError(it)
                    }
                })
    }

    /**
     * Sorts a list by the currently selected SortOptions
     */
    private fun sortByCurrentOptions(listData: List<EntityType>): List<EntityType> {
        val sortOptions = getCurrentSortOptions()

        if (sortOptions.isEmpty()) {
            return listData
        }

        val comparator: Comparator<EntityType> = compareBy(sortOptions.first().selector)
        getCurrentSortOptions()
                .drop(1)
                .forEach { criteria ->
                    if (criteria.reversed) {
                        comparator
                                .thenByDescending(criteria.selector)
                    } else {
                        comparator
                                .thenBy(criteria.selector)
                    }
                }

        return listData
                .sortedWith(comparator)
    }

    /**
     * Returns a list of all available sort criteria
     * Override this method in child classes
     */
    open fun getAllSortCriteria(): List<SortOption<EntityType>> {
        return emptyList()
    }

    /**
     * Store the currently selected sort options
     */
    fun persistCurrentSortOptions() {
        // remove currently stored options
        sortOptionPersistenceHandler
                .standardOperation()
                .query()
                .filter {
                    it.type == entityTypeId
                }
                .build()
                .find()
                .forEach {
                    sortOptionPersistenceHandler
                            .standardOperation()
                            .remove(it.entityId)
                }

        getCurrentSortOptions()
                .forEach {
                    sortOptionPersistenceHandler
                            .standardOperation()
                            .put(it.toEntity(entityTypeId))
                }
    }

    /**
     * Get a list of the currently selected (active) sort criteria
     */
    open fun getCurrentSortOptions(): List<SortOption<EntityType>> {
        val sortOptionEntities = sortOptionPersistenceHandler
                .standardOperation()
                .query()
                .filter {
                    it.type == entityTypeId
                }
                .build()
                .find()

        val sortOptions = sortOptionEntities
                .map {
                    SortOption
                            .from(it.id)
                }

        return sortOptions as List<SortOption<EntityType>>
    }

    private fun openSortSelection() {
        SortOptionSelectionDialog
                .newInstance(getAllSortCriteria())
                .show(childFragmentManager, null)
    }

    /**
     * Reload list data asEntity it's original source, persist it and display it to the user afterwards
     */
    protected fun reloadDataFromSource() {
        loadingComponent
                .showLoading()

        loadListDataFromSource()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .bindUntilEvent(this, Lifecycle.Event.ON_STOP)
                .subscribeBy(onSuccess = {
                    it
                            .toObservable()
                            .bindUntilEvent(this, Lifecycle.Event.ON_STOP)
                            .map {
                                mapToEntity(it)
                            }
                            .toList()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeBy(onSuccess = {
                                persistListData(it)
                                updateLastUpdatedFromSource()
                                fillListFromPersistence()
                            }, onError = {
                                if (it is CancellationException) {
                                    Timber
                                            .d { "persisting reload from source cancelled" }
                                } else {
                                    loadingComponent
                                            .showError(it)
                                }
                            })
                }, onError = {
                    if (it is CancellationException) {
                        Timber
                                .d { "reload from source cancelled" }
                    } else {
                        loadingComponent
                                .showError(it)
                    }
                })
    }

    /**
     * Map the source object to the persistence object
     */
    abstract fun mapToEntity(it: ModelType): EntityType

    /**
     * Get the persistence handler for this list
     */
    protected abstract fun getPersistenceHandler(): PersistenceManagerBase<EntityType>

    private fun persistListData(data: List<EntityType>) {
        getPersistenceHandler()
                .standardOperation()
                .removeAll()
        getPersistenceHandler()
                .standardOperation()
                .put(data)
    }

    private fun getLastUpdatedFromSource(): Long {
        val entityModelId = getPersistenceHandler()
                .getEntityModelId()
        return lastUpdatedManager
                .getLastUpdated(entityModelId.toLong())
    }

    private fun updateLastUpdatedFromSource() {
        val entityModelId = getPersistenceHandler()
                .getEntityModelId()
        lastUpdatedManager
                .setUpdatedNow(entityModelId.toLong())
    }

    private fun showEmpty() {
        recyclerView
                .visibility = View
                .INVISIBLE
        layoutEmpty
                .visibility = View
                .VISIBLE
    }

    private fun hideEmpty() {
        recyclerView
                .visibility = View
                .VISIBLE
        layoutEmpty
                .visibility = View
                .INVISIBLE
    }

    /**
     * Load the data to be displayed in the list asEntity the persistence
     */
    open fun loadListDataFromPersistence(): List<EntityType> {
        val persistenceHandler = getPersistenceHandler()
        return persistenceHandler
                .standardOperation()
                .all
    }

    /**
     * Load the data to be displayed in the list asEntity it's original source
     */
    abstract fun loadListDataFromSource(): Single<List<ModelType>>

    private fun updateFabVisibility(visible: Int) {
        if (visible == View.VISIBLE) {
            fabButtonViews
                    .forEach {
                        it
                                .visibility = View
                                .VISIBLE
                    }
        } else {
            fabButtonViews
                    .forEach {
                        it
                                .visibility = View
                                .INVISIBLE
                    }
        }
    }

    companion object {
        private val loaderIdCounter: AtomicInteger = AtomicInteger()
    }

}
