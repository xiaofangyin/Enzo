package com.enzo.commonlib.net.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.common.PhoneUtils;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 文 件 名: OkHttpManager
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/9
 * 邮   箱: xiaofangyinwork@163.com
 */
public class OkHttpManager {

    private static OkHttpManager mOkHttpManager;

    private OkHttpClient mOkHttpClient;

    private Gson gson;

    private Handler handler;

    private OkHttpManager() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.newBuilder().
                connectTimeout(10, TimeUnit.SECONDS).
                readTimeout(10, TimeUnit.SECONDS).
                writeTimeout(10, TimeUnit.SECONDS);
        gson = new Gson();
        handler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpManager getInstance() {
        if (mOkHttpManager == null) {
            mOkHttpManager = new OkHttpManager();
        }
        return mOkHttpManager;
    }

    /***********************
     * 对外公布的可调方法
     ************************/

    public void getRequest(String url, Map<String, String> params, final BaseCallBack callBack) {
        String urlWithParam = attachHttpGetParams(url, params);
        Request request = buildRequest(urlWithParam, null, HttpMethodType.GET);
        doRequest(request, params, callBack);
    }

    public void postRequest(String url, Map<String, String> params, final BaseCallBack callBack) {
        Request request = buildRequest(url, params, HttpMethodType.POST);
        doRequest(request, params, callBack);
    }

    public void postJsonRequest(String url, String json, BaseCallBack callBack) {
        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(url)//请求的url
                .post(requestBody)
                .build();
        doRequest(request, null, callBack);
    }

    public void postUploadSingleImage(String url, final BaseCallBack callback, File file, String fileKey, Map<String, String> params) {
        Param[] paramsArr = fromMapToParams(params);

        try {
            postAsyn(url, callback, file, fileKey, paramsArr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postUploadMoreImages(String url, final BaseCallBack callback, File[] files, String[] fileKeys, Map<String, String> params) {
        Param[] paramsArr = fromMapToParams(params);

        try {
            postAsyn(url, callback, files, fileKeys, paramsArr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***********************
     * 对内方法
     ************************/
    //单个文件上传请求  不带参数
    private void postAsyn(String url, BaseCallBack callback, File file, String fileKey) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        doRequest(request, null, callback);
    }

    //单个文件上传请求 带参数
    private void postAsyn(String url, BaseCallBack callback, File file, String fileKey, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        doRequest(request, null, callback);
    }

    //多个文件上传请求 带参数
    private void postAsyn(String url, BaseCallBack callback, File[] files, String[] fileKeys, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        doRequest(request, null, callback);
    }

    //异步下载文件
    public void asynDownloadFile(final String url, final String destFileDir, final String fileName, final BaseCallBack callBack) {
        final Request request = buildRequest(url, null, HttpMethodType.GET);
        callBack.onRequestBefore(request);  //提示加载框
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onResponse(response);
                InputStream is = null;
                byte[] buf = new byte[1024 * 2];
                final long fileLength = response.body().contentLength();
                int len = 0;
                long readLength = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(destFileDir, fileName);
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        readLength += len;
                        int curProgress = (int) (((float) readLength / fileLength) * 100);
                        callBack.inProgress(curProgress, fileLength, 0);
                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    callBackSuccess(callBack, call, response, file.getAbsolutePath());
                } catch (IOException e) {
                    callBackFailure(call, e, callBack);
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //构造上传图片 Request
    private Request buildMultipartFormRequest(String url, File[] files, String[] fileKeys, Param[] params) {
        params = validateParam(params);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Param param : params) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                    RequestBody.create(MediaType.parse("image/png"), param.value));
        }
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    //Activity页面所有的请求以Activity对象作为tag，可以在onDestory()里面统一取消,this
    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    private Param[] fromMapToParams(Map<String, String> params) {
        if (params == null)
            return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    //去进行网络 异步 请求
    private void doRequest(final Request request, final Map<String, String> params, final BaseCallBack callBack) {
        callBack.onRequestBefore(request);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                LogUtil.d("url: " + request.url().toString());
                if (params != null) {
                    LogUtil.d("params: " + getParamsString(params));
                }
                LogUtil.d("error: " + e.getMessage());
                callBackFailure(call, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onResponse(response);
                String result = response.body().string();
                if (response.isSuccessful()) {
                    LogUtil.d("url: " + request.url().toString());
                    if (params != null) {
                        LogUtil.d("params: " + getParamsString(params));
                    }
                    LogUtil.d("response: " + result);
                    if (callBack.mType == String.class) {
                        callBackSuccess(callBack, call, response, result);
                    } else {
                        try {
                            Object object = gson.fromJson(result, callBack.mType);//自动转化为 泛型对象
                            callBackSuccess(callBack, call, response, object);
                        } catch (JsonParseException e) {
                            //json解析错误时调用
                            callBackFailure(call, e, callBack);
                        }
                    }
                } else {
                    callBackFailure(call, null, callBack);
                }
            }
        });
    }

    //创建 Request对象
    private Request buildRequest(String url, Map<String, String> params, HttpMethodType methodType) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (methodType == HttpMethodType.GET) {
            builder.get();
        } else if (methodType == HttpMethodType.POST) {
            RequestBody requestBody = buildFormData(params);
            builder.post(requestBody);
        }
        return builder.build();
    }

    //构建请求所需的参数表单
    private RequestBody buildFormData(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (PhoneUtils.getInstance().getDefaultParams() != null) {
            for (Map.Entry<String, String> entry : PhoneUtils.getInstance().getDefaultParams().entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    private void callBackSuccess(final BaseCallBack callBack, final Call call, final Response response, final Object object) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(call, response, object);
            }
        });
    }

    private void callBackFailure(final Call call, final Exception e, final BaseCallBack callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onFailure(call, e);
            }
        });
    }

    private Param[] validateParam(Param[] params) {
        if (params == null)
            return new Param[0];
        else
            return params;
    }

    public static class Param {

        Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        String key;
        String value;
    }

    enum HttpMethodType {
        GET, POST
    }

    /**
     * get请求后边拼接参数
     */
    private String attachHttpGetParams(String url, Map<String, String> params) {
        StringBuilder stringBuffer = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            Iterator<String> keys = params.keySet().iterator();
            Iterator<String> values = params.values().iterator();
            stringBuffer.append("?");

            for (int i = 0; i < params.size(); i++) {
                String value = values.next();
                stringBuffer.append(keys.next() + "=" + value);
                if (i != params.size() - 1) {
                    stringBuffer.append("&");
                }
            }
        }
        return url + stringBuffer.toString();
    }

    private String getParamsString(Map<String, String> params) {
        if (params != null && params.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                stringBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append(",");
            }
            if (stringBuilder.toString().endsWith(",")) {
                stringBuilder = stringBuilder.deleteCharAt(stringBuilder.toString().length() - 1);
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
        return "";
    }
}
