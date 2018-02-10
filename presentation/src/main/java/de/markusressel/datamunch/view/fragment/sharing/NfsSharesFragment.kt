package de.markusressel.datamunch.view.fragment.sharing

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.NfsSharePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.NfsShareEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemNfsShareBinding
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class NfsSharesFragment : ListFragmentBase<NfsShareEntity>() {

    @Inject
    lateinit var nfsSharePersistenceManager: NfsSharePersistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<NfsShareEntity, ListItemNfsShareBinding>(R.layout.list_item_nfs_share) {
                    onCreate {
                        it
                                .binding
                                .setVariable(BR.presenter, this@NfsSharesFragment)
                    }
                    onClick {

                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): List<NfsShareEntity> {
        return freeNasWebApiClient
                .getNfsShares()
                .blockingGet()
                .map {
                    it
                            .asEntity()
                }
    }

    override fun getPersistenceHandler(): PersistenceManagerBase<NfsShareEntity> {
        return nfsSharePersistenceManager
    }

    override fun loadListDataFromPersistence(): List<NfsShareEntity> {
        return super
                .loadListDataFromPersistence()
                .sortedBy {
                    it
                            .id
                }
    }

    override fun onListViewCreated(view: View, savedInstanceState: Bundle?) {
    }

}