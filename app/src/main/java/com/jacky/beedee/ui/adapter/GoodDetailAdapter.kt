package com.jacky.beedee.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.jacky.beedee.R
import com.jacky.beedee.logic.entity.module.Good
import com.jacky.beedee.logic.image.ImageLoader
import com.jacky.beedee.support.ext.clickWithTrigger
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.support.util.regex.RegexConstants
import com.jacky.beedee.ui.widget.looper.LooperPagerAdapter
import kotlinx.android.synthetic.main.layout_banner_wrapper.view.*
import kotlinx.android.synthetic.main.layout_good_price.view.*
import java.util.regex.Pattern

class GoodDetailAdapter(private val context: Context, private val delegate: Delegate?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_BANNER_DETAIL = 1
        private const val TYPE_PRICE = 2
        private const val TYPE_TEXT = 3
        private const val TYPE_IMAGE = 4

        private val marginleft = AndroidUtil.dip2px(15f)
        private val max_width = (AndroidUtil.getScreenWidth() - 2 * marginleft).toInt()
    }

    private val map = HashMap<String, String>()
    private var dataList = ArrayList<Any>()
    private lateinit var item: Good
    fun setData(item: Good) {
        this.item = item
        dataList.clear()
        dataList.add(TYPE_BANNER_DETAIL)
        dataList.add(TYPE_PRICE)
        dataList.add(TYPE_TEXT)
        for (s in item.details) {
            val pattern = Pattern.compile(RegexConstants.REGEX_W_H)
            val matcher = pattern.matcher(s)
            if (matcher.find()) {
                map[s] = matcher.group()
            } else {
                map[s] = max_width.toString() + "x" + max_width
            }

            dataList.add(s)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var holder: BaseViewHolder? = null
        when (viewType) {
            TYPE_BANNER_DETAIL -> holder = GoodBannerHolder(LayoutInflater.from(context).inflate(R.layout.layout_good_banner_wrapper, parent, false))
            TYPE_PRICE -> holder = PriceViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_good_price, parent, false))
            TYPE_TEXT -> holder = TextViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_good_text, parent, false))
            TYPE_IMAGE -> holder = ImageViewHolder(ImageView(context))
        }

        return holder!!
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        var viewType = -1
        val data = dataList[position]
        when (data) {
            is Int -> {
                when (data) {
                    TYPE_BANNER_DETAIL -> viewType = TYPE_BANNER_DETAIL
                    TYPE_TEXT -> viewType = TYPE_TEXT
                    TYPE_PRICE -> viewType = TYPE_PRICE
                }
            }
            is String -> viewType = TYPE_IMAGE
        }

        return viewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        when (itemViewType) {
            TYPE_BANNER_DETAIL -> {
                val outfitBannerHolder = holder as GoodBannerHolder
                outfitBannerHolder.bind()
            }
            TYPE_PRICE -> {
                val priceViewHolder = holder as PriceViewHolder
                priceViewHolder.bind()
            }
            TYPE_TEXT -> {
                val textViewHolder = holder as TextViewHolder
                textViewHolder.bind()
            }
            TYPE_IMAGE -> {
                val imageViewHolder = holder as ImageViewHolder
                val url = dataList[position] as String
                imageViewHolder.bind(url)
            }
        }
    }

    private inner class GoodBannerHolder constructor(view: View) : BaseViewHolder(view) {
        fun bind() {
            itemView.viewPager.setAutoScroll(true, 5000)
            itemView.viewPager.adapter = LooperPagerAdapter(item.details.size) { position ->
                val imageView = ImageView(context)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                Glide.with(context)
                        .setDefaultRequestOptions(ImageLoader._16To9RequestOptions)
                        .load(item.gallery[position])
                        .into(imageView)

                imageView
            }
            itemView.circleIndicator.setViewPager(itemView.viewPager)
        }
    }

    private inner class PriceViewHolder constructor(view: View) : BaseViewHolder(view) {
        fun bind() {
            itemView.tvName.text = item.name
            itemView.tvPrice.text = "¥${item.price}"
            itemView.cb_like.isSelected = item.isCollected
            itemView.tv_like_num.text = item.collectCount.toString()
            itemView.cb_like.clickWithTrigger {
                delegate?.onLikeClick()
            }
        }
    }

    private inner class TextViewHolder constructor(view: View) : BaseViewHolder(view) {
        fun bind() {
            val textView = itemView as TextView
            textView.text = item.name
        }
    }

    private inner class ImageViewHolder constructor(view: View) : BaseViewHolder(view) {
        fun bind(url: String) {
            val imageView = itemView as ImageView
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            val layoutParams = RecyclerView.LayoutParams(100, 200)
            layoutParams.leftMargin = AndroidUtil.dip2px(15F).toInt()
            layoutParams.topMargin = AndroidUtil.dip2px(15F).toInt()

            try {
                val split = map[url]!!.split("x")
                layoutParams.width = split[0].toInt()
                layoutParams.height = split[1].toInt()
            } catch (e: Exception) {
                layoutParams.width = 600
                layoutParams.height = 600
            }

            imageView.layoutParams = layoutParams
            Glide.with(context)
                    .setDefaultRequestOptions(ImageLoader._1To1RequestOptions)
                    .load(url)
                    .into(imageView)
        }
    }

    interface Delegate {
        fun onLikeClick()

        fun onOutfitDetail()
    }
}