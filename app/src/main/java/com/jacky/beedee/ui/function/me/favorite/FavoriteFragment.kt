package com.jacky.beedee.ui.function.me.favorite

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.beedee.R
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.ui.adapter.FavoriteAdapter
import com.jacky.beedee.ui.inner.arch.MySupportFragment

class FavoriteFragment : MySupportFragment() {
    private var type = FAVORITE_WARE
    private var page = 0

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getInt(KEY_FAVORITE_TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val content = inflater.inflate(R.layout.fragment_favorite, null)
        recyclerView = content.findViewById(R.id.recyclerview)
        return content

    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        adapter = FavoriteAdapter(R.layout.item_favorite)
        recyclerView.adapter = adapter

        requestList(false)
    }

    private fun requestList(loadMore: Boolean) {
        val targetType = when (type) {
            FAVORITE_WARE -> {
                "OUTFIT"
            }
            else -> {
                "GOODS"
            }
        }

        RequestHelper.get().requestCollectList(targetType, page)
                .compose(bindUntilDetach())
                .subscribe {
                    if (page == 0) {
                        adapter.setNewData(it.content)
                    } else {
                        adapter.addData(it.content)
                    }

                    if (loadMore) {
                        page++
                    } else {
                        page = 0
                    }
                }
    }


    companion object {
        const val KEY_FAVORITE_TYPE = "KEY_FAVORITE_TYPE"
        const val FAVORITE_WARE = 1
        const val FAVORITE_PRODUCT = 2


        @JvmStatic
        fun create(type: Int): FavoriteFragment {
            val fragment = FavoriteFragment()
            val bundle = Bundle()
            bundle.putInt(KEY_FAVORITE_TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }


}