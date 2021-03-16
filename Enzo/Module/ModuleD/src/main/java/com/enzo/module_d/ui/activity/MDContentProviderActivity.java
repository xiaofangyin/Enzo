package com.enzo.module_d.ui.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;
import com.enzo.module_d.model.db.DbOpenHelper;

public class MDContentProviderActivity extends BaseActivity {

    private EditText edtBoyName;
    private EditText edtBoyAge;
    private EditText edtGirlName;
    private EditText edtGirlAge;

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
        edtGirlName = findViewById(R.id.edt_girl_name);
        edtGirlAge = findViewById(R.id.edt_girl_age);
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

        findViewById(R.id.btn_add_girl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtGirlName.getText().toString())) {
                    SQLiteDatabase sqLiteDatabase = new DbOpenHelper(MDContentProviderActivity.this).getWritableDatabase();
                    sqLiteDatabase.beginTransaction();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", edtGirlName.getText().toString());
                    contentValues.put("age", Integer.parseInt(edtGirlAge.getText().toString()));
                    sqLiteDatabase.insert(DbOpenHelper.GIRL_TABLE_NAME, null, contentValues);
                    sqLiteDatabase.setTransactionSuccessful();
                    sqLiteDatabase.endTransaction();
                }
            }
        });
    }
}
