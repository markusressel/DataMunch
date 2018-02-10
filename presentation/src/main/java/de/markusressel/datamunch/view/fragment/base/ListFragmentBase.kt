package de.markusressel.datamunch.view.fragment.base

import android.content.Context
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.MenuItem
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.github.nitrico.lastadapter.LastAdapter
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.freebsd.FreeBSDServerManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.freenaswebapiclient.BasicAuthConfig
import de.markusressel.freenaswebapiclient.FreeNasWebApiClient
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import kotlinx.android.synthetic.main.layout_empty.*
import javax.inject.Inject


/**
 * Created by Markus on 29.01.2018.
 */
abstract class ListFragmentBase<T : Any> : LoadingSupportFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.fragment_recyclerview

    override val optionsMenuRes: Int?
        get() = R.menu.options_menu_list

    open val isAddable = true

    protected val listValues: MutableList<T> = ArrayList()
    private lateinit var recyclerViewAdapter: LastAdapter

    @Inject
    lateinit var frittenbudeServerManager: FreeBSDServerManager

    val freeNasWebApiClient = FreeNasWebApiClient()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        recyclerViewAdapter = createAdapter()

        recyclerView
                .adapter = recyclerViewAdapter
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView
                .layoutManager = layoutManager

        // setup fab
        if (isAddable) {
            addFabButton
                    .setImageDrawable(iconHandler.getFabIcon(MaterialDesignIconic.Icon.gmi_plus))
            addFabButton
                    .setOnClickListener {
                        onAddClicked()
                    }

            val fabBehavior = ScrollAwareFABBehavior()
            val params = addFabButton.layoutParams as CoordinatorLayout.LayoutParams
            params
                    .behavior = fabBehavior
        }
        updateFabVisibility(View.VISIBLE)

        frittenbudeServerManager
                .setSSHConnectionConfig(connectionManager.getSSHProxy(),
                                        connectionManager.getMainSSHConnection())

        freeNasWebApiClient
                .setHostname("frittenbude.markusressel.de")
        freeNasWebApiClient
                .setApiResource("frittenbudeapi")
        freeNasWebApiClient
                .setBasicAuthConfig(BasicAuthConfig(
                        username = connectionManager.getMainSSHConnection().username,
                        password = connectionManager.getMainSSHConnection().password))


        onListViewCreated(view, savedInstanceState)

        fillListFromPersistence()
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
        showLoading()

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
                    showContent()

                    recyclerViewAdapter
                            .notifyDataSetChanged()
                }, onError = {
                    showError(it)
                })
    }

    /**
     * Reload list data asEntity it's original source, persist it and display it to the user afterwards
     */
    protected fun reloadDataFromSource() {
        showLoading()

        Single
                .fromCallable {
                    loadListDataFromSource()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    persistListData(it)
                    fillListFromPersistence()
                }, onError = {
                    showError(it)
                })
    }

    /**
     * Get the persistence handler for this list
     */
    protected abstract fun getPersistenceHandler(): PersistenceManagerBase<T>

    private fun persistListData(data: List<T>) {
        val persistenceHandler = getPersistenceHandler()
        persistenceHandler
                .standardOperation()
                .removeAll()
        persistenceHandler
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
    abstract fun loadListDataFromSource(): List<T>

    override fun onOptionsMenuItemSelected(item: MenuItem): Boolean {
        return when {
            item.itemId == R.id.refresh -> {
                reloadDataFromSource()
                true
            }
            else -> false
        }
    }

    override fun showContent() {
        super
                .showContent()
        updateFabVisibility(View.VISIBLE)
    }

    private fun updateFabVisibility(visible: Int) {
        if (isAddable && visible == View.VISIBLE) {
            addFabButton
                    .visibility = View
                    .VISIBLE
        } else {
            addFabButton
                    .visibility = View
                    .INVISIBLE
        }
    }

    override fun onShowError(message: String, t: Throwable?) {
        super
                .onShowError(message, t)
        layoutEmpty
                .visibility = View
                .GONE
        updateFabVisibility(View.INVISIBLE)
    }

    override fun onErrorClicked() {
        super
                .onErrorClicked()
        reloadDataFromSource()
    }

    /**
     * Called when tha "+"/Add Button is clicked
     */
    open fun onAddClicked() {
        MaterialDialog
                .Builder(context as Context)
                .title(R.string.add)
                .content("Not yet implemented")
                .positiveText(android.R.string.ok)
                .show()
    }

}