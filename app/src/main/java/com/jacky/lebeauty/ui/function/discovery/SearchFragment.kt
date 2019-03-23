package com.jacky.lebeauty.ui.function.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.lebeauty.R
import com.jacky.lebeauty.logic.DaoFacade
import com.jacky.lebeauty.support.ext.clickWithTrigger
import com.jacky.lebeauty.support.util.AndroidUtil
import com.jacky.lebeauty.support.util.Strings
import com.jacky.lebeauty.ui.inner.arch.MySupportFragment
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * 2018/11/10.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class SearchFragment : MySupportFragment(), SearchKeyFragment.OnKeyClickListener {
    override fun onHistoryKeyClick(key: String) {
        searchView.setText(key)

        showOrHide(searchResultFragment)
        AndroidUtil.runUI({
            searchKeyFragment.setKeyResponse(DaoFacade.get().addSearchKey(key))
        }, 500)
        searchResultFragment.requestSearchKey(key)
    }

    lateinit var searchKeyFragment: SearchKeyFragment
    lateinit var searchResultFragment: SearchResultFragment
    private val fragments = ArrayList<MySupportFragment>(2)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        searchKeyFragment = SearchKeyFragment()
        searchResultFragment = SearchResultFragment()
        fragments.add(searchKeyFragment)
        fragments.add(searchResultFragment)

        showOrHide(searchKeyFragment)

        tvCancel.clickWithTrigger { _mActivity.finish() }

        val searchKey = DaoFacade.get().searchKey
        if (Strings.isNotBlank(searchKey)) {
            searchView.setSearchHint("搜索产品，$searchKey")
        }
        searchView.setKeyDelegate {
            var key = searchView.text
            if (Strings.isNullOrEmpty(key)) {
                key = searchKey
            }

            if (Strings.isNullOrEmpty(key)) {
                AndroidUtil.toast("请输入关键字")
            }

            onHistoryKeyClick(key)
        }

        searchView.setOnTextChangedListener {
            if (Strings.isNullOrEmpty(it)) {
                showOrHide(searchKeyFragment)
            }
        }

    }

    private fun showOrHide(fragment: MySupportFragment) {
        val beginTransaction = childFragmentManager.beginTransaction()
        fragments.forEach {
            if (!it.isAdded) {
                beginTransaction.add(R.id.container, it)
            }

            beginTransaction.hide(it)
        }
        beginTransaction.show(fragment).commitAllowingStateLoss()
    }
}