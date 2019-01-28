package com.enzo.commonlib.widget.togglebutton;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Checkable;

import com.enzo.commonlib.utils.common.LogUtil;

/**
 * 文 件 名: FLSwitchButton
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/26
 * 邮   箱: xiaofangyin@360.cn
 */
public class FLSwitchButton extends View implements Checkable, View.OnClickListener {

    private static final int DEFAULT_WIDTH = dp2pxInt(52);
    private static final int DEFAULT_HEIGHT = dp2pxInt(34);

    private int shadowRadius;//阴影半径
    private int shadowOffset;//阴影Y偏移px
    private float viewRadius;//背景半径
    private float buttonRadius;//按钮半径

    private float left;//背景位置
    private float top;
    private float right;
    private float bottom;
    private float centerY;

    private int background;//背景底色
    private int uncheckColor;//背景关闭颜色
    private int checkedColor;//背景打开颜色
    private int borderWidth;//边框宽度px
    private float buttonMinX;//按钮最左边
    private float buttonMaxX;//按钮最右边

    private Paint buttonPaint;//按钮画笔
    private Paint paint;//背景画笔

    /**
     * 当前状态
     */
    private ViewState viewState;
    private ViewState beforeState;
    private ViewState afterState;

    private RectF rect = new RectF();

    private boolean isChecked;//是否选中
    private boolean isUiInited = false;

    private ValueAnimator valueAnimator;
    private ArgbEvaluator argbEvaluator;
    private OnCheckedChangeListener onCheckedChangeListener;

    public FLSwitchButton(Context context) {
        this(context, null);
    }

