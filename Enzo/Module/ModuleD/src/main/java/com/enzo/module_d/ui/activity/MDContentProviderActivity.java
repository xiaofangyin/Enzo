package com.enzo.module_d.ui.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;
import com.enzo.module_d.model.db.DbOpenHelper;

public class MDContentProviderActivity extends BaseActivity {

    private EditText edtBoyName;
    private EditText edtBoyAge;
    private TextView tvResult;

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("ContentProvider");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_content_provider;
    }

    @Override
    public void initView() {
        edtBoyName = findViewById(R.id.edt_boy_name);
        edtBoyAge = findViewById(R.id.edt_boy_age);
        tvResult = findViewById(R.id.tv_result);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_add_boy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtBoyName.getText().toString())) {
                    SQLiteDatabase sqLiteDatabase = new DbOpenHelper(MDContentProviderActivity.this).getWritableDatabase();
                    sqLiteDatabase.beginTransaction();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", edtBoyName.getText().toString());
                    contentValues.put("age", Integer.parseInt(edtBoyAge.getText().toString()));
                    sqLiteDatabase.insert(DbOpenHelper.BOY_TABLE_NAME, null, contentValues);
                    sqLiteDatabase.setTransactionSuccessful();
                    sqLiteDatabase.endTransaction();
                }
            }
        });
        findViewById(R.id.btn_all_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sqLiteDatabase = new DbOpenHelper(MDContentProviderActivity.this).getWritableDatabase();
                sqLiteDatabase.beginTransaction();
                Cursor boyCursor = sqLiteDatabase.query(DbOpenHelper.BOY_TABLE_NAME, new String[]{"_id", "name", "age"},
                        null, null, null, null, null);
                if (boyCursor != null) {
                    StringBuilder builder = new StringBuilder();
                    while (boyCursor.moveToNext()) {
                        String result = "ID:" + boyCursor.getInt(boyCursor.getColumnIndex("_id"))
                                + "  name:" + boyCursor.getString(boyCursor.getColumnIndex("name"))
                                + "  age:" + boyCursor.getString(boyCursor.getColumnIndex("age"));
                        Log.e("xfy", result);
                        builder.append(result)
                                .append("\r\n");
                    }
                    tvResult.setText(builder.toString());
                    boyCursor.close();
                }
                sqLiteDatabase.setTransactionSuccessful();
                sqLiteDatabase.endTransaction();
            }
        });
    }
}
