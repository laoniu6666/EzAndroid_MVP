package com.laoniu.ezandroid.mvp.im;

import com.blankj.utilcode.util.LogUtils;
import com.laoniu.ezandroid.base.BaseModel;
import com.laoniu.ezandroid.base.BasePresenter;
import com.laoniu.ezandroid.base.IBaseView;
import com.laoniu.ezandroid.bean.CommonData;
import com.laoniu.ezandroid.bean.IPAddress;
import com.laoniu.ezandroid.utils.http.BaseResponse;
import com.laoniu.ezandroid.utils.http.RetrofitHelper;
import com.laoniu.ezandroid.utils.other.WKCallback;
import com.laoniu.ezandroid.utils.other.WKJsonUtil;
import com.laoniu.ezandroid.utils.view.dialog.WKDialog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class IMPresenter extends BasePresenter<BaseModel, IBaseView> {

    @Override
    protected BaseModel getBaseModel() {
        return new BaseModel();
    }

    public void getIp(){
        WKDialog.showInputDialog(getView().getContext(),new WKCallback<String>() {
            @Override
            public void onCall(String str) {
                getIpAddress(str);
            }
        });
    }

    private void getIpAddress(String str){
        str="60.31.89.9";
        showLoading();
        RetrofitHelper.getInstance().getService().getIpAddress(str, CommonData.getKey_juhe_IP())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<IPAddress>>() {
                    @Override
                    public void accept(BaseResponse<IPAddress> response) {
                        dismissLoading();
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
                        dismissLoading();
                    }
                });
    }

}
