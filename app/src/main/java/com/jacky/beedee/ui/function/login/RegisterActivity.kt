package com.jacky.beedee.ui.function.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.jacky.beedee.R
import com.jacky.beedee.logic.MiscFacade
import com.jacky.beedee.logic.entity.MySelf
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.logic.network.exception.CustomException
import com.jacky.beedee.support.ext.clickWithTrigger
import com.jacky.beedee.support.ext.launch
import com.jacky.beedee.support.ext.toast
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.support.util.Strings
import com.jacky.beedee.support.util.regex.RegexUtils
import com.jacky.beedee.ui.Dialog.DialogTipsHelper
import com.jacky.beedee.ui.inner.arch.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_register.*
import java.util.concurrent.TimeUnit

class RegisterActivity : BaseActivity() {
    private val codeObserver = object : Observer<Long> {
        override fun onSubscribe(d: Disposable) {

        }

        override fun onNext(t: Long) {
            RxTextView.text(btn_gain_code).accept("剩余" + t + "秒")
        }

        override fun onError(e: Throwable) {
        }

        override fun onComplete() {
            RxTextView.textRes(btn_gain_code).accept(R.string.get_vertify_code)
            RxView.enabled(btn_gain_code).accept(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        titleView.setLeftAction(View.OnClickListener { finish() })
        et_phone.setText(intent.getStringExtra(KEY_MOBILE))

        val btnGainCode = btn_gain_code
        val vertifyCodeAvailable = MiscFacade.get().isVertifyCodeAvailable
        if (!vertifyCodeAvailable) {
            RxTextView.text(btnGainCode).accept(null)
        }
        RxView.enabled(btnGainCode).accept(vertifyCodeAvailable)
        if (!vertifyCodeAvailable) {
            trigVertifyCode(btnGainCode)
        }
        btnGainCode.clickWithTrigger {
            val phone = et_phone.text.toString()
            if (!RegexUtils.isMobileSimple(phone)) {
                toast(R.string.mobile_number_wrong)
                return@clickWithTrigger
            }

            RxView.enabled(btnGainCode).accept(false)
            RequestHelper.get().sendCode(phone).subscribe({
                AndroidUtil.toast("发送成功")
                trigVertifyCode(btnGainCode)
            }, { CustomException.handleException(it) })
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

    private fun trigVertifyCode(btnGainCode: Button) {
        MiscFacade.get().registerCodeListenerAndTrigger(codeObserver)
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

                    DialogTipsHelper.createSuccess(this, "注册成功")
                    AndroidUtil.runUI({ this@RegisterActivity.launch<RegisterFillInfoActivity>() }, 100)
                }, { CustomException.handleException(it) })
    }

    override fun onDestroy() {
        super.onDestroy()
        MiscFacade.get().removeCodeListener(codeObserver)
    }

    companion object {
        const val KEY_MOBILE = "KEY_MOBILE"
        @JvmStatic
        fun start(activity: BaseActivity, mobile: String) {
            val intent = Intent(activity, RegisterActivity::class.java)
            intent.putExtra(KEY_MOBILE, mobile)
            activity.startActivity(intent)
        }
    }
}