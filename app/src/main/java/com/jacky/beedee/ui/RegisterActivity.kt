package com.jacky.beedee.ui

import android.os.Bundle
import android.view.View
import com.jacky.beedee.R
import com.jacky.beedee.support.log.Logger
import com.jacky.beedee.ui.inner.arch.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_register.*
import java.util.concurrent.TimeUnit

class RegisterActivity : BaseActivity() {
    private val MAX_SECOND = 60L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        titleView.setLeftAction(View.OnClickListener { finish() })

        val btnGainCode = btn_gain_code
        RxView.clicks(btnGainCode).throttleFirst(MAX_SECOND, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .bindToLifecycle(btnGainCode)
                .doOnNext {
                    RxView.enabled(btnGainCode).accept(false)
                }.subscribe {
                    Observable.interval(1, TimeUnit.SECONDS).take(MAX_SECOND).observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ l -> RxTextView.text(btnGainCode).accept("剩余" + (MAX_SECOND - l) + "秒"); }, Logger.Companion::e, {
                                RxTextView.textRes(btnGainCode).accept(R.string.get_vertify_code)
                                RxView.enabled(btnGainCode).accept(true)
                            })
                }
    }
}