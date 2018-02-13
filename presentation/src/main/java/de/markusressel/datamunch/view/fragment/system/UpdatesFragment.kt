package de.markusressel.datamunch.view.fragment.system

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.UpdatePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.UpdateEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemUpdateBinding
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class UpdatesFragment : ListFragmentBase<UpdateEntity>() {

    @Inject
    lateinit var updatePersistenceManager: UpdatePersistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<UpdateEntity, ListItemUpdateBinding>(R.layout.list_item_update) {
                    onCreate {
                        it
                                .binding
                                .setVariable(BR.presenter, this@UpdatesFragment)
                    }
                    onClick {

                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): List<UpdateEntity> {
        return freeNasWebApiClient
                .getPendingUpdates()
                .blockingGet()
                .map {
                    it
                            .asEntity()
                }
    }

    override fun getPersistenceHandler(): PersistenceManagerBase<UpdateEntity> {
        return updatePersistenceManager
    }

    override fun loadListDataFromPersistence(): List<UpdateEntity> {
        return super
                .loadListDataFromPersistence()
                .sortedBy {
                    it
                            .name
                            .toLowerCase()
                }
    }

    override fun onListViewCreated(view: View, savedInstanceState: Bundle?) {
    }

}