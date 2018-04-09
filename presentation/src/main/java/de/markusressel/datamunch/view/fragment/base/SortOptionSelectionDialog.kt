package de.markusressel.datamunch.view.fragment.base

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.markusressel.datamunch.R

class SortOptionSelectionDialog : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_sort_option_selection, container,
                                    false) as ViewGroup

        return view
    }

}