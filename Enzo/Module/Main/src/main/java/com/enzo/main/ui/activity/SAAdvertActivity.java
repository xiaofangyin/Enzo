package com.enzo.main.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.imageloader.ImageLoader;
import com.enzo.commonlib.utils.statusbar.stateappbar.bar.StateAppBar;
import com.enzo.main.R;

public class SAAdvertActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.main_activity_advert;
    }

    @Override
    public void initView() {
        StateAppBar.translucentStatusBar(this, true);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ImageView ivAd = findViewById(R.id.iv_advert);
        new ImageLoader.Builder(this)
                .load(R.mipmap.icon_girl)
                .build()
                .into(ivAd);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), SAMainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    @Override
    public void initListener() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK;
    }
}
