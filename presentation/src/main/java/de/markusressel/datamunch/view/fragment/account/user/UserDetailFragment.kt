package de.markusressel.datamunch.view.fragment.account.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.entity.UserEntity
import de.markusressel.datamunch.view.activity.base.DaggerSupportActivityBase
import kotlinx.android.synthetic.main.fragment_user_detail.*

/**
 * Created by Markus on 15.02.2018.
 */
class UserDetailFragment : DaggerSupportActivityBase() {

    override val style: Int
        get() = DEFAULT

    override val layoutRes: Int
        get() = R.layout.fragment_user_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        setAdapter()
    }

    private fun setAdapter() {
        val entityId = intent
                .getLongExtra(KEY_ENTITY_ID, -1)

        val viewPager = materialViewPager
                .viewPager
        viewPager
                .adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return UserDetailContentFragment
                        .newInstance(entityId)
            }

            override fun getCount(): Int {
                return 1
            }

        }

        //After set an adapter to the ViewPager
        materialViewPager
                .pagerTitleStrip
                .setViewPager(materialViewPager.viewPager)
    }

    companion object {
        const val KEY_ENTITY_ID = "entity_id"

        fun newInstance(context: Context, user: UserEntity): Intent {
            return Intent(context, UserDetailFragment::class.java)
                    .apply {
                        putExtra(KEY_ENTITY_ID, user.entityId)
                    }
        }
    }

}