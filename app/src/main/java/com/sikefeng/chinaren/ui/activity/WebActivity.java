/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sikefeng.chinaren.BuildConfig;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseActivity;
import com.sikefeng.chinaren.databinding.ActivityWebBinding;
import com.sikefeng.mvpvmlib.base.RBasePresenter;


public class WebActivity extends BaseActivity<ActivityWebBinding> {

    /**
     * 进度值
     */
    private final int progress = 100;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initWebViewSettings();

//        if (null != getIntent()) {
//            String url = getIntent().getStringExtra("url");
//            if (TextUtils.isEmpty(url)) {
//                url = "about:blank";
//            }
//            getBinding().webView.loadUrl(url);
//        }
        getBinding().webView.loadUrl("http://www.baidu.com");
        getBinding().webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        getBinding().webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                getBinding().toolbar.setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                getBinding().pb.setProgress(newProgress);
                if (newProgress != progress) {
                    getBinding().pb.setVisibility(View.VISIBLE);
                } else {
                    getBinding().pb.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected Toolbar getToolbar() {
        return getBinding().toolbar;
    }

    @Override
    public void onBackPressed() {
        if (getBinding().webView.canGoBack()) {
            getBinding().webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 初始化浏览器设置
     */
    private void initWebViewSettings() {
        getBinding().webView.setVerticalScrollBarEnabled(false);
        WebSettings webSetting = getBinding().webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //webSetting.setSupportZoom(true);
        // webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        //Android 5.0中使用webView 加载网页不显示图片问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

}
