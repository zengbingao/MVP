package com.robin.mvp.common;

import android.annotation.SuppressLint;
import android.app.Application;

/**
 * Created by Robin on 2017/3/30.
 */

public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static API sApi;
    @Override
    public void onCreate() {
        super.onCreate();
        sApi = API.getApi(getApplicationContext());
    }

    public static API getApi() {
        return sApi;
    }
}
