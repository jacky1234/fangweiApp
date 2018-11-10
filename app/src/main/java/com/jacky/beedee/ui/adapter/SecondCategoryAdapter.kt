package com.jacky.beedee.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.jacky.beedee.R
import com.jacky.beedee.logic.entity.module.GoodItem
import com.jacky.beedee.logic.entity.response.SecondCategoryResponse
import com.jacky.beedee.logic.image.ImageLoader
import kotlinx.android.synthetic.main.item_good.view.*

/**
 * 2018/11/10.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class SecondCategoryAdapter(private val context: Context, private val listener: OnGoodClickListener?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_UNKNOW = 0
        const val TYPE_TITLE = 1
        const val TYPE_IMAGE = 2

        const val MAX_SPAN_COUNT = 2
    }

    private val lists = ArrayList<Any>()
    fun setData(list: List<SecondCategoryResponse>) {
        lists.clear()
        list.forEach {
            lists.add(it.category.name)
            lists.addAll(it.list)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var holder: BaseViewHolder? = null
        when (viewType) {
            TYPE_IMAGE -> holder = ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_good, parent, false))
            TYPE_TITLE -> holder = TextViewHolder(LayoutInflater.from(context).inflate(R.layout.item_second_category_title, parent, false))
        }

        return holder!!
    }

    override fun getItemCount() = lists.size

    override fun getItemViewType(position: Int): Int {
        var viewType = TYPE_UNKNOW
        val data = lists.get(position)
        when (data) {
            is String -> viewType = TYPE_TITLE
            is GoodItem -> viewType = TYPE_IMAGE
        }

        return viewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        when (itemViewType) {
            TYPE_IMAGE -> {
                val imageViewHolder = holder as ImageViewHolder
                imageViewHolder.bind(lists[position] as GoodItem)
            }
            TYPE_TITLE -> {
                val textViewHolder = holder as TextViewHolder
                textViewHolder.bind(lists[position] as String)
            }
        }
    }

    fun getSpanCount(position: Int): Int {
        var count = MAX_SPAN_COUNT
        val itemViewType = getItemViewType(position)
        if (itemViewType == TYPE_IMAGE) {
            count = 1
        }

        return count
    }

    private inner class ImageViewHolder constructor(view: View) : BaseViewHolder(view) {
        fun bind(item: GoodItem) {
            Glide.with(context)
                    .setDefaultRequestOptions(ImageLoader._1To1RequestOptions)
                    .load(item.thumb).into(itemView.imageView)

            itemView.setOnClickListener {
                listener?.onGoodClick(item)
            }
        }
    }

    private inner class TextViewHolder constructor(view: View) : BaseViewHolder(view) {
        fun bind(text: String) {
            val textView = itemView as TextView
            textView.text = text
        }
    }

    interface OnGoodClickListener {
        fun onGoodClick(goodItem: GoodItem)
    }
}