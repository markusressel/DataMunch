package de.markusressel.datamunch.view.fragment.storage.scrubs

import com.github.nitrico.lastadapter.LastAdapter
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.ScrubPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.EntityTypeId
import de.markusressel.datamunch.data.persistence.entity.ScrubEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemScrubBinding
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.storage.scrub.ScrubModel
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class ScrubsFragment : ListFragmentBase<ScrubModel, ScrubEntity>() {

    @Inject
    lateinit var persistenceManager: ScrubPersistenceManager

    override val entityTypeId: Long
        get() = EntityTypeId.Scrub.id

    override fun getPersistenceHandler(): PersistenceManagerBase<ScrubEntity> = persistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<ScrubEntity, ListItemScrubBinding>(R.layout.list_item_scrub) {
                    onCreate {
                        it
                                .binding
                                .presenter = this@ScrubsFragment
                    }
                    onClick {
                        openDetailView(listValues[it.adapterPosition])
                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): Single<List<ScrubModel>> {
        return freeNasWebApiClient
                .getScrubs()
    }

    override fun mapToEntity(it: ScrubModel): ScrubEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<ScrubEntity>> {
        return listOf(SortOption.SCRUB_VOLUME)
    }

    override fun getRightFabs(): List<FabConfig.Fab> {
        return listOf(FabConfig.Fab(description = "Add", icon = MaterialDesignIconic.Icon.gmi_plus,
                                    onClick = {
                                        openAddDialog()
                                    }))
    }

    private fun openAddDialog() {

    }

    private fun openDetailView(scrub: ScrubEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(ScrubDetailActivity::class.java, it, scrub.entityId)
                    startActivity(intent)
                }
    }

}