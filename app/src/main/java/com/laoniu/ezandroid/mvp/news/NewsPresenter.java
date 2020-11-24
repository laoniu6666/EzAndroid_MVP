package com.laoniu.ezandroid.mvp.news;

import com.blankj.utilcode.util.LogUtils;
import com.laoniu.ezandroid.BasePresenter;
import com.laoniu.ezandroid.model.CommonData;
import com.laoniu.ezandroid.model.NewsListBean;
import com.laoniu.ezandroid.utils.http.BaseResponse;
import com.laoniu.ezandroid.utils.http.WKHttp;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class NewsPresenter extends BasePresenter<NewsView> {

    public void getData(String newsType){
        mBaseView.showLoading();
        add(WKHttp.getInstance().getService().getNewsList(newsType, CommonData.getKey_juhe_News())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<NewsListBean>>() {
                    @Override
                    public void accept(BaseResponse<NewsListBean> response) {
                        mBaseView.dismissLoading();
                        if(response.error_code==0){
                            mBaseView.refreshData(response.result.getData());
                        }else{
                            LogUtils.e(""+response.result);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                        mBaseView.dismissLoading();
                    }
                }));
    }




}
