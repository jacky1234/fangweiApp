package com.jacky.labeauty.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.GoodItem
import com.jacky.labeauty.logic.entity.module.Video
import com.jacky.labeauty.logic.entity.wrapper.VideosWrapper
import com.jacky.labeauty.logic.image.ImageLoader
import com.jacky.labeauty.ui.widget.layoutManager.BannerLayoutManager
import kotlinx.android.synthetic.main.item_outfit_recommand.view.*
import kotlinx.android.synthetic.main.item_video.view.*

class OutfitAdapter(private val context: Context, private val delegate: Delegate?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_VIDEO_TITLE = 1
        const val TYPE_VIDEO_BANNER = 2
        const val TYPE_OUTFIT_RECOMMEND = 3
        const val TYPE_OUTFIT_ITEM = 4

        private const val VIDEO_TITLE = "衣服制作视频"
        private const val OUTFIT_RECOMMENT_TITLE = "搭配推荐"
    }

    private var dataList = ArrayList<Any>()

    fun setData(videos: List<Video>, goodItems: List<GoodItem>) {
        dataList.clear()
        dataList.add(VIDEO_TITLE)
        dataList.add(VideosWrapper(videos))
        dataList.add(OUTFIT_RECOMMENT_TITLE)
        dataList.addAll(goodItems)

        notifyDataSetChanged()
    }

    fun appendData(videos: List<Video>, goodItems: List<GoodItem>) {
        val origins = ArrayList<GoodItem>()
        dataList.forEach {
            if (it is GoodItem) {
                origins.add(it)
            }
        }

        origins.addAll(goodItems)
        setData(videos, origins)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: BaseViewHolder? = null
        when (viewType) {
            TYPE_VIDEO_TITLE -> {
                viewHolder = TitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_video_title, parent, false))
            }
            TYPE_VIDEO_BANNER -> {
                viewHolder = VideoVideoHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false))
            }
            TYPE_OUTFIT_RECOMMEND -> {
                viewHolder = TitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_outfit_recommand_title, parent, false))
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
            is VideosWrapper -> viewType = TYPE_VIDEO_BANNER
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
            TYPE_VIDEO_BANNER -> {
                val videoVideoHolder = holder as VideoVideoHolder
                val content = dataList[position] as VideosWrapper
                videoVideoHolder.bind(content.videos)
            }

            TYPE_OUTFIT_RECOMMEND -> {
                val titleViewHolder = holder as TitleViewHolder
                val content = dataList[position] as String
                titleViewHolder.bind(content)
            }
            TYPE_OUTFIT_ITEM -> {
                val outFitItemViewHolder = holder as OutFitItemViewHolder
                val content = dataList[position] as GoodItem
                outFitItemViewHolder.bind(content, position == itemCount - 1)
            }
        }
    }

    private class TitleViewHolder constructor(view: View) : BaseViewHolder(view) {
        fun bind(content: String) {
            val textView = itemView as TextView
            textView.text = content
        }
    }

    private inner class VideoVideoHolder constructor(view: View) : BaseViewHolder(view) {
        fun bind(videos: List<Video>) {
            val recyclerView = itemView.recyclerView

            recyclerView.layoutManager = BannerLayoutManager(context, recyclerView, videos.size)
            recyclerView.adapter = object : BaseQuickAdapter<Video, BaseViewHolder>(R.layout.item_video_detail, videos) {
                override fun convert(helper: BaseViewHolder, item: Video) {
                    val imageView = helper.getView<ImageView>(R.id.ivVideoCover)
                    val tvDesc = helper.getView<TextView>(R.id.tvDesc)
                    val ivPlay = helper.getView<ImageView>(R.id.ivPlay)
                    Glide.with(context)
                            .setDefaultRequestOptions(ImageLoader._16To9RequestRoundCornerOptions)
                            .load(item.url)
                            .into(imageView)

                    tvDesc.text = item.name

                    ivPlay.setOnClickListener {
                        delegate?.onVideoClick(item)
                    }
                }
            }
        }
    }

    private inner class OutFitItemViewHolder constructor(view: View) : BaseViewHolder(view) {
        fun bind(item: GoodItem, last: Boolean) {
            Glide.with(itemView.context)
                    .setDefaultRequestOptions(ImageLoader._16To9RequestRoundCornerOptions)
                    .load(item.thumb)
                    .into(itemView.imageView)

            itemView.tv_desc.text = item.name
            itemView.cb_like.isSelected = item.isCollected
            itemView.tv_like_num.text = item.collectCount.toString()
            itemView.cb_like.setOnClickListener {
                delegate?.onLikeClick(item)
            }

            itemView.setOnClickListener {
                delegate?.onOutfitDetail(item)
            }
        }
    }

    interface Delegate {
        fun onLikeClick(item: GoodItem)

        fun onOutfitDetail(item: GoodItem)

        fun onVideoClick(video: Video)
    }
}