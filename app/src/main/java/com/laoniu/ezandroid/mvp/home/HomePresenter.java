package com.laoniu.ezandroid.mvp.home;

import com.blankj.utilcode.util.LogUtils;
import com.laoniu.ezandroid.BasePresenter;
import com.laoniu.ezandroid.model.CommonData;
import com.laoniu.ezandroid.model.IPAddress;
import com.laoniu.ezandroid.utils.http.BaseResponse;
import com.laoniu.ezandroid.utils.http.WKHttp;
import com.laoniu.ezandroid.utils.other.WKCallback;
import com.laoniu.ezandroid.utils.other.WKJsonUtil;
import com.laoniu.ezandroid.utils.view.dialog.WKDialog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class HomePresenter extends BasePresenter {

    public void getIp(){
        WKDialog.showInputDialog(new WKCallback<String>() {
            @Override
            public void onCall(String str) {
                getIpAddress(str);
            }
        });
    }

    private void getIpAddress(String str){
        str="60.31.89.9";
        mBaseView.showLoading();
        add(WKHttp.getInstance().getService().getIpAddress(str, CommonData.getKey_juhe_IP())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<IPAddress>>() {
                    @Override
                    public void accept(BaseResponse<IPAddress> response) {
                        mBaseView.dismissLoading();
                        String text= WKJsonUtil.formatJson(response);
                        LogUtils.e(text);
                        if(response.error_code==0){
                            WKDialog.showSureDialog(text);
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
