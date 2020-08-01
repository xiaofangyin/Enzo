package com.enzo.module_d.model;

import com.enzo.commonlib.net.okhttp.BaseExecutor;
import com.enzo.commonlib.net.okhttp.OkHttpCallBack;
import com.enzo.module_d.model.bean.SubjectBean;

import java.util.LinkedHashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 文 件 名: GetSubjectExecutor
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/1
 * 邮   箱: xiaofywork@163.com
 */
public class GetSubjectExecutor extends BaseExecutor<LinkedHashMap> {

    public static final String BASE_URL = "http://api.douban.com/v2/movie/";
    // 豆瓣电影申请的开发者 API KEY（暂时不对个人开发者开放）
    public static final String API_KEY = "0b2bdeda43b5688921839c8ecb20399b";
    // 电影 ID （例：移动迷宫3：死亡解药）
    public static final String MOVIE_ID = "26004132";

    public GetSubjectExecutor(){
        params.put("apikey", API_KEY);
    }

    @Override
    protected String getHost() {
        return BASE_URL;
    }

    @Override
    protected String getVirtual() {
        return "subject/" + MOVIE_ID;
    }

    @Override
    public void executor() {
        post(new OkHttpCallBack<LinkedHashMap>() {
            @Override
            public void onSuccess(Call call, Response response, LinkedHashMap o) {
                jsonCallback.onSuccess(o);
            }

            @Override
            public void onFailure(Call call, Exception e) {
                jsonCallback.onFailed(e);
            }
        });
    }
}
