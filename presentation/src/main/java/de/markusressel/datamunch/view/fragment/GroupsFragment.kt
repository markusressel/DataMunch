package de.markusressel.datamunch.view.fragment

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.GroupPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.GroupEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemGroupBinding
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class GroupsFragment : ListFragmentBase<GroupEntity>() {

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
                .into(recyclerview)
    }

    override fun onListViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun loadListDataFromSource(): List<GroupEntity> {
        return freeNasWebApiClient
                .getGroups()
                .blockingGet()
                .map {
                    it
                            .asEntity()
                }
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

    private fun openDetailView(group: GroupEntity) {

    }

}