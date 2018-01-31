package de.markusressel.datamunch.view.fragment.base

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.MenuItem
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.freebsd.FreeBSDServerManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import javax.inject.Inject

/**
 * Created by Markus on 29.01.2018.
 */
abstract class ListFragmentBase<T : Any> : LoadingSupportFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.fragment_recyclerview

    override val optionsMenuRes: Int?
        get() = R.menu.options_menu_list

    protected val listValues: MutableList<T> = ArrayList()
    private lateinit var recyclerViewAdapter: LastAdapter

    @Inject
    lateinit var frittenbudeServerManager: FreeBSDServerManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewAdapter = createAdapter()

        recyclerview.adapter = recyclerViewAdapter
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerview.layoutManager = layoutManager


        frittenbudeServerManager.setSSHConnectionConfig(
                connectionManager.getSSHProxy(),
                connectionManager.getMainSSHConnection()
        )

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

        Single.fromCallable {
            loadListDataFromPersistence()
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            listValues.clear()

                            if (it.isEmpty()) {
                                showEmpty()
                            } else {
                                listValues.addAll(it)
                                showContent()
                            }

                            recyclerViewAdapter.notifyDataSetChanged()
                        },
                        onError = {
                            showError(it)
                        }
                )
    }

    /**
     * Reload list data from it's original source, persist it and display it to the user afterwards
     */
    protected fun reloadDataFromSource() {
        showLoading()

        Single.fromCallable {
            loadListDataFromSource()
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            persistListData(it)
                            fillListFromPersistence()
                        },
                        onError = {
                            showError(it)
                        }
                )
    }

    /**
     * Get the persistence handler for this list
     */
    protected abstract fun getPersistenceHandler(): PersistenceManagerBase<T>

    private fun persistListData(data: List<T>) {
        val persistenceHandler = getPersistenceHandler()
        persistenceHandler.standardOperation().removeAll()
        persistenceHandler.standardOperation().put(data)
    }

    private fun showEmpty() {
        showError(getString(R.string.no_items_found))
    }

    /**
     * Load the data to be displayed in the list from the persistence
     */
    open fun loadListDataFromPersistence(): List<T> {
        val persistenceHandler = getPersistenceHandler()
        return persistenceHandler.standardOperation().all
    }

    /**
     * Load the data to be displayed in the list from it's original source
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

    override fun onErrorClicked() {
        super.onErrorClicked()
        reloadDataFromSource()
    }

}