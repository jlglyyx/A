package com.example.module_video.helper

import com.example.lib_common.helper.getRemoteComponent
import com.example.module_video.di.component.DaggerVideoComponent
import com.example.module_video.di.component.VideoComponent
import com.example.module_video.di.module.VideoModule


private const val TAG = "VideoDaggerHelp.kt"

fun getVideoComponent(): VideoComponent {
    return DaggerVideoComponent.builder().remoteComponent(getRemoteComponent())
        .videoModule(VideoModule()).build()
}



