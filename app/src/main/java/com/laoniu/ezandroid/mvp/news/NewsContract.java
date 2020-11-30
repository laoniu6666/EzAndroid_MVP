package com.laoniu.ezandroid.mvp.news;

import com.laoniu.ezandroid.base.IBaseHttpCallback;
import com.laoniu.ezandroid.base.IBaseView;
import com.laoniu.ezandroid.bean.NewsListBean;

import java.util.List;

/**
 * 登录的关联类
 * Created by HDL on 2016/7/22.
 */
public class NewsContract {
    /**
     * V视图层
     */
    public interface INewsView extends IBaseView {
        void refreshData(List<NewsListBean.NewsList> data);
    }

    /**
     * P视图与逻辑处理的连接层
     */
    interface INewsPresenter{
        void getData(String newsType);
    }

    /**
     * 逻辑处理层
     */
    interface INewsModel {
        void getData(String newsType, IBaseHttpCallback callback);
    }
}