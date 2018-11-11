package com.jacky.beedee.ui.function.me.favorite

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.beedee.R
import com.jacky.beedee.logic.entity.module.Favorite
import com.jacky.beedee.logic.entity.module.GoodType
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.ui.adapter.FavoriteGoodAdapter
import com.jacky.beedee.ui.function.discovery.GoodDetailActivity
import com.jacky.beedee.ui.inner.arch.MySupportFragment
import com.jacky.beedee.ui.widget.decoration.GridLayoutPaddingItemDecoration
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.layout_recylerview_with_refresh.*

class FavoriteGoodFragment : MySupportFragment(), OnRefreshListener, OnLoadMoreListener {
    private var page = 0
    lateinit var adapter: FavoriteGoodAdapter
    private val padding = AndroidUtil.dip2px(15f).toInt()

    override fun onRefresh(refreshLayout: RefreshLayout) {
        requestList(false)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        requestList(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        recyclerView.layoutManager = GridLayoutManager(context,2)
        recyclerView.addItemDecoration(GridLayoutPaddingItemDecoration(padding))

        adapter = FavoriteGoodAdapter(R.layout.item_good)
        adapter.setOnItemClickListener { _, _, position ->
            adapter.getItem(position)!!.target?.let {
                GoodDetailActivity.start(_mActivity, adapter.getItem(position)!!.target.id)
            }
        }
        recyclerView.adapter = adapter
        requestList(false)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadMoreListener(this)
    }

    private fun requestList(loadMore: Boolean) {
        RequestHelper.get().requestCollectList(GoodType.GOODS, page)
                .compose(bindUntilDetach())
                .subscribe {
                    if (page == 0) {
                        adapter.setNewData(it.content)
                    } else {
                        adapter.addData(it.content)
                    }

                    if (loadMore) {
                        refreshLayout.finishLoadMore(true)
                        page++
                    } else {
                        refreshLayout.finishRefresh(true)
                        page = 0
                    }
                }
    }
}