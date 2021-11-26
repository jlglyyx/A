package com.yang.module_main.ui.main.activity

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.lxj.xpopup.XPopup
import com.yang.lib_common.adapter.CommentAdapter
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.data.CommentData
import com.yang.lib_common.dialog.EditBottomDialog
import com.yang.lib_common.dialog.ImageViewPagerDialog
import com.yang.lib_common.scope.ModelWithFactory
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.clicks
import com.yang.lib_common.util.symbolToList
import com.yang.module_main.R
import com.yang.module_main.adapter.DynamicAdapter
import com.yang.module_main.data.model.DynamicData
import com.yang.module_main.helper.getMainComponent
import com.yang.module_main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.act_dynamic_detail.*
import kotlinx.android.synthetic.main.item_dynamic_detail_comment.*
import kotlinx.android.synthetic.main.item_dynamic_detail_comment.view.*
import kotlinx.android.synthetic.main.item_main_content_image.*
import kotlinx.android.synthetic.main.item_main_content_text.*
import javax.inject.Inject

/**
 * @Author Administrator
 * @ClassName DynamicDetailActivity
 * @Description
 * @Date 2021/8/2 17:19
 */
@Route(path = AppConstant.RoutePath.DYNAMIC_DETAIL_ACTIVITY)
class DynamicDetailActivity : BaseActivity() {

    @Inject
    @ModelWithFactory
    lateinit var mainViewModel: MainViewModel

    private lateinit var commentAdapter: CommentAdapter

    override fun getLayout(): Int {

        return R.layout.act_dynamic_detail
    }

    override fun initData() {
        getDynamicDetail()

        mainViewModel.dynamicListLiveData.observe(this, Observer {
            val dynamicData = it[1]
            initItemMainContentImage(dynamicData)
            initItemMainTitle(dynamicData)
            initItemMainContentText(dynamicData)
        })
    }

    private fun initItemMainTitle(item: DynamicData) {
        siv_img.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY).withString(
                AppConstant.Constant.ID,
                ""
            ).navigation()
        }
        tv_time.text = item.createTime
        Glide.with(this).load(item.userImage)
            .error(R.drawable.iv_image_error)
            .placeholder(R.drawable.iv_image_placeholder).into(siv_img)
    }

    private fun initItemMainContentText(item: DynamicData) {
        if (TextUtils.isEmpty(item.content)) {
            item_main_content_text.visibility = View.GONE
        } else {
            item_main_content_text.visibility = View.VISIBLE
            tv_text.text = item.content
        }
    }

    private fun initItemMainContentImage(item: DynamicData) {
        mRecyclerView.layoutManager = LinearLayoutManager(this@DynamicDetailActivity)
        val dynamicAdapter = DynamicAdapter(
            R.layout.view_item_grid_nine_picture,
            item.imageUrls?.symbolToList("#")!!
        )
        mRecyclerView.adapter = dynamicAdapter
        dynamicAdapter.setOnItemClickListener { adapter, view, position ->
            val imageViewPagerDialog =
                ImageViewPagerDialog(
                    this@DynamicDetailActivity,
                    item.imageUrls?.symbolToList("#")!!,
                    position
                )
            XPopup.Builder(this@DynamicDetailActivity).asCustom(imageViewPagerDialog).show()
        }
    }

    override fun initView() {
        initRecyclerView()
        tv_send_comment.clicks().subscribe {
            XPopup.Builder(this).autoOpenSoftInput(true).asCustom(EditBottomDialog(this).apply {
                dialogCallBack = object : EditBottomDialog.DialogCallBack {
                    override fun getComment(s: String) {
                        commentAdapter.addData(0, CommentData(0, 0).apply {
                            comment = s
                        })
                        nestedScrollView.fullScroll(View.FOCUS_DOWN)
                        commentAdapter.getViewByPosition(
                            recyclerView,
                            0,
                            com.yang.lib_common.R.id.siv_img
                        )?.let { it1 ->
                            scrollToPosition(it1)
                        }
                    }

                }
            }).show()
        }
    }

    override fun initViewModel() {
        getMainComponent(this).inject(this)
    }

    override fun initUIChangeLiveData(): UIChangeLiveData {
        return mainViewModel.uC
    }

    private fun getDynamicDetail() {
        val mutableMapOf = mutableMapOf<String, String>()
        mutableMapOf[AppConstant.Constant.ID] = ""
        mainViewModel.getDynamicDetail(mutableMapOf)
    }

    private fun scrollToPosition(view: View) {
        val intArray = IntArray(2)
        view.getLocationOnScreen(intArray)
        nestedScrollView.scrollTo(intArray[0], intArray[1])
    }


    private fun initRecyclerView() {

        recyclerView.layoutManager = LinearLayoutManager(this)
        commentAdapter = CommentAdapter(mutableListOf<CommentData>().apply {
        }).apply {
            setOnItemChildClickListener { adapter, view, position ->
                val item = commentAdapter.getItem(position)
                item?.let {
                    when (view.id) {
                        com.yang.lib_common.R.id.siv_img -> {
                            buildARouter(AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY).withString(
                                AppConstant.Constant.ID,
                                ""
                            ).navigation()
                        }
                        com.yang.lib_common.R.id.siv_reply_img -> {
                            buildARouter(AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY).withString(
                                AppConstant.Constant.ID,
                                ""
                            ).navigation()
                        }
                        com.yang.lib_common.R.id.tv_reply -> {
                            XPopup.Builder(this@DynamicDetailActivity)
                                .autoOpenSoftInput(true)
                                .asCustom(EditBottomDialog(this@DynamicDetailActivity).apply {
                                    dialogCallBack = object : EditBottomDialog.DialogCallBack {
                                        override fun getComment(s: String) {
                                            when (it.itemType) {
                                                0 -> {
                                                    it.addSubItem(CommentData(1, 1).apply {
                                                        comment = s
                                                        parentId = it.id

                                                    })
                                                    commentAdapter.collapse(position)
                                                    commentAdapter.expand(position)
                                                }
                                                1, 2 -> {
                                                    it.parentId?.let { mParentId ->
                                                        val mPosition = commentAdapter.data.indexOf(commentAdapter.data.findLast {
                                                            TextUtils.equals(it.parentId,mParentId)
                                                        }?.apply {
                                                            addSubItem(CommentData(1, 2).apply {
                                                                    comment = s
                                                                    parentId = mParentId
                                                                })
                                                        })
                                                        commentAdapter.collapse(mPosition)
                                                        commentAdapter.expand(mPosition)
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }).show()
                        }
                        else -> {

                        }
                    }
                }


            }
        }
        recyclerView.adapter = commentAdapter
    }
}