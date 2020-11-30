package com.enzo.commonlib.env;

/**
 * 文 件 名: EnvConstants
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/31
 * 邮   箱: xiaofangyinwork@163.com
 */
public class EnvConstants {

    private static EnvConstants mInstance;
    //网络环境是否为正式环境
    private boolean PROD_ENV;
    //是否开启log
    private boolean LOG_OPEN;

    private EnvConstants() {

    }

    public static EnvConstants getInstance() {
        if (mInstance == null) {
            synchronized (EnvConstants.class) {
                if (mInstance == null) {
                    mInstance = new EnvConstants();
                }
            }
        }
        return mInstance;
    }

    public void init(boolean prodEnv, boolean logOpen) {
        this.PROD_ENV = prodEnv;
        this.LOG_OPEN = logOpen;
    }

    public boolean isLogOpen() {
        return LOG_OPEN;
    }

    public boolean isProdEnv() {
        return PROD_ENV;
    }
}
