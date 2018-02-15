package de.markusressel.datamunch.view.fragment.storage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.github.nitrico.lastadapter.LastAdapter
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.VolumePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.VolumeEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemVolumeBinding
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.freenaswebapiclient.storage.volume.VolumeModel
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class VolumesFragment : ListFragmentBase<VolumeModel, VolumeEntity>() {
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
                .into(recyclerView)
    }

    override fun onListViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun loadListDataFromSource(): Single<List<VolumeModel>> {
        return freeNasWebApiClient
                .getVolumes()
    }

    override fun mapToPersistenceEntity(it: VolumeModel): VolumeEntity {
        return it
                .asEntity()
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

    override fun getRightFabs(): List<FabConfig.Fab> {
        return listOf(FabConfig.Fab(icon = MaterialDesignIconic.Icon.gmi_plus, onClick = {
            openAddDialog()
        }))
    }

    private fun openAddDialog() {

    }

    private fun openDetailView(volume: VolumeEntity) {
        volume
                .childEntities
                .toObservable()
                .subscribeBy(onNext = {
                    Toast
                            .makeText(context, it.name, Toast.LENGTH_LONG)
                            .show()
                })
    }

}