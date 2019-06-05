package com.jacky.labeauty.ui.function.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.Banner
import com.jacky.labeauty.logic.entity.module.GoodItem
import com.jacky.labeauty.logic.image.ImageLoader
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.Starter
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.ext.launch
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.support.util.SpanUtils
import com.jacky.labeauty.support.util.Strings
import com.jacky.labeauty.ui.common.Image
import com.jacky.labeauty.ui.common.ImagePreviewActivity
import com.jacky.labeauty.ui.function.discovery.GoodDetailActivity
import com.jacky.labeauty.ui.function.other.ShowBrandActivity
import com.jacky.labeauty.ui.inner.arch.MySupportFragment
import com.jacky.labeauty.ui.widget.GridContainer
import com.jacky.labeauty.ui.widget.looper.LoopViewPager
import com.jacky.labeauty.ui.widget.looper.LooperPagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import me.relex.circleindicator.CircleIndicator
import java.util.*
import kotlin.collections.ArrayList


/**
 * 2018/11/1.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
@SuppressLint("CheckResult")
class HomeFragment : MySupportFragment() {
    private lateinit var viewPager: LoopViewPager
    private lateinit var circleIndicator: CircleIndicator
    private lateinit var tvBrandDesc: TextView
    private lateinit var tvOutFit: TextView
    private lateinit var tvHotTitle: TextView
    private lateinit var ivOutFitImageView: ImageView
    private lateinit var gridContainer: GridContainer

    private fun requestBanner() {
        RequestHelper.get().requestBannerList()
                .compose(bindUntilDetach())
                .subscribe {
                    onResultBannerList(it)
                }
    }

    private fun setOnBannerClickListener(imageView: ImageView, banner: Banner) {
        imageView.setOnClickListener {
            if (banner.link.contains("/")) {    //normal
                val start = banner.link.lastIndexOf("/") + 1
                val id = banner.link.substring(start)
                GoodDetailActivity.start(_mActivity, id)
            } else {
                val image = Image()
                image.origin = banner.image

                val images = ArrayList<Image>()
                images.add(image)
                ImagePreviewActivity.start(_mActivity, images)
            }
        }
    }

    private fun requestOutfitGoods() {
        RequestHelper.get().requestOutfitHot(0)
                .compose(bindUntilDetach())
                .subscribe {
                    if (it != null && !it.data.isEmpty()) {
                        onResultOutfit(it.data)
                    }
                }
    }

    private fun onResultOutfit(lists: List<GoodItem>) {
        val thumb = lists[0].thumb
        Glide.with(this)
                .setDefaultRequestOptions(ImageLoader._16To9RequestOptions)
                .load(thumb)
                .into(ivOutFitImageView)

        tvOutFit.clickWithTrigger {
            activity.launch<OutFitActivity>()
        }

        ivOutFitImageView.clickWithTrigger {
            activity.launch<OutFitActivity>()
        }
    }


    @SuppressLint("CheckResult")
    private fun requestHotGoods() {
        RequestHelper.get().requestHotGoods(0)
                .compose(bindUntilDetach())
                .subscribe {
                    if (it != null && !it.data.isEmpty()) {
                        onResultHotGoods(it.data)
                    }
                }
    }

    private fun onResultHotGoods(hots: List<GoodItem>) {
        if (isAttached()) {
            gridContainer.removeAllViews()
            tvHotTitle.clickWithTrigger {
                activity.launch<NewHotsGoodActivity>()
            }

            hots.forEachIndexed { index, goodItem ->
                if (index > 3) return@forEachIndexed

                val child = layoutInflater.inflate(R.layout.item_home_grid, gridContainer, false)
                val imageView = child.findViewById<ImageView>(R.id.imageView)
                Glide.with(this)
                        .setDefaultRequestOptions(ImageLoader._1To1RequestOptions)
                        .load(goodItem.thumb)
                        .into(imageView)
                gridContainer.addView(child)

                child.clickWithTrigger {
                    if (Strings.isNotBlank(goodItem.id)) {
                        GoodDetailActivity.start(_mActivity, goodItem.id)
                    } else {
                        AndroidUtil.toast("参数错误...")
                    }
                }
            }
        }
    }

    private fun onResultBannerList(list: List<Banner>) {
        if (isAttached()) {
            if (!list.isEmpty()) {
                viewPager.setAutoScroll(true, 5000)
                viewPager.adapter = LooperPagerAdapter(list.size) { position ->
                    val imageView = ImageView(activity)
                    if (isAttached() && !list.isEmpty()) {
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
            } else {
                constraintLayout.visibility = View.GONE
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val content = inflater.inflate(R.layout.fragment_home, null)
        initView(content)
        initGridContainer()
        initDefaultData()
        return content
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parent_brand.clickWithTrigger {
            activity.launch<ShowBrandActivity>()
        }
        tvBrandTitle.clickWithTrigger {
            activity.launch<ShowBrandActivity>()
        }
    }

    private fun initDefaultData() {
        ivOutFitImageView.setImageResource(R.drawable.item_empty_16_9)
        tvBrandDesc.text = getBranchDesc()
        onResultHotGoods(Collections.singletonList(GoodItem.empty))
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
        tvOutFit = content.findViewById(R.id.tv_outfit_title) as TextView
        tvHotTitle = content.findViewById(R.id.tv_hots_title) as TextView
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
                .appendLine("ANCILA").setFontSize(15, true).setForegroundColor(ContextCompat.getColor(Starter.getContext(), android.R.color.black)).setBold()
                .append(AndroidUtil.getString(R.string.intro_top_1)).setFontSize(12, true).setForegroundColor(ContextCompat.getColor(Starter.getContext(), R.color.tab_grey_color))
                .appendLine(AndroidUtil.getString(R.string.intro_top_2)).setFontSize(12, true).setForegroundColor(ContextCompat.getColor(Starter.getContext(), R.color.tab_grey_color))
                .append(AndroidUtil.getString(R.string.intro_top_3)).setFontSize(12, true)
                .appendLine(AndroidUtil.getString(R.string.intro_top_4)).setFontSize(12, true)
                .create()
    }
}