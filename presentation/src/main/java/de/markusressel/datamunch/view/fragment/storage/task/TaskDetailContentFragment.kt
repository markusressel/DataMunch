package de.markusressel.datamunch.view.fragment.storage.task

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.TaskPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.TaskEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import kotlinx.android.synthetic.main.content_storage_task_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class TaskDetailContentFragment : DetailContentFragmentBase<TaskEntity>() {

    @Inject
    protected lateinit var persistenceManager: TaskPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<TaskEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_storage_task_detail


    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()

        nameTextView
                .text = entity
                .task_ret_unit

        filesystemTextView
                .text = entity
                .task_filesystem

    }

}