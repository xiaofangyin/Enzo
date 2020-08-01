package com.enzo.module_d.ui.activity;

import android.os.Bundle;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.module_d.practice.datastructure.MyHashMap;
import com.enzo.module_d.practice.datastructure.QueueX;

/**
 * 文 件 名: MDStructureActivity
 * 创 建 人: xiaofy
 * 创建日期: 2019/6/24
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MDStructureActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //hashmap
        MyHashMap<String, String> map = new MyHashMap<>();
        for (int i = 0; i < 20; i++) {
            map.put("enzo" + i, "xiaofy" + i);
        }
        LogUtil.d(map.toString());

        //队列
        QueueX queueX = new QueueX(10);
        for (int i = 0; i < 20; i++) {
            queueX.insert(i);
        }
        for (int i = 0; i < 20; i++) {
            Object data = queueX.remove();
            LogUtil.d("data: " + data);
        }
    }

    @Override
    public void initListener() {

    }
}
