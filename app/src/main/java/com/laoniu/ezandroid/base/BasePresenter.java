package com.laoniu.ezandroid.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;


public abstract class BasePresenter<M extends BaseModel,V extends IBaseView>{

    //--解决内存泄漏
    //View接口类型弱引用
    protected Reference<V> mViewRef;
    public M mBaseModel;

    //建立关联
    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
        this.mBaseModel= getBaseModel();
    }

    protected abstract M getBaseModel();


    //解除关联
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    protected V getView() {
        return mViewRef.get();
    }
    public boolean isViewAttached() {//判断是否与View建立了关联
        return mViewRef != null && mViewRef.get() != null;
    }


    public void showLoading(){
        if(isViewAttached()){
            getView().showLoading();
        }
    }
    public void dismissLoading(){
        if(isViewAttached()){
            getView().dismissLoading();
        }
    }

}
