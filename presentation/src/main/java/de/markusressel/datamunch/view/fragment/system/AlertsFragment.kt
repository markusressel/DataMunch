package de.markusressel.datamunch.view.fragment.system

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.AlertPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.AlertEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemAlertBinding
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class AlertsFragment : ListFragmentBase<AlertEntity>() {

    @Inject
    lateinit var alertPersistenceManager: AlertPersistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<AlertEntity, ListItemAlertBinding>(R.layout.list_item_alert) {
                    onCreate {
                        it
                                .binding
                                .setVariable(BR.presenter, this@AlertsFragment)
                    }
                    onClick {

                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): List<AlertEntity> {
        return freeNasWebApiClient
                .getSystemAlerts()
                .blockingGet()
                .map {
                    it
                            .asEntity()
                }
    }

    override fun getPersistenceHandler(): PersistenceManagerBase<AlertEntity> {
        return alertPersistenceManager
    }

    override fun loadListDataFromPersistence(): List<AlertEntity> {
        return super
                .loadListDataFromPersistence()
                .sortedWith(compareBy({
                                          it
                                                  .dismissed
                                      }, {
                                          it
                                                  .level
                                      }, {
                                          it
                                                  .message
                                      }))
    }

    override fun onListViewCreated(view: View, savedInstanceState: Bundle?) {
    }

}