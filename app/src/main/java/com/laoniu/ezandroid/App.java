package com.laoniu.ezandroid;

import android.app.Application;


public class App extends MyApplication {

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
