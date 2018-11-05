package com.jacky.beedee.ui.function.main

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.beedee.R
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.ui.inner.arch.MySupportFragment

/**
 * 2018/11/1.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class HomeFragment : MySupportFragment() {
    lateinit var recyclerView: RecyclerView

    private fun requestBanner() {
        RequestHelper.get().requestBannerList()
                .compose(bindUntilDetach())
                .subscribe {

                }
    }

    private fun requestOutfitGoods() {
        RequestHelper.get().requestOutfitGoods()
                .compose(bindUntilDetach())
                .subscribe {

                }
    }

    private fun requestHotGoods() {
        RequestHelper.get().requestHotGoods()
                .compose(bindUntilDetach())
                .subscribe {

                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val content = inflater.inflate(R.layout.fragment_home, null)
        recyclerView = content.findViewById(R.id.recyclerView)

        return content
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        requestBanner()
        requestOutfitGoods()
        requestHotGoods()
    }
}