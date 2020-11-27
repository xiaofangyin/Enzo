package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.bolts.Continuation;
import com.enzo.commonlib.utils.bolts.Task;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * http://www.mamicode.com/info-detail-1334607.html
 */
public class MDBoltsActivity extends BaseActivity {

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("Bolts");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_bolts;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {
        //运行一个单个任务
        findViewById(R.id.btn_single_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task.call(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        int result = 3 + 5;
                        LogUtil.e("btn_single_task thread name: " + Thread.currentThread().getName());
                        return result;
                    }
                }, Task.BACKGROUND_EXECUTOR);
            }
        });

        // 每一个Task都有一个方法叫做continueWith并且它带有一个参数Continuation。Continuation是一个你需要实现的接口，
        // 你需要实现它的方法then。Then方法会在Task完成后被调用，你可以通过这个方法来检查Task是否成功以及获得它的结果。
        findViewById(R.id.btn_continueWith).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task.call(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        LogUtil.e("continue with..." + Thread.currentThread().getName());
                        return 3 + 7;
                    }
                }, Task.BACKGROUND_EXECUTOR)
                        .continueWith(new Continuation<Integer, Void>() {
                            @Override
                            public Void then(Task<Integer> task) throws Exception {
                                if (task.isFaulted()) {
                                    LogUtil.e("continue with then faulted..." + Thread.currentThread().getName());
                                } else if (task.isCompleted()) {
                                    LogUtil.e("continue with then completed..." + Thread.currentThread().getName());
                                }
                                return null;
                            }
                        }, Task.UI_THREAD_EXECUTOR);
            }
        });

        //如果我们只需要关心上一个任务执行成功才要进行处理，那么我们可以用.onSuccess方法来代替.continueWith方法
        findViewById(R.id.btn_onSuccess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task.call(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        LogUtil.e("on success..." + Thread.currentThread().getName());
                        return 3 + 7;
                    }
                }, Task.BACKGROUND_EXECUTOR)
                        .onSuccess(new Continuation<Integer, Void>() {
                            @Override
                            public Void then(Task<Integer> task) throws Exception {
                                LogUtil.e("on success then..." + Thread.currentThread().getName());
                                return null;
                            }
                        });
            }
        });

        //在知道如何使用上面的两种方法之后，我们就可以运用它们来执行一个顺序任务了
        findViewById(R.id.btn_sync_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task.call(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        LogUtil.e("sync task call..." + Thread.currentThread().getName());
                        return 6 + 8;
                    }
                }, Task.BACKGROUND_EXECUTOR)
                        .onSuccess(new Continuation<Integer, Void>() {
                            @Override
                            public Void then(Task<Integer> task) throws Exception {
                                LogUtil.e("sync task on success then..." + Thread.currentThread().getName());
                                return null;
                            }
                        }, Task.BACKGROUND_EXECUTOR)
                        .continueWith(new Continuation<Void, String>() {
                            @Override
                            public String then(Task<Void> task) throws Exception {
                                LogUtil.e("sync task continue with then..." + Thread.currentThread().getName());
                                return null;
                            }
                        }, Task.UI_THREAD_EXECUTOR);
            }
        });

        //任务1创建了任务2和任务3用于并列执行，当两个任务都执行结束的时候会调用任务4进行结果处理。
        findViewById(R.id.btn_parallel_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task.call(new Callable<List<Task<Integer>>>() {
                    @Override
                    public List<Task<Integer>> call() throws Exception {
                        List<Task<Integer>> tasks = new ArrayList<Task<Integer>>();
                        Task<Integer> task1 = Task.call(new Callable<Integer>() {
                            @Override
                            public Integer call() throws Exception {
                                // 任务2
                                LogUtil.e("parallel task 2: " + Thread.currentThread().getName());
                                return 2;
                            }
                        }, Task.BACKGROUND_EXECUTOR);

                        Task<Integer> task2 = Task.call(new Callable<Integer>() {
                            @Override
                            public Integer call() throws Exception {
                                // 任务3
                                LogUtil.e("parallel task 3: " + Thread.currentThread().getName());
                                return 3;
                            }
                        }, Task.BACKGROUND_EXECUTOR);
                        tasks.add(task1);
                        tasks.add(task2);
                        Task.whenAll(tasks).waitForCompletion();
                        return tasks;    //这里返回的
                    }
                }, Task.BACKGROUND_EXECUTOR)//后台线程运行
                        .continueWith(new Continuation<List<Task<Integer>>, Void>() {
                            @Override
                            public Void then(Task<List<Task<Integer>>> task) throws Exception {
                                // 任务4
                                if (task.isFaulted()) {
                                    //进行错误处理
                                    Exception exception = task.getError();
                                } else if (task.isCompleted()) {
                                    //取出两个并列执行的Task，获取执行结束的返回值
                                    for (Task<Integer> t : task.getResult()) {
                                        if (t.isCompleted()) {
                                            //task执行成功
                                            LogUtil.e("continue result: " + t.getResult());
                                        }
                                    }
                                }
                                return null;
                            }
                        }, Task.UI_THREAD_EXECUTOR);//主线程运行
            }
        });

        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        //task执行continueWith之前还可以进行延时，只需要在调用continueWith之前调用.delay(time)即可
        findViewById(R.id.btn_delay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task.call(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        LogUtil.e("delay..." + Thread.currentThread().getName());
                        int a = new Random().nextInt(10);
                        int b = new Random().nextInt(10);
                        Thread.sleep(2000);
                        if (a + b > 10) {
                            throw new RuntimeException("error: a + b = " + (a + b));
                        } else {
                            return a + b;
                        }
                    }
                }, executorService)
                        .continueWith(new Continuation<Integer, Void>() {
                            @Override
                            public Void then(Task<Integer> task) throws Exception {
                                if (task.isFaulted()) {
                                    LogUtil.e("delay then faulted..." + task.getError().getMessage());
                                } else if (task.isCompleted()) {
                                    LogUtil.e("delay then completed..." + Thread.currentThread().getName() + "...result: " + task.getResult());
                                }
                                return null;
                            }
                        }, Task.UI_THREAD_EXECUTOR);
            }
        });
    }
}
