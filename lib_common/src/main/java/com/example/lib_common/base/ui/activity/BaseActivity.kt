package com.example.lib_common.base.ui.activity

import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lib_common.base.viewmodel.BaseViewModel
import com.example.lib_common.bus.event.UIChangeLiveData
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        private const val TAG  = "BaseActivity"
    }

    private var uC: UIChangeLiveData? = null

    private var loadingPopupView: LoadingPopupView? = null

    val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        initViewModel()
        uC = initUIChangeLiveData()
        initData()
        initView()
        registerListener()
    }

    abstract fun getLayout(): Int

    abstract fun initData()

    abstract fun initView()

    abstract fun initViewModel()

    open fun initUIChangeLiveData(): UIChangeLiveData? {//在ViewModel层操作ui
        return null
    }
    
    fun <T:BaseViewModel> getViewModel(@NonNull clazz: Class<T>):T{

        return ViewModelProvider(this).get(clazz)
    }
    
    fun <T:BaseViewModel> getViewModel(@NonNull factory: ViewModelProvider.Factory,@NonNull clazz: Class<T>):T{

        return ViewModelProvider(this, factory).get(clazz)
    }




    private fun registerListener() {
        uC?.let { uC ->
            uC.showLoadingEvent.observe(this, Observer {
                if (loadingPopupView == null){
                    loadingPopupView = XPopup.Builder(this)
                        .asLoading(it)
                }else{
                    loadingPopupView?.setTitle(it)
                }
                if (!loadingPopupView?.isShow!!){
                    loadingPopupView?.show()
                }
            })

            uC.dismissDialogEvent.observe(this, Observer {
                loadingPopupView?.dismiss()
            })
        }

    }

    private fun unRegisterListener() {
        uC?.let { uC ->
            uC.showLoadingEvent.removeObservers(this)
            uC.dismissDialogEvent.removeObservers(this)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        loadingPopupView?.dismiss()
        loadingPopupView = null
        unRegisterListener()
    }
}