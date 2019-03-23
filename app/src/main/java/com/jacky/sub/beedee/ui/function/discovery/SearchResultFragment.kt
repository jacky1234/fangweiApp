package com.jacky.sub.beedee.ui.function.discovery

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.sub.beedee.R
import com.jacky.sub.beedee.logic.entity.module.GoodItem
import com.jacky.sub.beedee.logic.network.RequestHelper
import com.jacky.sub.beedee.support.util.AndroidUtil
import com.jacky.sub.beedee.ui.adapter.SearchResultAdapter
import com.jacky.sub.beedee.ui.inner.arch.MySupportFragment
import com.jacky.sub.beedee.ui.widget.EmptyView
import kotlinx.android.synthetic.main.fragment_search_result.*

/**
 * 2018/11/10.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class SearchResultFragment : MySupportFragment() {
    private lateinit var adapter: SearchResultAdapter
    private val list = ArrayList<GoodItem>()

    fun requestSearchKey(key: String) {
        RequestHelper.get().requestSearchGood(key)
                .compose(bindUntilDetach())
                .subscribe {
                    if (it.content.isEmpty()) {
                        AndroidUtil.toast("无搜索结果")
                    }
                    list.addAll(it.content)
                    adapter.notifyDataSetChanged()
                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchResultAdapter(R.layout.item_search_result, list)
        adapter.emptyView = EmptyView(context!!, R.mipmap.ic_search_blank)
        adapter.setOnItemClickListener { _, _, position ->
            val item = list[position]
            GoodDetailActivity.start(_mActivity, item.id)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        reenterTransition
    }
}