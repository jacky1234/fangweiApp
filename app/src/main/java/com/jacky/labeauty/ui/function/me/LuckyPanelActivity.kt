package com.jacky.labeauty.ui.function.me

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.Prize
import com.jacky.labeauty.logic.entity.module.PrizeLog
import com.jacky.labeauty.logic.entity.response.PrizeResponse
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.log.Logger
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.support.util.SpanUtils
import com.jacky.labeauty.ui.dialog.DialogTipsHelper
import com.jacky.labeauty.ui.dialog.LuckyBoxDialog
import com.jacky.labeauty.ui.function.me.prize.ExtractPrizeActivity
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import com.jacky.luckyfortune.LuckyMonkeyPanelView
import kotlinx.android.synthetic.main.activity_lucky_panel.*

class LuckyPanelActivity : BaseActivity(), LuckyMonkeyPanelView.Observer {

    companion object {
        const val REQUEST_LUCKY_CODE = 100

        fun launchForResult(from: Activity) {
            val intent = Intent(from, LuckyPanelActivity::class.java)
            from.startActivityForResult(intent, REQUEST_LUCKY_CODE)
        }
    }

    private var prizeResponse: PrizeResponse? = null
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lucky_panel)

        titleView.setLeftAction(View.OnClickListener { finish() })

        integral_desc_title.text = SpanUtils()
                .append("活动").setForegroundColor(resources.getColor(R.color.labe_blue)).setFontSize(17, true)
                .append("说明").setForegroundColor(Color.parseColor("#8b8b8d")).setFontSize(17, true)
                .create()

        integral_desc_content.text = SpanUtils()
                .appendImage(R.drawable.image_one).append("  您获得的积分将自动存入您的账户内；").setForegroundColor(Color.BLACK).setFontSize(12, true)
                .appendLine()
                .appendLine()
                .appendImage(R.drawable.image_two).append("  如果您获得的是实物奖品，请您及时填写地址，我们将送到您的手上；").setForegroundColor(Color.BLACK).setFontSize(12, true)
                .appendLine()
                .appendLine()
                .appendImage(R.drawable.image_three).append("  本次活动最终解释权归我公司所有。").setForegroundColor(Color.BLACK).setFontSize(12, true)
                .appendLine()
                .appendLine()
                .create()

        lucky_panel.setAction {
            if (!lucky_panel.isGameRunning) {
                prizeResponse = null
                requestPrizes()
                lucky_panel.startGame()

                AndroidUtil.runUI({
                    if (isFinishing) {
                        return@runUI
                    }

                    val response = prizeResponse
                    if (response != null) {
                        lucky_panel.tryToStop(response.prizeIndex)
                    } else {
                        AndroidUtil.toast("数据还没有准备好，请稍后")
                    }
                }, 2500)
            }
        }
        lucky_panel.addObserver(this)
    }

    override fun onDestroy() {
        lucky_panel.removeObserver(this)
        super.onDestroy()
    }

    override fun onPanelStatusChange(status: Int) {
        if (status == LuckyMonkeyPanelView.Observer.STOPPED) {
            val response = prizeResponse
            if (response != null) {
                val prize = response.prizes[response.prizeIndex]
                AndroidUtil.runUI({
                    if (!isFinishing) {
                        onOpenLuckyBox(prize, response.prizeLog)
                        this@LuckyPanelActivity.prizeResponse = null
                    }
                }, 200)
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun requestPrizes() {
        RequestHelper.get().requestPrizes()
                .compose(bindToDestroy())
                .subscribe {
                    this@LuckyPanelActivity.prizeResponse = it
                    Logger.i(it.toString())
                    //warm the cache
                    val prize = it.prizes[it.prizeIndex]
                    if (prize.targetType != Prize.TARGET_TYPE_EMPTY) {
                        Glide.with(this)
                                .download(prize.thumb)
                    }
                }
    }

    //0-8
    private fun onOpenLuckyBox(prize: Prize, prizeLog: PrizeLog) {
        DialogTipsHelper.createOpenLuckyBoxDialog(
                this, object : LuckyBoxDialog.OnGetPrizeClickListener {
            override fun getPrizeClickListener() {
                ExtractPrizeActivity.launch(this@LuckyPanelActivity, prize, prizeLog)
            }
        }, prize).show()
    }
}