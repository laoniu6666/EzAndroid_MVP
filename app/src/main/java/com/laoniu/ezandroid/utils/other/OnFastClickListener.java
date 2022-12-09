package com.laoniu.ezandroid.utils.other;

import android.view.View;

public abstract class OnFastClickListener implements View.OnClickListener {
    private final long MIN_CLICK_DELAY_TIME = 500L;
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