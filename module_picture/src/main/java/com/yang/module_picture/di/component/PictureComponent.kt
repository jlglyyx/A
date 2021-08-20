package com.yang.module_picture.di.component

import com.yang.lib_common.remote.di.component.RemoteComponent
import com.yang.lib_common.scope.ActivityScope
import com.yang.module_picture.di.module.PictureModule
import com.yang.module_picture.ui.activity.PictureItemActivity
import com.yang.module_picture.ui.fragment.PictureFragment
import com.yang.module_picture.ui.fragment.PictureItemFragment
import dagger.Component


@ActivityScope
@Component(modules = [PictureModule::class] ,dependencies = [RemoteComponent::class])
interface PictureComponent {
    fun inject(pictureFragment: PictureFragment)
    fun inject(pictureItemFragment: PictureItemFragment)
    fun inject(pictureItemActivity: PictureItemActivity)
}