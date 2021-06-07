package com.example.lib_common.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lib_common.base.viewmodel.BaseViewModel

abstract class BaseFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(getLayout(), container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initData()
        initView()
    }

    abstract fun getLayout(): Int

    abstract fun initData()

    abstract fun initView()

    abstract fun initViewModel()


    fun <T: BaseViewModel> getViewModel(@NonNull clazz: Class<T>):T{

        return ViewModelProvider(this).get(clazz)
    }

    fun <T: BaseViewModel> getViewModel(@NonNull factory: ViewModelProvider.Factory, @NonNull clazz: Class<T>):T{

        return ViewModelProvider(this, factory).get(clazz)
    }



    override fun onDestroyView() {
        super.onDestroyView()
    }
}