package com.enzo.commonlib.utils.taskqueue.task;

import android.os.Handler;

import com.enzo.commonlib.utils.common.LogUtil;

/**
 * 文 件 名: Task1
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/12
 * 邮   箱: xiaofywork@163.com
 */
public class Task2 extends BaseTask {

    @Override
    public void doTask() {
        super.doTask();
        LogUtil.d("222222222222222222222222");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                unLockBlock();
            }
        },4000);
    }
}
