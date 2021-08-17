@file:JvmName("MainDaggerHelp.kt")

package com.example.module_main.helper

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lib_common.helper.getRemoteComponent
import com.example.module_main.di.component.DaggerMainComponent
import com.example.module_main.di.component.MainComponent
import com.example.module_main.di.module.MainModule


private const val TAG = "MainDaggerHelp.kt"


fun getMainComponent(activity: AppCompatActivity): MainComponent {
    return DaggerMainComponent.builder().remoteComponent(getRemoteComponent())
        .mainModule(MainModule(activity)).build()
}
fun getMainComponent(fragment: Fragment): MainComponent {
    return DaggerMainComponent.builder().remoteComponent(getRemoteComponent())
        .mainModule(MainModule(fragment)).build()
}



