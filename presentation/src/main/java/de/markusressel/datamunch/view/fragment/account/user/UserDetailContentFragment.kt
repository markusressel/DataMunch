package de.markusressel.datamunch.view.fragment.account.user

import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding2.widget.RxTextView
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.UserPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.UserEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.content_accounts_user_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class UserDetailContentFragment : DetailContentFragmentBase<UserEntity>() {

    @Inject
    protected lateinit var persistenceManager: UserPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<UserEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_accounts_user_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        RxTextView
                .textChanges(fullNameEditText)
                .bindToLifecycle(fullNameEditText)
                .subscribeBy(onNext = {
                    val entityCopy = getEntityFromPersistence()
                            .copy(bsdusr_full_name = it.toString())

                    storeModifiedEntity(entityCopy)
                })

        RxTextView
                .textChanges(passwordEditText)
                .bindToLifecycle(passwordEditText)
                .subscribeBy(onNext = {

                })

        RxTextView
                .textChanges(emailEditText)
                .bindToLifecycle(emailEditText)
                .subscribeBy(onNext = {
                    val entityCopy = getEntityFromPersistence()
                            .copy(bsdusr_email = it.toString())

                    storeModifiedEntity(entityCopy)
                })

        RxTextView
                .textChanges(shellEditText)
                .bindToLifecycle(shellEditText)
                .subscribeBy(onNext = {
                    val entityCopy = getEntityFromPersistence()
                            .copy(bsdusr_email = it.toString())

                    storeModifiedEntity(entityCopy)
                })
    }


    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()

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
                .setText("")

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