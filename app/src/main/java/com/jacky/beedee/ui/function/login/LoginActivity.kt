package com.jacky.beedee.ui.function.login

import android.Manifest
import android.os.Bundle
import android.view.View
import com.jacky.beedee.R
import com.jacky.beedee.logic.entity.module.MySelf
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.support.ext.clickWithTrigger
import com.jacky.beedee.support.ext.launch
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.support.util.Checker
import com.jacky.beedee.ui.function.main.MainActivity
import com.jacky.beedee.ui.inner.arch.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import com.trello.rxlifecycle2.android.ActivityEvent
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
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe {
                    val phone = et_phone.text.toString()
                    val pwd = et_pwd.text.toString()
                    if (Checker.check(et_phone, "请输入手机号") &&
                            Checker.checkMobile(et_phone) && Checker.check(et_pwd, "请输入密码"))
                        RequestHelper.get().login(phone, pwd)
                                .compose(bindToDestroy())
                                .subscribe {
                                    AndroidUtil.toast("登录成功")
                                    AndroidUtil.runUI({ this@LoginActivity.launch<MainActivity>() }, 100)
                                }
                }

        tv_register.clickWithTrigger {
            RegisterActivity.start(this, et_phone.text.toString())
        }

        tv_forget_pwd.clickWithTrigger {
            ForgetPwdActivity.start(this, et_phone.text.toString())
        }
    }
}