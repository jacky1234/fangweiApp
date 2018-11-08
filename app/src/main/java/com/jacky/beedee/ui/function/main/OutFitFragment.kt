package com.jacky.beedee.ui.function.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.beedee.R
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.ui.adapter.VideoBannerAdapter
import com.jacky.beedee.ui.inner.arch.MySupportFragment
import kotlinx.android.synthetic.main.fragment_outfit.*

class OutFitFragment : MySupportFragment() {
    var page = 0
    private lateinit var adapter: VideoBannerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_outfit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleView.setLeftAction(View.OnClickListener { activity!!.finish() })
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = VideoBannerAdapter()
        recyclerView.adapter = adapter
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        //video
        RequestHelper.get().requestDesignVideo()
                .compose(bindUntilDetach())
                .subscribe {
                    it.content
                }

        //
        RequestHelper.get().requestOutfitHot()
                .compose(bindUntilDetach())
                .subscribe {
                    adapter.setData(it.content)
                }
    }
}