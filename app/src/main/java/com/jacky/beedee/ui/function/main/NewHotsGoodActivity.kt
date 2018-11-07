package com.jacky.beedee.ui.function.main

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.jacky.beedee.R
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.ui.adapter.NewHotsGoodAdapter
import com.jacky.beedee.ui.inner.arch.BaseActivity
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.activity_new_hots_good.*

/**
 * 2018/11/6.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class NewHotsGoodActivity : BaseActivity(), OnRefreshListener {
    private var adapter: NewHotsGoodAdapter? = null
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_hots_good)

        refreshLayout.setOnRefreshListener(this)
        adapter = NewHotsGoodAdapter(R.layout.item_good)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
//        adapter?.emptyView =

        requestGoods()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        requestGoods()
    }

    private fun requestGoods() {
        RequestHelper.get().requestHotGoods()
                .compose(bindToDestroy())
                .subscribe {
                    if (page == 0) {
                        adapter?.setNewData(it.content)
                    }
                }
    }

}