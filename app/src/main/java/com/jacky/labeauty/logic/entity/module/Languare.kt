package com.jacky.labeauty.logic.entity.module

import com.jacky.labeauty.R
import com.jacky.labeauty.support.util.AndroidUtil
import java.util.*

class Language private constructor(val key: Int, val desc: String, val locale: Locale) {
    companion object {
        fun create(key: Int, desc: String, locale: Locale): Language {
            return Language(key, desc, locale)
        }

        val languages = ArrayList<Language>()
        val defaultLanguage = create(1, AndroidUtil.getString(R.string.chinese).toString(), Locale.SIMPLIFIED_CHINESE)

        init {
            languages.add(defaultLanguage)
            languages.add(create(2, AndroidUtil.getString(R.string.japanese).toString(), Locale.JAPANESE))
        }
    }
}