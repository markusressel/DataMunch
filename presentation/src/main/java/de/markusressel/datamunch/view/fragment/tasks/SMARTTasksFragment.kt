package de.markusressel.datamunch.view.fragment.tasks

import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.SMARTTaskPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.SMARTTaskEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemSmartTaskBinding
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.freenasrestapiclient.library.tasks.smart.SMARTTaskModel
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class SMARTTasksFragment : ListFragmentBase<SMARTTaskModel, SMARTTaskEntity>() {

    @Inject
    lateinit var persistenceManager: SMARTTaskPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<SMARTTaskEntity> = persistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<SMARTTaskEntity, ListItemSmartTaskBinding>(R.layout.list_item_smart_task) {
                    onCreate {
                        it
                                .binding
                                .setVariable(BR.presenter, this@SMARTTasksFragment)
                    }
                    onClick {
                        openDetailView(listValues[it.adapterPosition])
                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): Single<List<SMARTTaskModel>> {
        return freeNasWebApiClient
                .getSMARTTasks()
    }

    override fun mapToEntity(it: SMARTTaskModel): SMARTTaskEntity {
        return it
                .asEntity()
    }

    override fun loadListDataFromPersistence(): List<SMARTTaskEntity> {
        return super
                .loadListDataFromPersistence()
                .sortedWith(compareBy({
                                          it
                                                  .smarttest_type
                                      }, {
                                          it
                                                  .smarttest_desc
                                      }))
    }

    private fun openDetailView(smartTask: SMARTTaskEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(SMARTTaskDetailActivity::class.java, it,
                                               smartTask.entityId)
                    startActivity(intent)
                }
    }

}