package com.jacky.sub.beedee.support.ext

import android.app.Activity
import android.content.Intent

/**
 * 2018/10/20.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */

//launch<DetailActivity>
inline fun <reified T : Activity> Activity?.launch() {
    val intent = Intent(this, T::class.java)
    this?.startActivity(intent)
}