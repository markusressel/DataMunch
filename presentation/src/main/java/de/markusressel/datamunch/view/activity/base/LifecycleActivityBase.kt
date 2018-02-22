package de.markusressel.datamunch.view.activity.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.CheckResult
import com.eightbitlab.rxbus.Bus
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject


/**
 * Created by Markus on 16.02.2018.
 */
abstract class LifecycleActivityBase : StateActivityBase(), LifecycleProvider<ActivityEvent> {

    private val lifecycleSubject: BehaviorSubject<ActivityEvent> = BehaviorSubject
            .create()

    @CheckResult
    override fun lifecycle(): Observable<ActivityEvent> {
        return lifecycleSubject
                .hide()
    }

    @CheckResult
    override fun <T> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> {
        return RxLifecycle
                .bindUntilEvent(lifecycleSubject, event)
    }

    @CheckResult
    override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid
                .bindActivity(lifecycleSubject)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)
        lifecycleSubject
                .onNext(ActivityEvent.CREATE)
    }

    @CallSuper
    override fun onStart() {
        super
                .onStart()
        lifecycleSubject
                .onNext(ActivityEvent.START)
    }

    @CallSuper
    override fun onResume() {
        super
                .onResume()
        lifecycleSubject
                .onNext(ActivityEvent.RESUME)
    }

    @CallSuper
    override fun onPause() {
        lifecycleSubject
                .onNext(ActivityEvent.PAUSE)
        super
                .onPause()
    }

    @CallSuper
    override fun onStop() {
        Bus
                .unregister(this)
        lifecycleSubject
                .onNext(ActivityEvent.STOP)
        super
                .onStop()
    }

    @CallSuper
    override fun onDestroy() {
        lifecycleSubject
                .onNext(ActivityEvent.DESTROY)
        super
                .onDestroy()
    }

}