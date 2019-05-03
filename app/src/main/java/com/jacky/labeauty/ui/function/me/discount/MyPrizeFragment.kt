package com.jacky.labeauty.ui.function.me.discount

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.labeauty.R
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.ui.adapter.page.TabLayoutAdapter
import com.jacky.labeauty.ui.function.me.prize.MyDiscountFragment
import com.jacky.labeauty.ui.inner.arch.MySupportFragment
import kotlinx.android.synthetic.main.fragment_my_prize.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

class MyPrizeFragment : MySupportFragment() {
    private val tabNames = ArrayList<String>(2)
    private val fragments = ArrayList<Fragment>(2)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_prize, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleView.setLeftAction(View.OnClickListener { activity!!.finish() })

        tabNames.add("优惠券")
        tabNames.add("实物奖品")
        fragments.add(MyDiscountFragment())
        fragments.add(MyDiscountFragment())

        val commonNavigator = CommonNavigator(context)
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

        val tabLayoutAdapter = TabLayoutAdapter(fragmentManager, tabNames, fragments as List<Fragment>)
        viewPager.adapter = tabLayoutAdapter
        ViewPagerHelper.bind(magic_indicator, viewPager)
    }
}