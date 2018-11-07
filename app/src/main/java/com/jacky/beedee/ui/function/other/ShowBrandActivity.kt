package com.jacky.beedee.ui.function.other

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.jacky.beedee.R
import com.jacky.beedee.support.util.SpanUtils
import com.jacky.beedee.ui.inner.arch.BaseActivity
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
        textView.text = getInfo()
    }

    private fun getInfo(): CharSequence {
        return SpanUtils().append("BEEDEE").setFontSize(28, true).setBold().appendLine()
                .append("SOMETHING DIFFERENT").setForegroundColor(Color.parseColor("#BFBEBE")).setFontSize(14, true)
                .appendLine()
                .append("Pursuit the 1 % Life \n" +
                        "Concept There are 80% of the people in the world lead \n" +
                        "normal lives.\n" +
                        "And 20 % of the people will pursuit for what they want. \n" +
                        "Maybe only 1 % of them made it to the end.\n" +
                        "Luckily,they're the ones who successed. \n" +
                        "Hoping we can be that 1 %.\n" +
                        "BeeDee as an original designer brand, stick to \n" +
                        "innovation, stick to originalityTo share what we want.\n" +
                        "We want to pass on a different design concept to those \n" +
                        "of you who want it.\n" +
                        "Thanks to the 1 %  who will always be with us.\n").setForegroundColor(Color.parseColor("#666666")).setFontSize(13, true)
                .appendLine()
                .append("品牌理念").setFontSize(19, true).setBold()
                .appendLine().appendLine()
                .append("追求1%的生活理念\n" +
                        "世上总有80%的人过着平凡的生活\n" +
                        "20%的人会去追求自己想要的生活\n" +
                        "或许只有1%的人坚持到最后\n" +
                        "新云的是他们成功了\n" +
                        "希望我们成为1%的那些人\n" +
                        "BeeDee作为设计师原创品牌，坚持创新，坚持原创\n" +
                        "追求我们想要的感觉\n" +
                        "希望把不一样的设计理念传递给有所追求的你们\n" +
                        "感谢那些永远跟随我们的1%\n")
                .create()

    }
}