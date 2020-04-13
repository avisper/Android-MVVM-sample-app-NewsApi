package com.avisper.mvvm.sampleapp.newsapi.ui.web

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.avisper.mvvm.sampleapp.newsapi.R


class WebActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        initView()
        if (savedInstanceState == null) {
            val url = intent.getStringExtra(KEY_URL)
            if (!url.isNullOrBlank())
                webView.loadUrl(url)
            else {
                //TODO
            }
        }
    }

    private fun initView() {
        webView = findViewById(R.id.webview)
        webView.webViewClient = WebViewClient()
    }

    companion object {
        const val KEY_URL = "url"
    }
}
