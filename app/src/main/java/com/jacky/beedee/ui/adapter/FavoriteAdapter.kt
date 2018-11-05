package com.jacky.beedee.ui.adapter

import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jacky.beedee.R
import com.jacky.beedee.logic.entity.ProductRow
import com.jacky.beedee.logic.image.ImageLoader

class FavoriteAdapter constructor(itemLayoutId: Int) : BaseQuickAdapter<ProductRow, BaseViewHolder>(itemLayoutId) {
    override fun convert(helper: BaseViewHolder, item: ProductRow) {
        Glide.with(mContext)
                .setDefaultRequestOptions(ImageLoader._16To9RequestOptions)
                .load(item.target?.thumb)
                .into(helper.getView(R.id.iv_picture))

        if (item.target != null) {
            helper.getView<View>(R.id.tv_name).visibility = View.VISIBLE
            helper.getView<View>(R.id.iv_yellow_heart).visibility = View.VISIBLE
            helper.getView<View>(R.id.tv_num).visibility = View.VISIBLE

            helper.getView<TextView>(R.id.tv_name).text = item.target.name
            helper.getView<TextView>(R.id.tv_num).text = item.target.collectCount.toString()
        } else {
            helper.getView<View>(R.id.tv_name).visibility = View.GONE
            helper.getView<View>(R.id.iv_yellow_heart).visibility = View.GONE
            helper.getView<View>(R.id.tv_num).visibility = View.GONE
        }
    }
}