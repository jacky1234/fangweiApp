package com.jacky.lebeauty.ui.function.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.lebeauty.R
import com.jacky.lebeauty.logic.DaoFacade
import com.jacky.lebeauty.support.ext.clickWithTrigger
import com.jacky.lebeauty.ui.Dialog.DialogHelper
import com.jacky.lebeauty.ui.inner.arch.MySupportFragment
import com.king.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.fragment_search_key.*

/**
 * 2018/11/10.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class SearchKeyFragment : MySupportFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_key, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestKeyWord()
        iv_clear.clickWithTrigger {
            DialogHelper.createSimpleConfirmDialog(activity!!, "确定需要删除历史记录吗？") { _, _ ->
                DaoFacade.get().clearHistoryKeys()
                requestKeyWord()
            }.show()
        }
    }


    private fun requestKeyWord() {
        Observable.create(ObservableOnSubscribe<List<String>> { emitter -> emitter.onNext(DaoFacade.get().searchHistoryKeys) })
                .compose(SchedulerUtils.ioToMain())
                .compose(bindUntilDetach())
                .subscribe {
                    setKeyResponse(it)
                }
    }

    fun setKeyResponse(it: List<String>) {
        onKeyResponse(it)
    }

    private fun onKeyResponse(it: List<String>) {
        if (it.isEmpty()) {
            iv_clear.visibility = View.GONE
        } else {
            iv_clear.visibility = View.VISIBLE
        }
        flowLayout.setViews(it) {
            val parent = parentFragment as OnKeyClickListener
            parent.onHistoryKeyClick(it)
        }
    }

    interface OnKeyClickListener {
        fun onHistoryKeyClick(key: String)
    }

}