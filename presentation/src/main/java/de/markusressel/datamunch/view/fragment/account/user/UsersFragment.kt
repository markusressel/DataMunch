package de.markusressel.datamunch.view.fragment.account.user

import com.github.nitrico.lastadapter.LastAdapter
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.UserPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.EntityTypeId
import de.markusressel.datamunch.data.persistence.entity.UserEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemUserBinding
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.account.user.UserModel
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
    lateinit var persistenceManager: UserPersistenceManager

    override val entityTypeId: Long
        get() = EntityTypeId.User.id

    override fun getPersistenceHandler(): PersistenceManagerBase<UserEntity> = persistenceManager

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

    override fun loadListDataFromSource(): Single<List<UserModel>> {
        return freeNasWebApiClient
                .getUsers()
    }

    override fun mapToEntity(it: UserModel): UserEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<UserEntity>> {
        return listOf(SortOption.USER_NAME)
    }

    override fun getRightFabs(): List<FabConfig.Fab> {
        return listOf(FabConfig.Fab(description = "Add", icon = MaterialDesignIconic.Icon.gmi_plus,
                                    onClick = {
                                        openAddDialog()
                                    }))
    }

    private fun openAddDialog() {

    }

    private fun openDetailView(user: UserEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(UserDetailActivity::class.java, it, user.entityId)
                    startActivity(intent)
                }
    }

}