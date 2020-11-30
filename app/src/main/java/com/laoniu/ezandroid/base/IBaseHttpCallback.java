package com.laoniu.ezandroid.base;

import com.laoniu.ezandroid.utils.http.BaseResponse;

public interface IBaseHttpCallback<BEAN> {
    void onSuccess(BaseResponse<BEAN> response);
    void onFail(String msg);
}