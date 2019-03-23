package com.jacky.lebeauty.ui.inner.arch

import android.app.Activity
import android.content.Context
import com.jacky.lebeauty.support.misc.LifeCircleComponents
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.subjects.BehaviorSubject

open class SecondFragment : RxFragment() {
    private val lifeCircleComponents = LifeCircleComponents()
    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()
    protected var activity: Activity? = null

    fun isAttached(): Boolean {
        return activity != null
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity = getActivity()
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

}