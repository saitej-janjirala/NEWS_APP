package com.saitejajanjirala.news_app.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.saitejajanjirala.news_app.R
import com.saitejajanjirala.news_app.databinding.ActivityDetailBinding
import com.saitejajanjirala.news_app.utils.Keys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.bind(View.inflate(this,R.layout.activity_detail,null))
        setContentView(detailBinding.root)
        fetchIntentData()
    }

    private fun fetchIntentData() {
        val url = intent.getStringExtra(Keys.URL_INTENT_KEY)
         if(url != null){
             detailBinding.webView.settings.javaScriptEnabled = true
             detailBinding.webView.webViewClient = WebViewClient()

//                 object :WebViewClient(){
//                     override fun shouldOverrideUrlLoading(
//                         view: WebView?,
//                         request: WebResourceRequest?
//                     ): Boolean {
//                         return true;
//                     }
//
//                     override fun onPageCommitVisible(view: WebView?, url: String?) {
//                         super.onPageCommitVisible(view, url)
//                     }
//                 }
            detailBinding.webView
                .loadUrl(url)


        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK && detailBinding.webView.canGoBack()){
            detailBinding.webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}