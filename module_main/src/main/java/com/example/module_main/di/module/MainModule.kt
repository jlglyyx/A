package com.example.module_main.di.module

import android.app.Application
import androidx.lifecycle.ViewModelStoreOwner
import com.example.lib_common.scope.ActivityScope
import com.example.lib_common.scope.ModelNoFactory
import com.example.lib_common.scope.ModelWithFactory
import com.example.lib_common.util.getViewModel
import com.example.module_main.api.MainApiService
import com.example.module_main.di.factory.MainViewModelFactory
import com.example.module_main.repository.MainRepository
import com.example.module_main.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class MainModule(private val viewModelStoreOwner: ViewModelStoreOwner) {


    @ActivityScope
    @Provides
    fun provideMainApi(retrofit: Retrofit): MainApiService = retrofit.create(
        MainApiService::class.java
    )


    @ActivityScope
    @Provides
    fun provideMainRepository(mainApiService: MainApiService): MainRepository =
        MainRepository(mainApiService)

    @ActivityScope
    @Provides
    fun provideMainViewModelFactory(
        application: Application,
        mainRepository: MainRepository
    ): MainViewModelFactory =
        MainViewModelFactory(
            application,
            mainRepository
        )


    @ActivityScope
    @Provides
    @ModelWithFactory
    fun provideModelWithFactory(
        mainViewModelFactory: MainViewModelFactory
    ): MainViewModel =
        getViewModel(viewModelStoreOwner, mainViewModelFactory, MainViewModel::class.java)

    @ActivityScope
    @Provides
    @ModelNoFactory
    fun provideModelNoFactory(
    ): MainViewModel = getViewModel(viewModelStoreOwner,MainViewModel::class.java)


}