package com.example.module_main.ui.fragment

import android.content.Intent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_common.base.ui.fragment.BaseLazyFragment
import com.example.lib_common.bus.event.UIChangeLiveData
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.dialog.ImageViewPagerDialog
import com.example.lib_common.widget.CommonToolBar
import com.example.lib_common.widget.GridNinePictureView
import com.example.module_main.R
import com.example.module_main.data.model.MainData
import com.example.module_main.di.factory.MainViewModelFactory
import com.example.module_main.helper.getMainComponent
import com.example.module_main.ui.activity.AddDynamicActivity
import com.example.module_main.viewmodel.MainViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fra_main.*
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.MAIN_FRAGMENT)
class MainFragment : BaseLazyFragment() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var mainViewModel: MainViewModel

    private lateinit var mAdapter: MAdapter

    override fun getLayout(): Int {
        return R.layout.fra_main
    }

    override fun initData() {
        //mainViewModel.getMainRepository()
    }

    override fun initView() {
        initRecyclerView()
        val registerForActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode != AppCompatActivity.RESULT_OK){
                    return@registerForActivityResult
                }
                val images = it.data?.getStringArrayListExtra("Data")
                val content = it.data?.getStringExtra("content")
//                if (images.isNullOrEmpty() || content.isNullOrEmpty()){
//                    return@registerForActivityResult
//                }
                val mutableListOf = mutableListOf<MainData>().apply {
                    add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE))
                    add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_TEXT).apply {
                        dynamicContent = content
                    })
                    add((MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE).apply {
                        imageList = images
                    }))
                    add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
                }
                mAdapter.addData(0, mutableListOf)
                recyclerView.scrollToPosition(0)
            }
        commonToolBar.imageAddCallBack = object : CommonToolBar.ImageAddCallBack {
            override fun imageAddClickListener(view: View) {
                registerForActivityResult.launch(
                    Intent(requireContext(), AddDynamicActivity::class.java)
                )
            }
        }
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return mainViewModel.uC
    }

    override fun initViewModel() {
        getMainComponent().inject(this)
        mainViewModel = getViewModel(mainViewModelFactory, MainViewModel::class.java)

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
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE).apply {
                imageList = mutableListOf<String>().apply {
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33102.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33150.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")

                }
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))

            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_TEXT))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE).apply {
                imageList = mutableListOf<String>().apply {
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33102.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33150.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                    add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32184.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32185.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32189.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32188.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32187.jpg")
                }
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE).apply {
                imageList = mutableListOf<String>().apply {
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33102.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33150.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                }
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))

            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_TEXT))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE).apply {
                imageList = mutableListOf<String>().apply {
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33102.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                    add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg")
                }
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE).apply {
                imageList = mutableListOf<String>().apply {
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33102.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33150.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                    add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32184.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32185.jpg")
                }
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))

            add(MainData(AppConstant.Constant.ITEM_MAIN_TITLE))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_TEXT))
            add(MainData(AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE).apply {
                imageList = mutableListOf<String>().apply {
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32188.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                    add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32184.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32185.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33102.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33150.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32189.jpg")
                    add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32187.jpg")
                }
            })
            add(MainData(AppConstant.Constant.ITEM_MAIN_IDENTIFICATION))
        }
        mAdapter = MAdapter(mutableListOf)
        recyclerView.adapter = mAdapter
    }

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
                    helper.setText(R.id.tv_text, item.dynamicContent)
                }
                AppConstant.Constant.ITEM_MAIN_CONTENT_IMAGE -> {

                    val gridNinePictureView =
                        helper.getView<GridNinePictureView>(R.id.gridNinePictureView)
                    gridNinePictureView.data = item.imageList!!
                    gridNinePictureView.imageCallback = object : GridNinePictureView.ImageCallback {
                        override fun imageClickListener(position: Int) {
                            val imageViewPagerDialog =
                                ImageViewPagerDialog(requireContext(), item.imageList!!, position)
                            XPopup.Builder(requireContext()).asCustom(imageViewPagerDialog).show()
                        }
                    }
                }
                AppConstant.Constant.ITEM_MAIN_IDENTIFICATION -> {

                }
            }
        }

    }
}