package de.markusressel.datamunch.view.fragment.account.user

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.UserPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.UserEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemUserBinding
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.freenaswebapiclient.account.user.UserModel
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class UsersFragment : ListFragmentBase<UserModel, UserEntity>() {

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

    override fun loadListDataFromSource(): Single<List<UserModel>> {
        return freeNasWebApiClient
                .getUsers()
    }

    override fun mapToPersistenceEntity(it: UserModel): UserEntity {
        return it
                .asEntity()
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

    override fun getRightFabs(): List<FabConfig.Fab> {
        return listOf(FabConfig.Fab(icon = MaterialDesignIconic.Icon.gmi_plus, onClick = {
            openAddDialog()
        }))
    }

    private fun openAddDialog() {

    }

    private fun openDetailView(user: UserEntity) {
        context
                ?.let {
                    startActivity(UserDetailFragment.newInstance(it, user))
                }
    }

}