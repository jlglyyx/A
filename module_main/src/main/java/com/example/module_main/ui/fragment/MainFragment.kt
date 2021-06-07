package com.example.module_main.ui.fragment

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_common.base.ui.fragment.BaseFragment
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.help.getBaseComponent
import com.example.module_main.R
import com.example.module_main.data.model.AccountList
import com.example.module_main.di.component.DaggerMainComponent
import com.example.module_main.di.factory.MainViewModelFactory
import com.example.module_main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fra_main.*
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.F_MAIN)
class MainFragment:BaseFragment() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var mainViewModel: MainViewModel

    override fun getLayout(): Int {
        return R.layout.fra_main
    }

    override fun initData() {
    }

    override fun initView() {
        initRecyclerView()
    }

    override fun initViewModel() {
        DaggerMainComponent.builder().baseComponent(getBaseComponent()).build().inject(this)
        mainViewModel = getViewModel(mainViewModelFactory, MainViewModel::class.java)
        mainViewModel.getMainRepository()
    }


    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mainViewModel.sMutableLiveData.observe(this, Observer {
            recyclerView.adapter = MAdapter(R.layout.item_title, it)
        })
    }

    inner class MAdapter(layoutResId: Int, list: MutableList<AccountList>) :
        BaseQuickAdapter<AccountList, BaseViewHolder>(layoutResId, list) {
        override fun convert(helper: BaseViewHolder, item: AccountList) {
            helper.setText(R.id.tv_title, item.id.toString())
                .setText(R.id.tv_name,item.name)
        }
    }
}