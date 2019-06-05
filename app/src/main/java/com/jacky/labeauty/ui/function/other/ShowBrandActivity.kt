package com.jacky.labeauty.ui.function.other

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.jacky.labeauty.R
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.support.util.SpanUtils
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import kotlinx.android.synthetic.main.activity_introduce.*

/**
 * 2018/11/3.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 * 品牌介绍
 */
class ShowBrandActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduce)

        titleView.setLeftAction(View.OnClickListener { finish() })
        tvCenter.text = getCenter()
        tvRegisterProtocol.text = getInfo()

    }

    private fun getCenter(): CharSequence {
        return SpanUtils()
                .appendLine(AndroidUtil.getString(R.string.intro_top_1))
                .appendLine(AndroidUtil.getString(R.string.intro_top_2))
                .appendLine(AndroidUtil.getString(R.string.intro_top_3))
                .appendLine(AndroidUtil.getString(R.string.intro_top_4))
                .create()
    }

    private fun getInfo(): CharSequence {
        return SpanUtils()
                .append("    ").append(AndroidUtil.getString(R.string.is_one)).setForegroundColor(Color.parseColor("#666666")).setFontSize(14, true)
                .append(AndroidUtil.getString(R.string.is_one_what)).setForegroundColor(Color.parseColor("#666666")).setFontSize(14, true).setBold()
                .appendLine(AndroidUtil.getString(R.string.intro_bottom_1))
                .setForegroundColor(Color.parseColor("#666666")).setFontSize(14, true)
                .append("    ").appendLine(AndroidUtil.getString(R.string.intro_bottom_2))
                .setForegroundColor(Color.parseColor("#666666")).setFontSize(14, true)
                .append("    ").appendLine(AndroidUtil.getString(R.string.intro_bottom_3))
                .setForegroundColor(Color.parseColor("#666666")).setFontSize(14, true)
                .create()

    }
}