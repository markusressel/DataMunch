package de.markusressel.datamunch.view.fragment.storage.task

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.TaskPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.TaskEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import javax.inject.Inject


/**
 * Created by Markus on 15.02.2018.
 */
class TaskDetailActivity : DetailActivityBase<TaskEntity>() {

    @Inject
    lateinit var persistenceHandler: TaskPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<TaskEntity> = persistenceHandler

    override val headerTextString: String
        get() = "Task"

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() = listOf(R.string.details to ::TaskDetailContentFragment)


}