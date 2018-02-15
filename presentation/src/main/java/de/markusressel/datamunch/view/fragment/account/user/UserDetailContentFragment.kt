package de.markusressel.datamunch.view.fragment.account.user

import android.os.Bundle
import android.view.View
import com.github.florent37.materialviewpager.MaterialViewPagerHelper
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.UserPersistenceManager
import de.markusressel.datamunch.view.activity.base.DetailActivityBase.Companion.KEY_ENTITY_ID
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import kotlinx.android.synthetic.main.content_user_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class UserDetailContentFragment : DaggerSupportFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.content_user_detail

    @Inject
    protected lateinit var userPersistenceManager: UserPersistenceManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        MaterialViewPagerHelper
                .registerScrollView(activity, scrollView)

        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val args = arguments
                ?: return

        val entityId: Long = args
                .getLong(KEY_ENTITY_ID)

        val entity = userPersistenceManager
                .standardOperation()
                .get(entityId)

        userIdTextView
                .text = "${entity.bsdusr_uid}"

        groupIdTextView
                .text = "${entity.bsdusr_group}"

        usernameTextView
                .text = entity
                .bsdusr_username

        fullNameEditText
                .setText(entity.bsdusr_full_name)

        passwordEditText
                .setText("********")

        emailEditText
                .setText(entity.bsdusr_email)

        homeDirectoryTextView
                .text = entity
                .bsdusr_home

        shellEditText
                .setText(entity.bsdusr_shell)

        lockedCheckBox
                .isChecked = entity
                .bsdusr_locked

        sudoCheckBox
                .isChecked = entity
                .bsdusr_sudo
    }

}