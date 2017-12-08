package com.ifenglian.commonlib.utils.updateversion;


import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * http  请求工具类
 *
 * @author winfo-wj
 */
public class HttpRequest {

    private static HttpUtils http = new HttpUtils();


    /**
     * 请求回调接口
     *
     * @author winfo-wj
     */
    public interface RequestCallBackListener {
        /**
         * 请求成功
         *
         * @param resultData 服务器返回的结果数据
         */
        public void onSuccess(String resultData);

        /**
         * 请求失败
         *
         * @param error 错误信息
         */
        public void onFailure(String error);
    }


    /**
     * get请求
     *
     * @param url                     请求路径
     * @param requestCallBackListener 请求回调
     */
    public static void get(String url, final RequestCallBackListener requestCallBackListener) {
        http.configTimeout(1000 * 10);
        http.send(HttpMethod.GET, url, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> response) {
                requestCallBackListener.onSuccess(response.result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                requestCallBackListener.onFailure(msg);
            }
        });
    }

    /**
     * post请求
     *
     * @param url                     请求地址
     * @param params                  请求参数
     * @param requestCallBackListener 请求回调
     */
    public static void post(String url, RequestParams params, final RequestCallBackListener requestCallBackListener) {
        http.configTimeout(1000 * 10);
        http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> response) {
                requestCallBackListener.onSuccess(response.result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                requestCallBackListener.onFailure(msg);
            }
        });
    }
}
