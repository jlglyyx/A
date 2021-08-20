package com.yang.lib_common.handle

import android.util.Log


/**
 * @Author Administrator
 * @ClassName CrashHandle
 * @Description
 * @Date 2021/8/4 17:04
 */
class CrashHandle : Thread.UncaughtExceptionHandler {

    private val uncaughtExceptionHandler: Thread.UncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()


    companion object{
        private const val TAG = "CrashHandle"
        val instance: CrashHandle by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            CrashHandle()
        }
    }

    override fun uncaughtException(t: Thread, e: Throwable) {

        Log.i(TAG, "uncaughtException: ${e.message}")



//        buildARouter(AppConstant.RoutePath.MAIN_ACTIVITY).navigation()
//        android.os.Process.killProcess(android.os.Process.myPid())
  //      exitProcess(0)
        uncaughtExceptionHandler.uncaughtException(t, e)
    }
}