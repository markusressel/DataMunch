package de.markusressel.datamunch.view.fragment

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.TemplatePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.TemplateEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemTemplateBinding
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class TemplatesFragment : ListFragmentBase<TemplateEntity>() {

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
                .into(recyclerview)
    }

    override fun onListViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun loadListDataFromSource(): List<TemplateEntity> {
        return freeNasWebApiClient
                .getTemplates()
                .blockingGet()
                .map {
                    it
                            .asEntity()
                }
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

    private fun openDetailView(template: TemplateEntity) {

    }

}