package com.rdp.ghostium.ui.tabs.common

import android.annotation.SuppressLint
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractActivity
import com.rdp.ghostium.databinding.ActivityWebviewBinding
import com.rdp.ghostium.utils.setSafeOnClickListener

class WebviewActivity : AbstractActivity<ActivityWebviewBinding>(R.layout.activity_webview) {

    var url: String? = "www.google.com"

    @SuppressLint("SetJavaScriptEnabled")
    override fun initLayout() {
        binding.isLoading = true
        binding.backButton.setSafeOnClickListener { onBackPressed() }
        intent.extras?.let {
            binding.webview.apply {
                webChromeClient = WebChromeClient()
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        if (url != null) {
                            view?.loadUrl(url)
                        }
                        return true
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        binding.isLoading = false
                        super.onPageFinished(view, url)
                    }
                }
            }
            url = it.getString("url")
        }
        binding.webview.loadUrl(url.toString())
    }

    override fun runOperation() {}

    override fun stopOperation() {
        binding.webview.clearCache(true)
        binding.webview.destroy()
    }

}