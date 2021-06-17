package com.example.lib_common.base.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lib_common.base.viewmodel.BaseViewModel
import com.example.lib_common.bus.event.UIChangeLiveData
import com.example.lib_common.util.getStatusBarHeight
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

abstract class BaseFragment : Fragment() {

    private var mView: View? = null

    lateinit var mContext: Context

    private var uC: UIChangeLiveData? = null

    private var loadingPopupView: LoadingPopupView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mView == null) {
            mView = inflater.inflate(getLayout(), container, false)
            if (setStatusPadding()) {
                mView?.setPadding(0, getStatusBarHeight(requireActivity()), 0, 0)
            }
            mContext = requireContext()
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        uC = initUIChangeLiveData()
        initView()
        registerListener()
    }

    open fun initUIChangeLiveData(): UIChangeLiveData? {//在ViewModel层操作ui
        return null
    }

    open fun setStatusPadding(): Boolean {
        return false
    }

    abstract fun getLayout(): Int

    abstract fun initData()

    abstract fun initView()

    abstract fun initViewModel()


    fun <T : BaseViewModel> getViewModel(@NonNull clazz: Class<T>): T {

        return ViewModelProvider(this).get(clazz)
    }

    fun <T : BaseViewModel> getViewModel(
        @NonNull factory: ViewModelProvider.Factory,
        @NonNull clazz: Class<T>
    ): T {

        return ViewModelProvider(this, factory).get(clazz)
    }

    private fun registerListener() {
        uC?.let { uC ->
            uC.showLoadingEvent.observe(this, Observer {
                if (loadingPopupView == null) {
                    loadingPopupView = XPopup.Builder(requireContext())
                        .asLoading(it)
                } else {
                    loadingPopupView?.setTitle(it)
                }
                if (!loadingPopupView?.isShow!!) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        unRegisterListener()
        loadingPopupView?.dismiss()
        loadingPopupView = null
        mView = null
    }
}