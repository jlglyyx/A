package com.yang.lib_common.base.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.handle.ErrorHandle
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * ui状态控制器
     */
    var uC = UIChangeLiveData()


    /**
     * showDialog
     */
    fun showDialog(content: String = "加载中") {
        uC.showLoadingEvent.value = content
    }

    /**
     * dismissDialog
     */
    fun dismissDialog() {
        uC.dismissDialogEvent.call()
    }

    /**
     * 关闭刷新
     */
    fun cancelRefresh() {
        uC.refreshEvent.call()
    }

    /**
     * 关闭加载
     */
    fun cancelLoadMore() {
        uC.loadMoreEvent.call()
    }

    /**
     * 关闭刷新和加载
     */
    fun cancelRefreshLoadMore(){
        uC.refreshEvent.call()
        uC.loadMoreEvent.call()
    }

    /**
     * 请求成功
     */
    fun requestSuccess(){
        uC.requestSuccessEvent.call()
    }
    /**
     * 请求失败
     */
    fun requestFail(){
        uC.requestFailEvent.call()
    }

    /**
     * 结束activity
     */
    fun finishActivity(){
        uC.finishActivityEvent.call()
    }

    /**
     * 展示recyclerView 布局类型
     */
    fun showRecyclerViewEvent(type:Int){
        uC.showRecyclerViewEvent.postValue(type)
    }

    /**
     * 展示recyclerView 加载失败
     */
    fun showRecyclerViewErrorEvent(){
        uC.showRecyclerViewEvent.postValue(AppConstant.LoadingViewEnum.ERROR_VIEW)
    }
    /**
     * 展示recyclerView 加载空数据
     */
    fun showRecyclerViewEmptyEvent(){
        uC.showRecyclerViewEvent.postValue(AppConstant.LoadingViewEnum.EMPTY_VIEW)
    }

    suspend fun delayTime(timeMillis: Long = 1000) {
        delay(timeMillis)
    }
    suspend fun delayMissDialog(timeMillis: Long = 1000) {
        delay(timeMillis)
        dismissDialog()
    }

    private suspend fun handleException(exception: Throwable, content: String = "") {
        val handle = ErrorHandle(exception).handle()
        if (TextUtils.isEmpty(content)) {
            showDialog(handle)
        } else {
            showDialog(content)
        }
        delayTime(1000)
        dismissDialog()
    }


    /**
     * 请求
     * @param onRequest 请求
     * @param onSuccess 请求成功
     * @param error 请求失败
     * @param messages dialog 请求开始 请求成功 请求失败提示
     * @param errorDialog 是否展示统一处理失败dialog
     */
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
                    delayTime()
                }
                val data = onRequest()
                onSuccess(data)
                if (messages.isNotEmpty() && messages.size >= 2) {
                    showDialog(messages[1])
                }
                delayTime()
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

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}