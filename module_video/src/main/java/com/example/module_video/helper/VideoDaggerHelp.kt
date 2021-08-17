package com.example.module_video.helper

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lib_common.helper.getRemoteComponent
import com.example.module_video.di.component.DaggerVideoComponent
import com.example.module_video.di.component.VideoComponent
import com.example.module_video.di.module.VideoModule


private const val TAG = "VideoDaggerHelp.kt"

fun getVideoComponent(activity: AppCompatActivity): VideoComponent {
    return DaggerVideoComponent.builder().remoteComponent(getRemoteComponent())
        .videoModule(VideoModule(activity)).build()
}
fun getVideoComponent(fragment: Fragment): VideoComponent {
    return DaggerVideoComponent.builder().remoteComponent(getRemoteComponent())
        .videoModule(VideoModule(fragment)).build()
}

