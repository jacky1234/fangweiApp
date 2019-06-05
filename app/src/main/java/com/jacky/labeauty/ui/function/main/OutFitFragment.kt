package com.jacky.labeauty.ui.function.main

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.MiscFacade
import com.jacky.labeauty.logic.entity.module.GoodItem
import com.jacky.labeauty.logic.entity.module.GoodType
import com.jacky.labeauty.logic.entity.module.MySelf
import com.jacky.labeauty.logic.entity.module.Video
import com.jacky.labeauty.logic.entity.response.HttpPageResponse
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.ext.launch
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.ui.adapter.OutfitAdapter
import com.jacky.labeauty.ui.function.login.LoginActivity
import com.jacky.labeauty.ui.inner.arch.MySupportFragment
import com.jacky.labeauty.ui.widget.decoration.BottomOffsetDecoration
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_outfit.*
import kotlinx.android.synthetic.main.layout_recylerview_with_refresh.*

@SuppressLint("CheckResult")
class OutFitFragment : MySupportFragment(), OnRefreshListener, OnLoadMoreListener {
    var page = 0
    private lateinit var adapter: OutfitAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_outfit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadMoreListener(this)

        titleView.setLeftAction(View.OnClickListener { pop() })
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = OutfitAdapter(context!!, object : OutfitAdapter.Delegate {
            override fun onVideoClick(video: Video) {
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(Uri.parse(video.url), "video/*")
                    startActivity(intent)
                } catch (e: Exception) {
                    AndroidUtil.toast("没有支持打开视频的应用")
                }
            }

            override fun onOutfitDetail(item: GoodItem) {
                start(OutFitDetailFragment.newInstance(item.id))
            }

            override fun onLikeClick(item: GoodItem) {
                if (MySelf.get().isLogined) {
                    if (item.isCollected) {
                        RequestHelper.get().uncollectItem(GoodType.OUTFIT, item.id)
                                .compose(bindUntilDetach())
                                .subscribe {
                                    item.isCollected = false
                                    item.collectCount--
                                    adapter.notifyDataSetChanged()
                                }
                    } else {
                        RequestHelper.get().collectItem(GoodType.OUTFIT, item.id)
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
                            initData()
                        }
                    }
                    activity.launch<LoginActivity>()
                }
            }
        })
        recyclerView.addItemDecoration(BottomOffsetDecoration(AndroidUtil.dip2px(15f).toInt()))
        recyclerView.adapter = adapter
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        requestOutfit(false)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        requestOutfit(true)
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
        initData()
    }

    private fun initData() {
        //video
        page = 0
        RequestHelper.get().requestDesignVideo(0)
                .compose(bindUntilDetach())
                .subscribe {
                    videos.clear()
                    videos.addAll(it.data)
                    adapter.setData(videos, goodItems)
                }

        requestOutfit(false)
    }

    private fun requestOutfit(append: Boolean) {
        RequestHelper.get().requestOutfitHot(page)
                .compose(bindUntilDetach())
                .subscribe {
                    triggerData(append, it)
                }
    }

    private fun triggerData(append: Boolean, response: HttpPageResponse<GoodItem>) {
        if (!append) {
            goodItems.clear()
        }
        goodItems.addAll(response.data)

        if (append) {
            adapter.appendData(videos, goodItems)
        } else {
            adapter.setData(videos, goodItems)
        }

        if (response.isLast) {
            refreshLayout.finishLoadMoreWithNoMoreData()
        } else {
            refreshLayout.finishLoadMore(true)
        }

        if (!append) {
            refreshLayout.finishRefresh()
        }
    }
}