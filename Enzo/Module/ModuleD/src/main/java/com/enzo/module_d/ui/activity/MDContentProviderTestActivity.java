package com.enzo.module_d.ui.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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

import java.util.Random;

public class MDContentProviderTestActivity extends BaseActivity {

    public static final String URI_PATH = "content://com.enzo.module_d.MyFirstContentProvider/boy";
    private EditText edtBoyName;
    private TextView tvResult;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("ContentResolver");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_content_resolver;
    }

    @Override
    public void initView() {
        edtBoyName = findViewById(R.id.edt_boy_name);
        tvResult = findViewById(R.id.tv_result);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        sqLiteDatabase = new DbOpenHelper(MDContentProviderTestActivity.this).getWritableDatabase();
    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1.使用ContentResolver查询
                try {
                    Uri boyUri = Uri.parse(URI_PATH);
                    Cursor boyCursor;
                    if (TextUtils.isEmpty(edtBoyName.getText().toString())) {
                        boyCursor = getContentResolver().query(boyUri, new String[]{"_id", "name", "age"},
                                null, null, null);
                    } else {
                        boyCursor = getContentResolver().query(boyUri, new String[]{"_id", "name", "age"},
                                "name = ?", new String[]{edtBoyName.getText().toString()}, null);
                    }
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
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 2.使用sql语句查询
//                sqLiteDatabase.beginTransaction();
//                Cursor boyCursor = sqLiteDatabase.query(DbOpenHelper.BOY_TABLE_NAME, new String[]{"_id", "name", "age"},
//                        null, null, null, null, null);
//                if (boyCursor != null) {
//                    while (boyCursor.moveToNext()) {
//                        Log.e("xfy", "ID:" + boyCursor.getInt(boyCursor.getColumnIndex("_id"))
//                                + "  name:" + boyCursor.getString(boyCursor.getColumnIndex("name"))
//                                + "  age:" + boyCursor.getString(boyCursor.getColumnIndex("age")));
//                    }
//                    boyCursor.close();
//                }
//                sqLiteDatabase.setTransactionSuccessful();
//                sqLiteDatabase.endTransaction();
            }
        });

        findViewById(R.id.btn_add_boy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri boyUri = Uri.parse(URI_PATH);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", edtBoyName.getText().toString());
                    contentValues.put("age", new Random().nextInt(100));
                    getContentResolver().insert(boyUri, contentValues);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn_remove_boy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri boyUri = Uri.parse(URI_PATH);
                    getContentResolver().delete(boyUri, "name = ?", new String[]{edtBoyName.getText().toString()});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
