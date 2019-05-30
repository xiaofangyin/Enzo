package com.enzo.commonlib.widget.barrage.model;

public interface DataSource {
    // 返回当前的类型
    int getType();

    // 返回生成的时间
    long getShowTime();
}
