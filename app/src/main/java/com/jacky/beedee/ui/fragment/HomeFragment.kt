package com.jacky.beedee.ui.fragment

import android.view.LayoutInflater
import android.view.View
import com.jacky.beedee.R
import com.jacky.beedee.ui.inner.arch.BaseFragment
import com.jacky.beedee.ui.widget.bottombar.BottomBarLayout
import com.jacky.beedee.ui.widget.bottombar.BottomBarTab

class HomeFragment : BaseFragment() {
    private lateinit var bottomBarLayout: BottomBarLayout
    override fun onCreateView(): View {
        val layout = LayoutInflater.from(activity).inflate(R.layout.fragment_home, null)
        initTabs(layout)
        return layout
    }

    private fun initTabs(view: View) {
        bottomBarLayout = view.findViewById(R.id.bottomBar)
        bottomBarLayout
                .addItem(BottomBarTab(activity, R.mipmap.ic_tab_home, R.mipmap.ic_tab_home_selected, "首页"))
                .addItem(BottomBarTab(activity, R.mipmap.ic_tab_discovery, R.mipmap.ic_tab_discovery_selected, "发现"))
                .addItem(BottomBarTab(activity, R.mipmap.ic_tab_defake, R.mipmap.ic_tab_defake_selected, "防伪检测"))
                .addItem(BottomBarTab(activity, R.mipmap.ic_tab_me, R.mipmap.ic_tab_me_selected, "我的"))

        bottomBarLayout.setOnTabSelectedListener(object :BottomBarLayout.OnTabSelectedListener {
            override fun onTabUnselected(position: Int) {

            }

            override fun onTabReselected(position: Int) {
            }

            override fun onTabSelected(position: Int, prePosition: Int) {

            }

        })

    }
}