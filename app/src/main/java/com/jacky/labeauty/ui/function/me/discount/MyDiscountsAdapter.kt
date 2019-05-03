package com.jacky.labeauty.ui.function.me.discount

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.MyDiscount
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.support.util.SpanUtils

class MyDiscountsAdapter constructor(itemLayoutId: Int) : BaseQuickAdapter<MyDiscount, BaseViewHolder>(itemLayoutId) {
    override fun convert(holder: BaseViewHolder, item: MyDiscount) {
        holder.setText(R.id.tv_title, getTitle(item))
                .setText(R.id.tv_store, item.name)
                .setText(R.id.tv_scope, item.desc)
                .setText(R.id.tv_date, getDate(item))
                .addOnClickListener(R.id.tv_to_use)


        holder.itemView.setBackgroundResource(when {
            item.expireRule == MyDiscount.FIXED -> R.drawable.shape_round_white_5
            item.expireRule == MyDiscount.DURATION -> R.drawable.shape_round_blue_5
            else -> 0
        })

        holder.getView<TextView>(R.id.tv_divider).setBackgroundResource(when {
            item.expireRule == MyDiscount.FIXED -> R.drawable.discount_divider_white_h
            item.expireRule == MyDiscount.DURATION -> R.drawable.discount_divider_blue_h
            else -> 0
        })
    }

    private fun getTitle(item: MyDiscount): CharSequence {
        if (item.reduceRule == MyDiscount.VALUE) {
            return SpanUtils()
                    .append("¥").setFontSize(11, true).setForegroundColor(mContext.resources.getColor(R.color.labe_blue))
                    .append(item.reduceValue.toString()).setFontSize(30, true).setForegroundColor(mContext.resources.getColor(R.color.labe_blue))
                    .create()
        } else if (item.reduceRule == MyDiscount.DISCOUNT) {
            return SpanUtils()
                    .append(item.reduceDiscount.toString()).setFontSize(30, true).setForegroundColor(mContext.resources.getColor(R.color.labe_blue))
                    .append(" 折").setFontSize(11, true).setForegroundColor(mContext.resources.getColor(R.color.labe_blue))
                    .create()
        }

        return ""
    }

    private fun getDate(item: MyDiscount): CharSequence {
        if (item.expireRule == MyDiscount.FIXED) {
            return "${formatTime(item.beginTime)}-${formatTime(item.endTime)}"
        } else if (item.expireRule == MyDiscount.DURATION) {
            return "${formatTime(item.sendTime)}-${formatTime(item.sendTime + item.duration)}"
        }

        return ""
    }

    private fun formatTime(time: Long): CharSequence {
        return AndroidUtil.formatTime("yyyy.MM.dd", time)
    }
}