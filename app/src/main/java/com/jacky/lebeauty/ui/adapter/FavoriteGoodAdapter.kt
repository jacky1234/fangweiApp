package com.jacky.lebeauty.ui.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jacky.lebeauty.logic.entity.module.Favorite
import com.jacky.lebeauty.logic.image.ImageLoader
import kotlinx.android.synthetic.main.item_good.view.*

/**
 * 2018/11/10.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class FavoriteGoodAdapter constructor(itemLayoutId: Int) : BaseQuickAdapter<Favorite, BaseViewHolder>(itemLayoutId) {
    override fun convert(helper: BaseViewHolder, item: Favorite) {
        helper.itemView.apply {
            item.target?.let { _ ->
                Glide.with(mContext)
                        .setDefaultRequestOptions(ImageLoader._1To1RequestOptions)
                        .load(item.target.thumb)
                        .into(imageView)
            }
        }
    }
}