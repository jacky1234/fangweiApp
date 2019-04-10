package com.jacky.labeauty.ui.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.Favorite
import com.jacky.labeauty.logic.image.ImageLoader
import kotlinx.android.synthetic.main.item_outfit_favorite.view.*

class FavoriteOutFitAdapter constructor(itemLayoutId: Int) : BaseQuickAdapter<Favorite, BaseViewHolder>(itemLayoutId) {
    override fun convert(helper: BaseViewHolder, item: Favorite) {
        helper.itemView.apply {
            if (item.target != null) {
                imageView.visibility = View.VISIBLE
                tvName.visibility = View.VISIBLE
                cbLike.visibility = View.VISIBLE
                tvNum.visibility = View.VISIBLE

                tvName.text = item.target.name
                cbLike.isSelected = item.target.isCollected
                tvNum.text = item.target.collectCount.toString()

                Glide.with(mContext)
                        .setDefaultRequestOptions(ImageLoader._16To9RequestOptions)
                        .load(item.target.thumb)
                        .into(imageView)
            } else {
                imageView.visibility = View.GONE
                tvName.visibility = View.GONE
                cbLike.visibility = View.GONE
                tvNum.visibility = View.GONE
            }
        }

        helper.addOnClickListener(R.id.cbLike)
    }
}