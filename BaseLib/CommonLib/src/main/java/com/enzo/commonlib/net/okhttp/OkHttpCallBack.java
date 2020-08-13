package com.enzo.commonlib.net.okhttp;

import okhttp3.Request;
import okhttp3.Response;

public abstract class OkHttpCallBack extends BaseCallBack {

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
