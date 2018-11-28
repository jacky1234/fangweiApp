package com.jacky.beedee.ui.inner.arch

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.CheckResult
import android.support.v4.app.ActivityCompat
import com.jacky.beedee.logic.MiscFacade
import com.jacky.beedee.support.log.Logger
import com.jacky.beedee.support.misc.LifeCircleComponents
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

/**
 * 2018/10/28.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
open class BaseActivity : MySupportActivity(), LifecycleProvider<ActivityEvent> {
    protected lateinit var rxPermissions: RxPermissions
    private val lifeCircleComponents = LifeCircleComponents()
    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()

    companion object {
        @JvmField
        var currentActivity: BaseActivity? = null
    }

    protected fun addDispose(subscription: Disposable) {
        lifeCircleComponents.addDispose(subscription)
    }

    @CallSuper
    override fun onDestroy() {
        try {
            super.onDestroy()
        } catch (e: Exception) {
            Logger.e(e)
        }
        currentActivity = null
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
        lifeCircleComponents.removeDispose()
    }


    @CheckResult
    override fun lifecycle(): Observable<ActivityEvent> {
        return lifecycleSubject.hide()
    }

    @CheckResult
    override fun <T> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    @CheckResult
    override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentActivity = this
        rxPermissions = RxPermissions(this)
        lifecycleSubject.onNext(ActivityEvent.CREATE)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        MiscFacade.get().loginOutFlag(this, false)
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    @CallSuper
    override fun onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    fun <T> bindToDestroy(): LifecycleTransformer<T> {
        return bindUntilEvent(ActivityEvent.DESTROY)
    }

    override fun onBackPressedSupport() {
        var backStackCount = supportFragmentManager.backStackEntryCount
        for (i in 0 until backStackCount) {            //新版glide上面有一层fragment覆盖着
            val entry = supportFragmentManager.getBackStackEntryAt(i)
            entry.name?.let {
                if (it.contains("SupportRequestManagerFragment")) {
                    backStackCount--
                }
            }
        }

        if (backStackCount > 1) {
            pop()
        } else {
            ActivityCompat.finishAfterTransition(this)
        }
    }
}