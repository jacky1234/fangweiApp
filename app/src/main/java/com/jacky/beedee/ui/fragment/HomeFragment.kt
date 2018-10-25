package com.jacky.beedee.ui.fragment

import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import com.jacky.beedee.R
import com.jacky.beedee.ui.inner.arch.BaseFragment
import com.qmuiteam.qmui.util.QMUIResHelper
import com.qmuiteam.qmui.widget.QMUITabSegment

class HomeFragment : BaseFragment() {
    private lateinit var mTabSegment: QMUITabSegment

    override fun onCreateView(): View {
        val layout = LayoutInflater.from(activity).inflate(R.layout.fragment_home, null)
        initTabs(layout)
        return layout
    }

    private fun initTabs(view: View) {
        mTabSegment = view.findViewById(R.id.tabs)
        val normalColor = QMUIResHelper.getAttrColor(activity, R.attr.qmui_config_color_gray_6)
        val selectColor = QMUIResHelper.getAttrColor(activity, R.attr.qmui_config_color_blue)
        mTabSegment.setDefaultNormalColor(normalColor)
        mTabSegment.setDefaultSelectedColor(selectColor)

        val home = QMUITabSegment.Tab(
                ContextCompat.getDrawable(context!!, R.mipmap.ic_tab_home),
                ContextCompat.getDrawable(context!!, R.mipmap.ic_tab_home_selected),
                "首页", false
        )

        val discovery = QMUITabSegment.Tab(
                ContextCompat.getDrawable(context!!, R.mipmap.ic_tab_discovery),
                ContextCompat.getDrawable(context!!, R.mipmap.ic_tab_discovery_selected),
                "发现", false
        )
        val deFake = QMUITabSegment.Tab(
                ContextCompat.getDrawable(context!!, R.mipmap.ic_tab_defake),
                ContextCompat.getDrawable(context!!, R.mipmap.ic_tab_defake_selected),
                "防伪检测", false
        )

        val me = QMUITabSegment.Tab(
                ContextCompat.getDrawable(context!!, R.mipmap.ic_tab_me),
                ContextCompat.getDrawable(context!!, R.mipmap.ic_tab_me_selected),
                "我的", false
        )
        mTabSegment.addTab(home)
                .addTab(discovery)
                .addTab(deFake)
                .addTab(me)
    }
}