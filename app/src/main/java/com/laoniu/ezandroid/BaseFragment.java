package com.laoniu.ezandroid;

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

public abstract class BaseFragment <V extends BaseView,P extends BasePresenter, B extends ViewDataBinding>
        extends Fragment{

    protected B binding;
    protected P presenter;
    protected V mBaseView;

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
            this.mBaseView = this.getBaseView();
            this.presenter = (P)this.getPresenter().newInstance();
            if (this.presenter != null) {
                this.presenter.init(mBaseView);
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
    protected abstract V getBaseView();

    @MainThread
    protected abstract void initData();

    public void showLoading() {
        WKDialog.showProgressDialog();
    }

    public void dismissLoading() {
        WKDialog.dissmissProgressDialog();
    }
}
