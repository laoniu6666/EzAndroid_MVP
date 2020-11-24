package com.laoniu.ezandroid.mvp.home;

import android.content.Intent;
import android.view.View;

import com.laoniu.ezandroid.BaseActivity;
import com.laoniu.ezandroid.BaseView;
import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.databinding.ActivityHomeBinding;
import com.laoniu.ezandroid.mvp.news.NewsActivity;
import com.laoniu.ezandroid.utils.other.OnFastClickListener;

public class HomeActivity extends BaseActivity<BaseView, HomePresenter, ActivityHomeBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected Class<HomePresenter> getPresenter() {
        return HomePresenter.class;
    }

    @Override
    protected BaseView getBaseView() {
        return this;
    }

    @Override
    protected void initData() {
        binding.tvName.setText("首页");
        binding.btnGetIp.setOnClickListener(new OnFastClickListener() {
            @Override
            public void onFastClick(View v) {
                presenter.getIp();
            }
        });
        binding.btnNews.setOnClickListener(new OnFastClickListener() {
            @Override
            public void onFastClick(View v) {
                startActivity(new Intent(HomeActivity.this, NewsActivity.class));
            }
        });
    }
}
