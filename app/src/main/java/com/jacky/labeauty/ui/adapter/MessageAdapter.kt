package com.jacky.labeauty.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.Message
import com.jacky.labeauty.support.util.AndroidUtil

class MessageAdapter constructor(itemLayoutId: Int) : BaseQuickAdapter<Message, BaseViewHolder>(itemLayoutId) {
    override fun convert(holder: BaseViewHolder, item: Message) {
        holder.setText(R.id.tv_title, item.title)
                .setText(R.id.tv_date, formatTime(item.createTime))
                .setText(R.id.tv_detail, item.content)
    }

    private fun formatTime(time: Long): CharSequence {
        return AndroidUtil.formatTime("yyyy.MM.dd hh:mm", time)
    }
}