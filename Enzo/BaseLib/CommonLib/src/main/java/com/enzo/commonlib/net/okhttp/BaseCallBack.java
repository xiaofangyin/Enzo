package com.enzo.commonlib.net.okhttp;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class BaseCallBack {

    public abstract void onRequestBefore(Request request);

    public abstract void onResponse(Response response);

    public abstract void onSuccess(Call call, Response response, String t);

    public abstract void onFailure(Call call, Exception e);

    public abstract void inProgress(int progress, long total, int id);
}
