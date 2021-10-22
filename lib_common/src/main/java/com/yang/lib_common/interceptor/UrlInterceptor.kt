package com.yang.lib_common.interceptor

import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Response

class UrlInterceptor: Interceptor {

    companion object{
        private const val TAG = "UrlInterceptor"
        var url:String? = null
        var disposable : Boolean = false
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val encodedPath = request.url().encodedPath()
        if (url!=null || !TextUtils.isEmpty(url)){
            if (!url!!.endsWith("/")){
                throw Exception("url请以\"/\"结尾")
            }
            request = request.newBuilder().url(url+encodedPath.substring(1,encodedPath.length)).build()
            if (disposable){
                url = null //重置url
            }
        }
        return chain.proceed(request)
    }

}