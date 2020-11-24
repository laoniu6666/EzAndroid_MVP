package com.laoniu.ezandroid.mvp.webview;

import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.laoniu.ezandroid.BaseActivity;
import com.laoniu.ezandroid.BasePresenter;
import com.laoniu.ezandroid.BaseView;
import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.databinding.ActWebviewBinding;
import com.laoniu.ezandroid.utils.other.WebViewUtils;

public class WebviewActivity extends BaseActivity<BaseView,BasePresenter, ActWebviewBinding> {

    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.act_webview;
    }

    @Override
    protected Class<BasePresenter> getPresenter() {
        return null;
    }

    @Override
    protected BaseView getBaseView() {
        return this;
    }

    @Override
    protected void initData() {
        String url = getIntent().getStringExtra("data");
        String title = getIntent().getStringExtra("title");

        setTitle(!TextUtils.isEmpty(title) ? title : "");
        if(TextUtils.isEmpty(url)){
//            ToastUtils.showShort("url不能为空");
//            finish();
//            return;
            url= "https://m.baidu.com";
        }
        webView = binding.webview;
        WebViewUtils.set(webView);
        webView.loadUrl(url);
    }


    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
