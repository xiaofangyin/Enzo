package com.enzo.module_d.model.retrofit.api;

import com.enzo.module_d.model.retrofit.retrofit.annotation.Field;
import com.enzo.module_d.model.retrofit.retrofit.annotation.GET;
import com.enzo.module_d.model.retrofit.retrofit.annotation.POST;
import com.enzo.module_d.model.retrofit.retrofit.annotation.Query;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;

public interface WeatherApi {

    @POST("/v3/weather/weatherInfo")
    @FormUrlEncoded
    Call<ResponseBody> postWeather(@Field("city") String city, @Field("key") String key);


    @GET("/v3/weather/weatherInfo")
    Call<ResponseBody> getWeather(@Query("city") String city, @Query("key") String key);
}
