package uz.maverick.movieexplorerdemo.utils

import android.util.Log

object Logger {
    var isLoggingEnabled: Boolean = true
    var defaultTag: String = "Logger"

    fun d(message: String, tag: String = defaultTag) {
        if (isLoggingEnabled) Log.d(tag, message)
    }

    fun w(message: String, tag: String = defaultTag) {
        if (isLoggingEnabled) Log.w(tag, message)
    }

    fun e(message: String, tag: String = defaultTag, throwable: Throwable? = null) {
        if (isLoggingEnabled) {
            if (throwable != null) {
                Log.e(tag, message, throwable)
            } else {
                Log.e(tag, message)
            }
        }
    }
}
