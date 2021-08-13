package com.enzo.module_d.utils.retrofit.retrofit;

import com.enzo.module_d.utils.retrofit.retrofit.annotation.Field;
import com.enzo.module_d.utils.retrofit.retrofit.annotation.GET;
import com.enzo.module_d.utils.retrofit.retrofit.annotation.POST;
import com.enzo.module_d.utils.retrofit.retrofit.annotation.Query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * 记录请求类型  参数  完整地址
 */
public class ServiceMethod {

    private final Call.Factory callFactory;
    private final String relativeUrl;
    private final ParameterHandler[] parameterHandler;
    private FormBody.Builder formBuild;
    HttpUrl baseUrl;
    String httpMethod;
    HttpUrl.Builder urlBuilder;

    public ServiceMethod(Builder builder) {
        baseUrl = builder.enjoyRetrofit.baseUrl;
        callFactory = builder.enjoyRetrofit.callFactory;

        httpMethod = builder.httpMethod;
        relativeUrl = builder.relativeUrl;
        parameterHandler = builder.parameterHandler;

        //如果是有请求体,创建一个okhttp的请求体对象
        if (builder.hasBody) {
            formBuild = new FormBody.Builder();
        }
    }

    public Object invoke(Object[] args) {
        /**
         * 1  处理请求的地址与参数
         */
        for (int i = 0; i < parameterHandler.length; i++) {
            ParameterHandler handlers = parameterHandler[i];
            //handler内本来就记录了key,现在给到对应的value
            handlers.apply(this, args[i].toString());
        }

        //获取最终请求地址
        HttpUrl url;
        if (urlBuilder == null) {
            urlBuilder = baseUrl.newBuilder(relativeUrl);
        }
        url = urlBuilder.build();

        //请求体
        FormBody formBody = null;
        if (formBuild != null) {
            formBody = formBuild.build();
        }

        Request request = new Request.Builder().url(url).method(httpMethod, formBody).build();
        return callFactory.newCall(request);
    }

    // get请求,  把 k-v 拼到url里面
    public void addQueryParameter(String key, String value) {
        if (urlBuilder == null) {
            urlBuilder = baseUrl.newBuilder(relativeUrl);
        }
        urlBuilder.addQueryParameter(key, value);
    }

    //Post   把k-v 放到 请求体中
    public void addFiledParameter(String key, String value) {
        formBuild.add(key, value);
    }


    public static class Builder {

        private final EnjoyRetrofit enjoyRetrofit;
        private final Annotation[] methodAnnotations;
        private final Annotation[][] parameterAnnotations;
        ParameterHandler[] parameterHandler;
        private String httpMethod;
        private String relativeUrl;
        private boolean hasBody;

        public Builder(EnjoyRetrofit enjoyRetrofit, Method method) {
            this.enjoyRetrofit = enjoyRetrofit;
            //获取方法上的所有的注解
            methodAnnotations = method.getAnnotations();
            //获得方法参数的所有的注解 (一个参数可以有多个注解,一个方法又会有多个参数)
            parameterAnnotations = method.getParameterAnnotations();
        }

        public ServiceMethod build() {

            /**
             * 1 解析方法上的注解, 只处理POST与GET
             */
            for (Annotation methodAnnotation : methodAnnotations) {
                if (methodAnnotation instanceof POST) {
                    //记录当前请求方式
                    this.httpMethod = "POST";
                    //记录请求url的path
                    this.relativeUrl = ((POST) methodAnnotation).value();
                    // 是否有请求体
                    this.hasBody = true;
                } else if (methodAnnotation instanceof GET) {
                    this.httpMethod = "GET";
                    this.relativeUrl = ((GET) methodAnnotation).value();
                    this.hasBody = false;
                }
            }


            /**
             * 2 解析方法参数的注解
             */
            int length = parameterAnnotations.length;
            parameterHandler = new ParameterHandler[length];
            for (int i = 0; i < length; i++) {
                // 一个参数上的所有的注解
                Annotation[] annotations = parameterAnnotations[i];
                // 处理参数上的每一个注解
                for (Annotation annotation : annotations) {
                    //todo 可以加一个判断:如果httpMethod是get请求,现在又解析到Filed注解,可以提示使用者使用Query注解
                    if (annotation instanceof Field) {
                        //得到注解上的value: 请求参数的key
                        String value = ((Field) annotation).value();
                        parameterHandler[i] = new ParameterHandler.FiledParameterHandler(value);
                    } else if (annotation instanceof Query) {
                        String value = ((Query) annotation).value();
                        parameterHandler[i] = new ParameterHandler.QueryParameterHandler(value);

                    }
                }
            }

            return new ServiceMethod(this);
        }
    }
}
