package de.markusressel.datamunch.view.fragment.storage.task

import com.github.nitrico.lastadapter.LastAdapter
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.TaskPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.TaskEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemTaskBinding
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.freenaswebapiclient.storage.task.TaskModel
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class TasksFragment : ListFragmentBase<TaskModel, TaskEntity>() {

    @Inject
    lateinit var persistenceManager: TaskPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<TaskEntity> = persistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<TaskEntity, ListItemTaskBinding>(R.layout.list_item_task) {
                    onCreate {
                        it
                                .binding
                                .presenter = this@TasksFragment
                    }
                    onClick {
                        openDetailView(listValues[it.adapterPosition])
                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): Single<List<TaskModel>> {
        return freeNasWebApiClient
                .getTasks()
    }

    override fun mapToEntity(it: TaskModel): TaskEntity {
        return it
                .asEntity()
    }

    override fun loadListDataFromPersistence(): List<TaskEntity> {
        return super
                .loadListDataFromPersistence()
                .sortedBy {
                    it
                            .task_filesystem
                            .toLowerCase()
                }
    }

    override fun getRightFabs(): List<FabConfig.Fab> {
        return listOf(FabConfig.Fab(icon = MaterialDesignIconic.Icon.gmi_plus, onClick = {
            openAddDialog()
        }))
    }

    private fun openAddDialog() {

    }

    private fun openDetailView(task: TaskEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(TaskDetailActivity::class.java, it, task.entityId)
                    startActivity(intent)
                }
    }

}