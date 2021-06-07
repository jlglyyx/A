@file:JvmName("DaggerHelp")
package com.example.lib_common.help
import com.example.lib_common.app.BaseApplication
import com.example.lib_common.base.di.component.BaseComponent
import com.example.lib_common.base.di.component.DaggerBaseComponent
import com.example.lib_common.base.di.module.BaseModule

private const val TAG = "DaggerHelp"

fun getBaseComponent(): BaseComponent {
    return DaggerBaseComponent.builder().baseModule(
        BaseModule(
            BaseApplication.baseApplication
        )
    ).build()
}
