package com.enzo.commonlib.net.retrofit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class PostParamsInterceptor implements Interceptor {

    private Map<String, String> mParamsMap = new HashMap<>();

    private PostParamsInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.method().equals("POST")) {
            if (request.body() instanceof FormBody) {
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                FormBody formBody = (FormBody) request.body();

                //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
                if (formBody != null) {
                    for (int i = 0; i < formBody.size(); i++) {
                        bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                    }
                }

                if (mParamsMap.size() > 0) {
                    for (Map.Entry<String, String> params : mParamsMap.entrySet()) {
                        bodyBuilder.addEncoded(params.getKey(), params.getValue());
                    }
                }

                formBody = bodyBuilder.build();
                request = request.newBuilder().post(formBody).build();
            }
        }
        return chain.proceed(request);
    }

    public static class Builder {
        PostParamsInterceptor mInterceptor;

        public Builder() {
            mInterceptor = new PostParamsInterceptor();
        }

        Builder addParams(String key, String value) {
            mInterceptor.mParamsMap.put(key, value);
            return this;
        }

        public Builder addParams(String key, int value) {
            return addParams(key, String.valueOf(value));
        }

        public Builder addParams(String key, float value) {
            return addParams(key, String.valueOf(value));
        }

        public Builder addParams(String key, long value) {
            return addParams(key, String.valueOf(value));
        }

        public Builder addParams(String key, double value) {
            return addParams(key, String.valueOf(value));
        }

        public Builder addParams(Map<String, String> map) {
            if (map.size() > 0) {
                for (Map.Entry<String, String> params : map.entrySet()) {
                    addParams(params.getKey(), String.valueOf(params.getValue()));
                }
            }
            return this;
        }

        public PostParamsInterceptor build() {
            return mInterceptor;
        }
    }
}
