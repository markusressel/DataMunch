package de.markusressel.datamunch.view.fragment.storage.snapshot

import com.github.nitrico.lastadapter.LastAdapter
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.SnapshotPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.SnapshotEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemSnapshotBinding
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.storage.snapshot.SnapshotModel
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class SnapshotsFragment : ListFragmentBase<SnapshotModel, SnapshotEntity>() {

    @Inject
    lateinit var persistenceManager: SnapshotPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<SnapshotEntity> = persistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<SnapshotEntity, ListItemSnapshotBinding>(R.layout.list_item_snapshot) {
                    onCreate {
                        it
                                .binding
                                .presenter = this@SnapshotsFragment
                    }
                    onClick {
                        openDetailView(listValues[it.adapterPosition])
                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): Single<List<SnapshotModel>> {
        return freeNasWebApiClient
                .getSnapshots(limit = 1000)
    }

    override fun mapToEntity(it: SnapshotModel): SnapshotEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<SnapshotEntity>> {
        return listOf(
                createSortOption(
                        R.string.name,
                        {
                            it
                                    .name
                                    .toLowerCase()
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

    private fun openDetailView(volume: SnapshotEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(SnapshotDetailActivity::class.java, it,
                                               volume.entityId)
                    startActivity(intent)
                }
    }

}