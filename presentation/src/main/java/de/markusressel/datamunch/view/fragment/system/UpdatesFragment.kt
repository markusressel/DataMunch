package de.markusressel.datamunch.view.fragment.system

import com.github.nitrico.lastadapter.LastAdapter
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.UpdatePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.UpdateEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemUpdateBinding
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.freenaswebapiclient.system.update.UpdateModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class UpdatesFragment : ListFragmentBase<UpdateModel, UpdateEntity>() {

    @Inject
    lateinit var persistenceManager: UpdatePersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<UpdateEntity> = persistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<UpdateEntity, ListItemUpdateBinding>(R.layout.list_item_update) {
                    onCreate {
                        it
                                .binding
                                .setVariable(BR.presenter, this@UpdatesFragment)
                    }
                    onClick {

                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): Single<List<UpdateModel>> {
        return freeNasWebApiClient
                .getPendingUpdates()
    }

    override fun mapToEntity(it: UpdateModel): UpdateEntity {
        return it
                .asEntity()
    }

    override fun loadListDataFromPersistence(): List<UpdateEntity> {
        return super
                .loadListDataFromPersistence()
                .sortedBy {
                    it
                            .name
                            .toLowerCase()
                }
    }

    override fun getRightFabs(): List<FabConfig.Fab> {
        return listOf(FabConfig.Fab(icon = MaterialDesignIconic.Icon.gmi_check, onClick = {
            applyPendingUpdates()
        }))
    }

    private fun applyPendingUpdates() {
        loadingPlugin
                .showLoading()
        freeNasWebApiClient
                .applyPendingUpdates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    loadingPlugin
                            .showContent()
                }, onError = {
                    loadingPlugin
                            .showError(it)
                })
    }

}