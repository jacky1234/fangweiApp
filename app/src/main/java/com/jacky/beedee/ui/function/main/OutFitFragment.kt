package com.jacky.beedee.ui.function.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.beedee.R
import com.jacky.beedee.logic.MiscFacade
import com.jacky.beedee.logic.entity.module.GoodItem
import com.jacky.beedee.logic.entity.module.MySelf
import com.jacky.beedee.logic.entity.module.Video
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.support.ext.launch
import com.jacky.beedee.ui.adapter.OutfitAdapter
import com.jacky.beedee.ui.function.login.LoginActivity
import com.jacky.beedee.ui.inner.arch.MySupportFragment
import kotlinx.android.synthetic.main.fragment_outfit.*

class OutFitFragment : MySupportFragment() {
    var page = 0
    private lateinit var adapter: OutfitAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_outfit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleView.setLeftAction(View.OnClickListener { activity!!.finish() })
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = OutfitAdapter(context!!, object : OutfitAdapter.Delegate {
            override fun onOutfitDetail(item: GoodItem) {
                start(OutFitDetailFragment.newInstance(item.id))
            }

            override fun onLikeClick(item: GoodItem) {
                if (MySelf.get().isLogined) {
                    if (item.isCollected) {
                        RequestHelper.get().uncollectOutFit(item.id)
                                .compose(bindUntilDetach())
                                .subscribe {
                                    item.isCollected = false
                                    item.collectCount--
                                    adapter.notifyDataSetChanged()
                                }
                    } else {
                        RequestHelper.get().collectOutFit(item.id)
                                .compose(bindUntilDetach())
                                .subscribe {
                                    item.isCollected = true
                                    item.collectCount++
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
        })
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        if (MiscFacade.get().lastRunnable != null) {
            MiscFacade.get().lastRunnable.run()
            MiscFacade.get().lastRunnable = null
        }
    }

    private val videos = ArrayList<Video>()
    private val goodItems = ArrayList<GoodItem>()
    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        requestData()
    }

    private fun requestData() {
        //video
        RequestHelper.get().requestDesignVideo()
                .compose(bindUntilDetach())
                .subscribe {
                    videos.clear()
                    videos.addAll(it.content)       //test
                    videos.addAll(it.content)
                    videos.addAll(it.content)
                    videos.addAll(it.content)
                    videos.addAll(it.content)
                    triggerData()
                }

        //
        RequestHelper.get().requestOutfitHot()
                .compose(bindUntilDetach())
                .subscribe {
                    goodItems.clear()
                    goodItems.addAll(it.content)        //test
                    goodItems.addAll(it.content)
                    goodItems.addAll(it.content)
                    goodItems.addAll(it.content)
                    goodItems.addAll(it.content)
                    triggerData()
                }
    }

    private fun triggerData() {
        adapter.setData(videos, goodItems)
    }
}