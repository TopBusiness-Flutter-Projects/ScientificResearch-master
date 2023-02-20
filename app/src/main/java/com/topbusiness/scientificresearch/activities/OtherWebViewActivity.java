package com.topbusiness.scientificresearch.activities;

import android.content.Intent;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.topbusiness.scientificresearch.R;
import com.topbusiness.scientificresearch.databinding.ActivityOtherWebViewBinding;

public class OtherWebViewActivity extends AppCompatActivity {

    private ActivityOtherWebViewBinding webViewBinding;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webViewBinding = DataBindingUtil.setContentView(this,R.layout.activity_other_web_view);
        getDataFromIntent();
        webViewBinding.webView.getSettings().setJavaScriptEnabled(true);
        webViewBinding.webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webViewBinding.webView.getSettings().setLoadWithOverviewMode(true);
        webViewBinding.webView.getSettings().setUseWideViewPort(true);
        webViewBinding.webView.getSettings().setBuiltInZoomControls(true);

        webViewBinding.webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webViewBinding.webView.loadUrl(url);
                return false;
            }
        });
        webViewBinding.webView.loadUrl(url);


    }
// اخري https://www.surveysystem.com/sscalc.htm
    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            url = intent.getStringExtra("url");
        }
    }


}
