package com.yang.module_main.ui.menu.activity

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.lxj.xpopup.XPopup
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.dialog.ImageViewPagerDialog
import com.yang.lib_common.scope.ModelWithFactory
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.commaToList
import com.yang.lib_common.util.formatWithComma
import com.yang.lib_common.util.getUserInfo
import com.yang.module_main.data.model.DynamicData
import com.yang.module_main.helper.getMainComponent
import com.yang.lib_common.widget.GridNinePictureView
import com.yang.module_main.R
import com.yang.module_main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.view_normal_recyclerview.*
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.MY_PUSH_ACTIVITY)
class MyPushActivity : BaseActivity(), OnRefreshLoadMoreListener {

    @Inject
    @ModelWithFactory
    lateinit var mainViewModel: MainViewModel

    private lateinit var mAdapter: MAdapter

    private var pageNum = 1

    override fun getLayout(): Int {
        return R.layout.act_my_push
    }

    override fun initData() {
        smartRefreshLayout.autoRefresh()
        initSmartRefreshLayout()

    }

    override fun initView() {
        initRecyclerView()
        finishRefreshLoadMore(smartRefreshLayout)
        mainViewModel.dynamicListLiveData.observe(this, Observer {
            when {
                smartRefreshLayout.isRefreshing -> {
                    smartRefreshLayout.finishRefresh()
                    mAdapter.replaceData(it)
                }
                smartRefreshLayout.isLoading -> {
                    smartRefreshLayout.finishLoadMore()
                    if (pageNum != 1 && it.isNotEmpty()) {
                        smartRefreshLayout.setNoMoreData(true)
                    } else {
                        smartRefreshLayout.setNoMoreData(false)
                        mAdapter.addData(it)
                    }
                }
                else -> {
                    mAdapter.replaceData(it)
                }
            }
        })
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return mainViewModel.uC
    }

    override fun initViewModel() {
        getMainComponent(this).inject(this)


    }
    private fun initSmartRefreshLayout() {
        smartRefreshLayout.setOnRefreshLoadMoreListener(this)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        val mutableListOf = mutableListOf<DynamicData>().apply {
                add(DynamicData().apply {
                    userImage =
                        "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
                    content = "今天天气真好"
                    imageUrls = mutableListOf<String>().apply {
                        add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33102.jpg")
                        add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33150.jpg")
                        add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                        add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                        add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                        add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")

                    }.formatWithComma()
                })
                add(DynamicData().apply {
                    userImage =
                        "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
                    imageUrls = mutableListOf<String>().apply {
                        add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33102.jpg")
                        add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33150.jpg")
                        add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                        add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                        add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                        add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")

                    }.formatWithComma()
                })
                add(DynamicData().apply {
                    userImage =
                        "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
                })
                add(DynamicData().apply {
                    userImage =
                        "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
                })
                add(DynamicData())
        }
        mAdapter = MAdapter(mutableListOf).apply {
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.siv_img -> {
                        buildARouter(AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY)
                            .navigation()
                    }

                }
            }
        }
        recyclerView.adapter = mAdapter


    }

    inner class MAdapter(list: MutableList<DynamicData>) :
        BaseQuickAdapter<DynamicData, BaseViewHolder>(list) {

        init {
            mLayoutResId = R.layout.view_dynamic_item
        }

        override fun convert(helper: BaseViewHolder, item: DynamicData) {

            initItemMainTitle(helper,item)

            if (item.content.isNullOrEmpty()){
                helper.setGone(R.id.item_main_content_text,false)
            }else{
                helper.setGone(R.id.item_main_content_text,true)
                initItemMainContentText(helper,item)
            }

            if (item.imageUrls.isNullOrEmpty()){
                helper.setGone(R.id.item_main_content_image,false)
            }else{
                helper.setGone(R.id.item_main_content_image,true)
                initItemMainContentImage(helper,item)
            }
            initItemMainIdentification(helper,item)
        }

        private fun initItemMainTitle(helper: BaseViewHolder,item: DynamicData){
            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
            helper.addOnClickListener(R.id.siv_img)
            helper.setText(R.id.tv_time, item.createTime)
            Glide.with(sivImg).load(item.userImage).into(sivImg)
        }
        private fun initItemMainContentText(helper: BaseViewHolder,item: DynamicData){
            helper.setText(R.id.tv_text, item.content)
        }
        private fun initItemMainContentImage(helper: BaseViewHolder,item: DynamicData){
            val gridNinePictureView =
                helper.getView<GridNinePictureView>(R.id.gridNinePictureView)
            gridNinePictureView.data = item.imageUrls?.commaToList()!!
            gridNinePictureView.imageCallback = object : GridNinePictureView.ImageCallback {
                override fun imageClickListener(position: Int) {
                    val imageViewPagerDialog =
                        ImageViewPagerDialog(mContext, item.imageUrls?.commaToList()!!, position)
                    XPopup.Builder(mContext).asCustom(imageViewPagerDialog).show()
                }
            }
        }
        private fun initItemMainIdentification(helper: BaseViewHolder,item: DynamicData){

        }
    }

    private fun getDynamicList(){
        val mutableMapOf = mutableMapOf<String, String>()
        mutableMapOf[AppConstant.Constant.USER_ID] = getUserInfo()?.id.toString()
        mutableMapOf[AppConstant.Constant.PAGE_NUMBER] = pageNum.toString()
        mutableMapOf[AppConstant.Constant.PAGE_SIZE] = AppConstant.Constant.PAGE_SIZE_COUNT.toString()
        mainViewModel.getDynamicList(mutableMapOf)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageNum++
        getDynamicList()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageNum = 1
        getDynamicList()
    }

}