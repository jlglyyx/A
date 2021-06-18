package com.example.module_login.di.component

import com.example.lib_common.remote.di.component.RemoteComponent
import com.example.lib_common.scope.ActivityScope
import com.example.module_login.di.module.LoginModule
import com.example.module_login.ui.activity.LoginActivity
import com.example.module_login.ui.activity.RegisterActivity
import dagger.Component


@ActivityScope
@Component(modules = [LoginModule::class] ,dependencies = [RemoteComponent::class])
interface LoginComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(registerActivity: RegisterActivity)
}