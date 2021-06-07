package com.example.lib_common.util

import android.util.DisplayMetrics
import android.util.Log
import android.view.Window

private const val TAG = "AppUtil"

fun getScreenSize(window: Window): IntArray {
    val displayMetrics = DisplayMetrics()
    window.windowManager.defaultDisplay.getRealMetrics(displayMetrics)
    Log.i(TAG, "getScreenSize: ${displayMetrics.widthPixels} === ${displayMetrics.heightPixels}")
    return intArrayOf(displayMetrics.widthPixels, displayMetrics.heightPixels)
}