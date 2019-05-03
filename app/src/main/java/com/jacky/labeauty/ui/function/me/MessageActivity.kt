package com.jacky.labeauty.ui.function.me

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.ui.adapter.MessageAdapter
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import com.jacky.labeauty.ui.widget.decoration.DividerPaddingDecoration
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.layout_recylerview_with_refresh.*

class MessageActivity : BaseActivity(), OnRefreshListener, OnLoadMoreListener {

    private var page = 0
    lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        adapter = MessageAdapter(R.layout.item_message)

        val dividerPaddingDecoration = DividerPaddingDecoration(this
                , DividerPaddingDecoration.VERTICAL_LIST, AndroidUtil.dip2px(10f).toInt())
        recyclerView.addItemDecoration(dividerPaddingDecoration)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadMoreListener(this)
        titleView.setLeftAction(View.OnClickListener { finish() })

        noticeMsgRead()
        requestMessages()
    }

    private fun noticeMsgRead() {
        RequestHelper.get().noticeMsgRead()
                .subscribe()
    }

    @SuppressLint("CheckResult")
    private fun requestMessages() {
        RequestHelper.get().requestMessages(page)
                .compose(bindToDestroy())
                .subscribe {
                    if (page == 0) {
                        adapter.setNewData(it.data)
                    } else {
                        adapter.addData(it.data)
                    }

                    refreshLayout.finishLoadMore(true)
                    refreshLayout.finishRefresh(true)
                    if (it.isLast) {
                        refreshLayout.setNoMoreData(true)
                    }
                }
    }


    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        requestMessages()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        requestMessages()
    }
}