package com.enzo.commonlib.net.okhttp;

import com.enzo.commonlib.utils.common.PhoneUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public abstract class BaseExecutor<T> {

    protected JsonCallback jsonCallback;
    protected Map<String, String> mParams = new HashMap<>();
    protected Map<String, String> mHeaders = new HashMap<>();

    protected abstract String getHost();

    protected abstract String getVirtual();

    public BaseExecutor<T> callback(JsonCallback<T> jsonCallback) {
        this.jsonCallback = jsonCallback;
        return this;
    }

    public BaseExecutor<T> params(Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            mParams.putAll(params);
        }
        return this;
    }

    public BaseExecutor<T> headers(Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            mHeaders.putAll(headers);
        }
        return this;
    }

    public abstract void execute();

    protected void post(final OkHttpCallBack<T> callback) {
        mParams.putAll(PhoneUtils.getInstance().getDefaultParams());
        OkHttpManager.getInstance().postRequest(getHost().concat(getVirtual()), mParams, mHeaders, new OkHttpCallBack<T>() {
            @Override
            public void onSuccess(Call call, Response response, T o) {
                if (callback != null) {
                    callback.onSuccess(call, response, o);
                }
            }

            @Override
            public void onFailure(Call call, Exception e) {
                if (callback != null) {
                    callback.onFailure(call, e);
                }
            }
        });
    }

    protected void get(final OkHttpCallBack<T> callback) {
        mParams.putAll(PhoneUtils.getInstance().getDefaultParams());
        OkHttpManager.getInstance().getRequest(getHost().concat(getVirtual()), mParams, mHeaders, new OkHttpCallBack<T>() {
            @Override
            public void onSuccess(Call call, Response response, T o) {
                if (callback != null) {
                    callback.onSuccess(call, response, o);
                }
            }

            @Override
            public void onFailure(Call call, Exception e) {
                if (callback != null) {
                    callback.onFailure(call, e);
                }
            }
        });
    }

    public interface JsonCallback<T> {

        void onSuccess(T t);

        void onFailed(Exception e);
    }
}
