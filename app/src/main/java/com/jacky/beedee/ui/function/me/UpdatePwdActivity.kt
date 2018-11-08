package com.jacky.beedee.ui.function.me

import android.os.Bundle
import android.view.View
import com.jacky.beedee.R
import com.jacky.beedee.logic.entity.module.MySelf
import com.jacky.beedee.logic.entity.request.UpdateUserRequest
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.support.ext.clickWithTrigger
import com.jacky.beedee.support.ext.launch
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.support.util.Checker
import com.jacky.beedee.ui.function.main.MainActivity
import com.jacky.beedee.ui.inner.arch.BaseActivity
import com.jacky.beedee.ui.widget.TitleView
import kotlinx.android.synthetic.main.activity_update_pwd.*

/**
 * 2018/11/4.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class UpdatePwdActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_pwd)

        titleView.setFlag(TitleView.COMPOSE_LEFT_MIDDLEL_FLAG)
        titleView.setLeftAction(View.OnClickListener {
            finish()
        })

        tv_confirm.clickWithTrigger {
            if (Checker.check(et_old_pwd, "请输入旧密码") &&
                    Checker.check(et_new_pwd, "请输入新密码") &&
                    Checker.check(et_confirm_new_pwd, "请再次输入新密码") &&
                    Checker.equalsContent(et_new_pwd, et_confirm_new_pwd, "两次输入的密码不一致")) {
                val request = UpdateUserRequest()
                request.password = et_new_pwd.text.toString()
                RequestHelper.get().updateUserInfo(request)
                        .compose(bindToDestroy())
                        .subscribe {
                            MySelf.get().saveFromUser(it)
                            AndroidUtil.toast(R.string.update_success)
                            launch<MainActivity>()
                        }
            }
        }
    }

}