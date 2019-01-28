package com.enzo.commonlib.utils.taskqueue;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 文 件 名: BaseTask
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/28
 * 邮   箱: xiaofangyin@360.cn
 */
public class BaseTask implements ITask {
    private final String TAG = getClass().getSimpleName();
    private TaskPriority mTaskPriority = TaskPriority.DEFAULT; //默认优先级
    private int mSequence;// 入队次序
    private Boolean mTaskStatus = false; // 标志任务状态，是否仍在展示
    protected WeakReference<BlockTaskQueue> taskQueue;//阻塞队列
    protected int duration = 0; //任务执行时间
    //此队列用来实现任务时间不确定的队列阻塞功能
    private PriorityBlockingQueue<Integer> blockQueue;

    //构造函数
    public BaseTask() {
        taskQueue = new WeakReference<>(BlockTaskQueue.getInstance());
        blockQueue = new PriorityBlockingQueue<>();
    }

    //入队实现
    @Override
    public void enqueue() {
        TaskScheduler.getInstance().enqueue(this);
    }

    //执行任务方法，此时标记为设为true，并且将当前任务记录下来
    @Override
    public void doTask() {
        mTaskStatus = true;
        CurrentRunningTask.setCurrentShowingTask(this);
    }

    //任务执行完成，改变标记位，将任务在队列中移除，并且把记录清除
    @Override
    public void finishTask() {
        this.mTaskStatus = false;
        this.taskQueue.get().remove(this);
        CurrentRunningTask.removeCurrentShowingTask();
        Log.d(TAG, taskQueue.get().size() + "");
    }

    //设置任务优先级实现
    @Override
    public ITask setPriority(TaskPriority mTaskPriority) {
        this.mTaskPriority = mTaskPriority;
        return this;
    }

    //设置任务执行时间
    public ITask setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    //获取任务优先级
    @Override
    public TaskPriority getPriority() {
        return mTaskPriority;
    }

    //获取任务执行时间
    @Override
    public int getDuration() {
        return duration;
    }

    //设置任务次序
    @Override
    public void setSequence(int mSequence) {
        this.mSequence = mSequence;
    }

    //获取任务次序
    @Override
    public int getSequence() {
        return mSequence;
    }

    //获取任务状态
    @Override
    public boolean getStatus() {
        return mTaskStatus;
    }

    //阻塞任务执行
    @Override
    public void blockTask() throws Exception {
        blockQueue.take(); //如果队列里面没数据，就会一直阻塞
    }

    //解除阻塞
    @Override
    public void unLockBlock() {
        blockQueue.add(1); //往里面随便添加一个数据，阻塞就会解除
    }

    /**
     * 排队实现
     * 优先级的标准如下：
     * TaskPriority.LOW < TaskPriority.DEFAULT < TaskPriority.HIGH
     * 当优先级相同 按照插入次序排队
     */
    @Override
    public int compareTo(ITask another) {
        final TaskPriority me = this.getPriority();
        final TaskPriority it = another.getPriority();
        return me == it ? this.getSequence() - another.getSequence() :
                it.ordinal() - me.ordinal();
    }

    //输出一些信息
    @Override
    public String toString() {
        return "task name : " + getClass().getSimpleName() + " sequence : " + mSequence + " TaskPriority : " + mTaskPriority;
    }
}
