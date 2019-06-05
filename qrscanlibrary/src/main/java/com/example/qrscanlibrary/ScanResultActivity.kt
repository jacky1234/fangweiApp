package org.xinkb.blackboard.android.ui.activity.scan

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_scan_result.*
import org.xinkb.blackboard.android.R
import org.xinkb.blackboard.android.ui.activity.BaseActivity

class ScanResultActivity : BaseActivity() {

    companion object {
        @JvmStatic
        fun launch(activity: BaseActivity) {
            activity.startActivity(Intent(activity, ScanResultActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_result)
        initTitle()
    }

    fun initTitle() {
        title_layout.titleText = context.resources.getString(R.string.scan_result_title)
        title_layout.setBackBtnImage(R.drawable.btn_back)
        title_layout.setBackBtnOnClick { finish() }
    }
}