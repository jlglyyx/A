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
        private const val MODULE_LOGIN = "module_login"

        /**
         * main
         */
        const val MAIN_ACTIVITY = "/$MODULE_MAIN/$ACTIVITY/MainActivity"
        const val MAIN_FRAGMENT = "/$MODULE_MAIN/$FRAGMENT/MainFragment"
        const val ADD_DYNAMIC_ACTIVITY = "/$MODULE_MAIN/$ACTIVITY/AddDynamicActivity"


        /**
         * picture
         */
        const val PICTURE_ITEM_ACTIVITY = "/$MODULE_PICTURE/$ACTIVITY/PictureItemActivity"

        const val PICTURE_FRAGMENT = "/$MODULE_PICTURE/$FRAGMENT/PictureFragment"
        const val PICTURE_ITEM_FRAGMENT = "/$MODULE_PICTURE/$FRAGMENT/PictureItemFragment"

        /**
         * login
         */
        const val SPLASH_ACTIVITY = "/$MODULE_LOGIN/$ACTIVITY/SplashActivity"
        const val LOGIN_ACTIVITY = "/$MODULE_LOGIN/$ACTIVITY/LoginActivity"
        const val REGISTER_ACTIVITY = "/$MODULE_LOGIN/$ACTIVITY/RegisterActivity"

        /**
         * video
         */
        const val VIDEO_FRAGMENT = "/$MODULE_VIDEO/$FRAGMENT/VideoFragment"
        const val VIDEO_ITEM_FRAGMENT = "/$MODULE_VIDEO/$FRAGMENT/VideoItemFragment"


        const val ADVERTISEMENT_VIDEO_ACTIVITY = "/$MODULE_VIDEO/$ACTIVITY/AdvertisementVideoActivity"
        const val VIDEO_MAIN_ACTIVITY = "/$MODULE_VIDEO/$ACTIVITY/MainActivity"

        const val VIDEO_ITEM_ACTIVITY = "/$MODULE_VIDEO/$ACTIVITY/VideoItemActivity"

        /**
         * mine
         */
        const val MINE_ACTIVITY = "/$MODULE_MINE/$ACTIVITY/MainActivity"

        const val MINE_FRAGMENT = "/$MODULE_MINE/$FRAGMENT/MineFragment"

    }

    object Constant{

        /**
         * 主页布局类型
         */
        const val ITEM_MAIN_TITLE = 0
        const val ITEM_MAIN_CONTENT_TEXT = 1
        const val ITEM_MAIN_CONTENT_IMAGE = 2
        const val ITEM_MAIN_IDENTIFICATION = 3
        const val ITEM_MAIN_CONTENT_VIDEO = 4

        /**
         * 主页视频布局类型
         */
        const val ITEM_VIDEO_RECOMMEND_TYPE = 0
        const val ITEM_VIDEO_SMART_IMAGE = 1
        const val ITEM_VIDEO_BIG_IMAGE = 2
    }
}