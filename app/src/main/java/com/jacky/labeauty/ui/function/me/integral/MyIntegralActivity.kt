package com.jacky.labeauty.ui.function.me.integral

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.ui.adapter.page.TabLayoutAdapter
import com.jacky.labeauty.ui.function.me.LuckyPanelActivity
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import kotlinx.android.synthetic.main.activity_my_integral.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

class MyIntegralActivity : BaseActivity() {
    private val tabNames = ArrayList<String>(2)
    private val fragments = ArrayList<IntegralRecorderFragment>(2)


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_integral)
        tabNames.add("积分获取记录")
        tabNames.add("积分消费记录")
        fragments.add(IntegralRecorderFragment.newInstance(IntegralRecorderFragment.TYPE_IN))
        fragments.add(IntegralRecorderFragment.newInstance(IntegralRecorderFragment.TYPE_OUT))

        titleView.setLeftAction(View.OnClickListener { finish() })

        val commonNavigator = CommonNavigator(this)
        commonNavigator.scrollPivotX = 0.25f
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount() = 2

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitleView(context)
//                val paint = simplePagerTitleView.paint
//                paint.isFakeBoldText = true

                simplePagerTitleView.text = tabNames[index]
                simplePagerTitleView.normalColor = Color.parseColor("#8b8b8d")
                simplePagerTitleView.selectedColor = ContextCompat.getColor(context, R.color.labe_blue)
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                simplePagerTitleView.setOnClickListener { viewPager.currentItem = index }
                simplePagerTitleView.height = AndroidUtil.dip2px(55F).toInt()
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.lineWidth = AndroidUtil.dip2px(65F)
                indicator.lineHeight = AndroidUtil.dip2px(4F)
                indicator.setColors(ContextCompat.getColor(context, R.color.labe_blue))
                return indicator
            }
        }

        magic_indicator.navigator = commonNavigator

        val tabLayoutAdapter = TabLayoutAdapter(supportFragmentManager, tabNames, fragments as List<Fragment>)
        viewPager.adapter = tabLayoutAdapter
        ViewPagerHelper.bind(magic_indicator, viewPager)

        tvTryLucky.clickWithTrigger {
            LuckyPanelActivity.launchForResult(this@MyIntegralActivity)
        }
    }

    @SuppressLint("CheckResult")
    override fun onResume() {
        super.onResume()
        RequestHelper.get().requestSign()
                .compose(bindToDestroy())
                .subscribe {
                    requestIntegral()
                    fragments.forEach {
                        it.setSinged()
                    }
                }
    }

    @SuppressLint("CheckResult")
    private fun requestIntegral() {
        RequestHelper.get().requestMyIntegral()
                .compose(bindToDestroy())
                .subscribe {
                    tv_integral_count.text = it.bonusPoints.toString()
                }
    }


}