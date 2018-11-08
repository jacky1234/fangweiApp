//package com.jacky.beedee.ui.adapter
//
//import android.content.Context
//import com.lzy.ninegrid.ImageInfo
//import com.lzy.ninegrid.NineGridView
//import com.lzy.ninegrid.NineGridViewAdapter
//
///**
// * 2018/11/7.
// * GitHub:[https://github.com/jacky1234]
// * @author  jacky
// *
// * todo 类似朋友圈效果
// */
//class HomeNineGridViewAdapter(context: Context, imageInfo: List<ImageInfo>, listener: OnImageItemClickListener?) : NineGridViewAdapter(context, imageInfo) {
//    private val listener: OnImageItemClickListener? = listener
//    override fun onImageItemClick(context: Context?, nineGridView: NineGridView?, index: Int, listImageInfo: List<ImageInfo>) {
//        super.onImageItemClick(context, nineGridView, index, imageInfo)
//        listener?.onImageItemClick(index, listImageInfo)
//    }
//
//    interface OnImageItemClickListener {
//        fun onImageItemClick(index: Int, listImageInfo: List<ImageInfo>)
//    }
//}