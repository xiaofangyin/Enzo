package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;
import com.enzo.module_d.utils.retrofit.api.EnjoyWeatherApi;
import com.enzo.module_d.utils.retrofit.api.WeatherApi;
import com.enzo.module_d.utils.retrofit.retrofit.EnjoyRetrofit;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MDRetrofitActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private WeatherApi weatherApi;
    private EnjoyWeatherApi enjoyWeatherApi;
    private TextView tvResult;
    private Handler mHandler;

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("Retrofit");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_retrofit;
    }

    @Override
    public void initView() {
        tvResult = findViewById(R.id.tv_result);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mHandler = new Handler();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://restapi.amap.com").build();
        weatherApi = retrofit.create(WeatherApi.class);

        EnjoyRetrofit enjoyRetrofit = new EnjoyRetrofit.Builder().baseUrl("https://restapi.amap.com").build();
        enjoyWeatherApi = enjoyRetrofit.create(EnjoyWeatherApi.class);
    }

    @Override
    public void initListener() {

    }

    public void get(View view) {
        Call<ResponseBody> call = weatherApi.getWeather("110101", "ae6c53e2186f33bbf240a12d80672d1b");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                result1(response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void post(View view) {
        Call<ResponseBody> call = weatherApi.postWeather("110101", "ae6c53e2186f33bbf240a12d80672d1b");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                result1(response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void enjoyGet(View view) {
        okhttp3.Call call = enjoyWeatherApi.getWeather("110101", "ae6c53e2186f33bbf240a12d80672d1b");
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                enjoyResult(response);
            }
        });

    }

    public void enjoyPost(View view) {
        okhttp3.Call call = enjoyWeatherApi.postWeather("110101", "ae6c53e2186f33bbf240a12d80672d1b");
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                enjoyResult(response);
            }
        });
    }

    private void result1(Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            try {
                final String string = body.string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText("");
                        tvResult.append("time: " + System.currentTimeMillis());
                        tvResult.append("\n");
                        tvResult.append(string);
                        Log.i(TAG, "onResponse get: " + string);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                body.close();
            }
        }
    }

    private void enjoyResult(okhttp3.Response response) {
        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            try {
                final String string = body.string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText("");
                        tvResult.append("time: " + System.currentTimeMillis());
                        tvResult.append("\n");
                        tvResult.append(string);
                        Log.i(TAG, "onResponse get: " + string);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                body.close();
            }
        }
    }

}
