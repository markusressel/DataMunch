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
import de.markusressel.datamunch.event.SortOptionSelectionDialogDismissedEvent

class SortOptionSelectionDialog : DaggerBottomSheetFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.bottom_sheet_sort_option_selection

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) as ViewGroup

        val sortOptionIdArray = arguments
                ?.getLongArray(KEY_SORT_OPTION_IDS)

        val sortOptions = sortOptionIdArray!!
                .map {
                    SortOption
                            .from(it)
                }

        val containerLayout: LinearLayout = view
                .findViewById(R.id.sortOptionsContainerLayout)
        sortOptions
                .forEach {
                    val sortOptionLayout = inflater
                            .inflate(R.layout.item_sort_option, null, false)

                    val name: CheckBox = sortOptionLayout
                            .findViewById(R.id.sortOptionName)
                    val orderSelector: ImageView = sortOptionLayout
                            .findViewById(R.id.orderSelector)
                    name
                            .text = getString(it.name)

                    orderSelector
                            .setOnClickListener {

                            }

                    containerLayout
                            .addView(sortOptionLayout)
                }

        return view
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super
                .onDismiss(dialog)

        Bus
                .send(SortOptionSelectionDialogDismissedEvent())
    }

    companion object {
        private const val KEY_SORT_OPTION_IDS = "KEY_SORT_OPTION_IDS"

        fun newInstance(sortOptionIds: List<SortOption<*>>): SortOptionSelectionDialog {
            val fragment = SortOptionSelectionDialog()
            val bundle = Bundle()
            bundle
                    .putLongArray(KEY_SORT_OPTION_IDS, sortOptionIds.map {
                        it
                                .id
                    }.toLongArray())

            fragment
                    .arguments = bundle

            return fragment
        }
    }

}