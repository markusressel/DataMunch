package de.markusressel.datamunch.view.fragment.jail

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.MountpointPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.MountpointEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemMountpointBinding
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class MountpointsFragment : ListFragmentBase<MountpointEntity>() {

    @Inject
    lateinit var mountpointPersistenceManager: MountpointPersistenceManager

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

    override fun onListViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun loadListDataFromSource(): List<MountpointEntity> {
        return freeNasWebApiClient
                .getMountpoints()
                .blockingGet()
                .map {
                    it
                            .asEntity()
                }
    }

    override fun getPersistenceHandler(): PersistenceManagerBase<MountpointEntity> {
        return mountpointPersistenceManager
    }

    override fun loadListDataFromPersistence(): List<MountpointEntity> {
        return super
                .loadListDataFromPersistence()
                .sortedBy {
                    it
                            .id
                }
    }

    private fun openDetailView(mountpoint: MountpointEntity) {

    }

}