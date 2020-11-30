package com.laoniu.ezandroid.mvp.news;

import com.laoniu.ezandroid.base.IBaseHttpCallback;
import com.laoniu.ezandroid.base.BaseModel;
import com.laoniu.ezandroid.bean.CommonData;
import com.laoniu.ezandroid.bean.NewsListBean;
import com.laoniu.ezandroid.utils.http.BaseResponse;
import com.laoniu.ezandroid.utils.http.RetrofitHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class NewsModel extends BaseModel implements NewsContract.INewsModel {

    @Override
    public void getData(String newsType, IBaseHttpCallback callback){
        RetrofitHelper.getInstance().getService().getNewsList(newsType, CommonData.getKey_juhe_News())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<NewsListBean>>() {
                    @Override
                    public void accept(BaseResponse<NewsListBean> response) {
                        if(response.error_code==0){
                            callback.onSuccess(response);
                        }else{
                            callback.onFail(response.reason);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                        callback.onFail(throwable.toString());
                    }
                });
    }
//    @Override
//    public void getData(String newsType){
//        RetrofitHelper.getInstance().getService().getNewsList(newsType, CommonData.getKey_juhe_News())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<BaseResponse<NewsListBean>>() {
//                    @Override
//                    public void accept(BaseResponse<NewsListBean> response) {
//                        mBaseView.dismissLoading();
//                        if(response.error_code==0){
//                            mBaseView.refreshData(response.result.getData());
//                        }else{
//                            LogUtils.e(""+response.result);
//                        }
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) {
//                        throwable.printStackTrace();
//                        mBaseView.dismissLoading();
//                    }
//                });
//    }




}
