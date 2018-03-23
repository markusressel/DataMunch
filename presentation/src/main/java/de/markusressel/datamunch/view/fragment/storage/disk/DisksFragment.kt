package de.markusressel.datamunch.view.fragment.storage.disk

import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.DiskPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.DiskEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemDiskBinding
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.freenaswebapiclient.storage.disk.DiskModel
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class DisksFragment : ListFragmentBase<DiskModel, DiskEntity>() {

    @Inject
    lateinit var persistenceManager: DiskPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<DiskEntity> = persistenceManager

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

    override fun loadListDataFromSource(): Single<List<DiskModel>> {
        return freeNasWebApiClient
                .getDisks()
    }

    override fun mapToEntity(it: DiskModel): DiskEntity {
        return it
                .asEntity()
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
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(DiskDetailActivity::class.java, it, disk.entityId)
                    startActivity(intent)
                }
    }

}