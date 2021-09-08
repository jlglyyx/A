package com.yang.lib_common.constant

interface AppConstant {

    object RoutePath {
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
        const val ADD_DYNAMIC_ACTIVITY = "/$MODULE_MAIN/$ACTIVITY/AddDynamicActivity"
        const val MY_COLLECTION_ACTIVITY = "/$MODULE_MAIN/$ACTIVITY/MyCollectionActivity"
        const val MY_PUSH_ACTIVITY = "/$MODULE_MAIN/$ACTIVITY/MyPushActivity"
        const val DYNAMIC_DETAIL_ACTIVITY = "/$MODULE_MAIN/$ACTIVITY/DynamicDetailActivity"
        const val PICTURE_SELECT_ACTIVITY = "/$MODULE_MAIN/$ACTIVITY/PictureSelectActivity"


        const val MAIN_FRAGMENT = "/$MODULE_MAIN/$FRAGMENT/MainFragment"
        const val LEFT_FRAGMENT = "/$MODULE_MAIN/$FRAGMENT/LeftFragment"
        const val MY_COLLECTION_PICTURE_FRAGMENT =
            "/$MODULE_MAIN/$FRAGMENT/MyCollectionPictureFragment"
        const val MY_COLLECTION_VIDEO_FRAGMENT = "/$MODULE_MAIN/$FRAGMENT/MyCollectionVideoFragment"


        /**
         * picture
         */
        const val PICTURE_ITEM_ACTIVITY = "/$MODULE_PICTURE/$ACTIVITY/PictureItemActivity"
        const val SEARCH_ACTIVITY = "/$MODULE_PICTURE/$ACTIVITY/SearchActivity"

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


        const val ADVERTISEMENT_VIDEO_ACTIVITY =
            "/$MODULE_VIDEO/$ACTIVITY/AdvertisementVideoActivity"
        const val VIDEO_MAIN_ACTIVITY = "/$MODULE_VIDEO/$ACTIVITY/MainActivity"

        const val VIDEO_ITEM_ACTIVITY = "/$MODULE_VIDEO/$ACTIVITY/VideoItemActivity"

        /**
         * mine
         */
        const val MINE_ACTIVITY = "/$MODULE_MINE/$ACTIVITY/MainActivity"

        const val OTHER_PERSON_INFO_ACTIVITY = "/$MODULE_MINE/$ACTIVITY/OtherPersonInfoActivity"

        const val CHANGE_USER_INFO_ACTIVITY = "/$MODULE_MINE/$ACTIVITY/ChangeUserInfoActivity"

        const val VIEW_HISTORY_ACTIVITY = "/$MODULE_MINE/$ACTIVITY/ViewHistoryActivity"


        const val MINE_FRAGMENT = "/$MODULE_MINE/$FRAGMENT/MineFragment"

    }

    object Constant {

        /**
         * 主页布局类型
         */
        const val CLICK_TIME: Long = 1000

        const val SPLASH_VIDEO_URL = "http://192.168.31.60:8080/AdobePhotoshopCS6.rar"
        const val SPLASH_VIDEO_URLS = "http://192.168.31.60:8080/ps2020pjb.rar"

        const val SPLASH_VIDEO_PATH = "/storage/emulated/0/A/splash.mp4"

        const val ID = "id"

        const val TYPE = "type"

        const val DATA = "data"

        const val NAME = "name"

        const val CONTENT = "content"

        const val PAGE_SIZE_COUNT = 10

        const val TAB_HEIGHT = "TAB_HEIGHT"

        const val USER_INFO = "user_info"

        const val USER_ID = "userId"

        const val PAGE_SIZE = "pageSize"

        const val PAGE_NUMBER = "pageNum"

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


    object LoadingViewEnum {

        const val EMPTY_VIEW = 0

        const val ERROR_VIEW = 1
    }
}