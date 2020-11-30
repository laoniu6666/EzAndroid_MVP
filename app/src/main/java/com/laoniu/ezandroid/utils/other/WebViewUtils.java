package com.laoniu.ezandroid.utils.other;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.laoniu.ezandroid.utils.view.dialog.WKDialog;

public class WebViewUtils {

    public WebView webView;

    public WebViewUtils(Context context,ViewGroup vp){
        webView = new WebView(context.getApplicationContext());
        vp.addView(webView);
    }

    public void setParam(){
        webView.clearCache(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
//        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebViewClient(getWebViewClient());
        webView.setWebChromeClient(getWebChromeClient());
        webView.loadUrl("");
    }



    private WebViewClient getWebViewClient(){
        return new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url,
                                      Bitmap favicon) {
                WKDialog.showProgressDialog();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                WKDialog.dissmissProgressDialog();
                super.onPageFinished(view, url);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    ActivityUtils.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        };
    }
    private WebChromeClient getWebChromeClient(){
        return new WebChromeClient(){

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                ToastUtils.showShort(message);
                return true;
            }

        };
    }


    public void onDestroy(){
        if (null!= webView) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
        }
    }

}
