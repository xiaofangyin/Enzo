package com.enzo.module_d.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.enzo.commonlib.utils.common.LogUtil;

public class DbOpenHelper extends SQLiteOpenHelper {

    //数据库名称
    private static final String DATA_BASE_NAME = "people.db";

    //数据库版本号
    private static final int DATE_BASE_VERSION = 2;

    //表名-男孩
    public static final String BOY_TABLE_NAME = "boy";

    //表名-女孩
    public static final String GIRL_TABLE_NAME = "girl";

    //创建表-男孩（两列：主键自增长、姓名）
    private static final String CREATE_BOY_TABLE = "create table " + BOY_TABLE_NAME + "(_id integer primary key autoincrement, name text,age integer)";

    public DbOpenHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATE_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtil.d("DbOpenHelper onCreate...");
        db.execSQL(CREATE_BOY_TABLE);//创建男孩表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.d("DbOpenHelper onUpgrade...oldVersion: " + oldVersion + "...newVersion: " + newVersion);
        if (oldVersion == 1) {
            // 新增一个字段
            db.execSQL("ALTER TABLE " + BOY_TABLE_NAME + " ADD COLUMN age integer");
        }
    }
}