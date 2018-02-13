package de.markusressel.datamunch.view.fragment.sharing

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.AfpSharePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.AfpShareEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemAfpShareBinding
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class AfpSharesFragment : ListFragmentBase<AfpShareEntity>() {

    @Inject
    lateinit var afpSharePersistenceManager: AfpSharePersistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<AfpShareEntity, ListItemAfpShareBinding>(R.layout.list_item_afp_share) {
                    onCreate {
                        it
                                .binding
                                .setVariable(BR.presenter, this@AfpSharesFragment)
                    }
                    onClick {

                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): List<AfpShareEntity> {
        return freeNasWebApiClient
                .getAfpShares()
                .blockingGet()
                .map {
                    it
                            .asEntity()
                }
    }

    override fun getPersistenceHandler(): PersistenceManagerBase<AfpShareEntity> {
        return afpSharePersistenceManager
    }

    override fun loadListDataFromPersistence(): List<AfpShareEntity> {
        return super
                .loadListDataFromPersistence()
                .sortedBy {
                    it
                            .afp_name
                            .toLowerCase()
                }
    }

    override fun getRightFabs(): List<FabConfig.Fab> {
        return listOf(FabConfig.Fab(icon = MaterialDesignIconic.Icon.gmi_plus, onClick = {
            openAddView()
        }))
    }

    private fun openAddView() {
    }

    override fun onListViewCreated(view: View, savedInstanceState: Bundle?) {
    }

}