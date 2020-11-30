package com.laoniu.ezandroid;

import android.app.Application;

import com.laoniu.ezandroid.base.BaseApplication;


public class App extends BaseApplication {

    public static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        registerActivityLifecycleCallbacks(new ActivityLifecycleImpl());
    }

    public static Application getInstance(){
        return instance;
    }

}
