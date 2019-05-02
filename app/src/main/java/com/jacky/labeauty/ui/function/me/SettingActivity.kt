package com.jacky.labeauty.ui.function.me

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.DaoFacade
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.ui.function.me.address.MyAddressActivity
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import com.jacky.labeauty.ui.widget.RowItemView
import kotlinx.android.synthetic.main.activity_bee_setting.*

class SettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bee_setting)

        titleView.setLeftAction(View.OnClickListener { finish() })
        parentPush.setType(RowItemView.FLAG_RIGHT_CHECKABLE)
        parentPush.setTitle("是否接收推送消息")
        parentPush.setChecked(DaoFacade.get().isPushOpened)

        parentAddress.setTitle("我的地址")
        parentAddress.setType(RowItemView.FLAG_NONE);

        parentPush.setCheckedChangeListener { _, _ ->
            DaoFacade.get().togglePushSetting()
        }

        parentAddress.clickWithTrigger {
            startActivity(Intent(this, MyAddressActivity::class.java))
        }
    }
}