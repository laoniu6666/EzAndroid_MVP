package com.laoniu.ezandroid.mvp.news;

import com.laoniu.ezandroid.BaseView;
import com.laoniu.ezandroid.model.NewsListBean;

import java.util.List;

public interface NewsView extends BaseView {
    void refreshData(List<NewsListBean.NewsList> data);
}
