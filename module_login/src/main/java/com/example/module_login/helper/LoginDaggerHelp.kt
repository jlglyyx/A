package com.example.module_login.helper

import com.example.lib_common.helper.getRemoteComponent
import com.example.module_login.di.component.DaggerLoginComponent
import com.example.module_login.di.component.LoginComponent
import com.example.module_login.di.module.LoginModule


private const val TAG = "LoginDaggerHelp.kt"


fun getLoginComponent(): LoginComponent {
    return DaggerLoginComponent.builder().remoteComponent(getRemoteComponent())
        .loginModule(LoginModule()).build()
}



