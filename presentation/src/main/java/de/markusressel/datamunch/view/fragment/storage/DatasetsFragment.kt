package de.markusressel.datamunch.view.fragment.storage

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.DatasetPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.DatasetEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemDatasetBinding
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.freenaswebapiclient.storage.dataset.DatasetModel
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class DatasetsFragment : ListFragmentBase<DatasetModel, DatasetEntity>() {

    @Inject
    lateinit var datasetPersistenceManager: DatasetPersistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<DatasetEntity, ListItemDatasetBinding>(R.layout.list_item_dataset) {
                    onCreate {
                        it
                                .binding
                                .presenter = this@DatasetsFragment
                    }
                    onClick {
                        openDetailView(listValues[it.adapterPosition])
                    }
                }
                .into(recyclerView)
    }

    override fun onListViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun loadListDataFromSource(): Single<List<DatasetModel>> {
        return freeNasWebApiClient
                .getDatasets()
    }

    override fun mapToPersistenceEntity(it: DatasetModel): DatasetEntity {
        return it
                .asEntity()
    }

    override fun getPersistenceHandler(): PersistenceManagerBase<DatasetEntity> {
        return datasetPersistenceManager
    }

    override fun loadListDataFromPersistence(): List<DatasetEntity> {
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

    private fun openDetailView(dataset: DatasetEntity) {
    }

}