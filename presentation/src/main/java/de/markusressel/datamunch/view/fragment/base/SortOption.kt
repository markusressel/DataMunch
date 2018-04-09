package de.markusressel.datamunch.view.fragment.base

import android.support.annotation.StringRes

/**
 * Class specifying a single SortOption for a list view
 */
data class SortOption<T : Any>(@StringRes val int: Int, val comparator: Comparator<T>)