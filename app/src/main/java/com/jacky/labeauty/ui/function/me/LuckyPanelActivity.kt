package com.jacky.labeauty.ui.function.me

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.jacky.labeauty.R
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.support.util.SpanUtils
import com.serenegiant.common.BaseActivity
import kotlinx.android.synthetic.main.activity_lucky_panel.*
import java.util.*

class LuckyPanelActivity : BaseActivity() {
    companion object {
        const val REQUEST_LUCKY_CODE = 100

        fun launchForResult(from: Activity) {
            val intent = Intent(from, LuckyPanelActivity::class.java)
            from.startActivityForResult(intent, REQUEST_LUCKY_CODE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lucky_panel)

        titleView.setLeftAction(View.OnClickListener { finish() })

        integral_desc_title.text = SpanUtils()
                .append("活动").setForegroundColor(Color.parseColor("#bed2e7")).setFontSize(14, true)
                .append("说明").setForegroundColor(Color.parseColor("#9c9a9c")).setFontSize(14, true)
                .create()

        integral_desc_content.text = SpanUtils()
                .appendImage(R.drawable.image_one).append("  您获得的积分将自动存入您的账户内；").setForegroundColor(Color.BLACK).setFontSize(12, true).appendLine()
                .appendImage(R.drawable.image_two).append("  如果您获得的是实物奖品，请您及时填写地址，我们将送到您的手上；").setForegroundColor(Color.BLACK).setFontSize(12, true).appendLine()
                .appendImage(R.drawable.image_three).append("  本次活动最终解释权归我公司所有。").setForegroundColor(Color.BLACK).setFontSize(12, true).appendLine()
                .create()

        lucky_panel.setAction {
            if (!lucky_panel.isGameRunning) {
                lucky_panel.startGame()
            } else {
                val stayIndex = Random().nextInt(8)
                lucky_panel.tryToStop(stayIndex)
                AndroidUtil.runUI({
                    if (!isFinishing) {
                        onOpenLuckyBox(stayIndex)
                    }
                }, 50)
            }
        }
    }

    //0-8
    private fun onOpenLuckyBox(index: Int) {

    }
}