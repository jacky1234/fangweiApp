package com.jacky.lebeauty.ui.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jacky.lebeauty.R
import com.jacky.lebeauty.logic.entity.module.GoodItem
import com.jacky.lebeauty.logic.image.ImageLoader

class NewHotsGoodAdapter constructor(itemLayoutId: Int) : BaseQuickAdapter<GoodItem, BaseViewHolder>(itemLayoutId) {


    override fun convert(holder: BaseViewHolder, item: GoodItem) {
        val imageView = holder.getView<ImageView>(R.id.imageView)
        Glide.with(mContext)
                .setDefaultRequestOptions(ImageLoader._1To1RequestOptions)
                .load(item.thumb).into(imageView)
    }
}