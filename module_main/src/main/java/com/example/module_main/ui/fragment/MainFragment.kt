package com.example.module_main.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_common.base.ui.fragment.BaseLazyFragment
import com.example.lib_common.bus.event.UIChangeLiveData
import com.example.lib_common.constant.AppConstant
import com.example.module_main.R
import com.example.module_main.data.model.MainData
import com.example.module_main.di.factory.MainViewModelFactory
import com.example.module_main.helper.getMainComponent
import com.example.module_main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fra_main.*
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.MAIN_FRAGMENT)
class MainFragment : BaseLazyFragment() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var mainViewModel: MainViewModel

    override fun getLayout(): Int {
        return R.layout.fra_main
    }

    override fun initData() {
        //mainViewModel.getMainRepository()
    }

    override fun initView() {
        initRecyclerView()
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return mainViewModel.uC
    }

    override fun initViewModel() {
        getMainComponent().inject(this)
        mainViewModel = getViewModel(mainViewModelFactory, MainViewModel::class.java)

    }

    override fun setStatusPadding(): Boolean {
        return true
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        mainViewModel.sMutableLiveData.observe(this, Observer {
//            recyclerView.adapter = MAdapter(R.layout.item_title, it)
//        })

        val mutableListOf = mutableListOf<MainData>().apply {
            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_TEXT))
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))

            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE))
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))

            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_TEXT))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE))
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
        }
        recyclerView.adapter = MAdapter(mutableListOf)
    }

    //    inner class MAdapter(layoutResId: Int, list: MutableList<AccountList>) :
//        BaseQuickAdapter<AccountList, BaseViewHolder>(layoutResId, list) {
//        override fun convert(helper: BaseViewHolder, item: AccountList) {
//            helper.setText(R.id.tv_title, item.id.toString())
//                .setText(R.id.tv_name, item.name)
//        }
//    }
    inner class MAdapter(list: MutableList<MainData>) :
        BaseMultiItemQuickAdapter<MainData, BaseViewHolder>(list) {

        init {
            addItemType(AppConstant.Constant.ITEM_MAIN_TITLE, R.layout.item_main_title)
            addItemType(
                AppConstant.Constant.ITEM_MAIN_CONTENT_TEXT,
                R.layout.item_main_content_text
            )
            addItemType(
                AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE,
                R.layout.item_main_content_image
            )
            addItemType(
                AppConstant.Constant.ITEM_MAIN_IDENTIFICATION,
                R.layout.item_main_identification
            )
        }

        override fun convert(helper: BaseViewHolder, item: MainData) {
            when (item.itemType) {
                AppConstant.Constant.ITEM_MAIN_TITLE -> {

                }
                AppConstant.Constant.ITEM_MAIN_CONTENT_TEXT -> {

                }
                AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE -> {

                }
                AppConstant.Constant.ITEM_MAIN_IDENTIFICATION -> {

                }
            }
        }

    }
}