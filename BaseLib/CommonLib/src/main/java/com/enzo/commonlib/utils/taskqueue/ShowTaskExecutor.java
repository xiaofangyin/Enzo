package com.enzo.commonlib.utils.taskqueue;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.TimeUnit;

/**
 * 文 件 名: ShowTaskExecutor
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/28
 * 邮   箱: xiaofangyin@360.cn
 */
public class ShowTaskExecutor {
    private final String TAG = "ShowTaskExecutor";
    private BlockTaskQueue taskQueue;
    private TaskHandler mTaskHandler;
    private boolean isRunning = true;
    private static final int MSG_EVENT_DO = 0;
    private static final int MSG_EVENT_FINISH = 1;

    public ShowTaskExecutor(BlockTaskQueue taskQueue) {
        this.taskQueue = taskQueue;
        mTaskHandler = new TaskHandler();
    }

    //开始遍历任务队列
    public void start() {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isRunning) { //死循环
                        ITask iTask;
                        iTask = taskQueue.take(); //取任务
                        if (iTask != null) {
                            //执行任务
                            TaskEvent doEvent = new TaskEvent();
                            doEvent.setTask(iTask);
                            doEvent.setEventType(TaskEvent.EventType.DO);
                            mTaskHandler.obtainMessage(MSG_EVENT_DO, doEvent).sendToTarget();
                            //一直阻塞，直到任务执行完
                            if (iTask.getDuration() != 0) {
                                TimeUnit.MICROSECONDS.sleep(iTask.getDuration());
                            } else {
                                iTask.blockTask();
                            }
                            //完成任务
                            TaskEvent finishEvent = new TaskEvent();
                            finishEvent.setTask(iTask);
                            finishEvent.setEventType(TaskEvent.EventType.FINISH);
                            mTaskHandler.obtainMessage(MSG_EVENT_FINISH, finishEvent).sendToTarget();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    //根据不同的消息回调不同的方法。
    private static class TaskHandler extends Handler {
        TaskHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TaskEvent taskEvent = (TaskEvent) msg.obj;
            if (msg.what == MSG_EVENT_DO && taskEvent.getEventType() == TaskEvent.EventType.DO) {
                taskEvent.getTask().doTask();
            }
            if (msg.what == MSG_EVENT_FINISH && taskEvent.getEventType() == TaskEvent.EventType.FINISH) {
                taskEvent.getTask().finishTask();
            }
        }
    }

    public void startRunning() {
        isRunning = true;
    }

    public void pauseRunning() {
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void resetExecutor() {
        isRunning = true;
        taskQueue.clear();
    }

    public void clearExecutor() {
        pauseRunning();
        taskQueue.clear();
    }
}
