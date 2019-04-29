package com.jacky.labeauty.ui.function.me.discount

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.ui.dialog.DialogTipsHelper
import com.jacky.labeauty.ui.inner.arch.MySupportFragment
import com.jacky.labeauty.ui.widget.decoration.DividerPaddingDecoration
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_my_discounts.*
import kotlinx.android.synthetic.main.layout_recylerview_with_refresh.*

class MyDiscountsFragment : MySupportFragment(), OnRefreshListener, OnLoadMoreListener {
    private var adapter: MyDiscountsAdapter? = null
    private var page = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_discounts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadMoreListener(this)
        adapter = MyDiscountsAdapter(R.layout.item_discount)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val span = AndroidUtil.dip2px(15f).toInt()
        recyclerView.addItemDecoration(DividerPaddingDecoration(context, LinearLayoutManager.VERTICAL, span))
        recyclerView.adapter = adapter
        requestDiscounts()

        titleView.setLeftAction(View.OnClickListener {
            activity!!.finish()
        })

        adapter?.setOnItemClickListener { _, _, position ->
            //detail
            DialogTipsHelper.showDiscountDetailDialog(fragmentManager!!)
        }
        adapter?.onItemChildClickListener = object : BaseQuickAdapter.OnItemChildClickListener {
            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                //to use discount

            }
        }
    }

    @SuppressLint("CheckResult")
    private fun requestDiscounts() {
        RequestHelper.get().requestDiscounts(page)
                .compose(bindUntilDetach())
                .subscribe {
                    if (page == 0) {
                        if (it.data != null) {
                            adapter?.setNewData(it.data)
                            refreshLayout.finishRefresh(true)
                        }
                    } else {
                        if (it.data != null) {
                            adapter?.addData(it.data)
                        }
                        if (it.isLast) {
                            refreshLayout.finishLoadMoreWithNoMoreData()
                        } else {
                            refreshLayout.finishLoadMore(true)
                        }
                    }
                }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        requestDiscounts()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        requestDiscounts()
    }
}