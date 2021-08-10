package com.example.lib_common.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.lib_common.R
import com.example.lib_common.util.getScreenPx


/**
 * @ClassName RemoteMessageDialog
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/11/25 10:20
 */
class RemoteMessageDialog(context: Context) : Dialog(context), LifecycleObserver {


    private var tv_content: TextView? = null
    private val screenPx = getScreenPx(context)
    var content: String? = null
        set(value) {
            field = value
            if (field!!.length<3){
                field = "   $field   "
            }
            tv_content?.text = field
            Log.i(TAG, "$field")
        }

    constructor(context: Context, content: String) : this(context) {
        this.content = content
    }

    companion object {
        private const val TAG = "RemoteMessageDialog"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_remote_message)
        setCanceledOnTouchOutside(false)
        window?.setBackgroundDrawableResource(R.drawable.shape_remote_message_dialog_bg)
        window?.decorView?.setPadding(0, 0, 0, 0)
        window?.setDimAmount(0f)
        tv_content = findViewById(R.id.tv_content)
        if (content != null) {
            tv_content?.text = content
        }

        val lp = window?.attributes as WindowManager.LayoutParams
//        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.width = screenPx[0]*5/6
        lp.height = screenPx[0]*5/6*2/4
        lp.gravity = Gravity.CENTER
        window?.attributes = lp
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        this.cancel()
    }


}