package com.example.module_main.ui.main.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.dialog.EditBottomDialog
import com.example.lib_common.help.buildARouter
import com.example.lib_common.util.clicks
import com.example.module_main.R
import com.google.android.material.imageview.ShapeableImageView
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.act_dynamic_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @Author Administrator
 * @ClassName DynamicDetailActivity
 * @Description
 * @Date 2021/8/2 17:19
 */
@Route(path = AppConstant.RoutePath.DYNAMIC_DETAIL_ACTIVITY)
class DynamicDetailActivity:BaseActivity() {

    private lateinit var commentAdapter: CommentAdapter

    override fun getLayout(): Int {

        return R.layout.act_dynamic_detail
    }

    override fun initData() {

    }

    override fun initView() {
        initRecyclerView()
        tv_send_comment.clicks().subscribe {
            XPopup.Builder(this).autoOpenSoftInput(true).asCustom(EditBottomDialog(this).apply {
                dialogCallBack = object : EditBottomDialog.DialogCallBack {
                    override fun getComment(s: String) {
                        commentAdapter.addData(0, s)
                        //nestedScrollView.fullScroll(View.FOCUS_UP)
                    }

                }
            }).show()
        }
    }

    override fun initViewModel() {

    }

    private fun initRecyclerView() {

        recyclerView.layoutManager = LinearLayoutManager(this)
        commentAdapter = CommentAdapter(R.layout.item_dynamic_detail_comment, mutableListOf<String>().apply {
            GlobalScope.launch(Dispatchers.IO) {
                for (i in 1..3) {
                    add(
                        "这电影真好看这电影真好看这电影真好看这电影真好看这电影真好看这电影真好看" +
                                "这电影真好看这电影真好看这电影真好看这电影真好看这电影真好看这电影真好看这电影真好看这电影真好看"
                    )
                }
            }
        }).apply {
            setOnItemChildClickListener { adapter, view, position ->
                when(view.id){
                    R.id.siv_img -> {
                        buildARouter(AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY).navigation()
                    }
                }

            }
        }
        recyclerView.adapter = commentAdapter
    }


    inner class CommentAdapter(layoutResId: Int, data: MutableList<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {
        override fun convert(helper: BaseViewHolder, item: String) {

            helper.addOnClickListener(R.id.siv_img)
            helper.setText(R.id.tv_comment, item)
            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
            Glide.with(sivImg).load("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg").into(sivImg)
        }

    }
}