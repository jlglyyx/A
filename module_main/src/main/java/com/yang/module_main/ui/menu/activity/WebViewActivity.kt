package com.yang.module_main.ui.menu.activity

import android.graphics.Bitmap
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.*
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.module_main.R
import kotlinx.android.synthetic.main.act_web_view.*


/**
 * @Author Administrator
 * @ClassName WebViewActivity
 * @Description
 * @Date 2021/11/16 14:19
 */
@Route(path = AppConstant.RoutePath.WEB_VIEW_ACTIVITY)
class WebViewActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.act_web_view
    }

    override fun initData() {

    }

    override fun initView() {
        val map = mutableMapOf<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)

        initWebView()
    }


    private fun initWebView() {
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(p0: WebView?, p1: String?): Boolean {
                webView.loadUrl(p1)
                return true
            }
            override fun onPageStarted(p0: WebView?, p1: String?, p2: Bitmap?) {
                super.onPageStarted(p0, p1, p2)
                Log.i(TAG, "onPageStarted: 加载开始 $p1  $p2")
            }

            override fun onPageFinished(p0: WebView?, p1: String?) {
                super.onPageFinished(p0, p1)
                Log.i(TAG, "onPageFinished: 加载完成 $p1")
            }

            override fun onReceivedError(p0: WebView?, p1: Int, p2: String?, p3: String?) {
                super.onReceivedError(p0, p1, p2, p3)
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(p0: WebView?, p1: Int) {
                super.onProgressChanged(p0, p1)
                Log.i(TAG, "onProgressChanged: $p1")
            }
        }



        webView.loadUrl("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%E8%85%BE%E8%AE%AFX5%E5%86%85%E6%A0%B8WebView&fenlei=256&oq=Android%2520%25E8%2585%25BE%25E8%25AE%25AFwebview%25E6%25A1%2586%25E6%259E%25B6&rsv_pq=da5d2c480000361b&rsv_t=adaa0CfLjh812ANiWVD6IrUmzwKPhGB7x9gg4%2BSASs2JrBN3BywArgC8C0g&rqlang=cn&rsv_enter=1&rsv_dl=tb&rsv_btype=t&inputT=258&rsv_n=2&rsv_sug3=33&rsv_sug2=0&rsv_sug4=258")
//        webView.settings.apply {
//            javaScriptEnabled = true
//            useWideViewPort = true
//            loadWithOverviewMode = true
//            setSupportZoom(true)
//            builtInZoomControls = true
//            displayZoomControls = false
//            pluginsEnabled = true
//            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK//关闭webview中缓存
//            allowFileAccess = true //设置可以访问文件
//            javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
//            loadsImagesAutomatically = true //支持自动加载图片
//            defaultTextEncodingName = "utf-8"//设置编码格式
//        }


        Log.i(TAG, "initWebView: ${webView.settingsExtension == null}   ${webView.x5WebViewExtension == null}")
        QbSdk.clearAllWebViewCache(this@WebViewActivity, true)

    }


    override fun initViewModel() {

    }


    override fun onResume() {
        super.onResume()
        webView.onResume()
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }
}