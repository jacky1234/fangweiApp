package com.jacky.labeauty.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.IntegralRecorder
import com.jacky.labeauty.support.util.AndroidUtil

class IntegralRecorderAdapter constructor(type: String, itemLayoutId: Int) : BaseQuickAdapter<IntegralRecorder, BaseViewHolder>(itemLayoutId) {
    override fun convert(helper: BaseViewHolder, item: IntegralRecorder) {
        helper.setText(R.id.tv_type, item.remark)
                .setText(R.id.tv_create_date, formatTime(item.createTime))
                .setText(R.id.tv_integral_count, "+" + item.changed)
    }

    private fun formatTime(time: Long): CharSequence {
        return AndroidUtil.formatTime("yyyy.MM.dd hh:mm", time)
    }
}