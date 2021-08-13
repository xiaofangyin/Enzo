package com.enzo.commonlib.net.retrofit;

import com.enzo.commonlib.utils.common.LogUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 文 件 名: LoggingInterceptor
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/5/27
 * 邮   箱: xiaofangyinwork@163.com
 */
class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();
        long t1 = System.nanoTime();//请求发起的时间
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();//收到响应的时间
        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        LogUtil.d("接口: " + request.url());
        String method = request.method();
        if ("POST".equals(method)) {
            if (request.body() instanceof FormBody) {
                FormBody formBody = (FormBody) request.body();
                if (formBody != null && formBody.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("{");
                    for (int i = 0; i < formBody.size(); i++) {
                        stringBuilder.append(formBody.name(i)).append("=").append(formBody.value(i));
                        if (i != formBody.size() - 1) {
                            stringBuilder.append(",");
                        }
                    }
                    stringBuilder.append("}");
                    LogUtil.d("参数: " + stringBuilder.toString());
                }
            }
        }
        LogUtil.d("结果: " + responseBody.string());
        LogUtil.d("用时: " + (t2 - t1) / 1e6d + "ms");
        return response;
    }
}
