package com.enzo.module_d.model.task;

import android.os.Handler;

import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.taskqueue.task.BaseTask;

/**
 * 文 件 名: Task1
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/12
 * 邮   箱: xiaofywork@163.com
 */
public class Task4 extends BaseTask {

    @Override
    public void doTask() {
        super.doTask();
        LogUtil.d("444444444444444444444444");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                unLockBlock();
            }
        }, 4000);
    }
}
