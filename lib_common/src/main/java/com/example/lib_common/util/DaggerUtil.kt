//@file:JvmName("DaggerUtil")
//
//package com.example.lib_common.util
//
//import com.example.lib_common.app.BaseApplication.Companion.baseApplication
//import com.example.lib_common.base.di.component.BaseComponent
//import com.example.lib_common.base.di.component.DaggerBaseComponent
//import com.example.lib_common.base.di.module.BaseModule
//import com.example.lib_common.remote.di.component.DaggerRemoteComponent
//import com.example.lib_common.remote.di.component.RemoteComponent
//
//private const val TAG = "DaggerUtil"
//
//fun getBaseComponent(): BaseComponent {
//
//    return DaggerBaseComponent
//        .builder()
//        .baseModule(BaseModule(baseApplication))
//        .build()!!
//}
//
//fun getRemoteComponent(): RemoteComponent {
//
//    return DaggerRemoteComponent.builder()
//        .baseComponent(
//            DaggerBaseComponent
//                .builder()
//                .baseModule(BaseModule(baseApplication))
//                .build()
//        )
//        .build()!!
//}
//
//
//
