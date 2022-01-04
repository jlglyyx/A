package com.yang.module_mine.ui.obtain.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.util.clicks
import com.yang.lib_common.util.formatDate_YYYYMMMDDHHMMSS
import com.yang.lib_common.util.simpleDateFormat
import com.yang.module_mine.R
import com.yang.module_mine.data.MineShippingAddressData
import kotlinx.android.synthetic.main.act_mine_create_order_detail.*
import kotlinx.coroutines.cancel
import java.util.*

/**
 * @Author Administrator
 * @ClassName MineOrderDetailActivity
 * @Description
 * @Date 2021/10/11 9:38
 */
@Route(path = AppConstant.RoutePath.MINE_CREATE_ORDER_ACTIVITY)
class MineCreateOrderActivity : BaseActivity() {

    private lateinit var registerForActivityResult: ActivityResultLauncher<Intent>

    override fun getLayout(): Int {
        return R.layout.act_mine_create_order_detail
    }

    override fun initData() {
        createOrder()
        registerForActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK && it.data != null){
                    val mineShippingAddressData = it.data?.getParcelableExtra<MineShippingAddressData>(AppConstant.Constant.DATA)
                    mineShippingAddressData?.let { bean ->
                        val spannableString = SpannableString(bean.name + "-" + bean.phone)
                        spannableString.setSpan(ForegroundColorSpan(ActivityCompat.getColor(this,R.color.black)),0,bean.name?.length!!, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                        spannableString.setSpan(AbsoluteSizeSpan(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,13f,resources.displayMetrics).toInt()),bean.name?.length!!,spannableString.length,Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                        tv_phone.text = spannableString
                        tv_address.text = bean.address
                        tv_phone.visibility = View.VISIBLE
                        tv_address.visibility = View.VISIBLE
                        tv_choose_address.visibility = View.GONE
                    }
                }
            }
    }

    override fun initView() {
        Glide.with(this)
            .load("https://img.alicdn.com/bao/uploaded/i2/2209667639897/O1CN015Oh5X32MysY6ea4fc_!!0-item_pic.jpg_200x200q90.jpg_.webp")
            .into(iv_image)
        cv_address.clicks().subscribe {
            registerForActivityResult.launch(Intent(this@MineCreateOrderActivity,MineAddressActivity::class.java))
        }
    }

    override fun initViewModel() {

    }

    @SuppressLint("SetTextI18n")
    private fun createOrder(){
        val date = Date()
        var hashCode =  UUID.randomUUID().toString().hashCode()
        if (hashCode < 0){
            hashCode = -hashCode
        }
        val format = String.format("%015d", hashCode)
        tv_good_code.text = "订单编号：${formatDate_YYYYMMMDDHHMMSS.format(date)}$format"
        tv_create_time.text = "创建时间：${simpleDateFormat.format(date)}"
    }



    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }
}