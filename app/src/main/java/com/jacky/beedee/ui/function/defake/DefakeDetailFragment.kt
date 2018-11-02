package com.jacky.beedee.ui.function.defake

import android.Manifest
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.beedee.R
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.ui.inner.arch.MySupportFragment
import com.jacky.beedee.ui.widget.TitleView
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine


class DefakeDetailFragment : MySupportFragment() {
    private lateinit var rxPermissions: RxPermissions

    companion object {
        private val REQUEST_CODE_CHOOSE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val content = inflater.inflate(R.layout.fragment_defake_detail, null)
        rxPermissions = RxPermissions(this)

        val titleView = content.findViewById<TitleView>(R.id.titleView)
        titleView.setLeftAction(View.OnClickListener { pop() })
        titleView.setRightAction(View.OnClickListener {
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe {
                        if (it) {
                            Matisse.from(this)
                                    .choose(MimeType.ofImage())
                                    .maxSelectable(1)
//                    .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                    .gridExpectedSize(300)
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(GlideEngine())
                                    .forResult(REQUEST_CODE_CHOOSE)
                        } else {
                            AndroidUtil.toast("请开启存储权限")
                        }
                    }

        })
        return content
    }
}