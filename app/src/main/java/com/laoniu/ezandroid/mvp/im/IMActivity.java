package com.laoniu.ezandroid.mvp.im;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.base.BaseActivity;
import com.laoniu.ezandroid.base.IBaseView;
import com.laoniu.ezandroid.databinding.ActivityHomeBinding;
import com.laoniu.ezandroid.mvp.home.HomePresenter;
import com.laoniu.ezandroid.mvp.news.NewsActivity;
import com.laoniu.ezandroid.utils.other.OnFastClickListener;

public class IMActivity extends BaseActivity<HomePresenter, ActivityHomeBinding> implements IBaseView{


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected Class<HomePresenter> getPresenter() {
        return HomePresenter.class;
    }

    @Override
    protected IBaseView getBaseView() {
        return this;
    }

    @Override
    protected void initData() {
        binding.tvName.setText("首页");
        binding.btnGetIp.setOnClickListener(new OnFastClickListener() {
            @Override
            public void onFastClick(View v) {
                mPresenter.getIp();
            }
        });
        binding.btnNews.setOnClickListener(new OnFastClickListener() {
            @Override
            public void onFastClick(View v) {
                startActivity(new Intent(IMActivity.this, NewsActivity.class));
            }
        });
    }

    @Override
    public Context getContext() {
        return this;
    }
}
