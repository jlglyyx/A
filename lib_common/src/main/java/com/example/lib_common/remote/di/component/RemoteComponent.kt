package com.example.lib_common.remote.di.component

import android.content.Context
import com.example.lib_common.app.BaseApplication
import com.example.lib_common.base.di.component.BaseComponent
import com.example.lib_common.remote.di.module.RemoteModule
import com.google.gson.Gson
import com.yang.common_lib.api.BaseApiService
import com.yang.common_lib.scope.RemoteScope
import dagger.Component
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@RemoteScope
@Component(modules = [RemoteModule::class],dependencies = [BaseComponent::class])
interface RemoteComponent{
    fun getBaseApplication(): BaseApplication
    fun getContext(): Context
    fun getRetrofit(): Retrofit
    fun getBaseApiService(): BaseApiService
    fun gson(): Gson
}