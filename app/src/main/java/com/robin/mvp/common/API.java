package com.robin.mvp.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.robin.mvp.app.first.model.DataModel;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Robin on 2017/3/29.
 */

public class API {
    private static final int CONNECT_TIME_OUT = 30 * 1000;
    private static final int READ_TIME_OUT = 30 * 1000;
    private static final int CACHE_SIZE = 10 * 1024 * 1024;
    private Context mContext;
    private RestApi mRestApi;
    @SuppressLint("StaticFieldLeak")
    private static API sApi;
    private String mBaseUrl = "http://gank.io/";
//    String androiddata = "http://gank.io/api/random/data/Android/20";
//    String iosdata = "http://gank.io/api/random/data/iOS/20";

    private API(Context context) {
        this.mContext = context.getApplicationContext();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(MyOkHttpClient(mContext))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mRestApi = retrofit.create(RestApi.class);
    }

    public static API getApi(Context context) {
        if (sApi == null) {
            sApi = new API(context);
        }
        return sApi;
    }

    public RestApi getRestApi() {
        return mRestApi;
    }

    private OkHttpClient MyOkHttpClient(final Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");//创建缓存文件
        Cache cache = new Cache(httpCacheDirectory, CACHE_SIZE);//设置缓存10M
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//log的等级，4种等级，这是最详细的一种等级
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)//超时时间
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)//超时时间
                .addInterceptor(new Interceptor() {//添加拦截器
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl httpUrl = request.url().newBuilder()
                                //这个地方的addQueryParameter是所有接口都附加的两个值，因各家app而异，加到这个地方就省去了，在retrofit里面单独添加的麻烦。
//                                .addQueryParameter("v", "1.0.3")
//                                .addQueryParameter("client","1")
                                .build();
                        request = request.newBuilder().url(httpUrl).build();
                        Response response = chain.proceed(request);
                        Log.d("Response Code", response.code() + "");
                        if (response.code() == 401) {//这个地方可以根据返回码做一些事情。通过sendBroadcast发出去。
//                            Intent intent = new Intent("Logout");
//                            intent.putExtra("badAuth", true);
//                            context.sendBroadcast(intent);
                        }
                        return response;
                    }
                })
                .addInterceptor(loggingInterceptor)//把上面的log拦截器添加进来
                .cache(cache)//添加缓存
                .build();//build生效
        return okHttpClient;//返回client
    }

    public interface RestApi {
        @GET("api/random/data/{name}/{number}")
        Observable<DataModel> getData(@Path("name") String name, @Path("number") String number);
    }
}
