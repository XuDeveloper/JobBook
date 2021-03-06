package com.example.jobbook.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;

import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.service.MyPushIntentService;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import org.litepal.LitePal;

/**
 * Created by Xu on 2016/8/24.
 */
public class MyApplication extends Application {

    private static PersonBean mPersonBean;

    private static int mLoginStatus = 0;

    private static String account;

    public static String mDevicetoken;

    private Handler handler = null;

    private PushAgent mPushAgent;

    public static View mSnackBarView;

    private static Context sContext;

    public static PersonBean getmPersonBean() {
        if (mPersonBean != null) {
            return mPersonBean;
        }
        return null;
    }

    /**
     * set mPersonBean,save LoginStatus
     *
     * @param context
     * @param personBean
     */
    public static void setmPersonBean(Context context, PersonBean personBean) {
        if (personBean != null) {
            mPersonBean = personBean;
            mLoginStatus = 1;
            SharedPreferences sharedPreferences = context.getSharedPreferences("user", MODE_PRIVATE);
            Util.savePersonBean(sharedPreferences, personBean);
            setAccount(mPersonBean.getAccount());
        }
    }

    public static void clearmPersonBean(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", MODE_PRIVATE);
        Util.clearPersonBean(sharedPreferences);
        mPersonBean = null;
        setmNoLoginStatus();
        setAccount(null);
    }

    public static String getAccount() {
        if (account != null) {
            return account;
        }
        return null;
    }

    private static void setAccount(String account) {
        MyApplication.account = account;
    }

    public static Context getContext() {
        return sContext;
    }

//    public static LinkedList<String> getmSearchRecordList() {
//        return mSearchRecordList;
//    }
//
//    public static void setmSearchRecordList(LinkedList<String> list) {
//        if (list != null) {
//            mSearchRecordList = list;
//        }
//    }
//
//    public static void addmSearchRecordList(String item) {
//        if (item != null) {
//            if (mSearchRecordList.size() <= 10) {
//                mSearchRecordList.add(item);
//            }else {
//                int index = mSearchRecordList.size() - 10;
//                while(index != 0) {
//                    mSearchRecordList.removeFirst();
//                    index --;
//                }
//                mSearchRecordList.addLast(item);
//            }
//        }
//    }


    public static int getmLoginStatus() {
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
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();

        RetrofitService.init();

//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
////                .addInterceptor(new LoggerInterceptor("TAG"))
//                .connectTimeout(50000L, TimeUnit.MILLISECONDS)
//                .readTimeout(50000L, TimeUnit.MILLISECONDS)
//                //其他配置
//                .build();
//
//        OkHttpUtils.initClient(okHttpClient);

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        LitePal.initialize(this);

        L.init(true);

        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "b5a16a0d8fa052cf6a11ac9ac8dd9619");

        mPushAgent = PushAgent.getInstance(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //注册推送服务，每次调用register方法都会回调该接口
                //注册推送服务，每次调用register方法都会回调该接口
                mPushAgent.register(new IUmengRegisterCallback() {
                    @Override
                    public void onSuccess(String deviceToken) {
                        //注册成功会返回deviceToken
                    }
                    @Override
                    public void onFailure(String s, String s1) {
                    }
                });
            }
        }).start();

//        if (Util.loadPersonBean(context.getSharedPreferences("user", MODE_PRIVATE)) != null) {
//            mPushAgent.enable(new IUmengCallback() {
//                @Override
//                public void onSuccess() {
//                    L.d("umeng-enabled", "success");
//                }
//
//                @Override
//                public void onFailure(String s, String s1) {
//                    L.d("umeng-enabled", s + s1);
//                }
//            });
//            L.d("myapplication", "register");
//        } else {
//            mPushAgent.disable(new IUmengCallback() {
//                @Override
//                public void onSuccess() {
//                    L.d("umeng-disabled", "success");
//                }
//
//                @Override
//                public void onFailure(String s, String s1) {
//                    L.d("umeng-disabled", s + s1);
//                }
//            });
//        }

        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
        mPushAgent.setDisplayNotificationNumber(5);
        // 内存泄漏检测工具
//        LeakCanary.install(this);

//        Context context = this.getApplicationContext();
//        SharedPreferences sharedPreferences = context.getSharedPreferences("user", MODE_PRIVATE);
//        MyApplication.setmPersonBean(context, Util.loadPersonBean(sharedPreferences));
    }
}
