package com.yang.module_picture.ui.activity

import android.graphics.Rect
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.tabs.TabLayout
import com.lxj.xpopup.XPopup
import com.yang.lib_common.adapter.ImageViewPagerAdapter
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.dialog.EditBottomDialog
import com.yang.lib_common.dialog.ImageViewPagerDialog
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.clicks
import com.yang.lib_common.util.dip2px
import com.yang.module_picture.R
import com.yang.module_picture.data.CommonData
import com.yang.module_picture.helper.getPictureComponent
import com.yang.module_picture.viewmodel.PictureViewModel
import com.youth.banner.transformer.ScaleInTransformer
import kotlinx.android.synthetic.main.act_picture_item.*
import kotlinx.android.synthetic.main.fra_item_picture.recyclerView
import javax.inject.Inject


@Route(path = AppConstant.RoutePath.PICTURE_ITEM_ACTIVITY)
class PictureItemActivity : BaseActivity() {

    private lateinit var mCommentAdapter: MCommentAdapter

    @Inject
    lateinit var pictureViewModel: PictureViewModel

    private var isScroll = false

    override fun getLayout(): Int {
        return R.layout.act_picture_item
    }

    override fun initData() {
        val intent = intent
        val sid = intent.getStringExtra(AppConstant.Constant.ID)
        pictureViewModel.getImageItemData(sid ?: "")
    }

    override fun initView() {
        initRecyclerView()
        initTabLayout()
        initViewPager()
        tv_send_comment.clicks().subscribe {
            XPopup.Builder(this).autoOpenSoftInput(true).asCustom(EditBottomDialog(this).apply {
                dialogCallBack = object : EditBottomDialog.DialogCallBack {
                    override fun getComment(s: String) {
                        mCommentAdapter.addData(0, CommonData(s))

                        recyclerView.smoothScrollToPosition(0)
                    }

                }
            }).show()
        }

        iv_back.clicks().subscribe {
            finish()
        }
    }

