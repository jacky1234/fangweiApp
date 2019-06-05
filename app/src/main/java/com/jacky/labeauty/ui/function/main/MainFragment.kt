package com.jacky.labeauty.ui.function.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.labeauty.R
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.ui.function.defake.DefakeFragment
import com.jacky.labeauty.ui.function.discovery.DiscoveryFragment
import com.jacky.labeauty.ui.function.me.Mefragment
import com.jacky.labeauty.ui.inner.arch.MySupportFragment
import com.jacky.labeauty.ui.widget.bottombar.BottomBarLayout
import com.jacky.labeauty.ui.widget.bottombar.BottomBarTab
import java.util.*

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
                .addItem(BottomBarTab(activity, R.mipmap.ic_tab_home, R.mipmap.ic_tab_home_selected, AndroidUtil.getString(R.string.home)))
                .addItem(BottomBarTab(activity, R.mipmap.ic_tab_discovery, R.mipmap.ic_tab_discovery_selected, AndroidUtil.getString(R.string.discovery)))
                .addItem(BottomBarTab(activity, R.mipmap.ic_tab_defake, R.mipmap.ic_tab_defake_selected, AndroidUtil.getString(R.string.inspect_defake)))
                .addItem(BottomBarTab(activity, R.mipmap.ic_tab_me, R.mipmap.ic_tab_me_selected, AndroidUtil.getString(R.string.mine)))

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mFragments.forEach {
            it.onActivityResult(requestCode, resultCode, data)
        }
    }
}