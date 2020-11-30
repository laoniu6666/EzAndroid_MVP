package com.laoniu.ezandroid.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.laoniu.ezandroid.utils.view.dialog.WKDialog;

public abstract class BaseFragment <P extends BasePresenter, B extends ViewDataBinding>
        extends Fragment{

    protected B binding;
    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initData();
    }

    protected void initPresenter(){
        try {
            this.mPresenter = this.getPresenter().newInstance();
            if (null!=mPresenter && null!=getBaseView()) {
                mPresenter.attachView(getBaseView());
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

    public void showLoading() {
        WKDialog.showProgressDialog();
    }

    public void dismissLoading() {
        WKDialog.dissmissProgressDialog();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null!=mPresenter) {
            mPresenter.detachView();
        }
    }

}
