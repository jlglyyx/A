package com.yang.common_lib.constant


/**
 * @ClassName RoutePath
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/11/28 14:34
 */
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


    //main activity
    const val MAIN_ACTIVITY = "/$MODULE_MAIN/$ACTIVITY/MainActivity"
    const val LOGIN_ACTIVITY = "/$MODULE_MAIN/$ACTIVITY/LoginActivity"
    const val SPLASH_ACTIVITY = "/$MODULE_MAIN/$ACTIVITY/SplashActivity"
    const val REGISTER_ACTIVITY = "/$MODULE_MAIN/$ACTIVITY/RegisterActivity"

    //main fragment


    //home fragment
    const val HOME_FRAGMENT = "/$MODULE_HOME/$FRAGMENT/HomeFragment"
    const val HOME_RECOMMEND_FRAGMENT = "/$MODULE_HOME/$FRAGMENT/HomeRecommendFragment"

    //home activity
    const val RELEASE_DYNAMIC_ACTIVITY = "/$MODULE_HOME/$ACTIVITY/ReleaseDynamicActivity"


    //picture fragment
    const val PICTURE_FRAGMENT = "/$MODULE_PICTURE/$FRAGMENT/HomeFragment"
    const val PICTURE_RECOMMEND_FRAGMENT = "/$MODULE_PICTURE/$FRAGMENT/PictureRecommendFragment"

    //picture activity
    const val PICTURE_PICTURE_DESC_ACTIVITY = "/$MODULE_PICTURE/$ACTIVITY/PictureDescActivity"

    //video fragment
    const val VIDEO_FRAGMENT = "/$MODULE_VIDEO/$FRAGMENT/VideoFragment"
    const val VIDEO_RECOMMEND_FRAGMENT = "/$MODULE_VIDEO/$FRAGMENT/VideoRecommendFragment"

    //home activity
    const val VIDEO_VIDEO_PLAY_ACTIVITY = "/$MODULE_VIDEO/$ACTIVITY/VideoPlayActivity"
    const val VIDEO_SEARCH_ACTIVITY = "/$MODULE_VIDEO/$ACTIVITY/SearchActivity"

    const val MINE_FRAGMENT = "/$MODULE_MINE/$FRAGMENT/MineFragment"


}