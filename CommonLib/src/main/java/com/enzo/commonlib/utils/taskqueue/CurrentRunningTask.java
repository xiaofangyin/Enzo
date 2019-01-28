package com.enzo.commonlib.utils.taskqueue;

/**
 * 文 件 名: CurrentRunningTask
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/28
 * 邮   箱: xiaofangyin@360.cn
 */
public class CurrentRunningTask {
    private static ITask sCurrentShowingTask;

    public static void setCurrentShowingTask(ITask task) {
        sCurrentShowingTask = task;
    }

    public static void removeCurrentShowingTask() {
        sCurrentShowingTask = null;
    }

    public static ITask getCurrentShowingTask() {
        return sCurrentShowingTask;
    }

    public static boolean getCurrentShowingStatus() {
        return sCurrentShowingTask != null && sCurrentShowingTask.getStatus();
    }
}
