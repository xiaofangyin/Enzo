package com.enzo.commonlib.net.retrofit;

import com.enzo.commonlib.env.EnvConstants;
import com.enzo.commonlib.utils.common.PhoneUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceManager {

    private static final int DEFAULT_TIME_OUT = 15;//超时时间 15s
    private static final int DEFAULT_WRITE_TIME_OUT = 15;
    private static final int DEFAULT_READ_TIME_OUT = 15;
    private Retrofit mRetrofit;

    private RetrofitServiceManager() {
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间

        //添加公共参数
        PostParamsInterceptor.Builder paramsBuilder = new PostParamsInterceptor.Builder();
        paramsBuilder.addParams(PhoneUtils.getInstance().getDefaultParams());
        builder.addInterceptor(paramsBuilder.build());

        //打印日志
        if (EnvConstants.getInstance().isLogOpen()) {
            builder.addInterceptor(new LoggingInterceptor());
        }

        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(EnvConstants.getInstance().isProdEnv() ? ApiConfig.BASE_URL : ApiConfig.BASE_TEST_URL)
                .build();
    }

    private static class SingletonHolder {
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }

    /**
     * 获取RetrofitServiceManager
     */
    public static RetrofitServiceManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取对应的Service
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }
}
