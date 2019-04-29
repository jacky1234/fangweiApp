package com.jacky.labeauty.ui.function.other

import android.os.Bundle
import android.view.View
import com.jacky.labeauty.R
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
        tvRegisterProtocol.text = getInfo()
    }

    private fun getInfo(): CharSequence {
        return SpanUtils()
                .appendLine()
                .append("ANCILA").setFontSize(28, true).setBold().appendLine()
                .append("        是一个")
                .append("蕴含生物科技的日本美容院线专业抗衰老品牌，致力于从生物科技中探索保持青春的生物密码。").setBold()
                .append("隶属日本LABEAUTY公司旗下品牌。（LABEAUTY是日本一家化妆品、美容仪器公司，于2013年在日本横滨成立，以”充满自信的美丽“为宗旨，开发和销售针对各种皮肤类型，量身定制不同的高端化妆品。）\n").setFontSize(14, true)
                .appendLine()
                .append("        ANCILA不仅成为日本女性冻龄护肤的秘密，并于2018年成功入驻中国市场，受到了广大消费者的喜爱。品牌主打灯塔水母系列，坚持纯粹高效的护肤态度，实现了产品的深层补水和修复亮白。其中爆款水母面膜和逆时空水母胶原蛋白水乳霜逐渐在市场上有了一定影响力，受到了张韶涵、沈梦辰等明星的倾力推荐。\n").setFontSize(14, true)
                .appendLine()
                .append("        以“成为高端护肤界的领先者”为企业愿景，ANCILA一直秉承“创新护肤、合理护肤”的新理念，希望把正确的护肤理念传递给有所追求的消费者。").setFontSize(14, true)
                .appendLine().appendLine()
                .create()

    }
}