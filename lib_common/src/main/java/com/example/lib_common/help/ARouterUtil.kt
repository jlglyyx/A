@file:JvmName("ARouterUtil")
package com.example.lib_common.help

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

fun buildARouter(path:String): Postcard {
    return ARouter.getInstance().build(path)
}
