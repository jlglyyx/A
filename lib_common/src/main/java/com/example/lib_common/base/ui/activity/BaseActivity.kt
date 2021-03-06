package com.example.lib_common.base.ui.activity

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lib_common.R
import com.example.lib_common.base.di.factory.BaseViewModelFactory
import com.example.lib_common.base.viewmodel.BaseViewModel
import com.example.lib_common.bus.event.UIChangeLiveData
import com.example.lib_common.help.getBaseComponent
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        private const val TAG  = "BaseActivity"
    }

    private var uC: UIChangeLiveData? = null
    @Inject
    lateinit var baseViewModelFactory: BaseViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        getBaseComponent().inject(this)
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


    private var dialog: AlertDialog? = null

    private fun registerListener() {
        uC?.let { uC ->
            uC.showLoadingEvent.observe(this, Observer {
                if (dialog == null){
                    dialog = AlertDialog.Builder(this).setTitle("标题").setMessage(it).setIcon(
                        R.drawable.iv_test
                    ).create()
                }else{
                    dialog?.setMessage(it)
                }
                if (!dialog?.isShowing!!){
                    dialog?.show()
                }
            })

            uC.dismissDialogEvent.observe(this, Observer {
                dialog?.dismiss()
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
        unRegisterListener()
    }
}