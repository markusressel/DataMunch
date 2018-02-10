package de.markusressel.datamunch.view.fragment.storage

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.DiskPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.DiskEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemDiskBinding
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class DisksFragment : ListFragmentBase<DiskEntity>() {

    @Inject
    lateinit var diskPersistenceManager: DiskPersistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<DiskEntity, ListItemDiskBinding>(R.layout.list_item_disk) {
                    onCreate {
                        it
                                .binding
                                .presenter = this@DisksFragment
                    }
                    onClick {
                        openDetailView(listValues[it.adapterPosition])
                    }
                }
                .into(recyclerView)
    }

    override fun onListViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun loadListDataFromSource(): List<DiskEntity> {
        return freeNasWebApiClient
                .getDisks()
                .blockingGet()
                .map {
                    it
                            .asEntity()
                }
    }

    override fun getPersistenceHandler(): PersistenceManagerBase<DiskEntity> {
        return diskPersistenceManager
    }

    override fun loadListDataFromPersistence(): List<DiskEntity> {
        return super
                .loadListDataFromPersistence()
                .sortedBy {
                    it
                            .disk_name
                            .toLowerCase()
                }
    }

    private fun openDetailView(disk: DiskEntity) {
    }

}