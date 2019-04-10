package com.jacky.labeauty.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.Category
import com.jacky.labeauty.logic.entity.module.GoodItem
import com.jacky.labeauty.logic.entity.response.SecondCategoryResponse
import com.jacky.labeauty.logic.image.ImageLoader
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
            lists.add(it.category)
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
        val data = lists[position]
        when (data) {
            is Category -> viewType = TYPE_TITLE
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
                textViewHolder.bind(lists[position] as Category)
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
        fun bind(category: Category) {
            getView<TextView>(R.id.tv_title).text = category.name
//            getView<TextView>(R.id.tv_more).let { tvMore ->
//                tvMore.text = "查看更多 >"
//                tvMore.setOnClickListener {
//                    listener?.onScanMore(category)
//                }
//            }
        }
    }

    interface OnGoodClickListener {
        fun onGoodClick(goodItem: GoodItem)
        fun onScanMore(category: Category)
    }
}