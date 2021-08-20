package com.yang.module_login.di.module
import android.app.Application
import androidx.lifecycle.ViewModelStoreOwner
import com.yang.lib_common.scope.ActivityScope
import com.yang.lib_common.scope.ModelWithFactory
import com.yang.lib_common.util.getViewModel
import com.yang.module_login.api.LoginApiService
import com.yang.module_login.di.factory.LoginViewModelFactory
import com.yang.module_login.repository.LoginRepository
import com.yang.module_login.viewmodel.LoginViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class LoginModule(private val viewModelStoreOwner: ViewModelStoreOwner) {


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

    @ActivityScope
    @Provides
    @ModelWithFactory
    fun provideModelWithFactory(
        loginViewModelFactory: LoginViewModelFactory
    ): LoginViewModel =
        getViewModel(viewModelStoreOwner, loginViewModelFactory, LoginViewModel::class.java)

//    @ActivityScope
//    @Provides
//    @ModelNoFactory
//    fun provideModelNoFactory(
//    ): LoginViewModel = getViewModel(viewModelStoreOwner,LoginViewModel::class.java)
}