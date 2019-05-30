package com.enzo.commonlib.widget.barrage.ui;

import android.view.View;

public interface IBarrageView {
    // 添加视图
    void addBarrageItem(View view);

    // 获取是否存在缓存
    View getCacheView(int type);

    // 发送View间隔 默认为0
    long getInterval();
}
