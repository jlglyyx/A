@file:JvmName("AppUtils")

package com.example.lib_common.util

import android.content.Context
import android.os.Environment
import android.util.Log
import android.view.View
import com.example.lib_common.constant.AppConstant.Constant.CLICK_TIME
import com.google.gson.Gson
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.core.Observable
import java.io.File
import java.util.concurrent.TimeUnit


private const val TAG = "AppUtils"

/*
* @return 宽高集合
**/
fun getScreenPx(context: Context): ArrayList<Int> {
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val widthPixels = displayMetrics.widthPixels
    val heightPixels = displayMetrics.heightPixels
    return arrayListOf(widthPixels, heightPixels)
}

/*
* @return 宽高集合
**/
fun getScreenDpi(context: Context): ArrayList<Float> {
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val widthPixels = displayMetrics.xdpi
    val heightPixels = displayMetrics.ydpi
    return arrayListOf(widthPixels, heightPixels)
}

fun getStatusBarHeight(context: Context): Int {
    val resources = context.resources
    val identifier = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(identifier)
}

/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun Float.dip2px(context: Context): Int {
    val scale = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun Float.px2dip(context: Context): Int{
    val scale = context.resources.displayMetrics.density
    return (this / scale + 0.5f).toInt()
}

fun View.clicks() :Observable<Unit> {
    return this.clicks().throttleFirst(CLICK_TIME, TimeUnit.MILLISECONDS)
}

fun toJson(src: Any): String {
    val gson = Gson()
    return gson.toJson(src)
}

fun <T> fromJson(json: String, t: Class<T>): T {
    val gson = Gson()
    return gson.fromJson<T>(json, t)
}

fun getFilePath():MutableList<String>{
    val mutableListOf = mutableListOf<String>()
    val file = File("${Environment.getExternalStorageDirectory()}/DCIM/Camera")
//    val file = File("${Environment.getExternalStorageDirectory()}/MFiles/picture")
    if (file.isDirectory){
        val listFiles = file.listFiles()
        for (mFiles in listFiles){
            Log.i(TAG, "getFilePath: ${mFiles.absolutePath}")
            mutableListOf.add(mFiles.absolutePath)
        }
    }

    return mutableListOf
}

