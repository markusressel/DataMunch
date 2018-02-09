package de.markusressel.datamunch.view.fragment

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.VolumePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.VolumeEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemVolumeBinding
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class VolumesFragment : ListFragmentBase<VolumeEntity>() {

    @Inject
    lateinit var volumePersistenceManager: VolumePersistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<VolumeEntity, ListItemVolumeBinding>(R.layout.list_item_volume) {
                    onCreate {
                        it
                                .binding
                                .presenter = this@VolumesFragment
                    }
                    onClick {
                        openDetailView(listValues[it.adapterPosition])
                    }
                }
                .into(recyclerview)
    }

    override fun onListViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun loadListDataFromSource(): List<VolumeEntity> {
        val volumesAndChildren: List<VolumeEntity> = freeNasWebApiClient
                .getVolumes()
                .blockingGet()
                .map {
                    it
                            .asEntity()
                }
                .flatMap {
                    val list = mutableListOf(it.first)
                    list
                            .addAll(it.second.map {
                                it
                                        .asEntity()
                            })
                    return list
                }

        return volumesAndChildren
    }

    override fun getPersistenceHandler(): PersistenceManagerBase<VolumeEntity> {
        return volumePersistenceManager
    }

    override fun loadListDataFromPersistence(): List<VolumeEntity> {
        return super
                .loadListDataFromPersistence()
                .sortedBy {
                    it
                            .name
                            .toLowerCase()
                }
    }

    private fun openDetailView(volume: VolumeEntity) {

    }

}