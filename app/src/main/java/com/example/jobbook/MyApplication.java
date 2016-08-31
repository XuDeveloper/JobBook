package com.example.jobbook;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.jobbook.bean.PersonBean;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Xu on 2016/8/24.
 */
public class MyApplication extends Application {

    private static PersonBean mPersonBean;

    private static int mLoginStatus = 0;

    private Handler handler = null;

    public static PersonBean getmPersonBean() {
        return mPersonBean;
    }

    public static void setmPersonBean(PersonBean personBean) {
        if(personBean != null){
            mPersonBean = personBean;
            mLoginStatus = 1;
        }
    }

    public static int getmLoginStatus(){
        return mLoginStatus;
    }

    public static void setmNoLoginStatus() {
        mLoginStatus = 0;
        mPersonBean = null;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(50000L, TimeUnit.MILLISECONDS)
                .readTimeout(50000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }
}
