package com.example.lib_common.constant

interface AppConstant {

    object RoutePath{
        private const val ACTIVITY = "activity"
        private const val FRAGMENT = "fragment"
        private const val SERVICE = "service"
        private const val PROVIDE = "provide"

        private const val MODULE_MAIN = "module_main"
        private const val MODULE_HOME = "module_home"
        private const val MODULE_MINE = "module_mine"
        private const val MODULE_VIDEO = "module_video"
        private const val MODULE_PICTURE = "module_picture"

        const val MAIN_ACTIVITY = "/$MODULE_MAIN/$ACTIVITY/MainActivity"
        const val MAIN_FRAGMENT = "/$MODULE_MAIN/$FRAGMENT/MainFragment"


        const val PICTURE_ITEM_ACTIVITY = "/$MODULE_PICTURE/$ACTIVITY/PictureItemActivity"

        const val PICTURE_FRAGMENT = "/$MODULE_PICTURE/$FRAGMENT/PictureFragment"
        const val PICTURE_ITEM_FRAGMENT = "/$MODULE_PICTURE/$FRAGMENT/PictureItemFragment"


    }

    object Constant{

        const val CLICK_TIME:Long = 1000

        const val SPLASH_VIDEO_URL = "http://192.168.31.60:8080/AdobePhotoshopCS6.rar"
        const val SPLASH_VIDEO_URLS = "http://192.168.31.60:8080/ps2020pjb.rar"

        const val SPLASH_VIDEO_PATH = "/storage/emulated/0/A/splash.mp4"

        const val ID = "id"

        const val TYPE = "type"

        const val PAGE_SIZE = 10

        const val TAB_HEIGHT = "TAB_HEIGHT"
    }
}