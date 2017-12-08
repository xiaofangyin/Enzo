package com.ifenglian.enzo;

import com.ifenglian.commonlib.base.BaseApplication;
import com.ifenglian.flkit.FLPluginFactory;
import com.ifenglian.main.plugin.SAFactoryManager;
import com.ifenglian.module_a.plugin.MAFactoryManager;
import com.ifenglian.module_b.plugin.MBFactoryManager;
import com.ifenglian.module_c.plugin.MCFactoryManager;
import com.ifenglian.module_d.plugin.MDFactoryManager;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cookie.store.PersistentCookieStore;
import com.lzy.okhttputils.model.HttpHeaders;
import com.lzy.okhttputils.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: App
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
 */
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initOkHttp();
        initFactory();
    }

    private void initOkHttp() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("commonHeaderKey1", "commonHeaderValue1");    //所有的 header 都 不支持 中文
        headers.put("commonHeaderKey2", "commonHeaderValue2");
        HttpParams params = new HttpParams();
        params.put("commonParamsKey1", "commonParamsValue1");     //所有的 params 都 支持 中文
        params.put("commonParamsKey2", "这里支持中文参数");

        //必须调用初始化
        OkHttpUtils.init(this);
        //以下都不是必须的，根据需要自行选择
        OkHttpUtils.getInstance()//
                .debug("OkHttpUtils")                                              //是否打开调试
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                 //全局的写入超时时间
//                .setCookieStore(new MemoryCookieStore())                           //cookie使用内存缓存（app退出后，cookie消失）
                .setCookieStore(new PersistentCookieStore())                       //cookie持久化存储，如果cookie不过期，则一直有效
                .addCommonHeaders(headers)                                         //设置全局公共头
                .addCommonParams(params);                                          //设置全局公共参数
    }

    private void initFactory() {
        List<FLPluginFactory> factoryList = new ArrayList<>();
        factoryList.add(MAFactoryManager.getInstance());
        factoryList.add(MBFactoryManager.getInstance());
        factoryList.add(MCFactoryManager.getInstance());
        factoryList.add(MDFactoryManager.getInstance());
        SAFactoryManager.getInstance().init(App.this, factoryList);
    }
}
