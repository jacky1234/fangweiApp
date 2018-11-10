package com.jacky.beedee.ui.function.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.beedee.R
import com.jacky.beedee.logic.DaoFacade
import com.jacky.beedee.support.ext.clickWithTrigger
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.support.util.Strings
import com.jacky.beedee.ui.inner.arch.MySupportFragment
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * 2018/11/10.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class SearchFragment : MySupportFragment(), SearchKeyFragment.OnKeyClickListener {
    override fun onHistoryKeyClick(key: String) {
        DaoFacade.get().addSearchKey(key)
        showOrHide(searchResultFragment!!)
        searchResultFragment!!.requestSearchKey(key)
    }

    private var searchKeyFragment: SearchKeyFragment? = null
    private var searchResultFragment: SearchResultFragment? = null
    private val fragments = ArrayList<MySupportFragment>(2)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        searchKeyFragment = SearchKeyFragment()
        searchResultFragment = SearchResultFragment()
        fragments.add(searchKeyFragment!!)
        fragments.add(searchResultFragment!!)

        showOrHide(searchKeyFragment!!)

        tvCancel.clickWithTrigger { _mActivity.finish() }
        searchView.setSearchHint("搜索产品，“羽绒服”...")
        searchView.setKeyDelegate {
            val key = searchView.text
            if (Strings.isNullOrEmpty(key)) {
                AndroidUtil.toast("请输入关键字")
            }

            onHistoryKeyClick(key)
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