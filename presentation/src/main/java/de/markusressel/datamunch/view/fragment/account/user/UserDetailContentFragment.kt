package de.markusressel.datamunch.view.fragment.account.user

import android.os.Bundle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase

/**
 * Created by Markus on 15.02.2018.
 */
class UserDetailContentFragment : DaggerSupportFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.list_item_user

    companion object {
        fun newInstance(entityId: Long): UserDetailContentFragment {
            val fragment = UserDetailContentFragment()
            val args = Bundle()
            //            args
            //                    .putInt("entityId", entityId)
            fragment
                    .arguments = args
            return fragment
        }
    }

}