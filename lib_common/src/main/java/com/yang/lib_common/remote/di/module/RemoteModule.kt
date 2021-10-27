package com.yang.lib_common.remote.di.module

import android.util.Log
import com.google.gson.GsonBuilder
import com.yang.lib_common.api.BaseApiService
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.interceptor.UrlInterceptor
import com.yang.lib_common.scope.RemoteScope
import dagger.Module
import dagger.Provides
import okhttp3.*
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

@Module
class RemoteModule() {


    @RemoteScope
    @Provides
    fun provideOkHttpClient(logInterceptor: Interceptor): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(UrlInterceptor())
            .addInterceptor(logInterceptor)
            .connectTimeout(AppConstant.ClientInfo.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(AppConstant.ClientInfo.READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(AppConstant.ClientInfo.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectionPool(ConnectionPool())
            .build()

    }

    @RemoteScope
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstant.ClientInfo.BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().disableHtmlEscaping().setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create()
                )
            )
            .client(okHttpClient)
            .build()
    }


    @RemoteScope
    @Provides
    fun provideLogInterceptor(): Interceptor {

//        return Interceptor { chain ->
//            val request = chain.request()
//            Log.i(TAG_LOG, "${request.url()}")
//            var response = chain.proceed(request)
//            response.body()?.also {
//                val let = it.string().apply {
//                    Log.i(TAG_LOG, this)
//                }
//                response = response.newBuilder().body(ResponseBody.create(it.contentType(),let)).build()
//            }
//
//            response
//        }


        return Interceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(request)
            Log.d(AppConstant.ClientInfo.TAG_LOG, "intercept: ===============request===============")
            //Log.d(AppConstant.ClientInfo.TAG_LOG, "request.baseUrl: ${request.url().toString().substring(0, request.url().toString().lastIndexOf("?"))}\n")
            Log.d(AppConstant.ClientInfo.TAG_LOG, "request.url: ${request.url()}\n")
            Log.d(AppConstant.ClientInfo.TAG_LOG, "request.method: ${request.method()}\n")
            Log.d(AppConstant.ClientInfo.TAG_LOG, "request.headers: ${request.headers()}\n")
            Log.d(AppConstant.ClientInfo.TAG_LOG, "request.body: ${getBody(request)}\n")
            Log.d(AppConstant.ClientInfo.TAG_LOG, "intercept: ===============request===============")
            Log.d(AppConstant.ClientInfo.TAG_LOG, "intercept: ===============response===============")
            Log.d(AppConstant.ClientInfo.TAG_LOG, "response.isSuccessful: ${response.isSuccessful}\n")
            Log.d(AppConstant.ClientInfo.TAG_LOG, "response.message: ${response.message()}\n")
            Log.d(AppConstant.ClientInfo.TAG_LOG, "response.headers: ${response.headers()}\n")
            Log.d(AppConstant.ClientInfo.TAG_LOG, "response.code: ${response.code()}\n")
            val content = response.body()?.string()
            val contentType = response.body()?.contentType()
            //Log.d(TAG_LOG, "response.body: ${content?.length} ${content}\n")
            showLogCompletion(content.toString(), 3000)
            Log.d(AppConstant.ClientInfo.TAG_LOG, "response.request.url: ${response.request().url()}\n")
            Log.d(AppConstant.ClientInfo.TAG_LOG, "intercept: ===============response===============")
            response = response.newBuilder().body(ResponseBody.create(contentType, content)).build()
            response
        }
    }


    @RemoteScope
    @Provides
    fun provideBaseApiService(retrofit: Retrofit): BaseApiService {
        return retrofit.create(BaseApiService::class.java)
    }


    private fun getBody(request: Request): String {
        try {
            val buffer = Buffer()
            val body = request.body()
//            if (body?.contentLength()!! >=1000000){
//                return "请求体太大了，打印OOM"
//            }
            body?.writeTo(buffer)
            val contentType = body?.contentType()
            val charset = contentType?.charset(Charsets.UTF_8)
            return if (charset != null) {
                buffer.readString(charset)
            } else {
                "无请求体"
            }
        } catch (e: Exception) {
            Log.i(AppConstant.ClientInfo.TAG, "getBody: ${e.message}")
        }
        return ""
    }

    private fun showLogCompletion(log: String, size: Int) {

        if (log.length > size) {
            val substring = log.substring(0, size)
            Log.d(AppConstant.ClientInfo.TAG_LOG, "response.body: ${substring}")
            if (log.length - substring.length > size) {
                val substring1 = log.substring(substring.length, log.length)
                showLogCompletion(substring1, size)
            } else {
                val substring1 = log.substring(substring.length, log.length)
                Log.d(AppConstant.ClientInfo.TAG_LOG, "${substring1}")
            }

        } else {
            Log.d(AppConstant.ClientInfo.TAG_LOG, "response.body: ${log}\n")
        }

    }


}