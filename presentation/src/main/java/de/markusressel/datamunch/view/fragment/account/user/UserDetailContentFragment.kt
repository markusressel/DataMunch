/*
 * DataMunch by Markus Ressel
 * Copyright (c) 2018.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.markusressel.datamunch.view.fragment.account.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxbinding2.widget.RxTextView
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.UserPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.UserEntity
import de.markusressel.datamunch.databinding.ContentAccountsUserDetailBinding
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

    override fun createViewDataBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding? {
        val binding: ContentAccountsUserDetailBinding = DataBindingUtil.inflate(layoutInflater, layoutRes, container, false)
        val viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        viewModel.getEntityLiveData(getPersistenceHandler(), entityId).observe(this, Observer<List<UserEntity>> {
            val entity = it.first()

            viewModel.id.value = entity.id
            viewModel.bsdusr_builtin.value = entity.bsdusr_builtin
            viewModel.bsdusr_email.value = entity.bsdusr_email
            viewModel.bsdusr_full_name.value = entity.bsdusr_full_name
            viewModel.bsdusr_group.value = entity.bsdusr_group
            viewModel.bsdusr_home.value = entity.bsdusr_home
            viewModel.bsdusr_locked.value = entity.bsdusr_locked
            viewModel.bsdusr_password_disabled.value = entity.bsdusr_password_disabled
            viewModel.bsdusr_shell.value = entity.bsdusr_shell
            viewModel.bsdusr_smbhash.value = entity.bsdusr_smbhash
            viewModel.bsdusr_uid.value = entity.bsdusr_uid
            viewModel.bsdusr_sshpubkey.value = entity.bsdusr_sshpubkey
            viewModel.bsdusr_unixhash.value = entity.bsdusr_unixhash
            viewModel.bsdusr_username.value = entity.bsdusr_username
            viewModel.bsdusr_sudo.value = entity.bsdusr_sudo
        })

        binding.let {
            it.setLifecycleOwner(this)
            it.viewModel = viewModel
        }

        return binding
    }

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

}