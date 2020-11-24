package com.laoniu.ezandroid.utils.http;

import com.laoniu.ezandroid.model.IPAddress;
import com.laoniu.ezandroid.model.NewsListBean;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    /**
     * 获取ip
     * @return Observable<BaseResponse<IPAddress>>
     */
    @POST(Api.IP)
    Observable<BaseResponse<IPAddress>> getIpAddress(@Query("ip") String ip, @Query("key")String key);

    /**
     * 获取新闻列表
     * @return Observable<BaseResponse<IPAddress>>
     */
    @POST(Api.newslist)
    Observable<BaseResponse<NewsListBean>> getNewsList(@Query("type") String type, @Query("key")String key);


}
