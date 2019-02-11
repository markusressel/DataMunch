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

package de.markusressel.datamunch.view.activity.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.github.florent37.materialviewpager.header.HeaderDesign
import de.markusressel.commons.random.random
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.EntityWithId
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.base.TabPageConstructor
import kotlinx.android.synthetic.main.fragment_item_detail.*
import kotlinx.android.synthetic.main.item_detail__header_logo.*


/**
 * Created by Markus on 15.02.2018.
 */
abstract class DetailFragmentBase<EntityType : EntityWithId> : DaggerSupportFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.fragment_item_detail

    protected abstract val headerTextString: String

    abstract val tabItems: List<TabPageConstructor>

    private val headerMap: MutableMap<Int, HeaderDesign> = mutableMapOf()
    private val usedHeaders: MutableSet<HeaderConfig> = mutableSetOf()

    private var currentEntityState: StateHolder<EntityType>? by savedInstanceState()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        setAdapter()
        setHeader()
        initEntityState()
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
                                        .fromColorResAndDrawable(config.colorRes, context!!.getDrawable(config.drawableRes))
                            }
                }
    }

    private fun setToolbar() {
        materialViewPager
                .toolbar?.let {

            it.visibility = View.GONE

//            it.setDisplayHomeAsUpEnabled(true)
//
//            //            actionBar
//            //                    .setDisplayShowHomeEnabled(true)
//
//            // activity title comes from library
//            it.setDisplayShowTitleEnabled(false)
//
//            it.setDisplayUseLogoEnabled(false)
//            it.setHomeButtonEnabled(true)
        }
    }

    private fun setAdapter() {
        val viewPager = materialViewPager
                .viewPager
        viewPager
                .adapter = object : FragmentStatePagerAdapter(activity!!.supportFragmentManager) {
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

    private fun initEntityState() {
        val entity = when (getEntityId()) {
            ENTITY_ID_MISSING_VALUE -> {
                throw IllegalStateException("Missing Entity ID!")
            }
            else -> {
                getEntityFromPersistence()!!
            }
        }

        currentEntityState = StateHolder(entity)
    }

    private fun getEntityId(): Long {
        return arguments!!.getLong(KEY_ENTITY_ID, ENTITY_ID_MISSING_VALUE)
    }

    /**
     * Get the entity from persistence
     */
    private fun getEntityFromPersistence(): EntityType? {
        return getPersistenceHandler()
                .standardOperation()
                .get(getEntityId())
    }

    /**
     * Get the entity from current state holder
     */
    protected fun getEntity(): EntityType {
        if (currentEntityState == null) {
            initEntityState()
        }

        return currentEntityState!!
                .entity
    }

    /**
     * Get the persistence handler for this view
     */
    protected abstract fun getPersistenceHandler(): PersistenceManagerBase<EntityType>

    companion object {

        const val KEY_ENTITY_ID = "entity_id"
        const val ENTITY_ID_MISSING_VALUE = -1L

        /**
         * Create a bundle for a fragment implementation of this class
         */
        fun createEntityBundle(entityId: Long): Bundle {
            return bundleOf(DetailFragmentBase.KEY_ENTITY_ID to entityId)
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