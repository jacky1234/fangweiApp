package com.jacky.lebeauty.support.misc

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class LifeCircleComponents {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun removeDispose() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun addDispose(subscription: Disposable) {
        compositeDisposable.add(subscription)
    }
}