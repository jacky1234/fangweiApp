package com.jacky.labeauty.ui.function.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.DaoFacade
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.support.util.Strings
import com.jacky.labeauty.ui.inner.arch.MySupportFragment
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
            searchView.setSearchHint(AndroidUtil.getString(R.string.search_product).toString() + "，$searchKey")
        }
        searchView.setKeyDelegate {
            var key = searchView.text
            if (Strings.isNullOrEmpty(key)) {
                key = searchKey
            }

            if (Strings.isNullOrEmpty(key)) {
                AndroidUtil.toast(R.string.please_input_key_words)
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