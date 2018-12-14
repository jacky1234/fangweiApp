package com.jacky.beedee.ui.function.me.favorite

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.beedee.R
import com.jacky.beedee.logic.MiscFacade
import com.jacky.beedee.logic.entity.module.GoodType
import com.jacky.beedee.logic.entity.module.MySelf
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.support.ext.launch
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.ui.Dialog.DialogHelper
import com.jacky.beedee.ui.adapter.FavoriteOutFitAdapter
import com.jacky.beedee.ui.function.login.LoginActivity
import com.jacky.beedee.ui.function.main.OutFitDetailActivity
import com.jacky.beedee.ui.inner.arch.MySupportFragment
import com.jacky.beedee.ui.widget.EmptyView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.layout_recylerview_with_refresh.*

class FavoriteOutFitFragment : MySupportFragment(), OnRefreshListener, OnLoadMoreListener {
    private var page = 0
    lateinit var adapter: FavoriteOutFitAdapter
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
        adapter = FavoriteOutFitAdapter(R.layout.item_outfit_favorite)
        adapter.emptyView = EmptyView(context!!, R.mipmap.ic_collect_blank)
        adapter.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.cbLike) {
                if (MySelf.get().isLogined) {
                    DialogHelper.createSimpleConfirmDialog(_mActivity, "确定取消收藏吗？") { _, _ ->
                        val item = adapter.getItem(position)!!
                        RequestHelper.get().uncollectItem(GoodType.OUTFIT, item.id)
                                .compose(bindUntilDetach())
                                .subscribe {
                                    adapter.remove(position)
                                }
                    }.show()
                } else {
                    MiscFacade.get().setLastRunnable {
                        if (isAttached()) {
                            requestList(false)
                        }
                    }
                    activity.launch<LoginActivity>()
                }
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                val position = parent.getChildAdapterPosition(view)
                if (position != 0 && position != parent.adapter.itemCount - 1) {
                    outRect.set(0, 0, 0, AndroidUtil.dip2px(15f).toInt())
                }
            }
        })
        adapter.setOnItemClickListener { _, _, position ->
            adapter.getItem(position)!!.target?.let {
                OutFitDetailActivity.start(_mActivity, adapter.getItem(position)!!.target.id)
            }
        }
        recyclerView.adapter = adapter
        requestList(false)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadMoreListener(this)
    }

    private fun requestList(loadMore: Boolean) {
        RequestHelper.get().requestCollectList(GoodType.OUTFIT, page)
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