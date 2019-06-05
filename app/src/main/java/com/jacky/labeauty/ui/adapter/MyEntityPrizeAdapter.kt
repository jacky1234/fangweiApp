package com.jacky.labeauty.ui.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.Constant
import com.jacky.labeauty.logic.entity.module.MyEntityPrize
import com.jacky.labeauty.support.util.AndroidUtil

class MyEntityPrizeAdapter constructor(itemLayoutId: Int) : BaseQuickAdapter<MyEntityPrize, BaseViewHolder>(itemLayoutId) {
    override fun convert(holder: BaseViewHolder, item: MyEntityPrize) {
        holder.setText(R.id.tv_title, item.name)
                .setText(R.id.tv_date, getDate(item))

        Glide.with(mContext)
                .load(item.thumb)
                .into(holder.getView(R.id.iv_prize_thumb))


//        holder.itemView.setBackgroundResource(when {
//            item.expireRule == MyDiscount.FIXED -> R.drawable.shape_round_white_5
//            item.expireRule == MyDiscount.DURATION -> R.drawable.shape_round_blue_5
//            else -> 0
//        })
//
//        holder.getView<TextView>(R.id.tv_divider).setBackgroundResource(when {
//            item.expireRule == MyDiscount.FIXED -> R.drawable.discount_divider_white_h
//            item.expireRule == MyDiscount.DURATION -> R.drawable.discount_divider_blue_h
//            else -> 0
//        })
    }

//    private fun getTitle(item: MyEntityPrize): CharSequence {
//        if (item.reduceRule == MyDiscount.VALUE) {
//            return SpanUtils()
//                    .append("¥").setFontSize(11, true).setForegroundColor(mContext.resources.getColor(R.color.labe_blue))
//                    .append(item.reduceValue.toString()).setFontSize(30, true).setForegroundColor(mContext.resources.getColor(R.color.labe_blue))
//                    .create()
//        } else if (item.reduceRule == MyDiscount.DISCOUNT) {
//            return SpanUtils()
//                    .append(item.reduceDiscount.toString()).setFontSize(30, true).setForegroundColor(mContext.resources.getColor(R.color.labe_blue))
//                    .append(" 折").setFontSize(11, true).setForegroundColor(mContext.resources.getColor(R.color.labe_blue))
//                    .create()
//        }
//
//        return ""
//    }

    private fun getDate(item: MyEntityPrize): CharSequence {
        return "${formatTime(item.sendTime)}-${formatTime(item.sendTime + Constant.SEVEN_DAY_TM)}"
    }

    private fun formatTime(time: Long): CharSequence {
        return AndroidUtil.formatTime("yyyy.MM.dd", time)
    }
}