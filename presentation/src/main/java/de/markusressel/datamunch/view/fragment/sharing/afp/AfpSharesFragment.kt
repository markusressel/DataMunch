package de.markusressel.datamunch.view.fragment.sharing.afp

import com.github.nitrico.lastadapter.LastAdapter
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.AfpSharePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.AfpShareEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemAfpShareBinding
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.freenasrestapiclient.library.sharing.afp.AfpShareModel
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class AfpSharesFragment : ListFragmentBase<AfpShareModel, AfpShareEntity>() {
    @Inject
    lateinit var persistenceManager: AfpSharePersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<AfpShareEntity> = persistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<AfpShareEntity, ListItemAfpShareBinding>(R.layout.list_item_afp_share) {
                    onCreate {
                        it
                                .binding
                                .setVariable(BR.presenter, this@AfpSharesFragment)
                    }
                    onClick {
                        openDetailView(listValues[it.adapterPosition])
                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): Single<List<AfpShareModel>> {
        return freeNasWebApiClient
                .getAfpShares()
    }

    override fun mapToEntity(it: AfpShareModel): AfpShareEntity {
        return it
                .asEntity()
    }

    override fun loadListDataFromPersistence(): List<AfpShareEntity> {
        return super
                .loadListDataFromPersistence()
                .sortedBy {
                    it
                            .afp_name
                            .toLowerCase()
                }
    }

    override fun getRightFabs(): List<FabConfig.Fab> {
        return listOf(FabConfig.Fab(description = "Add", icon = MaterialDesignIconic.Icon.gmi_plus,
                                    onClick = {
            openAddView()
        }))
    }

    private fun openAddView() {
    }

    private fun openDetailView(share: AfpShareEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(AfpShareDetailActivity::class.java, it,
                                               share.entityId)
                    startActivity(intent)
                }
    }

}