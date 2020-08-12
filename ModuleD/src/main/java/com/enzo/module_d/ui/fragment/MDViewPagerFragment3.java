package com.enzo.module_d.ui.fragment;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.widget.scrollingimageview.ScrollingImageView;
import com.enzo.commonlib.widget.waveview.WaveView;
import com.enzo.module_d.R;

/**
 * 文 件 名: MDViewPagerFragment3
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MDViewPagerFragment3 extends BaseFragment {

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
    public int getLayoutId() {
        return R.layout.md_fragment_d3;
    }

    @Override
    public void initView(View rootView) {
        scrollingImageView1 = rootView.findViewById(R.id.scroll_image_view_1);
        scrollingImageView2 = rootView.findViewById(R.id.scroll_image_view_2);
        scrollingImageView3 = rootView.findViewById(R.id.scroll_image_view_3);
        scrollingImageView4 = rootView.findViewById(R.id.scroll_image_view_4);
        scrollingImageView5 = rootView.findViewById(R.id.scroll_image_view_5);
        scrollingImageView6 = rootView.findViewById(R.id.scroll_image_view_6);
        scrollingImageView7 = rootView.findViewById(R.id.scroll_image_view_7);
        scrollingImageView8 = rootView.findViewById(R.id.scroll_image_view_8);
        scrollingImageView9 = rootView.findViewById(R.id.scroll_image_view_9);
        final ImageView ivAvatar = rootView.findViewById(R.id.iv_avatar);
        WaveView waveView = rootView.findViewById(R.id.wave_view);
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
    public void initListener(View rootView) {

    }

    @Override
    public void lazyLoad() {

    }

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
}
