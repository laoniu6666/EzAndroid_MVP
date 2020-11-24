package com.laoniu.ezandroid;

import android.app.Activity;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BasePresenter<V extends BaseView> implements IPresenter {

    public V mBaseView;
    public CompositeDisposable mDisposable;

    public void init(V mBaseView) {
        this.mBaseView=mBaseView;
        mDisposable = new CompositeDisposable();
    }


    protected void add(Disposable disposable) {
        mDisposable.add(disposable);
    }

    @Override
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {

    }

    @Override
    public void onCreate(@NotNull LifecycleOwner owner) {

    }

    @Override
    public void onStart(@NotNull LifecycleOwner owner) {

    }

    @Override
    public void onResume(@NotNull LifecycleOwner owner) {

    }

    @Override
    public void onPause(@NotNull LifecycleOwner owner) {

    }

    @Override
    public void onStop(@NotNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NotNull LifecycleOwner owner) {
        mDisposable.clear();
    }

    @Override
    public void onLifecycleChanged(@NotNull LifecycleOwner owner, @NotNull Lifecycle.Event event) {

    }
}
