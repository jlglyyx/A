package com.example.module_video.di.component

import com.example.lib_common.remote.di.component.RemoteComponent
import com.example.lib_common.scope.ActivityScope
import com.example.module_video.MainActivity
import com.example.module_video.di.module.VideoModule
import com.example.module_video.ui.fragment.VideoFragment
import com.example.module_video.ui.fragment.VideoItemFragment
import dagger.Component


@ActivityScope
@Component(modules = [VideoModule::class] ,dependencies = [RemoteComponent::class])
interface VideoComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(videoFragment: VideoFragment)
    fun inject(videoItemFragment: VideoItemFragment)
}