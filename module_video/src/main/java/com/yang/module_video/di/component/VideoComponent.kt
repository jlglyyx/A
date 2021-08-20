package com.yang.module_video.di.component

import com.yang.lib_common.remote.di.component.RemoteComponent
import com.yang.lib_common.scope.ActivityScope
import com.yang.module_video.MainActivity
import com.yang.module_video.di.module.VideoModule
import com.yang.module_video.ui.activity.VideoItemActivity
import com.yang.module_video.ui.fragment.VideoFragment
import com.yang.module_video.ui.fragment.VideoItemFragment
import dagger.Component


@ActivityScope
@Component(modules = [VideoModule::class] ,dependencies = [RemoteComponent::class])
interface VideoComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(videoFragment: VideoFragment)
    fun inject(videoItemFragment: VideoItemFragment)
    fun inject(videoItemActivity: VideoItemActivity)
}