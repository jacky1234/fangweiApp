package com.jacky.labeauty.ui.function.me.discount

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.MyDiscount
import com.jacky.labeauty.support.util.SpanUtils
import java.text.SimpleDateFormat
import java.util.*

class MyDiscountsAdapter constructor(itemLayoutId: Int) : BaseQuickAdapter<MyDiscount, BaseViewHolder>(itemLayoutId) {
    override fun convert(holder: BaseViewHolder, item: MyDiscount) {
        holder.setText(R.id.tv_title, getTitle(item))
                .setText(R.id.tv_store, item.name)
                .setText(R.id.tv_scope, item.instructions)
                .setText(R.id.tv_date, getDate(item))
                .addOnClickListener(R.id.tv_to_use)
    }

    private fun getTitle(item: MyDiscount): CharSequence {
        if (item.reduceRule == MyDiscount.VALUE) {
            return SpanUtils()
                    .append("¥")
                    .append(item.reduceValue.toString()).setFontSize(22, true)
                    .create()
        } else if (item.reduceRule == MyDiscount.DISCOUNT) {
            return SpanUtils()
                    .append(item.reduceValue.toString()).setFontSize(22, true)
                    .append(" 折")
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

    @SuppressLint("SimpleDateFormat")
    private fun formatTime(time: Long): CharSequence {
        return SimpleDateFormat("yyyy.MM.dd").format(Date(time))
    }
}