package com.example.lib_common.base.di.module

import android.app.Application
import com.example.lib_common.base.di.factory.BaseViewModelFactory
import com.example.lib_common.scope.ApplicationScope
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
class BaseModule (private val application: Application){

    @ApplicationScope
    @Provides
    fun provideApplication(): Application = application

    @ApplicationScope
    @Provides
    fun provideGson(): Gson = Gson()

}