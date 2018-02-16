package de.markusressel.datamunch.view.activity.base

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import com.github.florent37.materialviewpager.header.HeaderDesign
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.extensions.random
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail__header_logo.*


/**
 * Created by Markus on 15.02.2018.
 */
abstract class DetailActivityBase<T : Any> : DaggerSupportActivityBase() {

    override val style: Int
        get() {
            return if (resources.getBoolean(R.bool.is_tablet)) {
                DIALOG
            } else {
                DEFAULT
            }
        }

    override val layoutRes: Int
        get() = R.layout.activity_item_detail

    protected abstract val headerTextString: String

    abstract val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        setToolbar()
        setAdapter()
        setHeader()
    }

    private fun setHeader() {
        headerText
                .text = headerTextString

        materialViewPager
                .setMaterialViewPagerListener {
                    val headerList = listOf(
                            //                            HeaderDesign.fromColorResAndUrl(R.color.blue,
                            //                                                                            "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg"),
                            //                                            HeaderDesign.fromColorResAndUrl(R.color.cyan,
                            //                                                                            "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg"),
                            //                                            HeaderDesign.fromColorResAndUrl(R.color.red,
                            //                                                                            "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg"),

                            HeaderDesign.fromColorResAndDrawable(R.color.md_blue_800, getDrawable(
                                    R.drawable.header_blue)),
                            HeaderDesign.fromColorResAndDrawable(R.color.md_blue_800, getDrawable(
                                    R.drawable.header_circles_blue)),

                            HeaderDesign.fromColorResAndDrawable(R.color.md_green_500, getDrawable(
                                    R.drawable.header_colorful_1)),

                            HeaderDesign.fromColorResAndDrawable(R.color.md_yellow_800, getDrawable(
                                    R.drawable.header_colorful_2)),
                            HeaderDesign.fromColorResAndDrawable(R.color.md_red_800, getDrawable(
                                    R.drawable.header_dark_red)),
                            HeaderDesign.fromColorResAndDrawable(R.color.md_red_800, getDrawable(
                                    R.drawable.header_circles_red)),

                            HeaderDesign.fromColorResAndDrawable(R.color.md_grey_900, getDrawable(
                                    R.drawable.header_circles_rainbow)),

                            HeaderDesign.fromColorResAndDrawable(R.color.md_green_800, getDrawable(
                                    R.drawable.header_green)),
                            HeaderDesign.fromColorResAndDrawable(R.color.md_green_800, getDrawable(
                                    R.drawable.header_circles_green)),

                            HeaderDesign.fromColorResAndDrawable(R.color.md_brown_800, getDrawable(
                                    R.drawable.header_sepia))

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

            val actionBar = supportActionBar!!
            actionBar
                    .setDisplayHomeAsUpEnabled(true)

            //            actionBar
            //                    .setDisplayShowHomeEnabled(true)

            // activity title comes from library
            actionBar
                    .setDisplayShowTitleEnabled(false)

            actionBar
                    .setDisplayUseLogoEnabled(false)
            actionBar
                    .setHomeButtonEnabled(true)
        }
    }

    private fun setAdapter() {
        val viewPager = materialViewPager
                .viewPager
        viewPager
                .adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return tabItems[position]
                        .second()
                        .apply {
                            arguments = Bundle(2)
                                    .apply {
                                        putLong(KEY_ENTITY_ID, getEntityId())
                                    }
                        }
            }

            override fun getCount(): Int {
                return tabItems
                        .size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return getString(tabItems[position].first)
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
                    .underlineColor = Color
                    .TRANSPARENT

            materialViewPager
                    .pagerTitleStrip
                    .indicatorColor = Color
                    .TRANSPARENT

            //                    materialViewPager
            //                            .pagerTitleStrip
            //                            .visibility = View
            //                            .GONE
        }
    }

    private fun getEntityId(): Long {
        return intent
                .getLongExtra(KEY_ENTITY_ID, -1)
    }

    protected fun getEntity(): T {
        return getPersistenceHandler()
                .standardOperation()
                .get(getEntityId())
    }

    /**
     * Get the persistence handler for this view
     */
    protected abstract fun getPersistenceHandler(): PersistenceManagerBase<T>

    companion object {
        const val KEY_ENTITY_ID = "entity_id"

        fun <T : Class<*>> newInstance(clazz: T, context: Context): Intent {
            return Intent(context, clazz)
        }
    }
}