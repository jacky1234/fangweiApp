package com.jacky.beedee.ui.function.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jacky.beedee.R
import com.jacky.beedee.logic.entity.module.Banner
import com.jacky.beedee.logic.entity.module.GoodItem
import com.jacky.beedee.logic.image.ImageLoader
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.support.ext.clickWithTrigger
import com.jacky.beedee.support.ext.launch
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.support.util.SpanUtils
import com.jacky.beedee.ui.function.other.ShowBrandActivity
import com.jacky.beedee.ui.inner.arch.MySupportFragment
import com.jacky.beedee.ui.widget.GridContainer
import com.jacky.beedee.ui.widget.looper.LoopViewPager
import com.jacky.beedee.ui.widget.looper.LooperPagerAdapter
import me.relex.circleindicator.CircleIndicator
import java.util.*


/**
 * 2018/11/1.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class HomeFragment : MySupportFragment() {
    private lateinit var viewPager: LoopViewPager
    private lateinit var circleIndicator: CircleIndicator
    private lateinit var tvBrandDesc: TextView
    private lateinit var tvKnowMore: TextView
    private lateinit var tvOutFit: TextView
    private lateinit var ivOutFitImageView: ImageView
    private lateinit var gridContainer: GridContainer
    private val defaultBanners = Collections.singletonList(Banner.empty)

    private fun requestBanner() {
        RequestHelper.get().requestBannerList()
                .compose(bindUntilDetach())
                .subscribe {
                    onResultBannerList(it)
                }
    }

    private fun setOnBannerClickListener(imageView: ImageView, banner: Banner) {
        imageView.setOnClickListener {
            //            banner.link       todo
        }
    }

    private fun requestOutfitGoods() {
        RequestHelper.get().requestOutfitHot()
                .compose(bindUntilDetach())
                .subscribe {
                    if (it != null && !it.content.isEmpty()) {
                        Glide.with(this)
                                .setDefaultRequestOptions(ImageLoader._16To9RequestOptions)
                                .load(it.content[0].thumb)
                                .into(ivOutFitImageView)
                    }
                }
    }

    private fun requestHotGoods() {
        RequestHelper.get().requestHotGoods()
                .compose(bindUntilDetach())
                .subscribe {
                    if (it != null && !it.content.isEmpty()) {
                        onResultHotGoods(it.content)
                    }
                }
    }

    private fun onResultHotGoods(hots: List<GoodItem>) {
        gridContainer.removeAllViews()

        hots.forEachIndexed { index, goodItem ->
            if (index > 3) return@forEachIndexed

            val child = layoutInflater.inflate(R.layout.item_home_grid, gridContainer, false)
            val imageView = child.findViewById<ImageView>(R.id.imageView)
            Glide.with(this)
                    .setDefaultRequestOptions(ImageLoader._1To1RequestOptions)
                    .load(goodItem.thumb)
                    .into(imageView)
            gridContainer.addView(child)
        }
    }

    private fun onResultBannerList(list: List<Banner>) {
        if (isAttached()) {
            viewPager.setAutoScroll(true, 5000)
            viewPager.adapter = LooperPagerAdapter(list.size) { position ->
                val imageView = ImageView(activity)
                if (isAttached()) {
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    setOnBannerClickListener(imageView, list[position])
                    Glide.with(this)
                            .setDefaultRequestOptions(ImageLoader._16To9RequestOptions)
                            .load(list[position].image)
                            .into(imageView)
                }

                imageView
            }
            circleIndicator.setViewPager(viewPager)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val content = inflater.inflate(R.layout.fragment_home, null)
        initView(content)
        initListener()
        initGridContainer()
        initDefaultData()
        return content
    }

    private fun initDefaultData() {
        ivOutFitImageView.setImageResource(R.mipmap.item_empty_16_9)
        onResultBannerList(defaultBanners)
        tvBrandDesc.text = getBranchDesc()
        onResultHotGoods(Collections.singletonList(GoodItem.empty))
    }

    private fun initListener() {
        tvKnowMore.clickWithTrigger {
            activity.launch<ShowBrandActivity>()
        }

        tvOutFit.clickWithTrigger {
            activity.launch<NewHotsGoodActivity>()
        }
    }

    private fun initGridContainer() {
        gridContainer.setItemPadding(AndroidUtil.dip2px(15f))
                .setSpanCount(2)
                .setRatio(1.0f)
                .layout()
    }

    private fun initView(content: View) {
        viewPager = content.findViewById(R.id.viewPager) as LoopViewPager
        circleIndicator = content.findViewById(R.id.circleIndicator) as CircleIndicator
        tvBrandDesc = content.findViewById(R.id.tv_brand_desc) as TextView
        tvKnowMore = content.findViewById(R.id.tv_know_more) as TextView
        tvOutFit = content.findViewById(R.id.tv_outfit_title) as TextView
        ivOutFitImageView = content.findViewById(R.id.iv_hotFit) as ImageView
        gridContainer = content.findViewById(R.id.gridContainer) as GridContainer
        gridContainer.setItemPadding(ImageLoader.item_padding)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        requestBanner()
        requestOutfitGoods()
        requestHotGoods()
    }

    private fun getBranchDesc(): CharSequence {
        return SpanUtils()
                .append("BEEDEE").setFontSize(15, true).setForegroundColor(resources.getColor(R.color.black)).setBold().appendLine()
                .append("Pursuit the 1% Life, something different.").setFontSize(12, true).setForegroundColor(resources.getColor(R.color.tab_grey_color)).appendLine()
                .append("追求1%的生活理念").setFontSize(12, true).appendLine()
                .append("BeeDee作为设计师原创品牌，坚持创新,坚持原创追求我们想要的感觉。").setForegroundColor(resources.getColor(R.color.item_title_text_color)).appendLine()
                .create()
    }
}