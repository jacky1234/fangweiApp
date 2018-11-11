package com.jacky.beedee.ui.function.main

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.beedee.R
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.ui.adapter.NewHotsGoodAdapter
import com.jacky.beedee.ui.function.discovery.GoodDetailActivity
import com.jacky.beedee.ui.inner.arch.MySupportFragment
import com.jacky.beedee.ui.widget.decoration.GridLayoutPaddingItemDecoration
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_new_hots_good.*
import kotlinx.android.synthetic.main.layout_recylerview_with_refresh.*

/**
 * 2018/11/9.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class NewHotsGoodFragment : MySupportFragment(), OnRefreshListener, OnLoadMoreListener {
    private lateinit var adapter: NewHotsGoodAdapter
    private var page = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_hots_good, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadMoreListener(this)
        adapter = NewHotsGoodAdapter(R.layout.item_good)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        val span = AndroidUtil.dip2px(15f).toInt()
        recyclerView.addItemDecoration(GridLayoutPaddingItemDecoration(span))
        recyclerView.adapter = adapter
        requestGoods()

        titleView.setLeftAction(View.OnClickListener {
            activity!!.finish()
        })

        adapter.setOnItemClickListener { _, _, position ->
            GoodDetailActivity.start(_mActivity, adapter.getItem(position)!!.id)
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        requestGoods()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        requestGoods()
    }

    private fun requestGoods() {
        RequestHelper.get().requestHotGoods()
                .compose(bindUntilDetach())
                .subscribe {
                    if (page == 0) {
                        adapter.setNewData(it.content)
                        refreshLayout.finishRefresh(true)
                    } else {
                        if (it.content.isEmpty()) {
                            refreshLayout.finishLoadMoreWithNoMoreData()
                        } else {
                            refreshLayout.finishLoadMore(true)
                        }
                    }
                }
    }
}