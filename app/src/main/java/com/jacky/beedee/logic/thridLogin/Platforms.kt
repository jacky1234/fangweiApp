package com.jacky.beedee.logic.thridLogin

import android.support.annotation.IntDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * 2018/12/1.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class Platforms {
    companion object {
        const val WECHAT = 1
        const val QQ = 2
        const val WEIBO = 3
    }

    @IntDef(WECHAT, QQ, WEIBO)
    @Retention(RetentionPolicy.SOURCE)
    annotation class PLATFORM
}