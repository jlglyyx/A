package com.yang.apt_annotation.factory

import com.yang.apt_annotation.processor.*

/**
 * @Author Administrator
 * @ClassName ProcessorFactory
 * @Description
 * @Date 2021/12/21 14:27
 */
class ProcessorFactory {

    companion object{
        const val MODULE_MAIN = "module_main"
        const val MODULE_MINE = "module_mine"
        const val MODULE_VIDEO = "module_video"
        const val MODULE_PICTURE = "module_picture"
        const val MODULE_LOGIN = "module_login"
    }

    fun getProcessor(type:String): IProcessor {
        return when(type){
            MODULE_LOGIN ->{
                 LoginProcessor()
            }
            MODULE_MAIN ->{
                MainProcessor()
            }
            MODULE_MINE ->{
                MineProcessor()
            }
            MODULE_VIDEO ->{
                VideoProcessor()
            }
            MODULE_PICTURE ->{
                PictureProcessor()
            }
            else -> {
                LoginProcessor()
            }
        }

    }
}