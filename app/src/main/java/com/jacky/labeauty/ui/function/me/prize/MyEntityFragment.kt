package com.jacky.labeauty.ui.function.me.prize

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
import com.jacky.labeauty.ui.adapter.MyDiscountsAdapter
import com.jacky.labeauty.ui.adapter.MyEntityPrizeAdapter
import com.jacky.labeauty.ui.inner.arch.MySupportFragment
import com.jacky.labeauty.ui.widget.EmptyView
import com.jacky.labeauty.ui.widget.decoration.DividerPaddingDecoration
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.layout_recylerview_with_refresh.*

/**
 * 实物奖品展示
 */
class MyEntityFragment : MySupportFragment(), OnRefreshListener, OnLoadMoreListener {
    private var adapter: MyEntityPrizeAdapter? = null
    private var page = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_entity, container, false)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadMoreListener(this)
        adapter = MyEntityPrizeAdapter(R.layout.item_discount)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val span = AndroidUtil.dip2px(15f).toInt()
        recyclerView.addItemDecoration(DividerPaddingDecoration(context, LinearLayoutManager.VERTICAL, span))
        recyclerView.adapter = adapter
        requestDiscounts()

        adapter?.setOnItemClickListener { adapter, _, position ->
            //detail
//            DialogTipsHelper.showDiscountDetailDialog(fragmentManager!!)

            goToDetail(adapter, position)
        }

        val emptyView = EmptyView(context!!)
        emptyView.setImageResource(R.drawable.empty_prize)
        emptyView.setDescID(R.string.empty_entity_prize)
        adapter?.emptyView = emptyView
        adapter?.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            //            goToDetail(adapter, position)
        }
    }

    private fun goToDetail(adapter: BaseQuickAdapter<*, *>?, position: Int) {
        val cast = adapter as MyDiscountsAdapter
        val discount = cast.getItem(position)
        if (discount != null) {
            val parentFragment = fragmentManager!!.fragments[0] as MySupportFragment
            parentFragment.start(MyDiscountDetailFragment.newInstance(discount))
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