package com.enzo.commonlib.widget.snowview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: SnowView
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SnowView extends View {

    private static final int intervalTime = 8;//重绘间隔时间
    private List<SnowModel> fallObjects;
    private int mNum;
    private Bitmap mBitmap;

    public SnowView(Context context) {
        this(context, null);
    }

    public SnowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        fallObjects = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        SnowModel.Builder builder = new SnowModel.Builder(mBitmap);
        builder.setSpeed(8, true)
                .setParentSize(w, h)
                .randomSize(true);
        for (int i = 0; i < mNum; i++) {
            fallObjects.add(builder.build());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (fallObjects.size() > 0) {
            for (int i = 0; i < fallObjects.size(); i++) {
                //然后进行绘制
                fallObjects.get(i).drawSnow(canvas);
            }
            // 隔一段时间重绘一次, 动画效果
            postInvalidateDelayed(intervalTime);
        }
    }

    /**
     * 向View添加下落物体对象
     *
     * @param bitmap 下落物体对象
     * @param num    数量
     */
    public void initSnow(Bitmap bitmap, int num) {
        mBitmap = bitmap;
        mNum = num;
    }
}
