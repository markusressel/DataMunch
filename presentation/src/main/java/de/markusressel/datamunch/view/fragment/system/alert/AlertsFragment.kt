package de.markusressel.datamunch.view.fragment.system.alert

import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.AlertPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.AlertEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemAlertBinding
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.system.alert.AlertModel
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class AlertsFragment : ListFragmentBase<AlertModel, AlertEntity>() {

    @Inject
    lateinit var persistenceManager: AlertPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<AlertEntity> = persistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<AlertEntity, ListItemAlertBinding>(R.layout.list_item_alert) {
                    onCreate {
                        it
                                .binding
                                .setVariable(BR.presenter, this@AlertsFragment)
                    }
                    onClick {
                        openDetailView(listValues[it.adapterPosition])
                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): Single<List<AlertModel>> {
        return freeNasWebApiClient
                .getSystemAlerts()
    }

    override fun mapToEntity(it: AlertModel): AlertEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<AlertEntity>> {
        return listOf(
                createSortOption(
                        R.string.dismissed,
                        {
                            it
                                    .dismissed
                        }),
                createSortOption(
                        R.string.level,
                        {
                            it
                                    .level
                                    .toLowerCase()
                        }),
                createSortOption(
                        R.string.message,
                        {
                            it
                                    .message
                                    .toLowerCase()
                        }))
    }

    private fun openDetailView(alert: AlertEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(AlertDetailActivity::class.java, it, alert.entityId)
                    startActivity(intent)
                }
    }

}