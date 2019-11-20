package com.jacky.labeauty.ui.function.me

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.jacky.labeauty.R
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.ext.launch
import com.jacky.labeauty.support.system.DeviceDependency
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import com.jacky.labeauty.ui.widget.RowItemView
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutUsActivity : BaseActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        titleView.setLeftAction(View.OnClickListener { finish() })

        parentAppIntroduce.setTitle(AndroidUtil.getString(R.string.app_introduce))
        parentAppIntroduce.setType(RowItemView.FLAG_NONE)

        parentVersion.setTitle(AndroidUtil.getString(R.string.version_story))
        parentVersion.setType(RowItemView.FLAG_RIGHT_SHOW_TEXT, false)
        parentVersion.setRightDesc(DeviceDependency.current.device.appVersion)

        parentFeedback.setTitle(AndroidUtil.getString(R.string.advice_problem))
        parentFeedback.setType(RowItemView.FLAG_NONE)

        parentAppIntroduce.clickWithTrigger {
            launch<AppIntroduceActivity>()
        }

        parentFeedback.clickWithTrigger {
            launch<AdviceProblemActivity>()
        }
    }
}