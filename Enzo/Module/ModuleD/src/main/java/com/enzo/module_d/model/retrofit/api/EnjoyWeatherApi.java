package com.enzo.module_d.model.retrofit.api;

import com.enzo.module_d.model.retrofit.retrofit.annotation.Field;
import com.enzo.module_d.model.retrofit.retrofit.annotation.GET;
import com.enzo.module_d.model.retrofit.retrofit.annotation.POST;
import com.enzo.module_d.model.retrofit.retrofit.annotation.Query;

import okhttp3.Call;

public interface EnjoyWeatherApi {

    @POST("/v3/weather/weatherInfo")
    Call postWeather(@Field("city") String city, @Field("key") String key);


    @GET("/v3/weather/weatherInfo")
    Call getWeather(@Query("city") String city, @Query("key") String key);
}
