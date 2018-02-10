package de.markusressel.datamunch.view.fragment

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.ServicePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.ServiceEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemServiceBinding
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class ServicesFragment : ListFragmentBase<ServiceEntity>() {

    @Inject
    lateinit var servicePersistenceManager: ServicePersistenceManager

    override val isAddable: Boolean
        get() = false

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<ServiceEntity, ListItemServiceBinding>(R.layout.list_item_service) {
                    onCreate {
                        it
                                .binding
                                .setVariable(BR.presenter, this@ServicesFragment)
                    }
                    onClick {

                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): List<ServiceEntity> {
        return freeNasWebApiClient
                .getServices()
                .blockingGet()
                .map {
                    it
                            .asEntity()
                }
    }

    override fun getPersistenceHandler(): PersistenceManagerBase<ServiceEntity> {
        return servicePersistenceManager
    }

    override fun loadListDataFromPersistence(): List<ServiceEntity> {
        return super
                .loadListDataFromPersistence()
                .sortedBy {
                    it
                            .srv_service
                            .toLowerCase()
                }
    }

    override fun onListViewCreated(view: View, savedInstanceState: Bundle?) {
    }

}