package com.jacky.beedee.ui.adapter

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.jacky.beedee.R
import com.jacky.beedee.logic.entity.module.GoodItem
import com.jacky.beedee.logic.image.ImageLoader
import com.jacky.beedee.support.Starter
import com.jacky.beedee.support.util.AndroidUtil
import kotlinx.android.synthetic.main.item_outfit_recommand.view.*

class VideoBannerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_VIDEO_TITLE = 1
        const val TYPE_VIDEO_BANNER = 2
        const val TYPE_OUTFIT_RECOMMEND = 3
        const val TYPE_OUTFIT_ITEM = 4

        private const val VIDEO_TITLE = "衣服制作视频"
        private const val OUTFIT_RECOMMENT_TITLE = "搭配推荐"
    }

    private var dataList = ArrayList<Any>()


    fun setData(goodItems: List<GoodItem>) {
        dataList.add(VIDEO_TITLE)
        dataList.add(OUTFIT_RECOMMENT_TITLE)
        dataList.addAll(goodItems)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: BaseViewHolder? = null
        when (viewType) {
            TYPE_VIDEO_TITLE -> {
                viewHolder = TitleViewHolder(TextView(parent.context))
            }
            TYPE_VIDEO_BANNER -> {
                viewHolder = VideoVideoHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false))
            }
            TYPE_OUTFIT_RECOMMEND -> {
                viewHolder = TitleViewHolder(TextView(parent.context))
            }
            TYPE_OUTFIT_ITEM -> {
                viewHolder = OutFitItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_outfit_recommand, parent, false))
            }
        }

        return viewHolder!!
    }

    override fun getItemViewType(position: Int): Int {
        val obj = dataList[position]
        var viewType = -1
        when (obj) {
            is String -> when (obj) {
                VIDEO_TITLE -> viewType = TYPE_VIDEO_TITLE
                OUTFIT_RECOMMENT_TITLE -> viewType = TYPE_OUTFIT_RECOMMEND
            }
            is GoodItem -> viewType = TYPE_OUTFIT_ITEM
        }
        return viewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        when (itemViewType) {
            TYPE_VIDEO_TITLE -> {
                val titleViewHolder = holder as TitleViewHolder
                val content = dataList[position] as String
                titleViewHolder.bind(content)
            }
            TYPE_OUTFIT_RECOMMEND -> {
                val titleViewHolder = holder as TitleViewHolder
                val content = dataList[position] as String
                titleViewHolder.bind(content)
            }
            TYPE_OUTFIT_ITEM -> {
                val outFitItemViewHolder = holder as OutFitItemViewHolder
                val content = dataList[position] as GoodItem
                outFitItemViewHolder.bind(content)
            }
        }
    }

    private class TitleViewHolder constructor(view: View) : BaseViewHolder(view) {
        init {
            val textView = itemView as TextView
            textView.setSingleLine()
            textView.setPadding(AndroidUtil.dip2px(15F).toInt(), 0, 0, 0)
            textView.setTypeface(textView.typeface, Typeface.BOLD_ITALIC)
            textView.setTextColor(Starter.getContext().resources.getColor(R.color.black))
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19f)
        }

        fun bind(content: String) {
            val textView = itemView as TextView
            textView.text = content
        }
    }

    private class VideoVideoHolder constructor(view: View) : BaseViewHolder(view) {
        fun bind() {
            val recyclerView = itemView as RecyclerView
        }
    }

    private class OutFitItemViewHolder constructor(view: View) : BaseViewHolder(view) {
        fun bind(item: GoodItem) {
            Glide.with(itemView.context)
                    .setDefaultRequestOptions(ImageLoader._16To9RequestOptions)
                    .load(item.thumb)
                    .into(itemView.imageView)

            itemView.tv_desc.text = item.name
            itemView.iv_like.isEnabled = item.isCollected
            itemView.tv_like_num.text = item.collectCount.toString()
        }
    }
}