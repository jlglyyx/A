package com.yang.module_main.ui.main.fragment

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.gson.Gson
import com.lxj.xpopup.XPopup
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.yang.apt_annotation.annotain.InjectViewModel
import com.yang.lib_common.base.ui.fragment.BaseLazyFragment
import com.yang.lib_common.bus.event.LiveDataBus
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.dialog.ImageViewPagerDialog
import com.yang.lib_common.proxy.InjectViewModelProxy
import com.yang.lib_common.util.*
import com.yang.lib_common.widget.CommonToolBar
import com.yang.module_main.R
import com.yang.module_main.adapter.DynamicAdapter
import com.yang.module_main.data.model.DynamicData
import com.yang.module_main.ui.main.activity.AddDynamicActivity
import com.yang.module_main.ui.main.activity.MainActivity
import com.yang.module_main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fra_main.*
import kotlinx.android.synthetic.main.view_normal_recyclerview.*
import kotlinx.coroutines.cancel
import javax.inject.Inject


@Route(path = AppConstant.RoutePath.MAIN_FRAGMENT)
class MainFragment : BaseLazyFragment(), OnRefreshLoadMoreListener {

    @Inject
    lateinit var gson: Gson

    @InjectViewModel
    lateinit var mainViewModel: MainViewModel

    private lateinit var mAdapter: MAdapter

    private var mutableListOf: MutableList<DynamicData> = mutableListOf()

    private var pageNum = 1

    override fun getLayout(): Int {
        return R.layout.fra_main
    }

    override fun initData() {
        smartRefreshLayout.autoRefresh()
        initSmartRefreshLayout()

        LiveDataBus.instance.with("refresh_dynamic").observe(this, Observer {

            onRefresh(smartRefreshLayout)
        })

        mainViewModel.dynamicListLiveData.observe(this, Observer {
            when {
                smartRefreshLayout.isRefreshing -> {
                    smartRefreshLayout.finishRefresh()
                    if (it.size == 0) {
                        mainViewModel.showRecyclerViewEmptyEvent()
                    } else {
                        mAdapter.replaceData(it)
                    }
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
                    if (it.size == 0) {
                        mainViewModel.showRecyclerViewEmptyEvent()
                    } else {
                        mAdapter.replaceData(it)
                    }
                }
            }
        })
    }

