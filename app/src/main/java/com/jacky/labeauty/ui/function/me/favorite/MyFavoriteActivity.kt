package com.jacky.labeauty.ui.function.me.favorite

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import com.jacky.labeauty.R
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.ui.adapter.page.TabLayoutAdapter
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import kotlinx.android.synthetic.main.activity_favorite.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import java.util.*


class MyFavoriteActivity : BaseActivity() {
    private val tabNames = ArrayList<String>(2)
    private val fragments = ArrayList<Fragment>(2)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        tabNames.add("穿搭收藏")
        tabNames.add("产品收藏")
        fragments.add(FavoriteOutFitFragment())
        fragments.add(FavoriteGoodFragment())

        val commonNavigator = CommonNavigator(this)
        commonNavigator.scrollPivotX = 0.25f
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount() = 2

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.text = tabNames[index]
                simplePagerTitleView.normalColor = ContextCompat.getColor(context, R.color.tab_grey_color)
                simplePagerTitleView.selectedColor = ContextCompat.getColor(context, R.color.black)
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                simplePagerTitleView.setOnClickListener { view_pager.currentItem = index }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.lineWidth = AndroidUtil.dip2px(30F)
                indicator.lineHeight = AndroidUtil.dip2px(2F)
                indicator.setColors(Color.BLACK)
                return indicator
            }
        }

        magic_indicator.navigator = commonNavigator

        val tabLayoutAdapter = TabLayoutAdapter(supportFragmentManager, tabNames, fragments)
        view_pager.adapter = tabLayoutAdapter
        ViewPagerHelper.bind(magic_indicator, view_pager)
    }
}