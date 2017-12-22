package com.ifenglian.commonlib.widget.view.fallview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class FallingView extends View {

    private static final int intervalTime = 12;//重绘间隔时间
    private List<FallObject> fallObjects;
    private FallObject.Builder mBuilder;
    private int mNum;

    public FallingView(Context context) {
        this(context, null);
    }

    public FallingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        fallObjects = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBuilder.setParentSize(w, h);
        for (int i = 0; i < mNum; i++) {
            fallObjects.add(mBuilder.build());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (fallObjects.size() > 0) {
            for (int i = 0; i < fallObjects.size(); i++) {
                //然后进行绘制
                fallObjects.get(i).drawObject(canvas);
            }
            // 隔一段时间重绘一次, 动画效果
            postInvalidateDelayed(intervalTime);
        }
    }

    /**
     * 向View添加下落物体对象
     *
     * @param fallBuilder 下落物体对象
     * @param num         数量
     */
    public void addFallObject(final FallObject.Builder fallBuilder, final int num) {
        mBuilder = fallBuilder;
        mNum = num;
    }
}
