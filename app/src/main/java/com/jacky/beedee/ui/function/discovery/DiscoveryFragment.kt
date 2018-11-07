package com.jacky.beedee.ui.function.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jacky.beedee.R
import com.jacky.beedee.ui.inner.arch.MySupportFragment

/**
 * 2018/11/1.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class DiscoveryFragment : MySupportFragment() {
    private lateinit var tvSearch: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val content = inflater.inflate(R.layout.fragment_discovery, container, false)
        tvSearch = content.findViewById(R.id.tvSearch)

        tvSearch.setOnClickListener({
            SearchActivity.start(activity!!, SearchActivity.KEY_SEARCH_CODE_REQUEST)
        })
        return content
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

    }
}