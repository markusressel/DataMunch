package de.markusressel.datamunch.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.JailPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.JailEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemJailBinding
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class JailsFragment : ListFragmentBase<JailEntity>() {

    @Inject
    lateinit var jailPersistenceManager: JailPersistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<JailEntity, ListItemJailBinding>(R.layout.list_item_jail) {
                    onCreate { it.binding.presenter = this@JailsFragment }
                    onClick {
                        openJailDetailView(listValues[it.adapterPosition])
                    }
                }
                .into(recyclerview)
    }

    override fun onListViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun loadListDataFromSource(): List<JailEntity> {
        return freeNasWebApiClient.getJails().blockingGet().map {
            it.asEntity()
        }
    }

    override fun getPersistenceHandler(): PersistenceManagerBase<JailEntity> {
        return jailPersistenceManager
    }

    override fun loadListDataFromPersistence(): List<JailEntity> {
        return super.loadListDataFromPersistence().sortedBy {
            it.jail_host.toLowerCase()
        }
    }

    fun startJail(jail: JailEntity) {
        freeNasWebApiClient.startJail(jail.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            Toast.makeText(activity, "Success!", Toast.LENGTH_SHORT).show()
                            reloadDataFromSource()
                        },
                        onError = {
                            Toast.makeText(activity, "Error!", Toast.LENGTH_LONG).show()
                            showError(it)
                        }
                )
    }

    fun stopJail(jail: JailEntity) {
        freeNasWebApiClient.stopJail(jail.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            Toast.makeText(activity, "Success!", Toast.LENGTH_SHORT).show()
                            reloadDataFromSource()
                        },
                        onError = {
                            Toast.makeText(activity, "Error!", Toast.LENGTH_LONG).show()
                            showError(it)
                        }
                )
    }

    fun restartJail(jail: JailEntity) {
        freeNasWebApiClient.restartJail(jail.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            // TODO: show progress
                        },
                        onComplete = {
                            reloadDataFromSource()
                        },
                        onError = {
                            Toast.makeText(activity, "Error!", Toast.LENGTH_LONG).show()
                            showError(it)
                        }
                )
    }

    private fun openJailDetailView(jail: JailEntity) {

    }

}