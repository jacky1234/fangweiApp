package com.jacky.beedee.ui.function.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.jacky.beedee.R
import com.jacky.beedee.logic.entity.MySelf
import com.jacky.beedee.logic.entity.request.UpdateUserRequest
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.support.ext.launch
import com.jacky.beedee.support.ext.toast
import com.jacky.beedee.support.util.Strings
import com.jacky.beedee.ui.function.main.MainActivity
import com.jacky.beedee.ui.inner.arch.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import com.trello.rxlifecycle2.android.ActivityEvent
import kotlinx.android.synthetic.main.activity_register_fill_info.*
import java.util.concurrent.TimeUnit

class RegisterFillInfoActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_fill_info)
        val phone = intent.getStringExtra(KEY_PHONE)
        val code = intent.getStringExtra(KEY_CODE)

        titleView.setLeftAction(View.OnClickListener { finish() })

        val complete = tv_complete
        RxView.clicks(complete).throttleFirst(2, TimeUnit.SECONDS)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe {
                    val nickname = et_nickname.text.toString()
                    if (Strings.isNullOrEmpty(nickname)) {
                        toast("请输入昵称")
                        return@subscribe
                    }

                    val pwd = et_pwd.text.toString()
                    if (Strings.isNullOrEmpty(pwd)) {
                        toast("请输入密码")
                        return@subscribe
                    }
                    if (Strings.isNullOrEmpty(et_confirm_pwd.text.toString())) {
                        toast("请确认密码")
                        return@subscribe
                    }

                    if (pwd != et_confirm_pwd.text.toString()) {
                        toast("两次输入的密码不一致")
                        return@subscribe
                    }
                    val request = UpdateUserRequest()
                    request.nickName = nickname
                    request.gender = if (rb_boy.isChecked) "MALE" else "FEMALE"
                    request.password = pwd
                    RequestHelper.get().updateUserInfo(request)
                            .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                            .subscribe {
                                MySelf.get().saveFromUser(it)
                                finishAffinity()
                                this@RegisterFillInfoActivity.launch<MainActivity>()
                            }

                }
    }

    companion object {
        private const val KEY_PHONE = "KEY_PHONE"
        private const val KEY_CODE = "KEY_CODE"

        @JvmStatic
        fun launch(activity: BaseActivity, phone: String, code: String) {
            val intent = Intent(activity, RegisterFillInfoActivity.javaClass)
            intent.putExtra(KEY_PHONE, phone)
            intent.putExtra(KEY_CODE, code)
            activity.startActivity(intent)
        }
    }
}