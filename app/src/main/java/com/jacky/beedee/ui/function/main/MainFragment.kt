package com.jacky.beedee.ui.function.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.beedee.R
import com.jacky.beedee.ui.function.defake.DefakeFragment
import com.jacky.beedee.ui.function.discovery.DiscoveryFragment
import com.jacky.beedee.ui.function.me.Mefragment
import com.jacky.beedee.ui.inner.arch.MySupportFragment
import com.jacky.beedee.ui.widget.bottombar.BottomBarLayout
import com.jacky.beedee.ui.widget.bottombar.BottomBarTab

class MainFragment : MySupportFragment() {
    private val mFragments = ArrayList<MySupportFragment>(4)
    private lateinit var bottomBarLayout: BottomBarLayout
    private var tabIndex = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = LayoutInflater.from(activity).inflate(R.layout.fragment_main, null)
        val homeFragment = findChildFragment(HomeFragment::class.java)
        mFragments.clear()
        if (homeFragment == null) {
            mFragments.add(HomeFragment())
            mFragments.add(DiscoveryFragment())
            mFragments.add(DefakeFragment())
            mFragments.add(Mefragment())

            loadMultipleRootFragment(R.id.frameLayout, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3])
        } else {
            mFragments.add(homeFragment)
            mFragments.add(findChildFragment(DiscoveryFragment::class.java)!!)
            mFragments.add(findChildFragment(DefakeFragment::class.java)!!)
            mFragments.add(findChildFragment(Mefragment::class.java)!!)
        }

        showHideFragment(mFragments[tabIndex])

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

        bottomBarLayout.setOnTabSelectedListener(object : BottomBarLayout.OnTabSelectedListener {
            override fun onTabUnselected(position: Int) {

            }

            override fun onTabReselected(position: Int) {
            }

            override fun onTabSelected(position: Int, prePosition: Int) {
                if (!mFragments[position].isAttached() || !mFragments[prePosition].isAttached()) {
                    return
                }
                tabIndex = position
                showHideFragment(mFragments[position], mFragments[prePosition])
            }

        })

        bottomBarLayout.setOnTabDoubleClickListener {

        }

    }
}