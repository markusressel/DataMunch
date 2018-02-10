package de.markusressel.datamunch.view.fragment

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.CifsSharePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.CifsShareEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemCifsShareBinding
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class CifsSharesFragment : ListFragmentBase<CifsShareEntity>() {

    @Inject
    lateinit var cifsSharePersistenceManager: CifsSharePersistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<CifsShareEntity, ListItemCifsShareBinding>(R.layout.list_item_cifs_share) {
                    onCreate {
                        it
                                .binding
                                .setVariable(BR.presenter, this@CifsSharesFragment)
                    }
                    onClick {

                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): List<CifsShareEntity> {
        return freeNasWebApiClient
                .getCifsShares()
                .blockingGet()
                .map {
                    it
                            .asEntity()
                }
    }

    override fun getPersistenceHandler(): PersistenceManagerBase<CifsShareEntity> {
        return cifsSharePersistenceManager
    }

    override fun loadListDataFromPersistence(): List<CifsShareEntity> {
        return super
                .loadListDataFromPersistence()
                .sortedBy {
                    it
                            .cifs_name
                            .toLowerCase()
                }
    }

    override fun onListViewCreated(view: View, savedInstanceState: Bundle?) {
    }

}