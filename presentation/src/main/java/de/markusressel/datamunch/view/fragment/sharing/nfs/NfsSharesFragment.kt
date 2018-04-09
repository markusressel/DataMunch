package de.markusressel.datamunch.view.fragment.sharing.nfs

import com.github.nitrico.lastadapter.LastAdapter
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.NfsSharePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.NfsShareEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemNfsShareBinding
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.sharing.nfs.NfsShareModel
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class NfsSharesFragment : ListFragmentBase<NfsShareModel, NfsShareEntity>() {

    @Inject
    lateinit var persistenceManager: NfsSharePersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<NfsShareEntity> = persistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<NfsShareEntity, ListItemNfsShareBinding>(R.layout.list_item_nfs_share) {
                    onCreate {
                        it
                                .binding
                                .setVariable(BR.presenter, this@NfsSharesFragment)
                    }
                    onClick {
                        openDetailView(listValues[it.adapterPosition])
                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): Single<List<NfsShareModel>> {
        return freeNasWebApiClient
                .getNfsShares()
    }

    override fun mapToEntity(it: NfsShareModel): NfsShareEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<NfsShareEntity>> {
        return listOf(
                createSortOption(
                        R.string.id,
                        {
                            it
                                    .id
                        }))
    }

    override fun getRightFabs(): List<FabConfig.Fab> {
        return listOf(FabConfig.Fab(description = "Add", icon = MaterialDesignIconic.Icon.gmi_plus,
                                    onClick = {
                                        openAddDialog()
                                    }))
    }

    private fun openAddDialog() {

    }

    private fun openDetailView(share: NfsShareEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(NfsShareDetailActivity::class.java, it,
                                               share.entityId)
                    startActivity(intent)
                }
    }

}