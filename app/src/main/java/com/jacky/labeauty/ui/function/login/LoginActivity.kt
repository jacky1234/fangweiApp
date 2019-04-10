package com.jacky.labeauty.ui.function.login

import android.Manifest
import android.os.Bundle
import android.view.View
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.MiscFacade
import com.jacky.labeauty.logic.entity.module.MySelf
import com.jacky.labeauty.logic.entity.module.User
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.logic.thridLogin.*
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.ext.launch
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.support.util.Checker
import com.jacky.labeauty.ui.function.main.MainActivity
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import com.tencent.bugly.crashreport.CrashReport
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET)
                .subscribe()

        titleView.setLeftAction(View.OnClickListener { finish() })

        et_phone.setText(MySelf.get().mobile)
        val login = tv_login
        RxView.clicks(login).throttleFirst(1, TimeUnit.SECONDS)
                .compose(bindToDestroy())
                .subscribe {
                    val phone = et_phone.text.toString()
                    val pwd = et_pwd.text.toString()
                    if (Checker.check(et_phone, "请输入手机号") &&
                            Checker.checkMobile(et_phone) && Checker.check(et_pwd, "请输入密码"))
                        RequestHelper.get().login(phone, pwd)
                                .compose(bindToDestroy())
                                .subscribe {
                                    onLoginResult(it)
                                }
                }

        tv_register.clickWithTrigger {
            RegisterActivity.start(this, et_phone.text.toString())
        }

        tv_forget_pwd.clickWithTrigger {
            ForgetPwdActivity.start(this, et_phone.text.toString())
        }


        ivLoginWX.clickWithTrigger {
            ThirdLoginHelper.loginWx(listener)
        }

        ivLoginQQ.clickWithTrigger {
            ThirdLoginHelper.loginQQ(listener)
        }

        ivLoginWB.clickWithTrigger {
            ThirdLoginHelper.loginWb(listener)
        }
    }

    private fun onLoginResult(it: User) {
        MySelf.get().saveFromUser(it)
        CrashReport.setUserId(it.mobile)

        AndroidUtil.toast("登录成功")
        if (MiscFacade.get().lastRunnable == null) {
            AndroidUtil.runUI({ this@LoginActivity.launch<MainActivity>() }, 100)
        }

        finish()
    }


    val listener: OnThirdAuthListener = object : OnThirdAuthListener {
        override fun onCancel() {
        }

        override fun onError(e: AuthThrowable) {
            AndroidUtil.toast(e.message)
        }

        override fun onSuccess(result: AuthResult) {
            when (result.platform) {
                Platforms.WECHAT -> {
                    RequestHelper.get().loginWX(result.code)
                            .compose(bindToDestroy())
                            .subscribe {
                                onLoginResult(it)
                            }
                }

                Platforms.QQ -> {
                    RequestHelper.get().loginQQ(result.code)
                            .compose(bindToDestroy())
                            .subscribe {
                                onLoginResult(it)
                            }
                }

                Platforms.WEIBO -> {
                    RequestHelper.get().loginWB(result.code)
                            .compose(bindToDestroy())
                            .subscribe {
                                onLoginResult(it)
                            }
                }
            }
        }
    }
}