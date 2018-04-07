package de.markusressel.datamunch.view.component

import android.arch.lifecycle.LifecycleOwner
import android.support.v7.app.AppCompatActivity
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent

abstract class ActivityComponent(private val hostActivity: LifecycleProvider<ActivityEvent>) {

    init {
        // check if both casts are successful
        activity
        lifecycleOwner
        lifecycleProvider
    }

    protected val activity: AppCompatActivity
        get() = hostActivity as AppCompatActivity

    protected val lifecycleOwner: LifecycleOwner
        get() = hostActivity as LifecycleOwner

    protected val lifecycleProvider: LifecycleProvider<ActivityEvent>
        get() = hostActivity

}