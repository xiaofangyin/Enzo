package com.enzo.commonlib.utils.common;

import android.app.Activity;

import java.util.Stack;

/**
 * 文 件 名: ActivityHelper
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/11
 * 邮   箱: xiaofangyinwork@163.com
 */
public class ActivityHelper {

    private static Stack<Activity> activityStack;
    private static ActivityHelper instance;

    private ActivityHelper() {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
    }

    public static ActivityHelper getManager() {
        if (instance == null) {
            instance = new ActivityHelper();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    private void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            System.exit(0);
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        }
    }
}