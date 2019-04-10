package com.jacky.labeauty.ui.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jacky.labeauty.logic.entity.module.GoodItem
import com.jacky.labeauty.logic.image.ImageLoader
import kotlinx.android.synthetic.main.item_search_result.view.*

/**
 * 2018/11/10.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class SearchResultAdapter constructor(layoutId: Int, data: List<GoodItem>) : BaseQuickAdapter<GoodItem, BaseViewHolder>(layoutId, data){

    override fun convert(holder: BaseViewHolder, item: GoodItem) {
        Glide.with(mContext)
                .setDefaultRequestOptions(ImageLoader._1To1RequestOptions)
                .load(item.thumb).into(holder.itemView.imageView)

        holder.itemView.tvName.text = item.name
    }
}