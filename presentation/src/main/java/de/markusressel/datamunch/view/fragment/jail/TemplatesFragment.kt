package de.markusressel.datamunch.view.fragment.jail

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.TemplatePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.TemplateEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemTemplateBinding
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.freenaswebapiclient.jails.template.TemplateModel
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
    lateinit var templatePersistenceManager: TemplatePersistenceManager

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

    override fun onListViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun loadListDataFromSource(): Single<List<TemplateModel>> {
        return freeNasWebApiClient
                .getTemplates()
    }

    override fun mapToPersistenceEntity(it: TemplateModel): TemplateEntity {
        return it
                .asEntity()
    }

    override fun getPersistenceHandler(): PersistenceManagerBase<TemplateEntity> {
        return templatePersistenceManager
    }

    override fun loadListDataFromPersistence(): List<TemplateEntity> {
        return super
                .loadListDataFromPersistence()
                .sortedBy {
                    it
                            .id
                }
    }

    override fun getRightFabs(): List<FabConfig.Fab> {
        return listOf(FabConfig.Fab(icon = MaterialDesignIconic.Icon.gmi_plus, onClick = {
            openAddView()
        }))
    }

    private fun openDetailView(template: TemplateEntity) {

    }

    private fun openAddView() {
    }

}