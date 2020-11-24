package com.laoniu.ezandroid.utils.http;

import com.google.gson.Gson;
import com.laoniu.ezandroid.model.CommonData;
import com.laoniu.ezandroid.utils.http.httpsettings.OkHttpUtils;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WKHttp {
    private static volatile WKHttp sInstance;

    public static WKHttp getInstance() {
        if (sInstance == null) {
            synchronized (WKHttp.class) {
                if (sInstance == null) {
                    sInstance = new WKHttp();
                }
            }
        }
        return sInstance;
    }

    private final RetrofitService mRetrofitService;

    private WKHttp() {
        OkHttpClient client = new OkHttpUtils().getClient();
        mRetrofitService = (new Retrofit.Builder()).baseUrl(Api.BaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build()
                .create(RetrofitService.class);
    }

    public RetrofitService getService(){
        return mRetrofitService;
    }

    public RequestBody getRequestBody(Map<String, Object> map) {
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("Content-Type: application/json;charset=UTF-8"),
                        new Gson().toJson(map));
        return requestBody;
    }

//    //上传图片-多图上传
//    public Observable<BaseResponse<List<String>>> uploadImgs(List<String> pathList) {
//        Map<String, RequestBody> bodyMap = new HashMap<>();
//        if (pathList.size() > 0) {
//            for (String imgPath : pathList) {
//                File file = new File(imgPath);
//                if (file.exists()) {
//                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
//                    bodyMap.put("files" + "\";filename=\"" + file.getName(), requestBody);
//                    Log.e("zwk", "imgPath=" + imgPath);
//                }
//            }
//        }
//        return mRetrofitService.uploadImgs(bodyMap);
//    }


}
