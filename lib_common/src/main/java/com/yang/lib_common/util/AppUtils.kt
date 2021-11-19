@file:JvmName("AppUtils")

package com.yang.lib_common.util

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.jakewharton.rxbinding4.view.clicks
import com.tencent.mmkv.MMKV
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.constant.AppConstant.Constant.CLICK_TIME
import com.yang.lib_common.data.UserInfoData
import io.reactivex.rxjava3.core.Observable
import java.io.File
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit


private const val TAG = "AppUtils"

val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd hh:mm:ss")

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
fun Float.px2dip(context: Context): Int {
    val scale = context.resources.displayMetrics.density
    return (this / scale + 0.5f).toInt()
}

fun View.clicks(): Observable<Unit> {
    return this.clicks().throttleFirst(CLICK_TIME, TimeUnit.MILLISECONDS)
}


fun Any.toJson(): String {
    return Gson().toJson(this)
}

fun <T> fromJson(json: String, t: Class<T>): T {
    return Gson().fromJson<T>(json, t)
}

fun getFilePath(path: String = "/MFiles/picture"): MutableList<String> {
    val mutableListOf = mutableListOf<String>()
    //val file = File("${Environment.getExternalStorageDirectory()}/DCIM/Camera")
    val file = File("${Environment.getExternalStorageDirectory()}$path")
    if (file.isDirectory) {
        val listFiles = file.listFiles()
        listFiles?.let {
            for (mFiles in listFiles) {
                Log.i(TAG, "getFilePath: ${mFiles.absolutePath}")
                mutableListOf.add(mFiles.absolutePath)
            }
        }
    }

    return mutableListOf
}

/**
 * 获取getDefaultMMKV
 */
fun getDefaultMMKV(): MMKV {
    return MMKV.defaultMMKV()
}

fun getUserInfo(): UserInfoData? {
    val userInfo = getDefaultMMKV().decodeString(AppConstant.Constant.USER_INFO, "")
    if (!userInfo.isNullOrEmpty()) {
        return Gson().fromJson<UserInfoData>(userInfo, UserInfoData::class.java)
    }
    return null
}


fun MutableList<String>.formatWithSymbol(symbol: String = ","): String {
    val stringBuilder = StringBuilder()
    this.forEachIndexed { index, s ->
        if (index == this.size - 1) {
            stringBuilder.append(s)
        } else {
            stringBuilder.append(s).append(symbol)
        }
    }
    return stringBuilder.toString()
}

fun String.symbolToList(symbol: String = ","): MutableList<String> {
    val mutableListOf = mutableListOf<String>()
    val split = this.split(symbol)
    mutableListOf.addAll(split)
    return mutableListOf
}

fun uri2path(context: Context, uri: Uri): String {
    var path = ""
    val contentResolver = context.contentResolver
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val query = contentResolver.query(uri, projection, null, null, null)
    try {
        query?.let {
            val columnIndex = query.getColumnIndex(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            path = query.getString(columnIndex)
            query.close()
        }
    } catch (e: Exception) {
        Log.i(TAG, "uri2path: ${e.message}")
    }

    return path
}

/**
 * 获取app VersionCode
 */
fun getVersionCode(context: Context): Int {
    val packageManager = context.packageManager
    val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
    return packageInfo.versionCode

}

/**
 * 获取app VersionName
 */
fun getVersionName(context: Context): String {
    val packageManager = context.packageManager
    val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
    return packageInfo.versionName
}

/**
 * 获取指定文件大小
 */
fun getAllFileSize(file: File): Long {
    var size = 0L
    if (file.isDirectory) {
        val listFiles = file.listFiles()
        listFiles?.let {
            if (listFiles.isEmpty()) {
                return 0
            }
            for (mFile in listFiles) {
                size += if (mFile.isDirectory) {
                    getAllFileSize(mFile)
                } else {
                    mFile.length()
                }
            }
        }
    } else {
        size = file.length()
    }
    return size
}

/**
 * 格式化文件大小格式
 */
fun formatSize(size: Long): String {
    Log.i(TAG, "formatSize: $size")

    val k = size.toFloat() / 1024
    if (k < 1) {
        return "0K"
    }
    val m = k / 1024

    if (m < 1) {
        return DecimalFormat("0.00K").format(k)
    }

    val t = m / 1024

    if (t < 1) {
        return DecimalFormat("0.00M").format(m)
    }

    val t1 = t / 1024

    if (t1 < 1) {
        return DecimalFormat("0.00T").format(t)
    }
    return DecimalFormat("0.00T").format(t1)
}

fun deleteDirectory(file: File) {
    if (file.isDirectory) {
        file.listFiles()?.let {
            if (it.isNotEmpty()) {
                for (mFile in it) {
                    if (mFile.isDirectory) {
                        deleteDirectory(file)
                    } else {
                        deleteFile(mFile)
                    }
                }
            }
        }
    } else {
        deleteFile(file)
    }
}


fun deleteFile(file: File) {
    if (file.exists()) {
        file.delete()
    }
}

