package com.jacky.beedee.support.log

import android.util.Log

class Logger {
    companion object {
        @JvmStatic
        fun i(info: String) {
            write(info)
        }

        @JvmStatic
        fun w(warning: String) {
            write(warning)
        }

        @JvmStatic
        fun e(error: String) {
            write(error)
        }

        @JvmStatic
        fun t(throwable: Throwable) {
            write(Exceptions.getStackTrace(throwable))
        }

        @JvmStatic
        fun e(exception: Throwable) {
            write(Exceptions.getStackTrace(exception))
        }

        @JvmStatic
        private fun write(info: String) {
            Log.d("Logger", "Thread," + Thread.currentThread().name + ",info:" + info)
        }
    }
}