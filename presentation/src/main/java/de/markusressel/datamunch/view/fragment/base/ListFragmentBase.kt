package de.markusressel.datamunch.view.fragment.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.freebsd.FreeBSDServerManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.view.plugin.LoadingPlugin
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import kotlinx.android.synthetic.main.layout_empty_list.*
import javax.inject.Inject


/**
 * Created by Markus on 29.01.2018.
 */
abstract class ListFragmentBase<K : Any, T : Any> : OptionsMenuFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.fragment_recyclerview

    override val optionsMenuRes: Int?
        get() = R.menu.options_menu_list

    protected open val fabConfig: FabConfig = FabConfig(left = mutableListOf(),
                                                        right = mutableListOf())
    private val fabButtonViews = mutableListOf<FloatingActionButton>()

    protected val listValues: MutableList<T> = ArrayList()
    private lateinit var recyclerViewAdapter: LastAdapter

    @Inject
    lateinit var frittenbudeServerManager: FreeBSDServerManager

    protected val loadingPlugin = LoadingPlugin(onShowContent = {
        updateFabVisibility(View.VISIBLE)
    }, onShowError = { s: String, throwable: Throwable? ->
        layoutEmpty
                .visibility = View
                .GONE
        updateFabVisibility(View.INVISIBLE)
    })

    init {
        addFragmentPlugins(loadingPlugin)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        recyclerViewAdapter = createAdapter()

        recyclerView
                .adapter = recyclerViewAdapter
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView
                .layoutManager = layoutManager

        setupFabs()

        frittenbudeServerManager
                .setSSHConnectionConfig(connectionManager.getSSHProxy(),
                                        connectionManager.getMainSSHConnection())

        onListViewCreated(view, savedInstanceState)

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
        fab
                .onClick
                ?.let {
                    fabView
                            .setOnClickListener {
                                it()
                            }
                }

        fab
                .onLongClick
                ?.let {
                    val listener = it
                    fabView
                            .setOnLongClickListener {
                                listener()
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
     * Called after the list view layout has been inflated
     */
    abstract fun onListViewCreated(view: View, savedInstanceState: Bundle?)

    /**
     * Loads the data using {@link loadListDataFromPersistence()}
     */
    private fun fillListFromPersistence() {
        loadingPlugin
                .showLoading()

        Single
                .fromCallable {
                    loadListDataFromPersistence()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                    loadingPlugin
                            .showContent()

                    recyclerViewAdapter
                            .notifyDataSetChanged()
                }, onError = {
                    loadingPlugin
                            .showError(it)
                })
    }

    /**
     * Reload list data asEntity it's original source, persist it and display it to the user afterwards
     */
    protected fun reloadDataFromSource() {
        loadingPlugin
                .showLoading()

        loadListDataFromSource()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    it
                            .toObservable()
                            .map {
                                mapToPersistenceEntity(it)
                            }
                            .toList()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeBy(onSuccess = {
                                persistListData(it)
                                fillListFromPersistence()
                            }, onError = {
                                loadingPlugin
                                        .showError(it)
                            })
                }, onError = {
                    loadingPlugin
                            .showError(it)
                })
    }

    abstract fun mapToPersistenceEntity(it: K): T

    /**
     * Get the persistence handler for this list
     */
    protected abstract fun getPersistenceHandler(): PersistenceManagerBase<T>

    private fun persistListData(data: List<T>) {
        getPersistenceHandler()
                .standardOperation()
                .removeAll()
        getPersistenceHandler()
                .standardOperation()
                .put(data)
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
    open fun loadListDataFromPersistence(): List<T> {
        val persistenceHandler = getPersistenceHandler()
        return persistenceHandler
                .standardOperation()
                .all
    }

    /**
     * Load the data to be displayed in the list asEntity it's original source
     */
    abstract fun loadListDataFromSource(): Single<List<K>>


    override fun onOptionsMenuItemSelected(item: MenuItem): Boolean {
        return when {
            item.itemId == R.id.refresh -> {
                reloadDataFromSource()
                true
            }
            else -> false
        }
    }

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

}