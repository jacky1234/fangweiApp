package com.jacky.labeauty.logic.language

import android.content.Context
import android.os.Build
import com.jacky.labeauty.logic.DaoFacade
import com.jacky.labeauty.logic.entity.module.Language
import com.jacky.labeauty.support.Starter

class LanguageUtil {
    companion object {
        private var currentLanguage: Language? = null

        @JvmStatic
        fun readSet(context: Context) {
            val query = query()
            update(context, query)
        }

        @JvmStatic
        fun update(context: Context, language: Language): Boolean {
            val configuration = context.resources.configuration
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLocale(language.locale)
            } else {
                configuration.locale = language.locale
            }

            try {
                context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
                currentLanguage = language
            } catch (ignore: Exception) {
                return false
            }
            return true
        }

        @JvmStatic
        fun query(): Language {
            val languageKey = DaoFacade.get().languageKey
            if (languageKey != null) {
                val languages = Language.languages
                languages.forEach {
                    if (it.key == languageKey) {
                        return it
                    }
                }
            }

            return Language.defaultLanguage
        }

        @JvmStatic
        fun getCurrentLanguage(): Language {
            if (currentLanguage == null) {
                readSet(Starter.getContext())
            }

            return currentLanguage!!
        }
    }
}