package com.ifenglian.commonlib.widget.view.speedtest;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.ifenglian.commonlib.R;

/**
 * 文 件 名: SRSpeedTestView
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/9/21
 * 邮   箱: xiaofy@ifenglian.com
 */
public class SRSpeedTestView extends View {

    private static final String TAG = SRSpeedTestView.class.getSimpleName();
    private String currentSpeed = "";
    private String speedTitle = "";
    private int offSet;//圆心偏移量
    private int whiteCircleRadius, redCircleRadius;//中间白色跟红色圆半径
    private Bitmap bitmapBg1, bitmapBg2;//背景图
    private Bitmap cacheBitmap;
    private Paint paintPointer;
    private Paint paintText;
    private Paint paintLine;
    private Paint paintArc;
    private Path mPath;
    private RectF mRectF;
    private RadialGradient shadowWhite, shadowRed;
    private float mSweepDegree;//总共要旋转角度
    private float mCurrentDegree;//当前旋转角度
    private TypeEvaluator<Integer> te;

    public SRSpeedTestView(Context context) {
        this(context, null);
    }

    public SRSpeedTestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SRSpeedTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        int arcStrokeWidth = dp2Px(12);
        whiteCircleRadius = dp2Px(6.5f);
        redCircleRadius = dp2Px(10f);

        bitmapBg1 = BitmapFactory.decodeResource(getResources(), R.mipmap.lib_speed_test_bg);
        bitmapBg2 = BitmapFactory.decodeResource(getResources(), R.mipmap.lib_speed_test_bg2);

        //三角形path
        int triangleWidth = dp2Px(10);
        int triangleHeight = (int) (bitmapBg2.getWidth() / 2 * 0.74f);
        mPath = new Path();
        mPath.moveTo(-triangleWidth / 2, 0);
        mPath.lineTo(triangleWidth / 2, 0);
        mPath.lineTo(0, triangleHeight);
        mPath.close();

        //绘制arc需要的RectF
        int arcRadius = bitmapBg2.getWidth() / 2 - arcStrokeWidth - dp2Px(6);
        mRectF = new RectF();
        mRectF.set(-arcRadius, -arcRadius, arcRadius, arcRadius);

        paintPointer = new Paint();
        paintPointer.setAntiAlias(true);
        paintPointer.setDither(true);

        paintText = new Paint();
        paintText.setAntiAlias(true);
        paintText.setTextAlign(Paint.Align.CENTER);

        paintLine = new Paint();
        paintLine.setStrokeWidth(dp2Px(3));
        paintLine.setColor(Color.WHITE);
        paintLine.setAntiAlias(true);
        paintLine.setStyle(Paint.Style.FILL);

        paintArc = new Paint();
        paintArc.setAntiAlias(true);
        paintArc.setDither(true);
        paintArc.setStrokeCap(Paint.Cap.ROUND);
        paintArc.setStyle(Paint.Style.STROKE);
        paintArc.setStrokeWidth(arcStrokeWidth);

