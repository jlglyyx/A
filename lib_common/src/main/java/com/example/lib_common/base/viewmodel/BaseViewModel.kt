package com.example.lib_common.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_common.bus.event.UIChangeLiveData
import com.example.lib_common.help.HandleExceptionHelp
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

    fun cancleRefresh() {
        uC.refreshEvent.call()
    }

    fun cancleLoadMore() {
        uC.loadMoreEvent.call()
    }


    suspend fun delayShowDialog(timeMillis: Long = 1000) {
        delay(timeMillis)
    }

    private suspend fun handleException(exception: Throwable, content: String = "") {
        HandleExceptionHelp(this, exception).handle(content)
    }


    fun <T> launch(
        onRequest: suspend () -> T,
        onSuccess: suspend (t: T) -> Unit = {},
        error: suspend (t: Throwable) -> Unit = {},
        vararg messages: String = arrayOf()

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
                if (messages.isNotEmpty() && messages.size >= 3) {
                    handleException(t, messages[2])
                } else {
                    handleException(t)
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