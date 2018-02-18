package de.markusressel.datamunch.view.fragment.jail.jail

import com.github.nitrico.lastadapter.LastAdapter
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.JailPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.JailEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemJailBinding
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.freenaswebapiclient.jails.jail.JailModel
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class JailsFragment : ListFragmentBase<JailModel, JailEntity>() {

    @Inject
    lateinit var persistenceManager: JailPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<JailEntity> = persistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<JailEntity, ListItemJailBinding>(R.layout.list_item_jail) {
                    onCreate {
                        it
                                .binding
                                .presenter = this@JailsFragment
                    }
                    onClick {
                        openDetailView(listValues[it.adapterPosition])
                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): Single<List<JailModel>> {
        return freeNasWebApiClient
                .getJails()
    }

    override fun mapToEntity(it: JailModel): JailEntity {
        return it
                .asEntity()
    }

    override fun loadListDataFromPersistence(): List<JailEntity> {
        return super
                .loadListDataFromPersistence()
                .sortedBy {
                    it
                            .jail_host
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

    private fun openDetailView(jail: JailEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(JailDetailActivity::class.java, it, jail.entityId)
                    startActivity(intent)
                }
    }

}