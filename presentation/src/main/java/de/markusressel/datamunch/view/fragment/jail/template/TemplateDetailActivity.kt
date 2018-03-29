package de.markusressel.datamunch.view.fragment.jail.template

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.TemplatePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.TemplateEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import javax.inject.Inject


/**
 * Created by Markus on 15.02.2018.
 */
class TemplateDetailActivity : DetailActivityBase<TemplateEntity>() {

    @Inject
    lateinit var persistenceHandler: TemplatePersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<TemplateEntity> = persistenceHandler

    override val headerTextString: String
        get() = "${getEntity().id}"

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() = listOf(R.string.details to ::TemplateDetailContentFragment)


}