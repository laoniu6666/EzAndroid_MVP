package com.laoniu.ezandroid.utils.http;

public class BaseResponse<T> {
    public int error_code;
    public String resultcode;
    public String reason;
    public T result;
}