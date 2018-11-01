package com.jacky.beedee.ui.function.login

import android.os.Bundle
import android.view.View
import com.jacky.beedee.R
import com.jacky.beedee.logic.entity.MySelf
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.logic.network.exception.CustomException
import com.jacky.beedee.support.ext.clickWithTrigger
import com.jacky.beedee.support.ext.launch
import com.jacky.beedee.support.ext.toast
import com.jacky.beedee.support.log.Logger
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.support.util.Strings
import com.jacky.beedee.support.util.regex.RegexUtils
import com.jacky.beedee.ui.Dialog.DialogHelper
import com.jacky.beedee.ui.inner.arch.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.trello.rxlifecycle2.android.ActivityEvent
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
        btnGainCode.clickWithTrigger {
            val phone = et_phone.text.toString()
            if (!RegexUtils.isMobileSimple(phone)) {
                toast(R.string.mobile_number_wrong)
                return@clickWithTrigger
            }

            RxView.enabled(btnGainCode).accept(false)
            Observable.interval(1, TimeUnit.SECONDS).take(MAX_SECOND).observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ l -> RxTextView.text(btnGainCode).accept("剩余" + (MAX_SECOND - l) + "秒"); }, Logger.Companion::e, {
                        RxTextView.textRes(btnGainCode).accept(R.string.get_vertify_code)
                        RxView.enabled(btnGainCode).accept(true)
                    })
        }

        val next = tv_next
        RxView.clicks(next).throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .bindToLifecycle(btnGainCode)
                .subscribe {
                    val phone = et_phone.text.toString()
                    if (Strings.isNullOrEmpty(phone)) {
                        toast("请输入手机号")
                        return@subscribe
                    }

                    if (!RegexUtils.isMobileSimple(phone)) {
                        toast(R.string.mobile_number_wrong)
                        return@subscribe
                    }

                    val code = et_code.text.toString()
                    if (Strings.isNullOrEmpty(code)) {
                        toast("请输入验证码")
                        return@subscribe
                    }

                    requestRegister(phone, code)
                }
    }

    private fun requestRegister(phone: String, code: String) {
        RequestHelper.get().register(phone, code)
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe({ it ->
                    MySelf.get().id = it.id
                    MySelf.get().username = it.username
                    MySelf.get().mobile = it.mobile
                    MySelf.get().role = it.role
                    MySelf.get().createTime = it.createTime
                    MySelf.get().updateTime = it.updateTime
                    MySelf.get().save()

                    DialogHelper.createSuccess(this, "注册成功")
                    AndroidUtil.runUI({ this@RegisterActivity.launch<RegisterFillInfoActivity>() }, 100)
                }, { CustomException.handleException(it) })
    }
}