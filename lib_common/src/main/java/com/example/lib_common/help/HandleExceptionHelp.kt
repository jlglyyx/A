package com.example.lib_common.help

import android.text.TextUtils
import android.util.Log
import com.example.lib_common.base.viewmodel.BaseViewModel
import kotlinx.coroutines.delay

class HandleExceptionHelp<out T : BaseViewModel>(
    private val t: T,
    private val exception: Throwable
) {

    companion object {
        private const val TAG = "HandleExceptionHelp"
    }

    private lateinit var showContent: String

    suspend fun handle(content: String) {
        showContent = content
        exception.message?.let {
            if (TextUtils.isEmpty(showContent)) {
                showContent = it
            }
            t.showDialog(showContent)
            Log.e(TAG, "handle: $it")
        }
        delay(1000)
        t.dismissDialog()
    }

}