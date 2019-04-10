package com.jacky.labeauty.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.Good
import com.jacky.labeauty.logic.image.ImageLoader
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.log.Logger
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.support.util.regex.RegexConstants
import com.jacky.labeauty.ui.widget.looper.LooperPagerAdapter
import com.king.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.layout_banner_wrapper.view.*
import kotlinx.android.synthetic.main.layout_good_price.view.*
import java.io.File
import java.util.regex.Pattern

class GoodDetailAdapter(private val context: Context, private val delegate: Delegate?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_BANNER_DETAIL = 1
        private const val TYPE_PRICE = 2
        private const val TYPE_TEXT = 3
        private const val TYPE_IMAGE = 4

        private val marginLeft = AndroidUtil.dip2px(15f)
        private val max_width = (AndroidUtil.getScreenWidth() - 2 * marginLeft).toInt()
    }

    private val map = HashMap<String, String>()
    private var dataList = ArrayList<Any>()
    private lateinit var item: Good
    val numberRegex = Pattern.compile(RegexConstants.REGEX_NUMBER)
    fun setData(item: Good) {
        this.item = item
        dataList.clear()
        dataList.add(TYPE_BANNER_DETAIL)
        dataList.add(TYPE_PRICE)
        dataList.add(TYPE_TEXT)
        for (s in item.details) {
            val exceptSuffix = s.subSequence(0, s.lastIndexOf('.'))
            val split = exceptSuffix.split("_")
            try {
                if (split.size >= 2) {
                    if (Pattern.matches(RegexConstants.REGEX_NUMBER, split.last())
                            && Pattern.matches(RegexConstants.REGEX_NUMBER, split[split.size - 2])) {
                        map[s] = split[split.size - 2] + "_" + split.last()
                    }
                }
            } catch (e: Exception) {
                Logger.e(e)
                map[s] = max_width.toString() + "_" + max_width
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
            TYPE_IMAGE -> holder = SubSamplingImageViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_large_image, parent, false))
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
                val imageViewHolder = holder as SubSamplingImageViewHolder
                val url = dataList[position] as String
                imageViewHolder.bind(url)
            }
        }
    }

    private inner class GoodBannerHolder constructor(view: View) : BaseViewHolder(view) {
        fun bind() {
            itemView.viewPager.setAutoScroll(true, 5000)
            itemView.viewPager.adapter = LooperPagerAdapter(item.gallery.size) { position ->
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

    private inner class SubSamplingImageViewHolder constructor(view: View) : BaseViewHolder(view) {
        @SuppressLint("CheckResult")
        fun bind(url: String) {
            val subSamplingScaleImageView = itemView as SubsamplingScaleImageView
            val layoutParams = RecyclerView.LayoutParams(100, 200)
            layoutParams.leftMargin = AndroidUtil.dip2px(15F).toInt()
            layoutParams.topMargin = AndroidUtil.dip2px(15F).toInt()

            try {
                val split = map[url]!!.split("_")
                val originWidth = split[0].toInt()
                val originHeight = split[1].toInt()
                val scale = originHeight * 1.0f / originWidth

                layoutParams.width = max_width
                layoutParams.height = (scale * max_width).toInt()
            } catch (e: Exception) {
                layoutParams.width = 600
                layoutParams.height = 600
            }

            subSamplingScaleImageView.tag = url

            Observable.create(ObservableOnSubscribe<File> { emit ->
                val submit = Glide.with(context)
                        .asFile()
                        .load(url)
                        .submit()
                val get: File?
                try {
                    get = submit.get()
                    emit.onNext(get)
                } catch (e: Exception) {
                    emit.onError(e)
                }
            })
                    .compose(SchedulerUtils.ioToMain())
                    .subscribe({
                        if (url == subSamplingScaleImageView.tag) {
                            subSamplingScaleImageView.setImage(ImageSource.uri(it.absolutePath))
                        }
                    }, Logger.Companion::e)



            subSamplingScaleImageView.layoutParams = layoutParams
        }
    }

    interface Delegate {
        fun onLikeClick()

        fun onOutfitDetail()
    }
}