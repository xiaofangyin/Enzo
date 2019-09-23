package com.enzo.xiaofy;

import com.enzo.commonlib.base.BaseApplication;
import com.enzo.commonlib.env.EnvConstants;
import com.enzo.commonlib.utils.common.ALog;
import com.enzo.commonlib.utils.common.ExternalCacheUtil;
import com.enzo.commonlib.utils.crashlib.CrashManager;
import com.enzo.flkit.FLPluginFactory;
import com.enzo.main.plugin.SAFactoryManager;
import com.enzo.module_a.plugin.MAPluginFactory;
import com.enzo.module_b.plugin.MBPluginFactory;
import com.enzo.module_c.plugin.MCPluginFactory;
import com.enzo.module_d.plugin.MDPluginFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * ***********************************************
 * **                  _oo0oo_                  **
 * **                 o8888888o                 **
 * **                 88" . "88                 **
 * **                 (| -_- |)                 **
 * **                 0\  =  /0                 **
 * **               ___/'---'\___               **
 * **            .' \\\|     |// '.             **
 * **           / \\\|||  :  |||// \\           **
 * **          / _ ||||| -:- |||||- \\          **
 * **          | |  \\\\  -  /// |   |          **
 * **          | \_|  ''\---/''  |_/ |          **
 * **          \  .-\__  '-'  __/-.  /          **
 * **        ___'. .'  /--.--\  '. .'___        **
 * **     ."" '<  '.___\_<|>_/___.' >'  "".     **
 * **    | | : '-  \'.;'\ _ /';.'/ - ' : | |    **
 * **    \  \ '_.   \_ __\ /__ _/   .-' /  /    **
 * **====='-.____'.___ \_____/___.-'____.-'=====**
 * **                  '=---='                  **
 * ***********************************************
 * **              佛祖保佑  镇类之宝           **
 * ***********************************************
 * <p>
 * 文 件 名: App
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initLog();
        initEnv();
        initFactory();
    }

    private void initLog() {
        ALog.Config config = ALog.init(this)
                .setLogSwitch(true)// 设置log总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(BuildConfig.DEBUG)// 设置是否输出到控制台开关，默认开
                .setGlobalTag("xfy")// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLogHeadSwitch(true)// 设置log头信息开关，默认为开
                .setLog2FileSwitch(true)// 打印log时是否存到文件的开关，默认关
                .setDir(ExternalCacheUtil.getNetLogCachePath(this))// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("alog")// 当文件前缀为空时，默认为"alog"，即写入文件为"alog-MM-dd.txt"
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setSingleTagSwitch(true)// 一条日志仅输出一条，默认开，为美化 AS 3.1.0 的 Logcat
                .setConsoleFilter(ALog.V)// log的控制台过滤器，和logcat过滤器同理，默认Verbose
                .setFileFilter(ALog.V)// log文件过滤器，和logcat过滤器同理，默认Verbose
                .setStackDeep(1)// log 栈深度，默认为 1
                .setStackOffset(0)// 设置栈偏移，比如二次封装的话就需要设置，默认为 0
                .setSaveDays(3)// 设置日志可保留天数，默认为 -1 表示无限时长
                // 新增 ArrayList 格式化器，默认已支持 Array, Throwable, Bundle, Intent 的格式化输出
                .addFormatter(new ALog.IFormatter<ArrayList>() {
                    @Override
                    public String format(ArrayList list) {
                        return "ALog Formatter ArrayList { " + list.toString() + " }";
                    }
                });
        ALog.d(config.toString());
    }

    private void initEnv() {
        EnvConstants.getInstance().init(BuildConfig.PROD_ENV, BuildConfig.LOG_OPEN, "");

        CrashManager.getInstance().init(this, BuildConfig.DEBUG);
    }

    private void initFactory() {
        List<FLPluginFactory> factoryList = new ArrayList<>();
        factoryList.add(MAPluginFactory.getInstance());
        factoryList.add(MBPluginFactory.getInstance());
        factoryList.add(MCPluginFactory.getInstance());
        factoryList.add(MDPluginFactory.getInstance());
        SAFactoryManager.getInstance().init(factoryList);
    }
}
