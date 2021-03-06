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

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.widget.SearchView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.github.ajalt.timberkt.Timber
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.jakewharton.rxbinding2.view.RxView
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.EntityWithId
import de.markusressel.datamunch.data.freebsd.FreeBSDServerManager
import de.markusressel.datamunch.data.persistence.LastUpdateFromSourcePersistenceManager
import de.markusressel.datamunch.data.persistence.SortOptionPersistenceManager
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
import javax.inject.Inject


/**
 * Created by Markus on 29.01.2018.
 */
abstract class ListFragmentBase<ModelType : Any, EntityType : EntityWithId> : DaggerSupportFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.fragment_recyclerview

    protected open val fabConfig: FabConfig = FabConfig(left = mutableListOf(),
            right = mutableListOf())
    private val fabButtonViews = mutableListOf<FloatingActionButton>()

    val epoxyController by lazy { createEpoxyController() }

    protected val listValues: MutableList<EntityType> = ArrayList()

    @Inject
    lateinit var frittenbudeServerManager: FreeBSDServerManager

    @Inject
    lateinit var lastUpdatedManager: LastUpdateFromSourcePersistenceManager

    internal var currentSearchFilter: String by savedInstanceState("")

    @Inject
    lateinit var sortOptionPersistenceHandler: SortOptionPersistenceManager

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
                    val searchMenuItem = menu?.findItem(R.id.search)
                    searchMenuItem?.icon = iconHandler.getOptionsMenuIcon(
                            MaterialDesignIconic.Icon.gmi_search)

                    val searchView = searchMenuItem?.actionView as SearchView?
                    searchView?.let {
                        RxSearchView.queryTextChanges(it).skipInitialValue()
                                .bindUntilEvent(this, Lifecycle.Event.ON_DESTROY)
                                .debounce(300, TimeUnit.MILLISECONDS)
                                .observeOn(AndroidSchedulers.mainThread()).subscribeBy(onNext = {
                                    currentSearchFilter = it.toString()
                                    // TODO: this should be reactive
//                                    updateSearchFilter(currentSearchFilter)
                                }, onError = {
                                    Timber.e(it) { "Error filtering list" }
                                })
                    }

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

                    val sortOptionMenuItem = menu
                            ?.findItem(R.id.sortOrder)

                    sortOptionMenuItem
                            ?.let {
                                it
                                        .icon = sortIcon
                                if (getAllSortCriteria().isEmpty()) {
                                    sortOptionMenuItem
                                            .isVisible = false
                                }
                            }


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
        super.initComponents(context)
        loadingComponent
        optionsMenuComponent
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        optionsMenuComponent.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (super.onOptionsItemSelected(item)) {
            return true
        }
        return optionsMenuComponent.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val parent = super.onCreateView(inflater, container, savedInstanceState) as ViewGroup
        return loadingComponent.onCreateView(inflater, parent, savedInstanceState)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingComponent.showContent(false)

        recyclerView.setController(epoxyController)

        val layoutManager = StaggeredGridLayoutManager(
                resources.getInteger(R.integer.list_column_count),
                StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager

        setupFabs()
    }

    /**
     * Create the epoxy controller here.
     * The epoxy controller defines what information is displayed.
     */
    abstract fun createEpoxyController(): PagedListEpoxyController<EntityType>

    override fun onStart() {
        super.onStart()

        Bus.observe<SortOptionSelectionDialogDismissedEvent>().subscribe {
            // reload list with current sort options
            // TODO: SortOptions have to be reactive
        }.registerInBus(this)
    }

    override fun onResume() {
        super.onResume()

        if (System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(
                        5) > getLastUpdatedFromSource()) {
            Timber.d { "Persisted list data is old, refreshing from source" }
            // reloadDataFromSource()
        }
    }

    private fun setupFabs() {
        fabConfig.left.addAll(getLeftFabs())
        fabConfig.right.addAll(getRightFabs())

        // setup fabs
        fabConfig.left.forEach {
            addFab(true, it)
        }
        fabConfig.right.forEach {
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
        val inflater = LayoutInflater.from(context)

        val layout = when (isLeft) {
            true -> R.layout.view_fab_left
            false -> R.layout.view_fab_right
        }

        val fabView: FloatingActionButton = inflater.inflate(layout,
                recyclerView.parent as ViewGroup,
                false) as FloatingActionButton

        // icon
        fabView.setImageDrawable(iconHandler.getFabIcon(fab.icon))
        // fab color
        fab.color?.let {
            fabView.backgroundTintList = ContextCompat.getColorStateList(context as Context, it)
        }

        // behaviour
        val fabBehavior = ScrollAwareFABBehavior()
        val params = fabView.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior = fabBehavior

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


        fabButtonViews.add(fabView)
        val parent = recyclerView.parent as ViewGroup
        parent.addView(fabView)
    }

    /**
     * Sorts a list by the currently selected SortOptions
     */
    private fun sortByCurrentOptions(listData: List<EntityType>): List<EntityType> {
        val sortOptions = getCurrentSortOptions()

        if (sortOptions.isEmpty()) {
            return listData
        }

        // create initial comparator
        var comparator: Comparator<EntityType> = if (sortOptions.first().reversed) {
            compareByDescending(sortOptions.first().selector)
        } else {
            compareBy(sortOptions.first().selector)
        }

        // extend it with other criteria
        sortOptions.drop(1).forEach { criteria ->
            comparator = if (criteria.reversed) {
                comparator.thenByDescending(criteria.selector)
            } else {
                comparator.thenBy(criteria.selector)
            }
        }

        return listData.sortedWith(comparator)
    }

    /**
     * Returns a list of all available sort criteria
     * Override this method in child classes
     */
    open fun getAllSortCriteria(): List<SortOption<EntityType>> {
        return emptyList()
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

        val sortOptions = sortOptionEntities.map {
            SortOption.from(it.id)
        }

        return sortOptions as List<SortOption<EntityType>>
    }

    private fun openSortSelection() {
        SortOptionSelectionDialog
                .newInstance(getAllSortCriteria(), entityTypeId)
                .show(childFragmentManager, null)
    }

    /**
     * Reload list data asEntity it's original source, persist it and display it to the user afterwards
     */
    protected fun reloadDataFromSource() {
        loadingComponent.showLoading()

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
                                loadingComponent
                                        .showContent()
                            }, onError = {
                                if (it is CancellationException) {
                                    Timber.d { "persisting reload from source cancelled" }
                                } else {
                                    loadingComponent.showError(it)
                                }
                            })
                }, onError = {
                    if (it is CancellationException) {
                        Timber.d { "reload from source cancelled" }
                    } else {
                        loadingComponent.showError(it)
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
        getPersistenceHandler().insertUpdateAndCleanup(data)
    }

    private fun getLastUpdatedFromSource(): Long {
        val entityModelId = getPersistenceHandler().getEntityModelId()
        return lastUpdatedManager.getLastUpdated(entityModelId.toLong())
    }

    private fun updateLastUpdatedFromSource() {
        val entityModelId = getPersistenceHandler().getEntityModelId()
        lastUpdatedManager.setUpdatedNow(entityModelId.toLong())
    }

    private fun showEmpty() {
        recyclerView.visibility = View.INVISIBLE
        layoutEmpty.visibility = View.VISIBLE
    }

    private fun hideEmpty() {
        recyclerView.visibility = View.VISIBLE
        layoutEmpty.visibility = View.INVISIBLE
    }

    /**
     * Load the data to be displayed in the list asEntity the persistence
     */
    open fun loadListDataFromPersistence(): List<EntityType> {
        val persistenceHandler = getPersistenceHandler()
        return persistenceHandler.standardOperation().all
    }

    /**
     * Load the data to be displayed in the list asEntity it's original source
     */
    abstract fun loadListDataFromSource(): Single<List<ModelType>>

    private fun updateFabVisibility(visible: Int) {
        if (visible == View.VISIBLE) {
            fabButtonViews.forEach {
                it.visibility = View.VISIBLE
            }
        } else {
            fabButtonViews.forEach {
                it.visibility = View.INVISIBLE
            }
        }
    }

}
