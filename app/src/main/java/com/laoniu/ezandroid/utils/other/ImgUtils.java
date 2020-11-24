package com.laoniu.ezandroid.utils.other;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.laoniu.ezandroid.R;

import java.io.File;

public class ImgUtils {

    public static void setView(ImageView mIv, String url){
        setView(null,mIv,url,-1);
    }

    public static void setView(ImageView mIv, String url, int Radius){
        setView(null,mIv,url,Radius);
    }

    public static void setView(Activity act, ImageView mIv, String url){
        setView(act,mIv,url,-1);
    }

    public static void setView(Activity act, ImageView mIv, String url, int Radius){
        if(TextUtils.isEmpty(url)){
            return;
        }
        //context
        RequestManager rm = null==act ? Glide.with(mIv) : Glide.with(act);
        //radius
        RequestOptions options=new RequestOptions();
        RequestBuilder<Drawable> builder;
        if(Radius>0){
            options.transform(new CenterCrop(),new RoundedCorners(Radius));
        }
        if(url.startsWith("http")){
            builder = rm.load(url);
        }else {
            File file = new File(url);
            if(!file.exists()){
                rm.load(R.mipmap.ic_default).apply(options).into(mIv);
                return;
            }else{
                builder = rm.load(file);
            }
        }
        RequestBuilder<Drawable> placeholder = builder.placeholder(R.mipmap.ic_default);
        RequestBuilder<Drawable> apply = Radius>0 ? placeholder.apply(options) : placeholder;
        apply.thumbnail(rm.load(R.mipmap.ic_default).apply(options))
        .into(mIv);
    }

}
