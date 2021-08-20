package com.yang.lib_common.base.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.handle.ErrorHandle
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    var uC = UIChangeLiveData()


    fun showDialog(content: String = "加载中") {
        uC.showLoadingEvent.value = content
    }

    fun dismissDialog() {
        uC.dismissDialogEvent.call()
    }

    fun cancelRefresh() {
        uC.refreshEvent.call()
    }

    fun cancelLoadMore() {
        uC.loadMoreEvent.call()
    }

    fun cancelRefreshLoadMore(){
        uC.refreshEvent.call()
        uC.loadMoreEvent.call()
    }

    fun requestSuccess(){
        uC.requestSuccessEvent.call()
    }
    fun requestFail(){
        uC.requestFailEvent.call()
    }
    fun finishActivity(){
        uC.finishActivityEvent.call()
    }


    suspend fun delayShowDialog(timeMillis: Long = 1000) {
        delay(timeMillis)
    }

    private suspend fun handleException(exception: Throwable, content: String = "") {
        val handle = ErrorHandle(exception).handle()
        if (TextUtils.isEmpty(content)) {
            showDialog(handle)
        } else {
            showDialog(content)
        }
        delayShowDialog(1000)
        dismissDialog()
    }


    fun <T> launch(
        onRequest: suspend () -> T,
        onSuccess: suspend (t: T) -> Unit = {},
        error: suspend (t: Throwable) -> Unit = {},
        vararg messages: String = arrayOf(),
        errorDialog: Boolean = true
    ) {
        viewModelScope.launch {
            try {
                if (messages.isNotEmpty()) {
                    showDialog(messages[0])
                    delayShowDialog()
                }
                val data = onRequest()
                onSuccess(data)
                if (messages.isNotEmpty() && messages.size >= 2) {
                    showDialog(messages[1])
                }
                delayShowDialog()
                dismissDialog()
            } catch (t: Throwable) {
                error(t)
                if (errorDialog) {
                    if (messages.isNotEmpty() && messages.size >= 3) {
                        handleException(t, messages[2])
                    } else {
                        handleException(t)
                    }
                }
            }
        }
    }

    fun <T> async(
        onRequest: suspend () -> T,
        onSuccess: (t: T) -> Unit = {},
        error: (t: Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            val async = viewModelScope.async {
                onRequest()
            }
            try {
                onSuccess(async.await())
            } catch (t: Throwable) {
                error(t)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}