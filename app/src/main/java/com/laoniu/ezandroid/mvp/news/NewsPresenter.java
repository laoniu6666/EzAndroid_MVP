package com.laoniu.ezandroid.mvp.news;

import com.blankj.utilcode.util.ToastUtils;
import com.laoniu.ezandroid.base.IBaseHttpCallback;
import com.laoniu.ezandroid.base.BasePresenter;
import com.laoniu.ezandroid.bean.NewsListBean;
import com.laoniu.ezandroid.utils.http.BaseResponse;


public class NewsPresenter extends BasePresenter<NewsModel,NewsContract.INewsView> implements NewsContract.INewsPresenter {

    @Override
    protected NewsModel getBaseModel() {
        return new NewsModel();
    }

    @Override
    public void getData(String newsType){
        showLoading();
        mBaseModel.getData(newsType, new IBaseHttpCallback<NewsListBean>() {
            @Override
            public void onSuccess(BaseResponse<NewsListBean> response) {
                dismissLoading();
                getView().refreshData(response.result.getData());
            }

            @Override
            public void onFail(String msg) {
                dismissLoading();
                ToastUtils.showShort(msg);
            }
        });
    }


}