        //阴影
        shadowWhite = new RadialGradient(0, 0, whiteCircleRadius, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
        shadowRed = new RadialGradient(0, 0, redCircleRadius, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);

        te = new TypeEvaluator<Integer>() {
            @Override
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                int alpha = (int) (Color.alpha(startValue) + fraction * (Color.alpha(endValue) - Color.alpha(startValue)));
                int red = (int) (Color.red(startValue) + fraction * (Color.red(endValue) - Color.red(startValue)));
                int green = (int) (Color.green(startValue) + fraction * (Color.green(endValue) - Color.green(startValue)));
                int blue = (int) (Color.blue(startValue) + fraction * (Color.blue(endValue) - Color.blue(startValue)));
                return Color.argb(alpha, red, green, blue);
            }
        };
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bitmapBg1.getWidth(), bitmapBg1.getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int min = Math.min(w, h);
        offSet = min / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (cacheBitmap == null) {
            cacheBitmap = Bitmap.createBitmap(offSet * 2, offSet * 2, Bitmap.Config.ARGB_8888);
            Canvas cacheCanvas = new Canvas(cacheBitmap);
            //绘制蓝色背景
            cacheCanvas.drawBitmap(bitmapBg1, 0, 0, null);
            //绘制白色背景
            cacheCanvas.drawBitmap(bitmapBg2, (bitmapBg1.getWidth() - bitmapBg2.getWidth()) / 2,
                    (bitmapBg1.getHeight() - bitmapBg2.getHeight()) / 2, null);
            cacheCanvas.translate(offSet, offSet);
            //绘制网速速度
            drawSpeedScale(cacheCanvas);
            //绘制进度背景
            drawProgressBg(cacheCanvas);
        }
        canvas.drawBitmap(cacheBitmap, 0, 0, null);
        //将坐标(0,0)点移到圆心
        canvas.translate(offSet, offSet);
        //绘制进度
        drawProgressArc(canvas);
        //绘制分割线
        drawLine(canvas);
        //绘制指针
        drawPointer(canvas);
        //绘制测速结果
        drawSpeedTest(canvas);
    }

    /**
     * 绘制网速速度
     *
     * @param canvas 画布
     */
    private void drawSpeedScale(Canvas canvas) {
        paintText.setColor(0xFFA8A8A8);
        float textSize = (paintText.getFontMetrics().bottom - paintText.getFontMetrics().top);
        // 数字离圆心的距离
        int distance = bitmapBg2.getWidth() / 2 - dp2Px(37);
        // 数字的坐标(a,b)
        float a, b;
        for (int i = 0; i < 11; i++) {
            //弧度 = 2 * PI / 360 * 角度；
            a = (float) (distance * Math.sin((i * 22 + 250) * Math.PI / 180) + 0);
            b = (float) (0 - distance * Math.cos((i * 22 + 250) * Math.PI / 180));
            String speed = getSpeedScale(i);
            if (speed.equals("∞")) {
                paintText.setTextSize(sp2Px(10));
            } else {
                paintText.setTextSize(sp2Px(8));
            }
            canvas.drawText(speed, a, b + textSize / 3, paintText);
        }
    }

    /**
     * 绘制分割线
     *
     * @param canvas 画布
     */
    private void drawLine(Canvas canvas) {
        canvas.save();
        canvas.rotate(250, 0, 0);
        for (int i = 1; i <= 9; i++) {
            canvas.rotate(22, 0, 0);
            canvas.drawLine(0, -bitmapBg2.getWidth() / 2 + dp2Px(11), 0, -bitmapBg2.getWidth() / 2 + dp2Px(25), paintLine);
        }
        canvas.restore();
    }

    /**
     * 绘制进度背景
     *
     * @param canvas 画布
     */
    private void drawProgressBg(Canvas canvas) {
        paintArc.setColor(0xFFE2E2E2);
        canvas.drawArc(mRectF, 160, 220, false, paintArc);
    }

    /**
     * 绘制进度
     *
     * @param canvas 画布
     */
    private void drawProgressArc(Canvas canvas) {
        int total = 50;
        float averageDegrees = mCurrentDegree / total;
        for (int i = 0; i < total; i++) {
            paintArc.setColor(te.evaluate(i * 1f / total, 0xff14c8ff, 0xff00d734));
            canvas.drawArc(mRectF, 160 + i * averageDegrees, averageDegrees, false, paintArc);
        }
    }

    /**
     * 绘制指针
     *
     * @param canvas 画布
     */
    private void drawPointer(Canvas canvas) {
        paintPointer.setStyle(Paint.Style.FILL);

        //绘制红圆阴影
        canvas.save();
        paintPointer.setShader(shadowRed);
        canvas.translate(0, redCircleRadius * 0.2f);
        canvas.drawCircle(0, 0, redCircleRadius, paintPointer);
        paintPointer.setShader(null);
        canvas.restore();

        //绘制红圆
        paintPointer.setColor(0xFFCE0F5B);
        canvas.drawCircle(0, 0, redCircleRadius, paintPointer);

        //绘制指针
        canvas.save();
        paintPointer.setColor(0xFFCE0F5B);
        canvas.rotate(70 + mCurrentDegree, 0, 0);
        canvas.drawPath(mPath, paintPointer);
        canvas.restore();

        //绘制白圆阴影
        canvas.save();
        paintPointer.setShader(shadowWhite);
        canvas.translate(0, whiteCircleRadius * 0.25f);
        canvas.drawCircle(0, 0, whiteCircleRadius, paintPointer);
        paintPointer.setShader(null);
        canvas.restore();

        //绘制白圆
        paintPointer.setColor(Color.WHITE);
        canvas.drawCircle(0, 0, whiteCircleRadius, paintPointer);
    }

    /**
     * 绘制测速结果
     *
     * @param canvas 画布
     */
    private void drawSpeedTest(Canvas canvas) {
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(sp2Px(12));
        canvas.drawText(speedTitle, 0, bitmapBg2.getHeight() / 2 - dp2Px(34), paintText);

        paintText.setTextSize(sp2Px(18));
        canvas.drawText(currentSpeed, 0, bitmapBg2.getHeight() / 2 - dp2Px(10), paintText);
    }

    public void updateLatestSpeed(long speed) {
        speedTitle = speed == 0 ? "" : "上次测速";
        updateSpeed(speed);
    }

    public void updateCurrentSpeed(long speed) {
        speedTitle = "即时网速";
        updateSpeed(speed);
    }

    public void updateAverageSpeed(long speed) {
        speedTitle = "平均网速";
        updateSpeed(speed);
    }

    public void updateSpeed(long speed) {
        if (speed <= 1024) {
            currentSpeed = speed + "KB/s";
        } else {
            currentSpeed = String.format("%.2f", speed / 1024.0f) + "M/s";
        }
        setSpeed(speed);
    }

    public void setSpeed(long speed) {
        mSweepDegree = getSweepDegree(speed);
        if (mSweepDegree > 220) {
            mSweepDegree = 220;
        }
        if (mSweepDegree < 0) {
            mSweepDegree = 0f;
        }
        setSpeedTestAnim();
    }

    private ValueAnimator valueAnimator;

    private void setSpeedTestAnim() {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
            valueAnimator.removeAllUpdateListeners();
        }
        valueAnimator = ValueAnimator.ofFloat(mCurrentDegree, mSweepDegree);
        valueAnimator.setDuration(400);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentDegree = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    private String getSpeedScale(int index) {
        switch (index) {
            case 0:
                return "0";
            case 1:
                return "256k";
            case 2:
                return "512k";
            case 3:
                return "1M";
            case 4:
                return "3M";
            case 5:
                return "5M";
            case 6:
                return "10M";
            case 7:
                return "20M";
            case 8:
                return "50M";
            case 9:
                return "100M";
            case 10:
                return "∞";
        }
        return "";
    }

    private float getSweepDegree(long speed) {
        float avDegree = 22f;
        if (speed <= 256.0f) {
            return avDegree * (speed / 256.0f);
        }

        if (speed <= 512.0f) {
            return avDegree + avDegree * ((speed - 256.0f) / 256.0f);
        }

        if (speed <= 1024.0f) {
            return 2 * avDegree + avDegree * ((speed - 512.0f) / (512.0f));
        }

        if (speed <= 1024.0f * 3) {
            return 3 * avDegree + avDegree * ((speed - 1024.0f) / (1024.0f * 2));
        }

        if (speed <= 1024.0f * 5) {
            return 4 * avDegree + avDegree * ((speed - 1024.0f * 3) / (1024.0f * 2));
        }

        if (speed <= 1024.0f * 10) {
            return 5 * avDegree + avDegree * ((speed - 1024.0f * 5) / (1024.0f * 5));
        }

        if (speed <= 1024.0f * 20) {
            return 6 * avDegree + avDegree * ((speed - 1024.0f * 10) / (1024.0f * 10));
        }

        if (speed <= 1024.0f * 50) {
            return 7 * avDegree + avDegree * ((speed - 1024.0f * 20) / (1024.0f * 30));
        }

        if (speed <= 1025.0f * 100) {
            return 8 * avDegree + avDegree * ((speed - 1024.0f * 50) / (1024.0f * 50));
        }

        return 9 * avDegree;
    }

    private int dp2Px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private int sp2Px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}
