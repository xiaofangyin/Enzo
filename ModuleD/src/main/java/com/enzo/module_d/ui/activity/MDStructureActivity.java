package com.enzo.module_d.ui.activity;

import android.os.Bundle;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.net.okhttp.BaseExecutor;
import com.enzo.commonlib.net.okhttp.OkHttpCallBack;
import com.enzo.commonlib.net.okhttp.OkHttpManager;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.module_d.model.GetSubjectExecutor;
import com.enzo.module_d.model.bean.SubjectBean;
import com.enzo.module_d.practice.datastructure.MyHashMap;
import com.enzo.module_d.practice.datastructure.QueueX;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 文 件 名: MDStructureActivity
 * 创 建 人: xiaofy
 * 创建日期: 2019/6/24
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MDStructureActivity extends BaseActivity {

    // 豆瓣电影 API
    public static final String BASE_URL = "http://api.douban.com/v2/movie/";

    // 豆瓣电影申请的开发者 API KEY（暂时不对个人开发者开放）
    public static final String API_KEY = "0b2bdeda43b5688921839c8ecb20399b";

    // 电影 ID （例：移动迷宫3：死亡解药）
    public static final String MOVIE_ID = "26004132";

    // 演员 ID （例：道恩·强森）
    public static final String CELEBRITY_ID = "1044707";

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //hashmap
//        MyHashMap<String, String> map = new MyHashMap<>();
//        for (int i = 0; i < 20; i++) {
//            map.put("enzo" + i, "xiaofy" + i);
//        }
//        LogUtil.d(map.toString());
//
//        //队列
//        QueueX queueX = new QueueX(10);
//        for (int i = 0; i < 20; i++) {
//            queueX.insert(i);
//        }
//        for (int i = 0; i < 20; i++) {
//            Object data = queueX.remove();
//            LogUtil.d("data: " + data);
//        }

//        String url = BASE_URL + "subject/" + MOVIE_ID;
//        Map<String, String> map1 = new HashMap<>();
//        map1.put("apikey", API_KEY);
//        OkHttpManager.getInstance().postRequest(url, map1, new OkHttpCallBack<String>() {
//            @Override
//            public void onSuccess(Call call, Response response, String o) {
//
//            }
//
//            @Override
//            public void onFailure(Call call, Exception e) {
//
//            }
//        });

        new GetSubjectExecutor().callback(new BaseExecutor.JsonCallback<LinkedHashMap<String, String>>() {
            @Override
            public void onSuccess(LinkedHashMap<String, String> subjectBean) {

            }

            @Override
            public void onFailed(Exception e) {

            }
        }).executor();
    }

    @Override
    public void initListener() {

    }
}
