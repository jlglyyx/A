package com.example.lib_common.base.di.component

import android.app.Application
import com.example.lib_common.base.di.factory.BaseViewModelFactory
import com.example.lib_common.base.di.module.BaseModule
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.scope.ApplicationScope
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@ApplicationScope
@Component(modules = [BaseModule::class])
interface BaseComponent {
    fun provideApplication(): Application
}