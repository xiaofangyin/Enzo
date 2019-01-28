package com.enzo.commonlib.net.okhttp;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 文 件 名: OkHttpCallBack
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/9/9
 * 邮   箱: xiaofangyinwork@163.com
 */
public abstract class OkHttpCallBack<T> extends BaseCallBack<T> {

    @Override
    public void onRequestBefore(Request request) {

    }

    @Override
    public void onResponse(Response response) {

    }

    @Override
    public void inProgress(int progress, long total, int id) {

    }
}
