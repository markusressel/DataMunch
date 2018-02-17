package de.markusressel.datamunch.view.fragment.account.user

import com.jakewharton.rxbinding2.widget.RxTextView
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.UserPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.UserEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.content_user_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class UserDetailContentFragment : DetailContentFragmentBase<UserEntity>() {

    @Inject
    protected lateinit var persistenceManager: UserPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<UserEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_user_detail


    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()
                ?: return

        userIdTextView
                .text = "${entity.bsdusr_uid}"

        groupIdTextView
                .text = "${entity.bsdusr_group}"

        usernameTextView
                .text = entity
                .bsdusr_username

        fullNameEditText
                .setText(entity.bsdusr_full_name)
        RxTextView
                .textChanges(fullNameEditText)
                .bindToLifecycle(fullNameEditText)
                .subscribeBy(onNext = {
                    val entityCopy = entity
                            .copy(bsdusr_full_name = it.toString())

                    storeModifiedEntity(entityCopy)
                })

        passwordEditText
                .setText("")
        RxTextView
                .textChanges(passwordEditText)
                .bindToLifecycle(passwordEditText)
                .subscribeBy(onNext = {

                })


        emailEditText
                .setText(entity.bsdusr_email)
        RxTextView
                .textChanges(emailEditText)
                .bindToLifecycle(emailEditText)
                .subscribeBy(onNext = {
                    val entityCopy = entity
                            .copy(bsdusr_email = it.toString())

                    storeModifiedEntity(entityCopy)
                })

        homeDirectoryTextView
                .text = entity
                .bsdusr_home

        shellEditText
                .setText(entity.bsdusr_shell)
        RxTextView
                .textChanges(shellEditText)
                .bindToLifecycle(shellEditText)
                .subscribeBy(onNext = {
                    val entityCopy = entity
                            .copy(bsdusr_email = it.toString())

                    storeModifiedEntity(entityCopy)
                })

        lockedCheckBox
                .isChecked = entity
                .bsdusr_locked

        sudoCheckBox
                .isChecked = entity
                .bsdusr_sudo
    }

}