    override fun initView() {
        initRecyclerView()
        val registerForActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode != AppCompatActivity.RESULT_OK) {
                    return@registerForActivityResult
                }
                val images = it.data?.getStringArrayListExtra(AppConstant.Constant.DATA)
                val content = it.data?.getStringExtra(AppConstant.Constant.CONTENT)
                val mutableListOf = mutableListOf<DynamicData>().apply {
                    if (!content.isNullOrEmpty()) {
                        add(DynamicData().apply {
                            this.content = content
                        })
                    }
                    if (!images.isNullOrEmpty()) {
                        add((DynamicData().apply {
                            imageUrls = images.formatWithSymbol("#")
                        }))
                    }
                }
                mAdapter.addData(0, mutableListOf)
                recyclerView.scrollToPosition(0)
            }
        commonToolBar.ivBack.let {
            val mLayoutParams = it.layoutParams as ConstraintLayout.LayoutParams
            it.setPadding(
                0f.dip2px(requireContext()),
                0f.dip2px(requireContext()),
                0f.dip2px(requireContext()),
                0f.dip2px(requireContext())
            )
            mLayoutParams.marginStart = 15f.dip2px(requireContext())
            it.shapeAppearanceModel = ShapeAppearanceModel.builder()
                .setAllCornerSizes(ShapeAppearanceModel.PILL)
                .build()
        }
        val userInfo = getUserInfo()
        Glide.with(this)
            .load(userInfo?.userImage?:"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic%2F39%2Fb7%2F53%2F39b75357f98675e2d6d5dcde1fb805a3.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642840086&t=2a7574a5d8ecc96669ac3e050fe4fd8e")
            .error(R.drawable.iv_image_error)
            .placeholder(R.drawable.iv_image_placeholder)
            .into(commonToolBar.ivBack)

        commonToolBar.imageAddCallBack = object : CommonToolBar.ImageAddCallBack {
            override fun imageAddClickListener() {
                registerForActivityResult.launch(
                    Intent(requireContext(), AddDynamicActivity::class.java)
                )
            }
        }
        commonToolBar.imageBackCallBack = object : CommonToolBar.ImageBackCallBack {
            override fun imageBackClickListener() {
                (activity as MainActivity).drawerLayout.openDrawer(GravityCompat.START)
            }
        }


    }

    private fun initSmartRefreshLayout() {
        smartRefreshLayout.setOnRefreshLoadMoreListener(this)
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return mainViewModel.uC
    }

    override fun initViewModel() {
        InjectViewModelProxy.inject(this)
    }


    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = MAdapter(mutableListOf).apply {
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.siv_img -> {
                        buildARouter(AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY).withString(AppConstant.Constant.ID,"").navigation()
                    }
                }
            }

            setOnItemClickListener { adapter, view, position ->
                val item = mAdapter.getItem(position)
                buildARouter(AppConstant.RoutePath.DYNAMIC_DETAIL_ACTIVITY)
                    .withString(AppConstant.Constant.ID,item?.id)
                    .navigation()
            }
        }
        recyclerView.adapter = mAdapter
        registerRefreshAndRecyclerView(smartRefreshLayout,mAdapter)
    }

    inner class MAdapter(list: MutableList<DynamicData>) :
        BaseQuickAdapter<DynamicData, BaseViewHolder>(list) {

        init {
            mLayoutResId = R.layout.view_dynamic_item
        }

        override fun convert(helper: BaseViewHolder, item: DynamicData) {
            initItemMainTitle(helper, item)

            if (item.content.isNullOrEmpty()) {
                helper.setGone(R.id.item_main_content_text, false)
            } else {
                helper.setGone(R.id.item_main_content_text, true)
                initItemMainContentText(helper, item)
            }

            if (item.imageUrls.isNullOrEmpty()) {
                helper.setGone(R.id.mRecyclerView, false)
            } else {
                helper.setGone(R.id.mRecyclerView, true)
                initItemMainContentImage(helper, item)
            }

            initItemMainIdentification(helper, item)
        }

        private fun initItemMainTitle(helper: BaseViewHolder, item: DynamicData) {
            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
            helper.addOnClickListener(R.id.siv_img)
            helper.setText(R.id.tv_time, item.createTime)
            Glide.with(sivImg).load(item.userImage?:"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic%2F39%2Fb7%2F53%2F39b75357f98675e2d6d5dcde1fb805a3.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642840086&t=2a7574a5d8ecc96669ac3e050fe4fd8e")
                .error(R.drawable.iv_image_error)
                .placeholder(R.drawable.iv_image_placeholder).into(sivImg)
        }

        private fun initItemMainContentText(helper: BaseViewHolder, item: DynamicData) {
            helper.setText(R.id.tv_text, item.content)
        }

        private fun initItemMainContentImage(helper: BaseViewHolder, item: DynamicData) {
            val mRecyclerView = helper.getView<RecyclerView>(R.id.mRecyclerView)
            mRecyclerView.layoutManager = GridLayoutManager(mContext, 3)
            val dynamicAdapter = DynamicAdapter(
                R.layout.view_item_grid_nine_picture,
                item.imageUrls?.symbolToList("#")!!
            )
            mRecyclerView.adapter = dynamicAdapter
            dynamicAdapter.setOnItemClickListener { adapter, view, position ->
                val imageViewPagerDialog =
                    ImageViewPagerDialog(
                        requireContext(),
                        item.imageUrls?.symbolToList("#")!!,
                        position,
                        true
                    )
                XPopup.Builder(requireContext()).asCustom(imageViewPagerDialog).show()
            }
        }

        private fun initItemMainIdentification(helper: BaseViewHolder, item: DynamicData) {

        }
    }

    private fun getDynamicList() {
        val mutableMapOf = mutableMapOf<String, String>()
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


    override fun onDestroyView() {
        super.onDestroyView()
        lifecycleScope.cancel()
    }


}