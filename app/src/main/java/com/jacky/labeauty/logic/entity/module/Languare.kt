package com.jacky.labeauty.logic.entity.module

import com.jacky.labeauty.R
import com.jacky.labeauty.support.util.AndroidUtil

class Language private constructor(val key: Int, val desc: String, val locale: String) {
    companion object {
        fun create(key: Int, desc: String, locale: String): Language {
            return Language(key, desc, locale)
        }

        val languages = ArrayList<Language>()
        val defaultLanguage = create(2, AndroidUtil.getString(R.string.chinese).toString(), "zh_CN")

        init {
            languages.add(defaultLanguage)
            languages.add(create(2, AndroidUtil.getString(R.string.japanese).toString(), "jp"))
        }
    }
}