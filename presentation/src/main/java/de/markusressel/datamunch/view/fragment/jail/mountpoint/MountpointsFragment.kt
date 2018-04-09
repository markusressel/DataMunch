package de.markusressel.datamunch.view.fragment.jail.mountpoint

import com.github.nitrico.lastadapter.LastAdapter
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.MountpointPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.MountpointEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemMountpointBinding
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.jails.mountpoint.MountpointModel
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class MountpointsFragment : ListFragmentBase<MountpointModel, MountpointEntity>() {

    @Inject
    lateinit var persistenceManager: MountpointPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<MountpointEntity> = persistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<MountpointEntity, ListItemMountpointBinding>(R.layout.list_item_mountpoint) {
                    onCreate {
                        it
                                .binding
                                .presenter = this@MountpointsFragment
                    }
                    onClick {
                        openDetailView(listValues[it.adapterPosition])
                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): Single<List<MountpointModel>> {
        return freeNasWebApiClient
                .getMountpoints()
    }

    override fun mapToEntity(it: MountpointModel): MountpointEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<MountpointEntity>> {
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
                                        openAddView()
                                    }))
    }

    private fun openAddView() {
    }

    private fun openDetailView(mountpoint: MountpointEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(MountpointDetailActivity::class.java, it,
                                               mountpoint.entityId)
                    startActivity(intent)
                }
    }

}