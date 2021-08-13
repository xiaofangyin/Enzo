package com.enzo.module_d.model.task;

import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.taskqueue.task.BaseTask;

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
    }
}
