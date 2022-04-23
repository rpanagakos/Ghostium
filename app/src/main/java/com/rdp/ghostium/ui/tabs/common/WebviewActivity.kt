package com.rdp.ghostium.ui.tabs.common

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractActivity
import com.rdp.ghostium.databinding.ActivityWebviewBinding
import com.rdp.ghostium.utils.setSafeOnClickListener

class WebviewActivity : AbstractActivity<ActivityWebviewBinding>(R.layout.activity_webview) {

    @SuppressLint("SetJavaScriptEnabled")
    override fun initLayout() {
        binding.backButton.setSafeOnClickListener { onBackPressed() }
        intent.extras?.let {
            binding.webview.apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.databaseEnabled = true
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        if (url != null) {
                            view?.loadUrl(url)
                        }
                        return true
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                    }
                }
                loadUrl(it.getString("url", "www.google.com"))
            }
        }
    }

    override fun runOperation() {}

    override fun stopOperation() {
        binding.webview.destroy()
    }

}