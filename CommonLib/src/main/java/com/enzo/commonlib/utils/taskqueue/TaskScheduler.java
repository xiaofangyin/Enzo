package com.enzo.commonlib.utils.taskqueue;

/**
 * 文 件 名: TaskScheduler
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/28
 * 邮   箱: xiaofangyin@360.cn
 */
public class TaskScheduler {
    private final String TAG = "TaskScheduler";
    private BlockTaskQueue mTaskQueue = BlockTaskQueue.getInstance();
    private ShowTaskExecutor mExecutor;

    private static class ShowDurationHolder {
        private final static TaskScheduler INSTANCE = new TaskScheduler();
    }

    private TaskScheduler() {
        initExecutor();
    }

    private void initExecutor() {
        mExecutor = new ShowTaskExecutor(mTaskQueue);
        mExecutor.start();
    }

    public static TaskScheduler getInstance() {
        return ShowDurationHolder.INSTANCE;
    }

    public void enqueue(ITask task) {
        //因为TaskScheduler这里写成单例，如果isRunning改成false的话，不判断一下，就会一直都是false
        if (!mExecutor.isRunning()) {
            mExecutor.startRunning();
        }
        //按照优先级插入队列 依次播放
        mTaskQueue.add(task);
    }

    public void resetExecutor() {
        mExecutor.resetExecutor();
    }

    public void clearExecutor() {
        mExecutor.clearExecutor();
    }
}