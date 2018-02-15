package de.markusressel.datamunch.view.fragment.account.user

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.UserPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.UserEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import javax.inject.Inject


/**
 * Created by Markus on 15.02.2018.
 */
class UserDetailActivity : DetailActivityBase<UserEntity>() {

    @Inject
    protected lateinit var userPersistenceHandler: UserPersistenceManager

    override val headerTextString: String
        get() {
            val entity = getEntity()
            if (!entity.bsdusr_builtin && entity.bsdusr_full_name.isNotEmpty()) {
                return entity
                        .bsdusr_full_name
            }

            return "${getString(R.string.user)}: ${entity.bsdusr_username}"
        }

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() = listOf(R.string.details to ::UserDetailContentFragment)

    override fun getPersistenceHandler(): PersistenceManagerBase<UserEntity> {
        return userPersistenceHandler
    }
}