    public FLSwitchButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FLSwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化参数
     */
    private void init() {
        argbEvaluator = new ArgbEvaluator();
        shadowRadius = dp2pxInt(2.5f);

        shadowOffset = dp2pxInt(1.5f);

        uncheckColor = 0XffDDDDDD;

        checkedColor = 0Xff34c0be;

        borderWidth = dp2pxInt(1f);

        isChecked = false;

        background = Color.WHITE;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        buttonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        buttonPaint.setColor(Color.WHITE);

        //阴影颜色
        int shadowColor = 0X33000000;
        buttonPaint.setShadowLayer(
                shadowRadius,
                0, shadowOffset,
                shadowColor);

        viewState = new ViewState();
        beforeState = new ViewState();
        afterState = new ViewState();

        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(300);
        valueAnimator.setRepeatCount(0);

        valueAnimator.addUpdateListener(animatorUpdateListener);

        super.setClickable(true);
        this.setPadding(0, 0, 0, 0);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.UNSPECIFIED
                || widthMode == MeasureSpec.AT_MOST) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(DEFAULT_WIDTH, MeasureSpec.EXACTLY);
        }
        if (heightMode == MeasureSpec.UNSPECIFIED
                || heightMode == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(DEFAULT_HEIGHT, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float viewPadding = Math.max(shadowRadius + shadowOffset, borderWidth);
        //背景高
        float height = h - viewPadding - viewPadding;

        viewRadius = height * 0.5f;
        buttonRadius = viewRadius - borderWidth;

        left = viewPadding;
        top = viewPadding;
        right = w - viewPadding;
        bottom = h - viewPadding;

        centerY = (top + bottom) * 0.5f;

        buttonMinX = left + viewRadius;
        buttonMaxX = right - viewRadius;

        if (isChecked()) {
            setCheckedViewState(viewState);
        } else {
            setUncheckViewState(viewState);
        }

        isUiInited = true;

        setOnClickListener(this);
    }

    private void setUncheckViewState(ViewState viewState) {
        viewState.radius = 0;
        viewState.checkStateColor = uncheckColor;
        viewState.buttonX = buttonMinX;
    }

    private void setCheckedViewState(ViewState viewState) {
        viewState.radius = viewRadius;
        viewState.checkStateColor = checkedColor;
        viewState.buttonX = buttonMaxX;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStrokeWidth(borderWidth);
        paint.setStyle(Paint.Style.FILL);
        //绘制白色背景
        paint.setColor(background);
        drawRoundRect(canvas,
                left, top, right, bottom,
                viewRadius, paint);
        //绘制关闭状态的边框
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(uncheckColor);
        drawRoundRect(canvas,
                left, top, right, bottom,
                viewRadius, paint);

        //绘制开启背景色
        float des = viewState.radius * 0.5f;//[0-backgroundRadius*0.5f]
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(viewState.checkStateColor);
        paint.setStrokeWidth(borderWidth + des * 2f);
        drawRoundRect(canvas,
                left + des, top + des, right - des, bottom - des,
                viewRadius, paint);

        //绘制按钮左边绿色长条遮挡
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        drawArc(canvas, left, top, left + 2 * viewRadius,
                top + 2 * viewRadius, 90, 180, paint);
        canvas.drawRect(left + viewRadius, top,
                viewState.buttonX, top + 2 * viewRadius,
                paint);
        //绘制按钮
        drawButton(canvas, viewState.buttonX, centerY);
    }

    /**
     * 绘制圆弧
     */
    private void drawArc(Canvas canvas,
                         float left, float top,
                         float right, float bottom,
                         float startAngle, float sweepAngle,
                         Paint paint) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(left, top, right, bottom,
                    startAngle, sweepAngle, true, paint);
        } else {
            rect.set(left, top, right, bottom);
            canvas.drawArc(rect,
                    startAngle, sweepAngle, true, paint);
        }
    }

    private void drawRoundRect(Canvas canvas,
                               float left, float top,
                               float right, float bottom,
                               float backgroundRadius,
                               Paint paint) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(left, top, right, bottom,
                    backgroundRadius, backgroundRadius, paint);
        } else {
            rect.set(left, top, right, bottom);
            canvas.drawRoundRect(rect,
                    backgroundRadius, backgroundRadius, paint);
        }
    }

    private void drawButton(Canvas canvas, float x, float y) {
        canvas.drawCircle(x, y, buttonRadius, buttonPaint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(0XffDDDDDD);
        canvas.drawCircle(x, y, buttonRadius, paint);
    }

    @Override
    public void onClick(View view) {
        toggle();
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked == isChecked()) {
            postInvalidate();
            return;
        }
        toggle(false, false);
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        toggle(true);
    }

    /**
     * 切换状态
     */
    public void toggle(boolean animate) {
        toggle(animate, true);
    }

    private void toggle(boolean animate, boolean broadcast) {
        LogUtil.d("1 animate: " + animate + "...broadcast: " + broadcast);
        if (!isEnabled()) {
            return;
        }
        if (!isUiInited) {
            isChecked = !isChecked;
            if (broadcast) {
                broadcastEvent();
            }
            return;
        }

        if (valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }

        if (animate) {
            LogUtil.d("2 toggle...");
            isChecked = !isChecked;
            beforeState.copy(viewState);
            if (isChecked()) {
                //切换到unchecked
                setCheckedViewState(afterState);
            } else {
                setUncheckViewState(afterState);
            }
            valueAnimator.start();
        } else {
            isChecked = !isChecked;
            if (isChecked()) {
                LogUtil.d("3 toggle checked...");
                setCheckedViewState(viewState);
            } else {
                LogUtil.d("4 toggle unChecked...");
                setUncheckViewState(viewState);
            }
            postInvalidate();
        }
        if (broadcast) {
            LogUtil.d("5 toggle...");
            broadcastEvent();
        }
    }

    private void broadcastEvent() {
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChanged(this, isChecked());
        }
    }

    @Override
    public final void setOnLongClickListener(OnLongClickListener l) {
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener l) {
        onCheckedChangeListener = l;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(FLSwitchButton switchButton, boolean isChecked);
    }

    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener
            = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = (Float) animation.getAnimatedValue();
            viewState.buttonX = beforeState.buttonX
                    + (afterState.buttonX - beforeState.buttonX) * value;

            float fraction = (viewState.buttonX - buttonMinX) / (buttonMaxX - buttonMinX);

            viewState.checkStateColor = (int) argbEvaluator.evaluate(
                    fraction,
                    uncheckColor,
                    checkedColor
            );

            viewState.radius = fraction * viewRadius;
            postInvalidate();
        }
    };

    /**
     * 保存动画状态
     */
    private static class ViewState {
        /**
         * 按钮x位置[buttonMinX-buttonMaxX]
         */
        float buttonX;
        /**
         * 状态背景颜色
         */
        int checkStateColor;
        /**
         * 状态背景的半径
         */
        float radius;

        private void copy(ViewState source) {
            this.buttonX = source.buttonX;
            this.checkStateColor = source.checkStateColor;
            this.radius = source.radius;
        }
    }

    /*******************************************************/
    private static float dp2px(float dp) {
        Resources r = Resources.getSystem();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    private static int dp2pxInt(float dp) {
        return (int) dp2px(dp);
    }
}
