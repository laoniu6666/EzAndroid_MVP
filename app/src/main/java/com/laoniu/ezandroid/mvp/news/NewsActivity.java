package com.laoniu.ezandroid.mvp.news;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.laoniu.ezandroid.base.BaseActivity;
import com.laoniu.ezandroid.base.BasePresenter;
import com.laoniu.ezandroid.base.IBaseView;
import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.databinding.ActivityNewsBinding;
import com.laoniu.ezandroid.bean.CommonData;
import com.laoniu.ezandroid.mvp.news.fmt.NewsFragment;

public class NewsActivity extends BaseActivity<BasePresenter, ActivityNewsBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    protected Class<BasePresenter> getPresenter() {
        return null;
    }

    @Override
    protected IBaseView getBaseView() {
        return null;
    }

    @Override
    protected void initData() {
        NewsViewPagerAdapter pagerAdapter = new NewsViewPagerAdapter(this);
        binding.vp.setAdapter(pagerAdapter);
        binding.tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        // 添加页签选中监听
        binding.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                binding.vp.setCurrentItem(tab.getPosition());
            }
        });

        // 注册页面变化的回调接口
        binding.vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.tab.setScrollPosition(position, 0, true);
            }
        });

        for (int i = 0; i < CommonData.news_title.length; i++) {
            TabLayout.Tab tab = binding.tab.newTab();
            tab.setTag(i);
            tab.setText(CommonData.news_title[i]);
            binding.tab.addTab(tab);
        }
    }


    public class NewsViewPagerAdapter extends FragmentStateAdapter {

        public NewsViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return NewsFragment.newInstance(position);
        }

        @Override
        public int getItemCount() {
            return CommonData.news_title.length;
        }
    }








}
