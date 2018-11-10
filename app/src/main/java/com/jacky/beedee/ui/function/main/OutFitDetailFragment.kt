package com.jacky.beedee.ui.function.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.beedee.R
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.ui.adapter.OutfitDetailAdapter
import com.jacky.beedee.ui.inner.arch.MySupportFragment
import com.jacky.beedee.ui.widget.decoration.BottomOffsetDecoration
import kotlinx.android.synthetic.main.fragment_detail_outfit.*

class OutFitDetailFragment : MySupportFragment() {
    private lateinit var adapter: OutfitDetailAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_outfit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleView.setLeftAction(View.OnClickListener { pop() })

        adapter = OutfitDetailAdapter(context!!)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(BottomOffsetDecoration(AndroidUtil.dip2px(15f).toInt()))
        recyclerView.adapter = adapter
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        val id = arguments?.getString(KEY_OUTFITID)
        requestOutfitDetail(id!!)
    }

    private fun requestOutfitDetail(id: String) {
        RequestHelper.get().requestOutfitDetail(id)
                .compose(bindUntilDetach())
                .subscribe {
                    adapter.setData(it)
                }
    }

    companion object {
        const val KEY_OUTFITID = "KEY_OUTFITID"

        @JvmStatic
        fun newInstance(id: String): OutFitDetailFragment {
            val fragment = OutFitDetailFragment()
            val bundle = Bundle()
            bundle.putString(KEY_OUTFITID, id)
            fragment.arguments = bundle
            return fragment
        }
    }
}
