package com.enzo.module_d.ui.activity.lighter;

import android.app.Activity;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.enzo.module_d.R;

import java.util.ArrayList;
import java.util.List;

public class MDLighterHelper {

    public final static int[] PICTURES = new int[]{
            R.mipmap.lighter_pic_1,
            R.mipmap.lighter_pic_2,
            R.mipmap.lighter_pic_3,
            R.mipmap.lighter_pic_4,
            R.mipmap.lighter_pic_5,
            R.mipmap.lighter_pic_6,
            R.mipmap.lighter_pic_7,
    };

    public final static List<Integer> sPictureList = new ArrayList<>();
    static {
        for (int i = 0; i < 20; i++){
            sPictureList.add(MDLighterHelper.PICTURES[i % MDLighterHelper.PICTURES.length]);
        }
    }

    private MDLighterHelper(){}

    public static Paint getDashPaint(){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint.setPathEffect(new DashPathEffect(new float[]{20, 20}, 0));
        paint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
        return paint;
    }

    public static Paint getDiscretePaint(){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint.setPathEffect(new DiscretePathEffect(10, 10));
        paint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
        return paint;
    }

    public static View createCommonTipView(Activity activity, int drawableId, String string){
        View view = activity.getLayoutInflater().inflate(R.layout.md_layout_lighter_tip_common, null);
        ImageView imageView = view.findViewById(R.id.iv_image);
        imageView.setImageResource(drawableId);
        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(string);
        return view;
    }

    public static void setupToolBarBackAction(final Activity appCompatActivity, Toolbar toolbar){
        if (appCompatActivity == null
                || toolbar == null){
            return;
        }

//        appCompatActivity.setSupportActionBar(toolbar);
//        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(true);
//        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appCompatActivity.finish();//返回
            }
        });
    }

    public static Animation getScaleAnimation(){
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1f, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setInterpolator(new BounceInterpolator());
        return scaleAnimation;
    }
}
