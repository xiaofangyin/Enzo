package com.ifenglian.module_d.activity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.model.LottieComposition;
import com.ifenglian.module_d.R;

/**
 * @author zkk
 *         简书:    http://www.jianshu.com/u/61f41588151d
 *         github: https://github.com/panacena
 */
public class MDSimpleActivity extends AppCompatActivity {

    private LottieAnimationView animation_view_click;
    private TextView tv_seek;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // md_activity_simple.xml中 lottie_fileName="data_123.json"
        // 所以只需要在 app/src/main/assets 中添加AE 生成的 json文件，重命名为data.json就可以显示动画
        setContentView(R.layout.md_activity_simple);

        animation_view_click = findViewById(R.id.animation_view_click);
        tv_seek = findViewById(R.id.md_seed);
        LottieComposition.fromAssetFileName(this, "data_123.json", new LottieComposition.OnCompositionLoadedListener() {
            @Override
            public void onCompositionLoaded(LottieComposition composition) {
                animation_view_click.setComposition(composition);
                animation_view_click.loop(true);
                animation_view_click.playAnimation();
            }
        });

        animation_view_click.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tv_seek.setText(" 动画进度" + (int) (animation.getAnimatedFraction() * 100) + "%");
            }
        });

        findViewById(R.id.md_btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnima();
            }
        });

        findViewById(R.id.md_btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAnima();
            }
        });
    }

    /**
     * 开始动画
     */
    private void startAnima() {
        boolean inPlaying = animation_view_click.isAnimating();
        if (!inPlaying) {
            animation_view_click.setProgress(0f);
            animation_view_click.playAnimation();
        }
    }

    /**
     * 停止动画
     */
    private void stopAnima() {
        boolean inPlaying = animation_view_click.isAnimating();
        if (inPlaying) {
            animation_view_click.cancelAnimation();
        }
    }

}
