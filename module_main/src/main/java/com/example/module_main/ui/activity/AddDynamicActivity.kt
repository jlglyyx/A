package com.example.module_main.ui.activity

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.adapter.NormalImageAdapter
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.widget.CommonToolBar
import com.example.module_main.R
import kotlinx.android.synthetic.main.act_add_dynamic.*
import java.util.*

@Route(path = AppConstant.RoutePath.ADD_DYNAMIC_ACTIVITY)
class AddDynamicActivity : BaseActivity() {


    private lateinit var recyclerView: RecyclerView

    private lateinit var normalImageAdapter:NormalImageAdapter<String>

    private var data: MutableList<String> = mutableListOf()


    override fun getLayout(): Int {
        return R.layout.act_add_dynamic
    }

    override fun initData() {
    }

    override fun initView() {
        commonToolBar.tVRightCallBack = object : CommonToolBar.TVRightCallBack{
            override fun tvRightClickListener(view: View) {
                val intent = Intent()
                intent.putStringArrayListExtra("Data",
                    normalImageAdapter.data as ArrayList<String>?
                )
                intent.putExtra("content",et_dynamic.text.toString())
                setResult(RESULT_OK, intent)
                finish()
            }

        }
        initRecyclerView()

    }

    override fun initViewModel() {

    }


    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        normalImageAdapter = NormalImageAdapter(data)
        recyclerView.adapter = normalImageAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        val registerForActivityResult =
            registerForActivityResult(ActivityResultContracts.OpenMultipleDocuments()) { it ->
                val map = it.map { item ->
                    item.toString()
                }
                normalImageAdapter.addData(map)
            }

        val imageView = ImageView(this).apply {
            setImageResource(R.drawable.iv_add)
            scaleType = ImageView.ScaleType.CENTER_CROP
            setOnClickListener {
                if (normalImageAdapter.data.size == 8) {
                    this.visibility = View.GONE
                }
                registerForActivityResult.launch(arrayOf("image/*"))

            }
        }

        normalImageAdapter.addFooterView(imageView)
        normalImageAdapter.setOnItemLongClickListener { adapter, view, position ->
            normalImageAdapter.remove(position)
            if (!imageView.isVisible) {
                imageView.visibility = View.VISIBLE
            }
            return@setOnItemLongClickListener false
        }
    }


}