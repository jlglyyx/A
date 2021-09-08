package com.yang.module_picture.ui.activity

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.room.BaseAppDatabase
import com.yang.lib_common.room.entity.SearchData
import com.yang.lib_common.util.clicks
import com.yang.lib_common.util.showShort
import com.yang.lib_common.util.simpleDateFormat
import com.yang.module_picture.R
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.act_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.*

/**
 * @Author Administrator
 * @ClassName SearchActivity
 * @Description
 * @Date 2021/9/8 11:37
 */
@Route(path = AppConstant.RoutePath.SEARCH_ACTIVITY)
class SearchActivity : BaseActivity() {

    private var list = mutableListOf<SearchData>()
    private lateinit var flowLayoutAdapter: FlowLayoutAdapter

    override fun getLayout(): Int {
        return R.layout.act_search
    }

    override fun initData() {
        queryData()
    }

    override fun initView() {
        iv_back.clicks().subscribe {
            finish()
        }
        iv_delete.clicks().subscribe {
            deleteAllData()
        }
        tv_search.clicks().subscribe {
            if (TextUtils.isEmpty(et_search.text.toString().trim()) || list.findLast {
                    TextUtils.equals(it.content, et_search.text.toString())
                } != null) {
                return@subscribe
            }
            insertData()

        }
    }

    override fun initViewModel() {

    }

    private fun initFlowLayout() {
        flowLayoutAdapter = FlowLayoutAdapter(list)
        flowLayout.adapter = flowLayoutAdapter
        if (list.size == 0){
            iv_delete.visibility = View.GONE
        }else{
            iv_delete.visibility = View.VISIBLE
        }

        flowLayout.setOnTagClickListener { view, position, parent ->

            showShort(list[position])
            return@setOnTagClickListener true
        }
    }

    private fun insertData() {
        val searchData = SearchData(
            UUID.randomUUID().toString().replace("-", ""),
            0,
            et_search.text.toString(),
            simpleDateFormat.format(System.currentTimeMillis()),
            simpleDateFormat.format(System.currentTimeMillis())
        )
        lifecycleScope.launch(Dispatchers.Main) {
            val async = async(Dispatchers.IO) {
                BaseAppDatabase.instance.searchHistoryDao().insertData(searchData)
            }
            val await = async.await()
            if (await != 0L) {
                list.add(searchData)
                if (iv_delete.visibility == View.GONE){
                    iv_delete.visibility = View.VISIBLE
                }
                flowLayoutAdapter.notifyDataChanged()
            }
        }
    }

    private fun queryData() {
        lifecycleScope.launch(Dispatchers.Main) {
            val async = async(Dispatchers.IO) {
                BaseAppDatabase.instance.searchHistoryDao().queryAll()
            }
            list.addAll(async.await())
            initFlowLayout()
        }
    }

    private fun deleteData(position: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            val async = async(Dispatchers.IO) {
                BaseAppDatabase.instance.searchHistoryDao().deleteData(list[position])
            }
            val await = async.await()
            if (await != 0) {
                list.remove(list[position])
                if (list.size == 0){
                    iv_delete.visibility = View.GONE
                }
                flowLayoutAdapter.notifyDataChanged()
            }
        }
    }
    private fun deleteAllData() {
        lifecycleScope.launch(Dispatchers.Main) {
            val async = async(Dispatchers.IO) {
                BaseAppDatabase.instance.searchHistoryDao().deleteAllData(list)
            }
            val await = async.await()
            if (await != 0) {
                list.clear()
                iv_delete.visibility = View.GONE
                flowLayoutAdapter.notifyDataChanged()
            }
        }
    }

    inner class FlowLayoutAdapter(mData: MutableList<SearchData>) : TagAdapter<SearchData>(mData) {
        override fun getView(parent: FlowLayout, position: Int, t: SearchData): View {
            val view = LayoutInflater.from(this@SearchActivity)
                .inflate(R.layout.item_flow_layout, null, false)
            val tvContent = view.findViewById<TextView>(R.id.tv_content)
            val tvDelete = view.findViewById<TextView>(R.id.tv_delete)
            tvContent.text = t.content
            tvDelete.clicks().subscribe {
                deleteData(position)
            }
            return view
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }
}