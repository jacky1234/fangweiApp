package com.jacky.beedee.ui.function.discovery

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.beedee.R
import com.jacky.beedee.logic.MiscFacade
import com.jacky.beedee.logic.entity.module.Good
import com.jacky.beedee.logic.entity.module.GoodType
import com.jacky.beedee.logic.entity.module.MySelf
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.support.ext.launch
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.ui.adapter.GoodDetailAdapter
import com.jacky.beedee.ui.function.login.LoginActivity
import com.jacky.beedee.ui.inner.arch.MySupportFragment
import com.jacky.beedee.ui.widget.decoration.BottomOffsetDecoration
import kotlinx.android.synthetic.main.fragment_good_detail.*

class GoodDetailFragment : MySupportFragment() {
    private lateinit var adapter: GoodDetailAdapter
    private var good: Good? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_good_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleView.setLeftAction(View.OnClickListener { _mActivity.finish() })
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = GoodDetailAdapter(context!!, object : GoodDetailAdapter.Delegate {
            override fun onOutfitDetail() {

            }

            override fun onLikeClick() {
                good?.let {
                    if (MySelf.get().isLogined) {
                        if (it.isCollected) {
                            RequestHelper.get().uncollectItem(GoodType.GOODS, it.id)
                                    .compose(bindUntilDetach())
                                    .subscribe { _ ->
                                        it.isCollected = false
                                        it.collectCount--
                                        adapter.notifyDataSetChanged()
                                    }
                        } else {
                            RequestHelper.get().collectItem(GoodType.GOODS, it.id)
                                    .compose(bindUntilDetach())
                                    .subscribe { _ ->
                                        it.isCollected = true
                                        it.collectCount++
                                        adapter.notifyDataSetChanged()
                                    }
                        }
                    } else {
                        MiscFacade.get().setLastRunnable {
                            if (isAttached()) {
                                requestData()
                            }
                        }
                        activity.launch<LoginActivity>()
                    }
                }
            }
        })
        recyclerView.addItemDecoration(BottomOffsetDecoration(AndroidUtil.dip2px(15f).toInt()))
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        if (MiscFacade.get().lastRunnable != null) {
            MiscFacade.get().lastRunnable.run()
            MiscFacade.get().lastRunnable = null
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        requestData()
    }

    @SuppressLint("CheckResult")
    private fun requestData() {
        RequestHelper.get()
                .requestGoodDetail(arguments!!.getString(KEY_GOOD_ID))
                .compose(bindUntilDetach())
                .subscribe {
                    good = it
                    adapter.setData(it)
                }
    }

    companion object {
        const val KEY_GOOD_ID = "KEY_GOOD_ID"


        @JvmStatic
        fun newInstance(id: String): GoodDetailFragment {
            val fragment = GoodDetailFragment()
            val bundle = Bundle()
            bundle.putString(KEY_GOOD_ID, id)
            fragment.arguments = bundle
            return fragment
        }
    }
}