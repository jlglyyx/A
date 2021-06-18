package com.example.module_login.di.module
import android.app.Application
import com.example.lib_common.scope.ActivityScope
import com.example.module_login.api.LoginApiService
import com.example.module_login.di.factory.LoginViewModelFactory
import com.example.module_login.repository.LoginRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class LoginModule {


    @ActivityScope
    @Provides
    fun provideLoginApi(retrofit: Retrofit): LoginApiService = retrofit.create(
        LoginApiService::class.java)


    @ActivityScope
    @Provides
    fun provideLoginRepository(loginApiService: LoginApiService): LoginRepository =
        LoginRepository(loginApiService)

    @ActivityScope
    @Provides
    fun provideLoginViewModelFactory(
        application: Application,
        loginApiService: LoginRepository
    ): LoginViewModelFactory =
        LoginViewModelFactory(
            application,
            loginApiService
        )


}