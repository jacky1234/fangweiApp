package com.jacky.beedee.ui.function.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.beedee.R
import com.jacky.beedee.ui.adapter.VideoBannerAdapter
import com.jacky.beedee.ui.inner.arch.MySupportFragment
import com.jacky.beedee.ui.widget.layoutManager.BannerLayoutManager
import kotlinx.android.synthetic.main.fragment_outfit.*

class OutFitFragment : MySupportFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_outfit, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = BannerLayoutManager(context, recyclerView, 10)
        recyclerView.adapter = VideoBannerAdapter(R.layout.item_hot_video)
    }
}