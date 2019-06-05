package com.jacky.labeauty.ui.function.discovery

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.DaoFacade
import com.jacky.labeauty.logic.entity.module.Category
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.support.util.Strings
import com.jacky.labeauty.ui.adapter.SearchOnFirstCategoryAdapter
import com.jacky.labeauty.ui.inner.arch.MySupportFragment
import kotlinx.android.synthetic.main.fragment_discovery.*

/**
 * 2018/11/1.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class DiscoveryFragment : MySupportFragment() {
    private lateinit var adapter: SearchOnFirstCategoryAdapter
    private val data = ArrayList<Category>()
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RequestHelper.get().searchKeyword
                .compose(bindUntilDetach())
                .subscribe {
                    DaoFacade.get().searchKey = it.value
                    recoveryKeyWord()
                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discovery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recoveryKeyWord()
        tvSearch.clickWithTrigger {
            SearchActivity.start(activity!!, SearchActivity.KEY_SEARCH_CODE_REQUEST)
        }
        adapter = SearchOnFirstCategoryAdapter(R.layout.item_first_category, data)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener { adapter, _, position ->
            if (index == position) {
                return@setOnItemClickListener
            }

            data.forEachIndexed { index, sortResponse ->
                sortResponse.selected = position == index
            }
            index = position
            setFragment(data[index])
            adapter.notifyDataSetChanged()
        }
    }

    private fun recoveryKeyWord() {
        val searchKey = DaoFacade.get().searchKey
        if (Strings.isNotBlank(searchKey)) {
            tvSearch.text = AndroidUtil.getString(R.string.search_product).toString() + "，$searchKey"
        }
    }

    @SuppressLint("CheckResult")
    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        RequestHelper.get().requestFirstSort()
                .compose(bindUntilDetach())
                .subscribe {
                    if (!it.isEmpty()) {
                        data.addAll(it)
                        data[0].selected = true
                        setFragment(data[0])
                        adapter.notifyDataSetChanged()
                    }
                }
    }

    private val fragments = ArrayList<MySupportFragment>()
    private fun setFragment(response: Category) {
        var findFragmentByTag = fragmentManager!!.findFragmentByTag(response.id)
        if (findFragmentByTag == null) {
            findFragmentByTag = SecondCategoryFragment.newInstance(response.id)
            fragments.add(findFragmentByTag)
            fragmentManager!!.beginTransaction()
                    .add(R.id.container, findFragmentByTag, response.id)
                    .commitAllowingStateLoss()
        }

        val beginTransaction = fragmentManager!!.beginTransaction()
        for (fragment in fragments) {
            if (fragment == findFragmentByTag) {
                beginTransaction.show(fragment)
            } else {
                beginTransaction.hide(fragment)
            }
        }
        beginTransaction.commitAllowingStateLoss()
    }


}