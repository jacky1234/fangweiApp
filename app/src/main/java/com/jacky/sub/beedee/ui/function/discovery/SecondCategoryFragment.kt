package com.jacky.sub.beedee.ui.function.discovery

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.sub.beedee.R
import com.jacky.sub.beedee.logic.entity.module.Category
import com.jacky.sub.beedee.logic.entity.module.GoodItem
import com.jacky.sub.beedee.logic.network.RequestHelper
import com.jacky.sub.beedee.support.util.AndroidUtil
import com.jacky.sub.beedee.ui.adapter.SecondCategoryAdapter
import com.jacky.sub.beedee.ui.inner.arch.MySupportFragment
import com.jacky.sub.beedee.ui.widget.decoration.GridLayoutPaddingItemDecoration
import kotlinx.android.synthetic.main.fragment_second_category.*

/**
 * 2018/11/10.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class SecondCategoryFragment : MySupportFragment() {

    private lateinit var adapter: SecondCategoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SecondCategoryAdapter(context!!, object : SecondCategoryAdapter.OnGoodClickListener {
            override fun onScanMore(category: Category) {
//                AndroidUtil.toast("todo server interface...")
            }

            override fun onGoodClick(goodItem: GoodItem) {
                GoodDetailActivity.start(_mActivity, goodItem.id)
            }
        })
        val layoutManager = GridLayoutManager(context!!, SecondCategoryAdapter.MAX_SPAN_COUNT)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = adapter.getSpanCount(position)
        }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(GridLayoutPaddingItemDecoration(13))
    }

    @SuppressLint("CheckResult")
    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val categoryId = arguments!!.getString(KEY_CATEGORY_ID)

        RequestHelper.get().requestGroupByCategroy(categoryId)
                .compose(bindUntilDetach())
                .subscribe {
                    adapter.setData(it)
                }
    }

    companion object {
        const val KEY_CATEGORY_ID = "KEY_CATEGORY_ID"

        @JvmStatic
        fun newInstance(categoryId: String): SecondCategoryFragment {
            val fragment = SecondCategoryFragment()
            val bundle = Bundle()
            bundle.putString(KEY_CATEGORY_ID, categoryId)
            fragment.arguments = bundle
            return fragment
        }
    }
}