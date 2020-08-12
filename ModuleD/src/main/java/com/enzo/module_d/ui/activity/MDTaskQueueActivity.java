package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.taskqueue.TaskPriority;
import com.enzo.commonlib.utils.taskqueue.TaskScheduler;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;
import com.enzo.module_d.model.task.Task1;
import com.enzo.module_d.model.task.Task2;
import com.enzo.module_d.model.task.Task3;

/**
 * 文 件 名: MDTaskQueueActivity
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/12
 * 邮   箱: xiaofywork@163.com
 */
public class MDTaskQueueActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_task_queue;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("任务队列");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_add_task_low).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task1 task1 = new Task1();
                task1.setPriority(TaskPriority.LOW);
                task1.setDuration(5000);
                TaskScheduler.getInstance().enqueue(task1);
            }
        });
        findViewById(R.id.btn_add_task_normal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task2 task2 = new Task2();
                task2.setPriority(TaskPriority.DEFAULT);
                task2.setDuration(3000);
                TaskScheduler.getInstance().enqueue(task2);
            }
        });
        findViewById(R.id.btn_add_task_high).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task3 task3 = new Task3();
                task3.setPriority(TaskPriority.HIGH);
                TaskScheduler.getInstance().enqueue(task3);
            }
        });
        findViewById(R.id.btn_clear_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskScheduler.getInstance().clearExecutor();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TaskScheduler.getInstance().clearExecutor();
    }
}
