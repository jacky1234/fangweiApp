package com.jacky.sub.beedee.ui.function.me

import android.os.Bundle
import android.view.View
import com.jacky.sub.beedee.R
import com.jacky.sub.beedee.logic.DaoFacade
import com.jacky.sub.beedee.ui.inner.arch.BaseActivity
import com.jacky.sub.beedee.ui.widget.RowItemView
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