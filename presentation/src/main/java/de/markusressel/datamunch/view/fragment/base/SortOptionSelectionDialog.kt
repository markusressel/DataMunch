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

package de.markusressel.datamunch.view.fragment.base

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import com.eightbitlab.rxbus.Bus
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.SortOptionPersistenceManager
import de.markusressel.datamunch.event.SortOptionSelectionDialogDismissedEvent
import javax.inject.Inject

class SortOptionSelectionDialog : DaggerBottomSheetFragmentBase() {

    @Inject
    lateinit var sortOptionPersistenceHandler: SortOptionPersistenceManager

    private val sortOptions: List<SortOption<*>> by lazy {
        val ortOptionIdArray = arguments?.getLongArray(KEY_SORT_OPTION_IDS)
        ortOptionIdArray!!.map {
            SortOption.from(it)
        }
    }
    private val entityTypeId: Long by lazy {
        arguments?.getLong(KEY_ENTITY_TYPE_ID)!!
    }

    override val layoutRes: Int
        get() = R.layout.bottom_sheet_sort_option_selection

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) as ViewGroup
        val containerLayout: LinearLayout = view.findViewById(R.id.sortOptionsContainerLayout)
        inflateViews(containerLayout, inflater)

        return view
    }

    @SuppressLint("InflateParams")
    private fun inflateViews(containerLayout: ViewGroup, inflater: LayoutInflater) {
        sortOptions.forEach { sortOption ->
            val sortOptionLayout = inflater.inflate(R.layout.item_sort_option, containerLayout, false)
            val name: CheckBox = sortOptionLayout.findViewById(R.id.sortOptionName)
            val orderSelector: ImageView = sortOptionLayout.findViewById(R.id.orderSelector)
            name.text = getString(sortOption.name)
            name.isChecked = true

            orderSelector.rotation = when (sortOption.reversed) {
                true -> 180F
                false -> 0F
            }

            orderSelector.setOnClickListener {
                sortOption.reversed = !sortOption.reversed
                orderSelector.rotation = orderSelector.rotation + 180 % 360
            }

            containerLayout.addView(sortOptionLayout)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        persistSortOptions()
        Bus.send(SortOptionSelectionDialogDismissedEvent())
    }

    private fun persistSortOptions() {
        // remove currently stored options
        sortOptionPersistenceHandler
                .standardOperation()
                .query()
                .filter {
                    it.type == entityTypeId
                }
                .build()
                .find()
                .forEach {
                    sortOptionPersistenceHandler
                            .standardOperation()
                            .remove(it.entityId)
                }

        // save new
        sortOptions.forEach {
            sortOptionPersistenceHandler
                    .standardOperation()
                    .put(it.toEntity(entityTypeId))
        }
    }

    companion object {
        private const val KEY_SORT_OPTION_IDS = "KEY_SORT_OPTION_IDS"
        private const val KEY_ENTITY_TYPE_ID = "KEY_ENTITY_TYPE_ID"

        fun newInstance(sortOptionIds: List<SortOption<*>>,
                        entityTypeId: Long): SortOptionSelectionDialog {
            val fragment = SortOptionSelectionDialog()
            fragment.arguments = Bundle().apply {
                putLongArray(KEY_SORT_OPTION_IDS, sortOptionIds.map {
                    it.id
                }.toLongArray())
                putLong(KEY_ENTITY_TYPE_ID, entityTypeId)
            }

            return fragment
        }
    }

}