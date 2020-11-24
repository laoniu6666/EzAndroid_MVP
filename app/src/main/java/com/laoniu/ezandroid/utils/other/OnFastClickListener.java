package com.laoniu.ezandroid.utils.other;

import android.view.View;

public abstract class OnFastClickListener implements View.OnClickListener {
    private final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime;

    public abstract void onFastClick(View v);
    @Override
    public void onClick(View v) {
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            lastClickTime = curClickTime;
            onFastClick(v);
        }
    }
}