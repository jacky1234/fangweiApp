package com.jacky.beedee.ui.fragment

import android.view.LayoutInflater
import android.view.View
import com.jacky.beedee.R
import com.jacky.beedee.ui.inner.arch.BaseFragment
import com.jacky.beedee.ui.widget.bottombar.BottomBarTab
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {
    override fun onCreateView(): View {
        val layout = LayoutInflater.from(activity).inflate(R.layout.fragment_home, null)
        layout.setOnClickListener({

        })
        initTabs()
        return layout
    }

    private fun initTabs() {
        bottomBar
                .addItem(BottomBarTab(activity,R.mipmap.ic_tab_home,R.mipmap.ic_tab_home_selected,"首页"))
                .addItem(BottomBarTab(activity,R.mipmap.ic_tab_discovery,R.mipmap.ic_tab_discovery_selected,"发现"))
                .addItem(BottomBarTab(activity,R.mipmap.ic_tab_defake,R.mipmap.ic_tab_defake_selected,"防伪检测"))
                .addItem(BottomBarTab(activity,R.mipmap.ic_tab_me,R.mipmap.ic_tab_me_selected,"我的"))
//        bottomBar.setOnTabSelectedListener(BottomBarLayout.OnTabDoubleClickListener(){
//
//        })
    }
}