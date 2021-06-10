package com.example.module_picture.helper

import com.example.lib_common.help.getRemoteComponent
import com.example.module_picture.di.component.DaggerPictureComponent
import com.example.module_picture.di.component.PictureComponent
import com.example.module_picture.di.module.PictureModule


private const val TAG = "PictureDaggerHelp.kt"

fun getPictureComponent(): PictureComponent {
    return DaggerPictureComponent.builder().remoteComponent(getRemoteComponent())
        .pictureModule(PictureModule()).build()
}



