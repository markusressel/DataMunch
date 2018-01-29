package de.markusressel.datamunch.view.fragment.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.R
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recyclerview.*

/**
 * Created by Markus on 29.01.2018.
 */
abstract class ListFragmentBase<T : Any> : LoadingSupportFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.fragment_recyclerview

    /**
     * The layout resource for an item in the list
     */
    @get:LayoutRes
    protected abstract val itemLayoutRes: Int

    protected val listValues: MutableList<T> = ArrayList()
    private lateinit var recyclerViewAdapter: LastAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewAdapter = createAdapter()

        recyclerview.adapter = recyclerViewAdapter
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerview.layoutManager = layoutManager
    }

    abstract fun createAdapter(): LastAdapter

    /**
     * Loads the data using {@link loadListData()}
     */
    protected fun updateListData() {
        showLoading()

        Single.fromCallable {
            loadListData()
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            listValues.clear()
                            listValues.addAll(it)

                            recyclerViewAdapter.notifyDataSetChanged()

                            showContent()
                        },
                        onError = {
                            showError(it)
                        }
                )
    }

    /**
     * Load the data to be displayed in the list
     */
    abstract fun loadListData(): List<T>

}