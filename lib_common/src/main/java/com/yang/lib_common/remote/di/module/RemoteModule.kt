package com.yang.lib_common.remote.di.module

import com.google.gson.GsonBuilder
import com.yang.lib_common.api.BaseApiService
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.interceptor.LogInterceptor
import com.yang.lib_common.interceptor.UrlInterceptor
import com.yang.lib_common.scope.RemoteScope
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

@Module
class RemoteModule {


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
        return LogInterceptor()
    }


    @RemoteScope
    @Provides
    fun provideBaseApiService(retrofit: Retrofit): BaseApiService {
        return retrofit.create(BaseApiService::class.java)
    }





}