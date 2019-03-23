package com.jacky.sub.beedee.ui.function.me

import android.os.Bundle
import android.view.View
import com.jacky.sub.beedee.R
import com.jacky.sub.beedee.logic.entity.module.MySelf
import com.jacky.sub.beedee.logic.entity.request.UpdateUserRequest
import com.jacky.sub.beedee.logic.network.RequestHelper
import com.jacky.sub.beedee.support.ext.clickWithTrigger
import com.jacky.sub.beedee.support.ext.launch
import com.jacky.sub.beedee.support.ext.toast
import com.jacky.sub.beedee.support.util.Checker
import com.jacky.sub.beedee.ui.function.main.MainActivity
import com.jacky.sub.beedee.ui.inner.arch.BaseActivity
import com.jacky.sub.beedee.ui.widget.RowItemView
import com.jacky.sub.beedee.ui.widget.TitleView
import kotlinx.android.synthetic.main.activity_update_nickname.*

/**
 * 2018/11/4.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class UpdateNickNameActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_nickname)

        titleView.setFlag(TitleView.COMPOSE_LEFT_MIDDLEL_FLAG)
        titleView.setLeftAction(View.OnClickListener {
            finish()
        })

        modify_nickname.setType(RowItemView.FLAG_RIGHT_EDITABLE)
        modify_nickname.setTitle("昵称")
        modify_nickname.setRightEditableText(MySelf.get().nickName)
        tv_confirm.clickWithTrigger {
            if (Checker.check(modify_nickname.rightEditText, "请输入昵称")) {
                val request = UpdateUserRequest()
                request.nickName = modify_nickname.rightEditableContent.toString()
                RequestHelper.get().updateUserInfo(request)
                        .compose(bindToDestroy())
                        .subscribe {
                            MySelf.get().saveFromUser(it)
                            toast(R.string.update_success)
                            launch<MainActivity>()
                        }
            }
        }
    }
}