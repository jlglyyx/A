package com.example.module_main.di.module
import android.app.Application
import com.example.lib_common.scope.ActivityScope
import com.example.module_main.api.MainApiService
import com.example.module_main.di.factory.MainViewModelFactory
import com.example.module_main.repository.MainRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class MainModule {


    @ActivityScope
    @Provides
    fun provideMainApi(retrofit: Retrofit): MainApiService = retrofit.create(
        MainApiService::class.java)


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


}