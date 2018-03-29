package de.markusressel.datamunch.view.fragment.jail.template

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.TemplatePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.TemplateEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import kotlinx.android.synthetic.main.content_jails_template_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class TemplateDetailContentFragment : DetailContentFragmentBase<TemplateEntity>() {

    @Inject
    protected lateinit var persistenceManager: TemplatePersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<TemplateEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_jails_template_detail


    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()

        idTextView
                .text = "${entity.id}"

        nameTextView
                .text = entity
                .jt_name

        osTextView
                .text = entity
                .jt_os

        instancesTextView
                .text = "${entity.jt_instances}"

        archTextView
                .text = entity
                .jt_arch

        urlTextView
                .text = entity
                .jt_url
    }

}