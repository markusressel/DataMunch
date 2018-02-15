package de.markusressel.datamunch.view.fragment.account.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import com.github.florent37.materialviewpager.header.HeaderDesign
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.entity.UserEntity
import de.markusressel.datamunch.extensions.random
import de.markusressel.datamunch.view.activity.base.DaggerSupportActivityBase
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail__header_logo.*


/**
 * Created by Markus on 15.02.2018.
 */
class UserDetailActivity : DaggerSupportActivityBase() {

    override val style: Int
        get() = DEFAULT

    override val layoutRes: Int
        get() = R.layout.activity_item_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        setToolbar()
        setAdapter()
        setHeader()
    }

    private fun setHeader() {
        headerText
                .setText(R.string.users)

        materialViewPager
                .setMaterialViewPagerListener {
                    val headerList = listOf(HeaderDesign.fromColorResAndUrl(R.color.blue,
                                                                            "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg"),


                                            HeaderDesign.fromColorResAndUrl(R.color.cyan,
                                                                            "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg"),

                                            HeaderDesign.fromColorResAndUrl(R.color.red,
                                                                            "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg"),

                                            HeaderDesign.fromColorResAndDrawable(
                                                    R.color.md_blue_800,
                                                    getDrawable(R.drawable.header_blue)),

                                            HeaderDesign.fromColorResAndDrawable(
                                                    R.color.md_green_500,
                                                    getDrawable(R.drawable.header_colorful_1)),

                                            HeaderDesign.fromColorResAndDrawable(
                                                    R.color.md_yellow_800,
                                                    getDrawable(R.drawable.header_colorful_2)),
                                            HeaderDesign.fromColorResAndDrawable(R.color.md_red_800,
                                                                                 getDrawable(
                                                                                         R.drawable.header_dark_red))

                    )

                    // select random header image
                    headerList[(0 until headerList.size).random()]
                }
    }

    private fun setToolbar() {
        val toolbar = materialViewPager
                .toolbar
        if (toolbar != null) {
            setSupportActionBar(toolbar)

            val actionBar = supportActionBar
            actionBar!!
                    .setDisplayHomeAsUpEnabled(true)

            //            actionBar
            //                    .setDisplayShowHomeEnabled(true)

            // activity title comes from library
            //            title = ""
            actionBar
                    .setDisplayShowTitleEnabled(false)

            actionBar
                    .setDisplayUseLogoEnabled(false)
            actionBar
                    .setHomeButtonEnabled(true)
        }
    }

    private fun setAdapter() {
        val entityId = intent
                .getLongExtra(KEY_ENTITY_ID, -1)

        val viewPager = materialViewPager
                .viewPager
        viewPager
                .adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return UserDetailContentFragment
                        .newInstance(entityId)
            }

            override fun getCount(): Int {
                return 1
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return "User"
            }

        }

        viewPager
                .offscreenPageLimit = (viewPager.adapter as FragmentStatePagerAdapter)
                .count

        //After set an adapter to the ViewPager
        materialViewPager
                .pagerTitleStrip
                .setViewPager(materialViewPager.viewPager)

        if ((viewPager.adapter as FragmentStatePagerAdapter).count == 1) {
            materialViewPager
                    .pagerTitleStrip
                    .visibility = View
                    .GONE
        }
    }

    companion object {
        const val KEY_ENTITY_ID = "entity_id"

        fun newInstance(context: Context, user: UserEntity): Intent {
            return Intent(context, UserDetailActivity::class.java)
                    .apply {
                        putExtra(KEY_ENTITY_ID, user.entityId)
                    }
        }
    }

}