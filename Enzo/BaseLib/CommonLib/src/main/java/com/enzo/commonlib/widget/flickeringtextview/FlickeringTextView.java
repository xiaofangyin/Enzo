package com.enzo.commonlib.widget.flickeringtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.enzo.commonlib.R;

/**
 * 闪烁的TextView
 * Created by Wood on 2016/7/5.
 */
public class FlickeringTextView extends AppCompatTextView {

    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private int mTranslate;

    private int textColor = 0xFFFFFFFF;//文字颜色
    private int flickeringColor = 0xFF000000;//闪烁颜色

    private static final int[] ATTRS = new int[]{
            android.R.attr.textColor,
    };

    public FlickeringTextView(Context context) {
        this(context, null);
    }

    public FlickeringTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlickeringTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //先获取系统属性，文字颜色
        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        textColor = a.getColor(0, textColor);
        a.recycle();
        //再获取自定义属性，闪烁颜色
        a = context.obtainStyledAttributes(attrs, R.styleable.FlickeringTextView);
        flickeringColor = a.getColor(R.styleable.FlickeringTextView_flickeringColor, flickeringColor);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mGradientMatrix != null) {
            mTranslate += getWidth() / 5;
            if (mTranslate > getWidth() * 2) {
                mTranslate = -getWidth();
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        TextPaint mPaint = getPaint();
        mLinearGradient = new LinearGradient(
                0,//渐变起初点坐标x位置
                0,//渐变起初点坐标y位置
                w,//渐变终点
                0,//渐变终点
                new int[]{textColor, flickeringColor, textColor},//参与渐变效果的颜色集合
                null,//每个颜色处于的渐变相对位置
                Shader.TileMode.CLAMP//平铺方式
        );
        mPaint.setShader(mLinearGradient);
        mGradientMatrix = new Matrix();
    }
}
