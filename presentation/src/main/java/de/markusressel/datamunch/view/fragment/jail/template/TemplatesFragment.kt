package de.markusressel.datamunch.view.fragment.jail.template

import com.github.nitrico.lastadapter.LastAdapter
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.TemplatePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.TemplateEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemTemplateBinding
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.jails.template.TemplateModel
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class TemplatesFragment : ListFragmentBase<TemplateModel, TemplateEntity>() {

    @Inject
    lateinit var persistenceManager: TemplatePersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<TemplateEntity> = persistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<TemplateEntity, ListItemTemplateBinding>(R.layout.list_item_template) {
                    onCreate {
                        it
                                .binding
                                .presenter = this@TemplatesFragment
                    }
                    onClick {
                        openDetailView(listValues[it.adapterPosition])
                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): Single<List<TemplateModel>> {
        return freeNasWebApiClient
                .getTemplates()
    }

    override fun mapToEntity(it: TemplateModel): TemplateEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<TemplateEntity>> {
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

    private fun openDetailView(template: TemplateEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(TemplateDetailActivity::class.java, it,
                                               template.entityId)
                    startActivity(intent)
                }
    }

    private fun openAddView() {
    }

}