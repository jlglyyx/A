package com.example.module_main.ui.main.fragment

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_common.base.ui.fragment.BaseLazyFragment
import com.example.lib_common.bus.event.UIChangeLiveData
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.dialog.ImageViewPagerDialog
import com.example.lib_common.scope.ModelWithFactory
import com.example.lib_common.util.*
import com.example.lib_common.widget.CommonToolBar
import com.example.lib_common.widget.GridNinePictureView
import com.example.module_main.R
import com.example.module_main.data.model.MainData
import com.example.module_main.helper.getMainComponent
import com.example.module_main.ui.main.activity.AddDynamicActivity
import com.example.module_main.ui.main.activity.MainActivity
import com.example.module_main.viewmodel.MainViewModel
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.gson.Gson
import com.lxj.xpopup.XPopup
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fra_main.*
import kotlinx.android.synthetic.main.view_normal_recyclerview.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


@Route(path = AppConstant.RoutePath.MAIN_FRAGMENT)
class MainFragment : BaseLazyFragment(), OnRefreshLoadMoreListener {

    @Inject
    lateinit var gson: Gson
    @Inject
    @ModelWithFactory
    lateinit var mainViewModel: MainViewModel

    private lateinit var mAdapter: MAdapter

    private var mutableListOf: MutableList<MainData> = mutableListOf()

    private var pageNum = 1

    override fun getLayout(): Int {
        return R.layout.fra_main
    }

    override fun initData() {
        smartRefreshLayout.autoRefresh()
        initSmartRefreshLayout()
        lifecycleScope.launch(Dispatchers.IO) {
            mAdapter.setNewData(
                mutableListOf<MainData>().apply {
                    add(MainData().apply {
                        userImage =
                            "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
                        dynamicContent = "今天天气真好"
                        imageUrls = mutableListOf<String>().apply {
                            add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33102.jpg")
                            add("https://scpic.chinaz.net/files/pic/pic9/202106/apic33150.jpg")
                            add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                            add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")
                            add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32309.jpg")
                            add("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg")

                        }.formatWithComma()
                        videoUrls =
                            "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
                    })
                    add(MainData().apply {
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
                        videoUrls =
                            "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
                    })
                    add(MainData().apply {
                        userImage =
                            "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
                        videoUrls =
                            "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
                    })
                    add(MainData().apply {
                        userImage =
                            "https://img2.baidu.com/it/u=1801164193,3602394305&fm=26&fmt=auto&gp=0.jpg"
                    })
                    add(MainData())
                })

        }
    }

    override fun initView() {
        initRecyclerView()

        val registerForActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode != AppCompatActivity.RESULT_OK) {
                    return@registerForActivityResult
                }
                val images = it.data?.getStringArrayListExtra("Data")
                val content = it.data?.getStringExtra("content")
                val mutableListOf = mutableListOf<MainData>().apply {
                    if (!content.isNullOrEmpty()) {
                        add(MainData().apply {
                            dynamicContent = content
                        })
                    }
                    if (!images.isNullOrEmpty()) {
                        add((MainData().apply {
                            imageUrls = images.formatWithComma()
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
            .load(userInfo?.userImage)
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
        finishRefreshLoadMore(smartRefreshLayout)
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return mainViewModel.uC
    }

    override fun initViewModel() {
        getMainComponent(this).inject(this)

    }


    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        mainViewModel.sMutableLiveData.observe(this, Observer {
//            recyclerView.adapter = MAdapter(R.layout.item_title, it)
//        })

        mAdapter = MAdapter(mutableListOf).apply {
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.siv_img -> {
                        buildARouter(AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY)
                            .navigation()
                    }
                }
            }

            setOnItemClickListener { adapter, view, position ->
                buildARouter(AppConstant.RoutePath.DYNAMIC_DETAIL_ACTIVITY)
                    .navigation()
            }
        }
        recyclerView.adapter = mAdapter


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItem =
                    linearLayoutManager.findFirstVisibleItemPosition()
                val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                //大于0说明有播放
                if (GSYVideoManager.instance().playPosition >= 0) {
                    //当前播放的位置
                    val position = GSYVideoManager.instance().playPosition
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance()
                            .playTag == TAG && (position < firstVisibleItem || position > lastVisibleItem)
                    ) {
                        if (GSYVideoManager.isFullState(requireActivity())) {
                            return
                        }
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        GSYVideoManager.releaseAllVideos()
                        mAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    inner class MAdapter(list: MutableList<MainData>) :
        BaseQuickAdapter<MainData, BaseViewHolder>(list) {

        init {
            mLayoutResId = R.layout.view_dynamic_item
        }

        override fun convert(helper: BaseViewHolder, item: MainData) {
            initItemMainTitle(helper,item)

            if (item.dynamicContent.isNullOrEmpty()){
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

            if (item.videoUrls.isNullOrEmpty()){
                helper.setGone(R.id.item_main_content_video,false)
            }else{
                helper.setGone(R.id.item_main_content_video,true)
                initItemMainContentVideo(helper,item)
            }

            initItemMainIdentification(helper,item)
        }

        private fun initItemMainTitle(helper: BaseViewHolder,item: MainData){
            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
            helper.addOnClickListener(R.id.siv_img)
            helper.setText(R.id.tv_time, item.createTime)
            Glide.with(sivImg).load(item.userImage).into(sivImg)
        }
        private fun initItemMainContentText(helper: BaseViewHolder,item: MainData){
            helper.setText(R.id.tv_text, item.dynamicContent)
        }
        private fun initItemMainContentImage(helper: BaseViewHolder,item: MainData){
            val gridNinePictureView =
                helper.getView<GridNinePictureView>(R.id.gridNinePictureView)
            gridNinePictureView.data = item.imageUrls?.commaToList()!!
            gridNinePictureView.imageCallback = object : GridNinePictureView.ImageCallback {
                override fun imageClickListener(position: Int) {
                    val imageViewPagerDialog =
                        ImageViewPagerDialog(requireContext(), item.imageUrls?.commaToList()!!, position)
                    XPopup.Builder(requireContext()).asCustom(imageViewPagerDialog).show()
                }
            }
        }
        private fun initItemMainContentVideo(helper: BaseViewHolder,item: MainData){
            var gsyVideoPlayer = helper.getView<StandardGSYVideoPlayer>(R.id.detailPlayer)

            gsyVideoPlayer.thumbImageView = ImageView(requireContext()).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                setImageResource(R.drawable.iv_bear)
            }
            gsyVideoPlayer.setUpLazy(item.videoUrls, true, null, null, "这是title")
            gsyVideoPlayer.titleTextView.visibility = View.GONE
            gsyVideoPlayer.backButton.visibility = View.GONE
            gsyVideoPlayer.fullscreenButton
                .setOnClickListener {
                    gsyVideoPlayer.startWindowFullscreen(
                        context,
                        false,
                        true
                    )
                }
            gsyVideoPlayer.playTag = TAG
            gsyVideoPlayer.playPosition = helper.adapterPosition
            gsyVideoPlayer.isAutoFullWithSize = true
            gsyVideoPlayer.isReleaseWhenLossAudio = false
            gsyVideoPlayer.isShowFullAnimation = true
            gsyVideoPlayer.setIsTouchWiget(false)
        }
        private fun initItemMainIdentification(helper: BaseViewHolder,item: MainData){

        }
    }

    private fun getDynamicList(){
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


    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycleScope.cancel()
    }


}