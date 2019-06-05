package com.jacky.labeauty.ui.function.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.DaoFacade
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.ui.dialog.DialogHelper
import com.jacky.labeauty.ui.inner.arch.MySupportFragment
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
            DialogHelper.createSimpleConfirmDialog(activity!!, AndroidUtil.getString(R.string.confirm_del_history)) { _, _ ->
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