    override fun initViewModel() {
        getPictureComponent(this).inject(this)
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return pictureViewModel.uC
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        mCommentAdapter =
            MCommentAdapter(mutableListOf<CommonData>().apply {
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
            }, R.layout.item_picture_comment).also {
                it.setOnItemChildClickListener { adapter, view, position ->
                    when (view.id) {
                        R.id.siv_img -> {
                            buildARouter(
                                AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY
                            ).navigation()
                        }
                    }
                }
            }
        recyclerView.adapter = mCommentAdapter


        nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            val rect = Rect()
            cl_picture.getHitRect(rect)
            isScroll = true
            if (cl_picture.getLocalVisibleRect(rect)) {
                tabLayout.getTabAt(0)?.select()
            } else {
                tabLayout.getTabAt(1)?.select()
            }
        }
    }

    private fun initViewPager() {

        val mutableListOf = mutableListOf<String>()
        mutableListOf.add("https://desk-fd.zol-img.com.cn/t_s1680x1050c5/g6/M00/0A/08/ChMkKV_ceSGIcOJdABOhSqd4uy8AAG8WwO_rfYAE6Fi406.jpg")
        mutableListOf.add("http://img.netbian.com/file/2021/1025/9e8c4ee0cdcafa7b1a6af4adf288b4a6.jpg")
        mutableListOf.add("https://desk-fd.zol-img.com.cn/t_s1680x1050c5/g6/M00/0A/08/ChMkKV_ceSGIcOJdABOhSqd4uy8AAG8WwO_rfYAE6Fi406.jpg")
        mutableListOf.add("https://desk-fd.zol-img.com.cn/t_s1680x1050c5/g6/M00/0A/08/ChMkKV_ceSGIcOJdABOhSqd4uy8AAG8WwO_rfYAE6Fi406.jpg")
        mutableListOf.add("http://img.netbian.com/file/2021/1025/9e8c4ee0cdcafa7b1a6af4adf288b4a6.jpg")
        mutableListOf.add("https://desk-fd.zol-img.com.cn/t_s1680x1050c5/g6/M00/0A/08/ChMkKV_ceSGIcOJdABOhSqd4uy8AAG8WwO_rfYAE6Fi406.jpg")
        mutableListOf.add("http://img.netbian.com/file/2021/0927/cb7c2da3190d6dd54f0113c3462784b3.jpg")
        mutableListOf.add("https://desk-fd.zol-img.com.cn/t_s1680x1050c5/g6/M00/0A/08/ChMkKV_ceSGIcOJdABOhSqd4uy8AAG8WwO_rfYAE6Fi406.jpg")
        mutableListOf.add("https://desk-fd.zol-img.com.cn/t_s1680x1050c5/g6/M00/0A/08/ChMkKV_ceSGIcOJdABOhSqd4uy8AAG8WwO_rfYAE6Fi406.jpg")
        mutableListOf.add("https://desk-fd.zol-img.com.cn/t_s1680x1050c5/g6/M00/0A/08/ChMkKV_ceSGIcOJdABOhSqd4uy8AAG8WwO_rfYAE6Fi406.jpg")
        mutableListOf.add("http://img.netbian.com/file/2021/1025/9e8c4ee0cdcafa7b1a6af4adf288b4a6.jpg")
        mutableListOf.add("http://img.netbian.com/file/2021/0927/cb7c2da3190d6dd54f0113c3462784b3.jpg")
        mutableListOf.add("https://desk-fd.zol-img.com.cn/t_s1680x1050c5/g6/M00/0A/08/ChMkKV_ceSGIcOJdABOhSqd4uy8AAG8WwO_rfYAE6Fi406.jpg")
        mutableListOf.add("https://desk-fd.zol-img.com.cn/t_s1680x1050c5/g6/M00/0A/08/ChMkKV_ceSGIcOJdABOhSqd4uy8AAG8WwO_rfYAE6Fi406.jpg")
        mutableListOf.add("https://desk-fd.zol-img.com.cn/t_s1680x1050c5/g6/M00/0A/08/ChMkKV_ceSGIcOJdABOhSqd4uy8AAG8WwO_rfYAE6Fi406.jpg")
        mutableListOf.add("https://desk-fd.zol-img.com.cn/t_s1680x1050c5/g6/M00/0A/08/ChMkKV_ceSGIcOJdABOhSqd4uy8AAG8WwO_rfYAE6Fi406.jpg")
        mutableListOf.add("https://desk-fd.zol-img.com.cn/t_s1680x1050c5/g6/M00/0A/08/ChMkKV_ceSGIcOJdABOhSqd4uy8AAG8WwO_rfYAE6Fi406.jpg")
        mutableListOf.add("https://desk-fd.zol-img.com.cn/t_s1680x1050c5/g6/M00/0A/08/ChMkKV_ceSGIcOJdABOhSqd4uy8AAG8WwO_rfYAE6Fi406.jpg")

        val imageViewPagerAdapter = ImageViewPagerAdapter(mutableListOf)
        viewPager.adapter = imageViewPagerAdapter
        viewPager.offscreenPageLimit = mutableListOf.size

        imageViewPagerAdapter.clickListener = object : ImageViewPagerAdapter.ClickListener {
            override fun onClickListener(view: View, position: Int) {
                val imageViewPagerDialog =
                    ImageViewPagerDialog(
                        this@PictureItemActivity,
                        imageViewPagerAdapter.data,
                        position
                    )
                imageViewPagerDialog.imageViewPagerDialogCallBack = object :
                    ImageViewPagerDialog.ImageViewPagerDialogCallBack {
                    override fun getPosition(position: Int) {
                        //viewPager.setCurrentItem(position, false)
                    }

                    override fun onViewClickListener(view: View) {

                    }

                }
                XPopup.Builder(this@PictureItemActivity).asCustom(imageViewPagerDialog).show()
            }

        }

        pictureViewModel.mImageItemData.observe(this, Observer {
            imageViewPagerAdapter.data = it.map { item ->
                item.imageUrl
            } as MutableList<String>
            imageViewPagerAdapter.notifyDataSetChanged()
        })

        val pageRecyclerView = viewPager.getChildAt(0) as RecyclerView
        pageRecyclerView.setPadding(10f.dip2px(this),0,10f.dip2px(this),0)
        pageRecyclerView.clipToPadding = false
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(5f.dip2px(this)))
        compositePageTransformer.addTransformer(ScaleInTransformer())
        viewPager.setPageTransformer(compositePageTransformer)
    }


    private fun initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().apply {
            view.setOnClickListener {
                isScroll = false
            }
        }.setText("图片"))
        tabLayout.addTab(tabLayout.newTab().apply {
            view.setOnClickListener {
                isScroll = false
            }
        }.setText("评论"))


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (!isScroll) {
                    if (tab?.position == 0) {
                        nestedScrollView.fullScroll(View.FOCUS_UP)
                    } else {
                        cl_picture.post {
                            nestedScrollView.scrollY = cl_picture.height
                        }
                    }
                }

            }

        })
    }


    inner class MCommentAdapter(list: MutableList<CommonData>, layoutResId: Int) :
        BaseQuickAdapter<CommonData, BaseViewHolder>(layoutResId, list) {

        override fun convert(helper: BaseViewHolder, item: CommonData) {
            helper.addOnClickListener(R.id.siv_img)
            helper.setText(R.id.tv_comment, item.content)
            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
            Glide.with(sivImg)
                .load("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
                .error(R.drawable.iv_image_error)
                .placeholder(R.drawable.iv_image_placeholder)
                .into(sivImg)
        }
    }

}