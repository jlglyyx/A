@file:JvmName("MainDaggerHelp.kt")

package com.example.module_main.helper

import com.example.lib_common.helper.getRemoteComponent
import com.example.module_main.di.component.DaggerMainComponent
import com.example.module_main.di.component.MainComponent
import com.example.module_main.di.module.MainModule


private const val TAG = "MainDaggerHelp.kt"


fun getMainComponent(): MainComponent {
    return DaggerMainComponent.builder().remoteComponent(getRemoteComponent())
        .mainModule(MainModule()).build()
}



