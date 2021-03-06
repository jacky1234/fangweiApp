package com.jacky.beedee.ui.adapter

import android.graphics.Color
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jacky.beedee.R
import com.jacky.beedee.logic.entity.module.Category
import com.jacky.beedee.support.Starter

/**
 * 2018/11/9.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class SearchOnFirstCategoryAdapter constructor(layoutId: Int, data: List<Category>) : BaseQuickAdapter<Category, BaseViewHolder>(layoutId, data) {

    override fun convert(helper: BaseViewHolder, item: Category) {
        val textView = helper.itemView as TextView
        textView.text = item.name
        when (item.selected) {
            true -> textView.setTextColor(Starter.getContext().resources.getColor(R.color.black))
            false -> textView.setTextColor(Color.parseColor("#999999"))
        }
    }
}