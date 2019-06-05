package com.jacky.labeauty.ui.function.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.Constant
import com.jacky.labeauty.logic.MiscFacade
import com.jacky.labeauty.logic.entity.module.MySelf
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.ext.launch
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.support.util.Checker
import com.jacky.labeauty.ui.dialog.DialogTipsHelper
import com.jacky.labeauty.ui.function.other.WebActivity
import com.jacky.labeauty.ui.inner.arch.BaseActivity
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
                AndroidUtil.toast(R.string.send_success)
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

                    if (Checker.check(et_phone, AndroidUtil.getString(R.string.please_input_phone_number)) &&
                            Checker.checkMobile(et_phone) &&
                            Checker.check(et_code, AndroidUtil.getString(R.string.please_input_sms_code)) &&
                            Checker.CheckChecked(checkbox, AndroidUtil.getString(R.string.please_agree_protocol)))
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
                    DialogTipsHelper.createSuccess(this, AndroidUtil.getString(R.string.register_success))
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