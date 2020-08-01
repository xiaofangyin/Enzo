package com.enzo.commonlib.net.okhttp;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public abstract class BaseExecutor<T> {

    protected JsonCallback jsonCallback;
    protected Map<String, String> params = new HashMap<>();

    protected abstract String getHost();

    protected abstract String getVirtual();

    public BaseExecutor callback(JsonCallback<T> jsonCallback) {
        this.jsonCallback = jsonCallback;
        return this;
    }

    public abstract void executor();

    protected void post(final OkHttpCallBack<T> callback) {
        OkHttpManager.getInstance().postRequest(getHost().concat(getVirtual()), params, new OkHttpCallBack<T>() {
            @Override
            public void onSuccess(Call call, Response response, T o) {
                if (jsonCallback != null) {
                    jsonCallback.onSuccess(o);
                }
            }

            @Override
            public void onFailure(Call call, Exception e) {
                if (jsonCallback != null) {
                    jsonCallback.onFailed(e);
                }
            }
        });
    }

    public interface JsonCallback<T> {

        void onSuccess(T t);

        void onFailed(Exception e);
    }
}
