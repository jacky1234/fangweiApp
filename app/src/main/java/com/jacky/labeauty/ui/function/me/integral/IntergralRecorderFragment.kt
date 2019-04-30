package com.jacky.labeauty.ui.function.me.integral

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.IntegralRecorder
import com.jacky.labeauty.logic.entity.response.HttpPageResponse
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.log.Logger
import com.jacky.labeauty.ui.adapter.IntegralRecorderAdapter
import com.jacky.labeauty.ui.inner.arch.MySupportFragment
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.layout_recylerview_with_refresh.*


class IntergralRecorderFragment : MySupportFragment(), OnRefreshListener, OnLoadMoreListener {
    var page = 0
    lateinit var type: String
    lateinit var adapter: IntegralRecorderAdapter

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        request()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        request()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments!!.getString(TYPE_KEY)
        adapter = IntegralRecorderAdapter(type, R.layout.item_integral_recorder)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_intergral_recorder, container, false)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadMoreListener(this)

        request()
    }

    @SuppressLint("CheckResult")
    private fun request() {
        RequestHelper.get().requestIntegrals(page, type)
                .compose(bindUntilDetach())
                .subscribe(
                        { t -> handleResult(t) },
                        {
                            handleError(it)
                        })
    }

    private fun handleError(it: Throwable) {
        if (page > 0) {
            page--
        }
        Logger.e(it)
        refreshLayout.finishLoadMore(true)
        refreshLayout.finishRefresh(true)
    }

    private fun handleResult(response: HttpPageResponse<IntegralRecorder>) {
        if (page == 0) {
            adapter.setNewData(response.data)
        } else {
            adapter.addData(response.data)
        }

        refreshLayout.finishLoadMore(true)
        refreshLayout.finishRefresh(true)
    }

    companion object {
        private const val TYPE_KEY = "TYPE_KEY"
        const val TYPE_IN = "IN"
        const val TYPE_OUT = "OUT"
        const val TYPE_WHOLE = ""

        @JvmStatic
        fun newInstance(type: String): IntergralRecorderFragment {
            val fragment = IntergralRecorderFragment()
            val bundle = Bundle()
            bundle.putString(TYPE_KEY, type)
            fragment.arguments = bundle
            return fragment
        }
    }
}