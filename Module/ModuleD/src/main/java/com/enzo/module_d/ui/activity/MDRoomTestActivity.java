package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.room.Room;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.module_d.R;
import com.enzo.module_d.model.room.database.AppDatabase;
import com.enzo.module_d.model.room.entity.Anime;

import java.util.List;

public class MDRoomTestActivity extends BaseActivity {

    private AppDatabase appDB;

    private EditText edtName;
    private EditText edtType;
    private EditText edtDate;
    private TextView tvResult;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_room_test;
    }

    @Override
    public void initView() {
        edtName = findViewById(R.id.edt_anim_name);
        edtType = findViewById(R.id.edt_anim_type);
        edtDate = findViewById(R.id.edt_anim_play_date);
        tvResult = findViewById(R.id.tv_result);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        appDB = Room.databaseBuilder(this, AppDatabase.class, "anime_info")
                .addMigrations()
                .allowMainThreadQueries()
                .build();
    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_add_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Anime anim = new Anime();
                anim.setName(edtName.getText().toString());
                anim.setType(edtType.getText().toString());
                anim.setPlayDate(edtDate.getText().toString());
                appDB.getAnimDao().insertOneAnime(anim);
            }
        });
        findViewById(R.id.btn_delete_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.btn_modify_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.btn_check_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResult.setText("");
                List<Anime> list = appDB.getAnimDao().getAllAnime();
                if (list != null && !list.isEmpty()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < list.size(); i++) {
                        stringBuilder.append(list.get(i).toString());
                        stringBuilder.append("\r\n");
                    }
                    tvResult.setText(stringBuilder.toString());
                }
            }
        });
    }
}
