package com.laoniu.ezandroid.base;

import android.content.Context;

public interface IBaseView {
    Context getContext();//获取上下文对象,用于保存数据等
    void showLoading();
    void dismissLoading();
}
