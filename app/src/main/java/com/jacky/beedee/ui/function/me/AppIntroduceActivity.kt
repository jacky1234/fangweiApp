package com.jacky.beedee.ui.function.me

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.jacky.beedee.R
import com.jacky.beedee.support.Starter
import com.jacky.beedee.support.util.SpanUtils
import com.jacky.beedee.ui.inner.arch.BaseActivity
import kotlinx.android.synthetic.main.activity_app_introduce.*

class AppIntroduceActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_introduce)

        titleView.setLeftAction(View.OnClickListener { finish() })

        textView.text = getInfo()
    }

    private fun getInfo(): CharSequence {
        return SpanUtils()
                .appendLine().appendLine().appendLine()
                .append("BEEDEE").setFontSize(28, true).setBold().appendLine().appendLine()
                .append("为了净化市场，减少假冒产品给我们顾客及BEEDEE带来的负面影响和损失，维护企业的形象和声誉，增强消费者购正规商品的信心我们亲历推出本款APP，以便于每个消费者都成为打假的行家，BEEDEE作为原创品牌我们坚持创新，坚持原创，希望把不一样的设计理念传递给有所追求的你们。近年来广大消费者及许多企业饱受假冒坑害之苦，有关调查资料表明，90%以上的消费者和几乎所有名牌产品的生产企业均曾受到过假冒的侵扰。")
                .setForegroundColor(Color.parseColor("#292929")).setFontSize(14, true)
                .appendLine().appendLine().appendLine()
                .append("当前，我国的假冒有以下几个特点：").setForegroundColor(ContextCompat.getColor(Starter.getContext(), R.color.black)).setFontSize(16, true)
                .appendLine().appendLine()
                .append("特点之一：").setForegroundColor(ContextCompat.getColor(Starter.getContext(), R.color.black)).setFontSize(15, true).appendLine()
                .append("假冒商品品种多、数量大。从生产资料到生活日用品，从内销到外贸出口，从一般到高档耐用消费者，从日常生活用品到高科技产品，假冒伪劣几乎无所不有，尤以制作利润高、销售快的假冒名烟、名酒和药品的问题最为严重。")
                .setForegroundColor(Color.parseColor("#666666")).setFontSize(14, true).appendLine()

                .append("\n特点之二：").setForegroundColor(ContextCompat.getColor(Starter.getContext(), R.color.black)).setFontSize(15, true).appendLine()
                .append("出现区域性“产、供、销”一条龙假冒地，违法活动更具有稳蔽性、流动性。有的地方造假已形成相当规模，有的已形成“专业村”、“集散地”、“黑窝地”，并有人提供仓库、银行帐号、代办运输等，显然是有组织的犯罪活动，具有很强的再生能力和扩散能力。由于国内打击严厉，相当一部分造假活动已发展到境内外勾结，在境外制造，通过走私偷运到国内销售，人称“走私假冒商品")
                .setForegroundColor(Color.parseColor("#666666")).setFontSize(14, true).appendLine()

                .append("\n特点之三：").setForegroundColor(ContextCompat.getColor(Starter.getContext(), R.color.black)).setFontSize(15, true).appendLine()
                .append("假冒国外名牌的问题日益突出。")
                .setForegroundColor(Color.parseColor("#666666")).setFontSize(14, true).appendLine()

                .append("\n特点之四：").setForegroundColor(ContextCompat.getColor(Starter.getContext(), R.color.black)).setFontSize(15, true).appendLine()
                .append("重大的恶性案件增多，违法数额攀升。假冒伪劣品对消费者及生产厂家的危害主要表现为：侵害名牌商标形象，真假难辨使消费者和用户望而生畏；严重影响名牌企业的经济效益；严重败坏出口商品的信誉，对我国国际贸易造成不良的影响；名牌产品被挤出了市场，使企业面监停产、甚至陷入破产倒闭的窘境等。因此，广大消费者和企业应增强自我保护意识，在打击假冒，保卫名牌活动中奋起自卫。")
                .setForegroundColor(Color.parseColor("#666666")).setFontSize(14, true).appendLine()
                .appendLine().appendLine().appendLine()
                .appendLine().appendLine().appendLine()
                .create()
    }
}