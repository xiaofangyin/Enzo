package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.commonlib.widget.scrollingimageview.ScrollingImageView;
import com.enzo.commonlib.widget.waveview.WaveView;
import com.enzo.module_d.R;

/**
 * 文 件 名: MDScrollingImageViewActivity
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/2
 * 邮   箱: xiaofywork@163.com
 */
public class MDScrollingImageViewActivity extends BaseActivity {

    private ScrollingImageView scrollingImageView1;
    private ScrollingImageView scrollingImageView2;
    private ScrollingImageView scrollingImageView3;
    private ScrollingImageView scrollingImageView4;
    private ScrollingImageView scrollingImageView5;
    private ScrollingImageView scrollingImageView6;
    private ScrollingImageView scrollingImageView7;
    private ScrollingImageView scrollingImageView8;
    private ScrollingImageView scrollingImageView9;

    @Override
    public void onResume() {
        super.onResume();
        scrollingImageView1.start();
        scrollingImageView2.start();
        scrollingImageView3.start();
        scrollingImageView4.start();
        scrollingImageView5.start();
        scrollingImageView6.start();
        scrollingImageView7.start();
        scrollingImageView8.start();
        scrollingImageView9.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        scrollingImageView1.stop();
        scrollingImageView2.stop();
        scrollingImageView3.stop();
        scrollingImageView4.stop();
        scrollingImageView5.stop();
        scrollingImageView6.stop();
        scrollingImageView7.stop();
        scrollingImageView8.stop();
        scrollingImageView9.stop();
    }

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_scrolling_image_view;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("滚动动画");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        scrollingImageView1 = findViewById(R.id.scroll_image_view_1);
        scrollingImageView2 = findViewById(R.id.scroll_image_view_2);
        scrollingImageView3 = findViewById(R.id.scroll_image_view_3);
        scrollingImageView4 = findViewById(R.id.scroll_image_view_4);
        scrollingImageView5 = findViewById(R.id.scroll_image_view_5);
        scrollingImageView6 = findViewById(R.id.scroll_image_view_6);
        scrollingImageView7 = findViewById(R.id.scroll_image_view_7);
        scrollingImageView8 = findViewById(R.id.scroll_image_view_8);
        scrollingImageView9 = findViewById(R.id.scroll_image_view_9);
        final ImageView ivAvatar = findViewById(R.id.iv_avatar);
        WaveView waveView = findViewById(R.id.wave_view);
        final FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) ivAvatar.getLayoutParams();
        waveView.setOnWaveAnimationListener(new WaveView.OnWaveAnimationListener() {
            @Override
            public void OnWaveAnimation(float y) {
                lp.setMargins(0, 0, 0, (int) y + 2);
                ivAvatar.setLayoutParams(lp);
            }


        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }
}