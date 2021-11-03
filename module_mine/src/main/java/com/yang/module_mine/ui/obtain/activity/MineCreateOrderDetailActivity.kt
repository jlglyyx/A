package com.yang.module_mine.ui.obtain.activity

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
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.util.clicks
import com.yang.module_mine.R
import com.yang.module_mine.data.MineShippingAddressData
import kotlinx.android.synthetic.main.act_mine_create_order_detail.*
import kotlinx.coroutines.cancel
import java.text.DecimalFormat

/**
 * @Author Administrator
 * @ClassName MineOrderDetailActivity
 * @Description
 * @Date 2021/10/11 9:38
 */
@Route(path = AppConstant.RoutePath.MINE_CREATE_ORDER_DETAIL_ACTIVITY)
class MineCreateOrderDetailActivity : BaseActivity() {

    private lateinit var registerForActivityResult: ActivityResultLauncher<Intent>

    override fun getLayout(): Int {
        return R.layout.act_mine_create_order_detail
    }

    override fun initData() {
        //initTimer()

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
        cv_address.clicks().subscribe {
            registerForActivityResult.launch(Intent(this@MineCreateOrderDetailActivity,MineShippingAddressActivity::class.java))
        }
    }

    override fun initViewModel() {

    }

    private fun initTimer() {
//        lifecycleScope.launch(Dispatchers.Main) {
//            for (i in 60 * 60 downTo 1) {
//                tv_good_status.text = "${formatNumber(i / 60 / 60 % 60)}:${formatNumber(i / 60 % 60)}:${formatNumber(i % 60)}"
//                delay(1000)
//            }
//        }
    }

    private fun formatNumber(i:Int):String{
        return DecimalFormat("00").format(i)
    }



    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }
}