package de.markusressel.datamunch.view.fragment

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.UserPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.UserEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemUserBinding
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class UsersFragment : ListFragmentBase<UserEntity>() {

    @Inject
    lateinit var userPersistenceManager: UserPersistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<UserEntity, ListItemUserBinding>(R.layout.list_item_user) {
                    onCreate {
                        it
                                .binding
                                .presenter = this@UsersFragment
                    }
                    onClick {
                        openDetailView(listValues[it.adapterPosition])
                    }
                }
                .into(recyclerView)
    }

    override fun onListViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun loadListDataFromSource(): List<UserEntity> {
        return freeNasWebApiClient
                .getUsers()
                .blockingGet()
                .map {
                    it
                            .asEntity()
                }
    }

    override fun getPersistenceHandler(): PersistenceManagerBase<UserEntity> {
        return userPersistenceManager
    }

    override fun loadListDataFromPersistence(): List<UserEntity> {
        return super
                .loadListDataFromPersistence()
                .sortedBy {
                    it
                            .bsdusr_username
                            .toLowerCase()
                }
    }

    private fun openDetailView(user: UserEntity) {

    }

}