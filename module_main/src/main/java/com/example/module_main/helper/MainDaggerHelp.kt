@file:JvmName("MainDaggerHelp.kt")

package com.example.module_main.helper

import com.example.lib_common.help.getBaseComponent
import com.example.lib_common.help.getRemoteComponent
import com.example.lib_common.remote.di.component.DaggerRemoteComponent
import com.example.lib_common.remote.di.module.RemoteModule
import com.example.module_main.di.component.DaggerMainComponent
import com.example.module_main.di.component.MainComponent
import com.example.module_main.di.module.MainModule


private const val TAG = "MainDaggerHelp.kt"


fun getMainComponent(): MainComponent {
    return DaggerMainComponent.builder().remoteComponent(getRemoteComponent())
        .mainModule(MainModule()).build()
}



