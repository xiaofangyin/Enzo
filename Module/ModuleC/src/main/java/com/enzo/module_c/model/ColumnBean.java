package com.enzo.module_c.model;

import java.io.Serializable;

/**
 * 版块数据模型
 * <p>
 * Created by zhaolei on 2017/4/20.
 */

public class ColumnBean implements Serializable {

    private String column_name;
    private int column_id;

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public int getColumn_id() {
        return column_id;
    }

    public void setColumn_id(int column_id) {
        this.column_id = column_id;
    }
}
