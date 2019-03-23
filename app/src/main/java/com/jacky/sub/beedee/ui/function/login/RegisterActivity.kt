package com.jacky.sub.beedee.ui.function.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.jacky.sub.beedee.R
import com.jacky.sub.beedee.logic.Constant
import com.jacky.sub.beedee.logic.MiscFacade
import com.jacky.sub.beedee.logic.entity.module.MySelf
import com.jacky.sub.beedee.logic.network.RequestHelper
import com.jacky.sub.beedee.support.ext.clickWithTrigger
import com.jacky.sub.beedee.support.ext.launch
import com.jacky.sub.beedee.support.util.AndroidUtil
import com.jacky.sub.beedee.support.util.Checker
import com.jacky.sub.beedee.ui.Dialog.DialogTipsHelper
import com.jacky.sub.beedee.ui.function.other.WebActivity
import com.jacky.sub.beedee.ui.inner.arch.BaseActivity
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
        val verifyCodeAvailable = MiscFacade.get().isVerifyCodeAvailable
        if (!verifyCodeAvailable) {
            RxTextView.text(btnGainCode).accept(null)
        }
        RxView.enabled(btnGainCode).accept(verifyCodeAvailable)
        if (!verifyCodeAvailable) {
            trigVerifyCode(btnGainCode)
        }
        btnGainCode.clickWithTrigger {
            val phone = et_phone.text.toString()
            if (!Checker.checkMobile(et_phone)) {
                return@clickWithTrigger
            }

            RxView.enabled(btnGainCode).accept(false)
            RequestHelper.get().sendCode(phone).subscribe {
                AndroidUtil.toast("发送成功")
                trigVerifyCode(btnGainCode)
            }
        }

        val next = tv_next
        RxView.clicks(next).throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .bindToLifecycle(btnGainCode)
                .subscribe {
                    val phone = et_phone.text.toString()
                    val code = et_code.text.toString()

                    if (Checker.check(et_phone, "请输入手机号") &&
                            Checker.checkMobile(et_phone) &&
                            Checker.check(et_code, "请输入验证码") &&
                            Checker.CheckChecked(checkbox, "请先同意使用协议"))
                        requestRegister(phone, code)
                }

        tvRegisterProtocol.clickWithTrigger {
            WebActivity.launch(this, Constant.REGISTER_PROTOCOL_URL)
        }
    }

    private fun trigVerifyCode(btnGainCode: Button) {
        MiscFacade.get().registerCodeListenerAndTrigger(codeObserver)
    }

    private fun requestRegister(phone: String, code: String) {
        RequestHelper.get().register(phone, code)
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe {
                    MySelf.get().saveFromUser(it)
                    DialogTipsHelper.createSuccess(this, "注册成功")
                    AndroidUtil.runUI({ this@RegisterActivity.launch<RegisterFillInfoActivity>() }, 100)
                }
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