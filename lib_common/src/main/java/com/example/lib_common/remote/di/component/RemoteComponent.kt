package com.example.lib_common.remote.di.component

import android.app.Application
import android.content.Context
import com.example.lib_common.api.BaseApiService
import com.example.lib_common.app.BaseApplication
import com.example.lib_common.base.di.component.BaseComponent
import com.example.lib_common.base.di.factory.BaseViewModelFactory
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.remote.di.module.RemoteModule
import com.example.lib_common.scope.RemoteScope
import com.google.gson.Gson
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@RemoteScope
@Component(modules = [RemoteModule::class], dependencies = [BaseComponent::class])
interface RemoteComponent {
    fun getBaseApiService(): BaseApiService
    fun provideOkHttpClient(): OkHttpClient
    fun provideRetrofit(): Retrofit
    //fun provideBaseViewModelFactory(): BaseViewModelFactory
    fun provideApplication(): Application
}