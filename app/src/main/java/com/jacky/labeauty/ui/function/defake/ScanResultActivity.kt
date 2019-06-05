package com.jacky.labeauty.ui.function.defake

import android.os.Bundle
import android.view.View
import com.jacky.labeauty.R
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.support.util.SpanUtils
import com.jacky.labeauty.support.util.Strings
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import kotlinx.android.synthetic.main.activity_scan_result.*

class ScanResultActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_result)

        titleView.setLeftAction(View.OnClickListener { finish() })
//        TODO("")
        val real = true
        if (real) {
            parent_true.visibility = View.VISIBLE
            parent_false.visibility = View.GONE

            tv_real.text = getRealInfo()
            tv_scan_detail.clickWithTrigger {
                //            TODO("scan detail")
            }

        } else {
            parent_true.visibility = View.GONE
            parent_false.visibility = View.VISIBLE
            tv_confirm.clickWithTrigger {
                finish()
            }
        }
    }

    private fun getRealInfo(): CharSequence {
        return SpanUtils().append(AndroidUtil.getString(R.string.inspect_result_real)).setFontSize(19, false).setBold()
                .appendLine().appendLine()
                .appendImage(R.mipmap.ic_small_back_vertical).append(Strings.enter).append("安全信息")
//                .appendLine().append("产品编号：").append("${}")
//                .appendLine().append("生产日期：").append("${}")
//                .appendLine().append("生产公司：").append("${}")
                .appendLine().appendLine()
                .appendImage(R.mipmap.ic_small_back_vertical).append(Strings.enter).append("产品详情")
//                .appendLine().append("${}")
                .create()
    }
}