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

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ScrollAwareFABBehavior : FloatingActionButton.Behavior() {
    private var mIsAnimatingOut = false


    /**
     * Same animation that FloatingActionButton.Behavior
     * uses to show the FAB when the AppBarLayout enters
     *
     * @param floatingActionButton FAB
     */
    //
    private fun animateIn(floatingActionButton: FloatingActionButton) {
        floatingActionButton
                .visibility = View
                .VISIBLE

        ViewCompat
                .animate(floatingActionButton)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .alpha(1.0f)
                .setInterpolator(INTERPOLATOR)
                .setDuration(300)
                .withLayer()
                .setListener(object : ViewPropertyAnimatorListener {
                    override fun onAnimationStart(view: View) {
                        this@ScrollAwareFABBehavior
                                .mIsAnimatingOut = true
                        view
                                .visibility = View
                                .VISIBLE
                    }

                    override fun onAnimationCancel(view: View) {
                        this@ScrollAwareFABBehavior
                                .mIsAnimatingOut = false
                    }

                    override fun onAnimationEnd(view: View) {
                        this@ScrollAwareFABBehavior
                                .mIsAnimatingOut = false
                    }
                })
                .start()
    }

    /**
     * Same animation that FloatingActionButton.Behavior uses to
     * hide the FAB when the AppBarLayout exits
     *
     * @param floatingActionButton FAB
     */
    private fun animateOut(floatingActionButton: FloatingActionButton) {
        ViewCompat
                .animate(floatingActionButton)
                .scaleX(0.0f)
                .scaleY(0.0f)
                .alpha(0.0f)
                .setInterpolator(INTERPOLATOR)
                .setDuration(150)
                .withLayer()
                .setListener(object : ViewPropertyAnimatorListener {
                    override fun onAnimationStart(view: View) {
                        this@ScrollAwareFABBehavior
                                .mIsAnimatingOut = true
                    }

                    override fun onAnimationCancel(view: View) {
                        this@ScrollAwareFABBehavior
                                .mIsAnimatingOut = false
                    }

                    override fun onAnimationEnd(view: View) {
                        this@ScrollAwareFABBehavior
                                .mIsAnimatingOut = false
                        view
                                .visibility = View
                                .INVISIBLE
                    }
                })
                .start()
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout,
                                     child: FloatingActionButton, directTargetChild: View,
                                     target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(
                coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton,
                                target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int,
                                dyUnconsumed: Int, type: Int) {
        super
                .onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed,
                                dxUnconsumed, dyUnconsumed, type)

        if (dyConsumed > 0 && !this.mIsAnimatingOut && child.visibility == View.VISIBLE) {
            animateOut(child)
        } else if (dyConsumed < 0 && child.visibility != View.VISIBLE) {
            animateIn(child)
        }
    }

    companion object {
        private val INTERPOLATOR = FastOutSlowInInterpolator()
    }

}