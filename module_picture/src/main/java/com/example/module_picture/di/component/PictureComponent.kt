package com.example.module_picture.di.component

import com.example.lib_common.remote.di.component.RemoteComponent
import com.example.lib_common.scope.ActivityScope
import com.example.module_picture.MainActivity
import com.example.module_picture.di.module.PictureModule
import com.example.module_picture.ui.activity.PictureItemActivity
import com.example.module_picture.ui.fragment.PictureFragment
import com.example.module_picture.ui.fragment.PictureItemFragment
import dagger.Component


@ActivityScope
@Component(modules = [PictureModule::class] ,dependencies = [RemoteComponent::class])
interface PictureComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(pictureFragment: PictureFragment)
    fun inject(pictureItemFragment: PictureItemFragment)
    fun inject(pictureItemActivity: PictureItemActivity)
}