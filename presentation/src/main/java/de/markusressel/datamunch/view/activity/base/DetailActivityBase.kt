package de.markusressel.datamunch.view.activity.base

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.KeyEvent
import android.view.MenuItem
import com.eightbitlab.rxbus.Bus
import com.github.florent37.materialviewpager.header.HeaderDesign
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.event.KeyDownEvent
import de.markusressel.datamunch.extensions.random
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail__header_logo.*


/**
 * Created by Markus on 15.02.2018.
 */
abstract class DetailActivityBase<EntityType : Any> : DaggerSupportActivityBase() {

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

    private val headerMap: MutableMap<Int, HeaderDesign> = mutableMapOf()
    private val usedHeaders: MutableSet<HeaderConfig> = mutableSetOf()

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
                    val pageIndex = it

                    // get header from map (if already instantiated)
                    headerMap
                            .getOrPut(it) {
                                // get random header for page
                                // filter out headers that are already in use
                                val filteredHeaders = HEADER_CONFIGS
                                        .filter {
                                            !usedHeaders.contains(it)
                                        }


                                val config: HeaderConfig
                                if (!filteredHeaders.isEmpty()) {
                                    config = filteredHeaders[(0 until filteredHeaders.size).random()]
                                    // remember that this header is now in use
                                    usedHeaders
                                            .add(config)
                                } else {
                                    // use random header if all headers are already in use
                                    config = HEADER_CONFIGS[(0 until HEADER_CONFIGS.size).random()]
                                }


                                // instantiate this header
                                HeaderDesign
                                        .fromColorResAndDrawable(config.colorRes,
                                                                 getDrawable(config.drawableRes))
                            }
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

    /**
     * Get the entity from persistence
     */
    protected fun getEntity(): EntityType {
        return getPersistenceHandler()
                .standardOperation()
                .get(getEntityId())
    }

    /**
     * Get the persistence handler for this view
     */
    protected abstract fun getPersistenceHandler(): PersistenceManagerBase<EntityType>

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (super.onOptionsItemSelected(item)) {
            return true
        }

        return when (item?.itemId) {
            android.R.id.home -> {
                // TODO: Show dialog if unsaved changes still exist
                finish()
                true
            }
            else -> {
                false
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Bus
                .send(KeyDownEvent(keyCode, event))

        return super
                .onKeyDown(keyCode, event)
    }

    companion object {

        const val KEY_ENTITY_ID = "entity_id"
        fun <T : Class<*>> newInstanceIntent(clazz: T, context: Context, entityId: Long): Intent {
            return Intent(context, clazz)
                    .apply {
                        putExtra(KEY_ENTITY_ID, entityId)
                    }
        }

        private val HEADER_CONFIGS = listOf(
                HeaderConfig(R.color.md_blue_800, R.drawable.header_blue),
                HeaderConfig(R.color.md_blue_800, R.drawable.header_circles_blue),
                HeaderConfig(R.color.md_green_500, R.drawable.header_colorful_1),
                HeaderConfig(R.color.md_yellow_800, R.drawable.header_colorful_2),
                HeaderConfig(R.color.md_red_800, R.drawable.header_dark_red),
                HeaderConfig(R.color.md_red_800, R.drawable.header_circles_red),
                HeaderConfig(R.color.md_grey_900, R.drawable.header_circles_rainbow),
                HeaderConfig(R.color.md_green_800, R.drawable.header_green),
                HeaderConfig(R.color.md_green_800, R.drawable.header_circles_green),
                HeaderConfig(R.color.md_brown_800, R.drawable.header_sepia))

    }

    data class HeaderConfig(@ColorRes val colorRes: Int, @DrawableRes val drawableRes: Int)

}