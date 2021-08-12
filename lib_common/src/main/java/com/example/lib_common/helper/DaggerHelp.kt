@file:JvmName("DaggerHelp")

package com.example.lib_common.helper

import com.example.lib_common.app.BaseApplication
import com.example.lib_common.base.di.component.BaseComponent
import com.example.lib_common.base.di.component.DaggerBaseComponent
import com.example.lib_common.base.di.module.BaseModule
import com.example.lib_common.remote.di.component.DaggerRemoteComponent
import com.example.lib_common.remote.di.component.RemoteComponent
import com.example.lib_common.remote.di.module.RemoteModule

private const val TAG = "DaggerHelp"

fun getBaseComponent(): BaseComponent {
    return DaggerBaseComponent.builder().baseModule(
        BaseModule(
            BaseApplication.baseApplication
        )
    ).build()
}

fun getRemoteComponent(): RemoteComponent {
    return DaggerRemoteComponent.builder().baseComponent(getBaseComponent())
        .remoteModule(RemoteModule()).build()
}



