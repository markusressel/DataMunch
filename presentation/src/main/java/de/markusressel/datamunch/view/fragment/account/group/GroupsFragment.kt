package de.markusressel.datamunch.view.fragment.account.group

import com.github.nitrico.lastadapter.LastAdapter
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.GroupPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.GroupEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemGroupBinding
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.freenaswebapiclient.account.group.GroupModel
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class GroupsFragment : ListFragmentBase<GroupModel, GroupEntity>() {

    @Inject
    lateinit var groupPersistenceManager: GroupPersistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<GroupEntity, ListItemGroupBinding>(R.layout.list_item_group) {
                    onCreate {
                        it
                                .binding
                                .presenter = this@GroupsFragment
                    }
                    onClick {
                        openDetailView(listValues[it.adapterPosition])
                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): Single<List<GroupModel>> {
        return freeNasWebApiClient
                .getGroups()
    }

    override fun mapToPersistenceEntity(it: GroupModel): GroupEntity {
        return it
                .asEntity()
    }

    override fun getPersistenceHandler(): PersistenceManagerBase<GroupEntity> {
        return groupPersistenceManager
    }

    override fun loadListDataFromPersistence(): List<GroupEntity> {
        return super
                .loadListDataFromPersistence()
                .sortedBy {
                    it
                            .bsdgrp_group
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

    private fun openDetailView(group: GroupEntity) {

    }

}