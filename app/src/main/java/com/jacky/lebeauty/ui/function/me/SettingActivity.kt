package com.jacky.lebeauty.ui.function.me

import android.os.Bundle
import android.view.View
import com.jacky.lebeauty.R
import com.jacky.lebeauty.logic.DaoFacade
import com.jacky.lebeauty.ui.inner.arch.BaseActivity
import com.jacky.lebeauty.ui.widget.RowItemView
import kotlinx.android.synthetic.main.activity_bee_setting.*

class SettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bee_setting)

        titleView.setLeftAction(View.OnClickListener { finish() })
        parentPush.setType(RowItemView.FLAG_RIGHT_CHECKABLE)
        parentPush.setTitle("是否接收推送消息")
        parentPush.setChecked(DaoFacade.get().isPushOpened)
        parentPush.setCheckedChangeListener { _, isChecked ->
            DaoFacade.get().togglePushSetting()
        }
    }
}