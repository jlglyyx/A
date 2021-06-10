package com.example.lib_common.dialog

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.lib_common.R
import com.lxj.xpopup.impl.FullScreenPopupView

class ImageViewPagerDialog : FullScreenPopupView {

    private lateinit var viewPager: ViewPager2

    constructor(context: Context) : super(context)

    override fun getImplLayoutId(): Int {
        return super.getImplLayoutId()
    }

    override fun onCreate() {
        super.onCreate()
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {

            }

            override fun getItemCount(): Int {

            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            }

        }



    }



}