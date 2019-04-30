package com.jacky.labeauty.ui.inner.arch

import android.app.Activity
import android.content.Context
import com.jacky.labeauty.support.misc.LifeCircleComponents
import io.reactivex.disposables.Disposable

open class SecondFragment : RxFragment() {
    private val lifeCircleComponents = LifeCircleComponents()
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

    fun addDispose(dispose: Disposable) {
        lifeCircleComponents.addDispose(dispose)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifeCircleComponents.removeDispose()
    }

}