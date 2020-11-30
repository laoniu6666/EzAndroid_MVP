package com.laoniu.ezandroid.mvp.webview;

import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.laoniu.ezandroid.base.BaseActivity;
import com.laoniu.ezandroid.base.BasePresenter;
import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.base.IBaseView;
import com.laoniu.ezandroid.databinding.ActWebviewBinding;
import com.laoniu.ezandroid.utils.other.WebViewUtils;
import com.laoniu.ezandroid.utils.view.ActionBarHelper;

public class WebviewActivity extends BaseActivity<BasePresenter, ActWebviewBinding> {

    WebViewUtils webViewUtils;
    @Override
    protected int getLayoutId() {
        return R.layout.act_webview;
    }

    @Override
    protected Class<BasePresenter> getPresenter() {
        return null;
    }

    @Override
    protected IBaseView getBaseView() {
        return null;
    }
    String url;
    @Override
    protected void initData() {
        url = getIntent().getStringExtra("data");
        String title = getIntent().getStringExtra("title");

        title=!TextUtils.isEmpty(title) ? title : "";
        ActionBarHelper.setTitle(WebviewActivity.this,title);
        if(TextUtils.isEmpty(url)){
//            ToastUtils.showShort("url不能为空");
//            finish();
//            return;
            url= "https://m.baidu.com";
        }
        webViewUtils = new WebViewUtils(WebviewActivity.this,binding.flContent);
        webViewUtils.setParam();
        webViewUtils.webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if(webViewUtils.webView.canGoBack()&& !url.equals(webViewUtils.webView.getOriginalUrl())){
            webViewUtils.webView.goBack();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        android.os.Process.killProcess(android.os.Process.myPid());
        Log.e("zwk","杀死这个webview");
        super.onDestroy();
    }
}
