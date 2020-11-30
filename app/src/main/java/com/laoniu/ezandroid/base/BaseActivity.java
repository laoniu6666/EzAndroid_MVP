 package com.laoniu.ezandroid.base;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.Context;
import android.os.Bundle;

import com.laoniu.ezandroid.utils.view.dialog.WKDialog;


 public abstract class BaseActivity<P extends BasePresenter, B extends ViewDataBinding>
         extends AppCompatActivity{

     protected B binding;
     protected P mPresenter;

     @Override
     protected void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this, getLayoutId());
         initPresenter();
         initData();
     }

     protected void initPresenter(){
         try {
             mPresenter = this.getPresenter().newInstance();
             if (null!=mPresenter && null!=getBaseView()) {
                 this.mPresenter.attachView(getBaseView());
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
     };


     @MainThread
     protected abstract int getLayoutId();

     @MainThread
     protected abstract Class<P> getPresenter();
     @MainThread
     protected abstract IBaseView getBaseView();

     @MainThread
     protected abstract void initData();

     @MainThread
     public void showLoading() {
         WKDialog.showProgressDialog(this);
     }

     @MainThread
     public void dismissLoading() {
         WKDialog.dissmissProgressDialog();
     }

     @Override
     public void onDestroy() {
         super.onDestroy();
         WKDialog.dissmissProgressDialog();
         if (null!= mPresenter) {
             mPresenter.detachView();
         }
     }


 }