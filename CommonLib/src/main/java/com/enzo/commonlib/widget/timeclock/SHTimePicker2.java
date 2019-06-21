package com.enzo.commonlib.widget.timeclock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.nineoldandroids.animation.TypeEvaluator;

/**
 * 文 件 名: TimePicker
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/17
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SHTimePicker2 extends View {

    private static final int AN_HOUR_AS_MINUTES = 60;
    private static final int HALF_DAY_AS_HOURS = 12;
    private int dialColor = Color.parseColor("#00c37b");
    private int bgColor1 = Color.parseColor("#2F2F2F");
    private int bgColor2 = Color.parseColor("#404040");
    private Paint paintText;
    private Paint paintLine;
    private Paint paintCircle;
    private Paint paintArc;
    private RectF rectF;
    private Path path;

    private boolean startAm, endAm;
    private int preHour1, preHour2;
    private float dialRadius, offset;
    private float slopX1, slopY1, dialX1, dialY1;
    private float slopX2, slopY2, dialX2, dialY2;
    private double angle1, degrees1;
    private double angle2, degrees2;
    private boolean isMoving, slideStart, slideEnd;
    private float rectWidth;
    private String time;
    private Bitmap cacheBitmap;

    public SHTimePicker2(Context context) {
        this(context, null);
    }

    public SHTimePicker2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SHTimePicker2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);

        paintText = new Paint();
        paintText.setColor(Color.WHITE);
        paintText.setAntiAlias(true);
        paintText.setTextAlign(Paint.Align.CENTER);

        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setAntiAlias(true);
        paintLine.setStyle(Paint.Style.STROKE);

        paintCircle = new Paint();
        paintCircle.setAntiAlias(true);
        paintCircle.setStyle(Paint.Style.FILL);

        paintArc = new Paint();
        paintArc.setAntiAlias(true);
        paintArc.setStyle(Paint.Style.STROKE);
        paintArc.setStrokeCap(Paint.Cap.ROUND);

        path = new Path();
        rectF = new RectF();
    }

    TypeEvaluator<Integer> te = new TypeEvaluator<Integer>() {
        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            int alpha = (int) (Color.alpha(startValue) + fraction * (Color.alpha(endValue) - Color.alpha(startValue)));
            int red = (int) (Color.red(startValue) + fraction * (Color.red(endValue) - Color.red(startValue)));
            int green = (int) (Color.green(startValue) + fraction * (Color.green(endValue) - Color.green(startValue)));
            int blue = (int) (Color.blue(startValue) + fraction * (Color.blue(endValue) - Color.blue(startValue)));
            return Color.argb(alpha, red, green, blue);
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float min = Math.min(w, h);
        offset = min / 2;
        dialRadius = dip2px(15);
        rectWidth = min / 2 - dip2px(34);

        int triangleWidth = dip2px(8);
        double triangleHeight = triangleWidth * Math.sin(Math.PI / 180 * 60);
        path.moveTo(-(float) (triangleHeight / 3 * 2), 0);
        path.lineTo((float) (triangleHeight / 3), -triangleWidth / 2f);
        path.lineTo((float) (triangleHeight / 3), triangleWidth / 2f);
        path.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (slideStart) {
            setStartTimeDegrees();
            calculatePointerPosition1(angle1);
        } else if (slideEnd) {
            setEndTimeDegrees();
            calculatePointerPosition2(angle2);
        } else {
            setStartTimeDegrees();
            setEndTimeDegrees();
            calculatePointerPosition1(angle1);
            calculatePointerPosition2(angle2);
        }

        if (cacheBitmap == null) {
            cacheBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas singleUseCanvas = new Canvas(cacheBitmap);
            singleUseCanvas.translate(offset, offset);
            singleUseCanvas.drawColor(Color.TRANSPARENT);
            drawBg(singleUseCanvas);

            //绘制刻度
            drawLines(singleUseCanvas);

            //绘制整点
            drawText(singleUseCanvas);
        }
        canvas.drawBitmap(cacheBitmap, 0, 0, null);
        canvas.translate(offset, offset);

        //绘制时间差
        drawTime(canvas);

        //绘制环形
        drawArc(canvas);

        //绘制内圆 俩个小圆
        drawCircles(canvas);
    }

    private void drawBg(Canvas canvas) {
        rectF.set(-offset, offset, offset, -offset);
        paintCircle.setColor(bgColor2);
        canvas.drawOval(rectF, paintCircle);

        rectF.set(-rectWidth, rectWidth, rectWidth, -rectWidth);
        paintCircle.setColor(bgColor1);
        canvas.drawOval(rectF, paintCircle);
    }

    private void setStartTimeDegrees() {
        degrees1 = (Math.toDegrees(angle1) + 90 + 360) % 360;
        int temMinutes1 = ((int) (degrees1 * 2)) % AN_HOUR_AS_MINUTES;
        if (temMinutes1 % 5 == 4)//由于由  时间->弧度->角度->时间 过程会产生误差（比如15分钟转换后可能变成14分钟）
            temMinutes1++;
        if (temMinutes1 == 60)
            temMinutes1 = 55;
        int temHour1 = ((int) degrees1 / 30) % HALF_DAY_AS_HOURS;
        if ((preHour1 == 11 && temHour1 == 0) || (preHour1 == 12 && temHour1 == 11)
                || (preHour1 == 23 && temHour1 == 0) || (preHour1 == 0 && temHour1 == 11)) {
            startAm = !startAm;
        }
        if (!startAm) {
            temHour1 = temHour1 + 12;
        }
        if (mListener != null) {
            String strHour = temHour1 < 10 ? "0" + temHour1 : temHour1 + "";
            temMinutes1 = temMinutes1 - temMinutes1 % 5;
            String strMinutes = (temMinutes1 < 10 ? "0" + temMinutes1 : temMinutes1 + "");
            mListener.onStartTimeChange(strHour + ":" + strMinutes);
        }
        preHour1 = temHour1;
    }

    private void setEndTimeDegrees() {
        degrees2 = (Math.toDegrees(angle2) + 90 + 360) % 360;
        int temMinutes2 = ((int) (degrees2 * 2)) % AN_HOUR_AS_MINUTES;
        if (temMinutes2 % 5 == 4)//由于由  时间->弧度->角度->时间 过程会产生误差（比如15分钟转换后可能变成14分钟）
            temMinutes2++;
        if (temMinutes2 == 60)
            temMinutes2 = 55;
        int temHour2 = ((int) degrees2 / 30) % HALF_DAY_AS_HOURS;
        if ((preHour2 == 11 && temHour2 == 0) || (preHour2 == 12 && temHour2 == 11)
                || (preHour2 == 23 && temHour2 == 0) || (preHour2 == 0 && temHour2 == 11)) {
            endAm = !endAm;
        }
        if (!endAm) {
            temHour2 = temHour2 + 12;
        }
        if (mListener != null) {
            String strHour = temHour2 < 10 ? "0" + temHour2 : temHour2 + "";
            temMinutes2 = temMinutes2 - temMinutes2 % 5;
            String strMinutes = (temMinutes2 < 10 ? "0" + temMinutes2 : temMinutes2 + "");
            mListener.onEndTimeChange(strHour + ":" + strMinutes);
        }
        preHour2 = temHour2;
    }

    private void calculatePointerPosition1(double angle) {
        dialX1 = (float) ((offset - dialRadius) * Math.cos(angle));
        dialY1 = (float) ((offset - dialRadius) * Math.sin(angle));
    }

    private void calculatePointerPosition2(double angle) {
        dialX2 = (float) ((offset - dialRadius) * Math.cos(angle));
        dialY2 = (float) ((offset - dialRadius) * Math.sin(angle));
    }

    /**
     * 绘制时钟刻度和分钟刻度
     *
     * @param canvas 画布
     */
    private void drawLines(Canvas canvas) {
        canvas.save();
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                //绘制整点刻度
                paintLine.setStrokeWidth(dip2px(2));
                canvas.drawLine(0, -rectWidth, 0, -rectWidth + dip2px(13), paintLine);
            } else {
                //绘制分钟刻度
                paintLine.setStrokeWidth(dip2px(1));
                canvas.drawLine(0, -rectWidth, 0, -rectWidth + dip2px(10), paintLine);
            }
            //绕着(centerX,centerY)旋转6°
            canvas.rotate(6f, 0, 0);
        }
        canvas.restore();
    }

    /**
     * 绘制整点数字
     *
     * @param canvas 画布
     */
    private void drawText(Canvas canvas) {
        paintText.setTextSize(sp2px(12));
        // 获取文字高度用于设置文本垂直居中
        float textSize = (paintText.getFontMetrics().bottom - paintText.getFontMetrics().top);
        // 数字离圆心的距离,16dp为刻度的长度,20文字大小
        int distance = (int) (rectWidth - dip2px(16) - 20);
        // 数字的坐标(a,b)
        float a, b;
        // 每30°写一个数字
        for (int i = 0; i < 12; i++) {
            //弧度 = 2 * PI / 360 * 角度；
            a = (float) (distance * Math.sin(i * 30 * Math.PI / 180) + 0);
            b = (float) (0 - distance * Math.cos(i * 30 * Math.PI / 180));
            if (i == 0) {
                canvas.drawText("12", a, b + textSize / 3, paintText);
            } else {
                canvas.drawText(String.valueOf(i), a, b + textSize / 3, paintText);
            }
        }
    }

    /**
     * 绘制弧形
     *
     * @param canvas 画布
     */
    private void drawArc(Canvas canvas) {
        paintArc.setStrokeWidth(dialRadius * 2);
        rectF.set(-offset + dialRadius, -offset + dialRadius, offset - dialRadius, offset - dialRadius);
        if (time.equals("24小时") || time.equals("12小时")) {
            float sweepDegrees = 360f;
            int total = 50;//圆弧平均分总数
            float degrees = sweepDegrees / total;
            for (int i = 0; i < total; i++) {
                paintArc.setColor(te.evaluate((float) i / total, 0xffff00ff, 0xffff00));
                if (i > 10 && i < total - 10) {
                    canvas.drawArc(rectF, (float) degrees1 - 90 + degrees * i + 1, degrees, false, paintArc);
                } else {
                    canvas.drawArc(rectF, (float) degrees1 - 90 + degrees * i, degrees, false, paintArc);
                }
            }
        } else {
            float sweepDegrees = (float) ((degrees2 - degrees1 + 360) % 360);
            int total = 50;//圆弧平均分总数
            float degrees = sweepDegrees / total;
            for (int i = 0; i < total; i++) {
                paintArc.setColor(te.evaluate((float) i / total, 0xffff00ff, 0xffff00));
                if (i > 10 && i < total - 10 && sweepDegrees > 30) {
                    canvas.drawArc(rectF, (float) degrees1 - 90 + degrees * i + 1, degrees, false, paintArc);
                } else {
                    canvas.drawArc(rectF, (float) degrees1 - 90 + degrees * i, degrees, false, paintArc);
                }
            }
        }
    }

    private void drawCircles(Canvas canvas) {
        //结束时间拖拽圆
        paintCircle.setColor(dialColor);
        canvas.drawCircle(dialX2, dialY2, dialRadius, paintCircle);

        //结束时间拖拽小白圆
        paintCircle.setColor(Color.WHITE);
        canvas.drawCircle(dialX2, dialY2, dip2px(5), paintCircle);

        //开始时间拖拽圆
        paintCircle.setColor(dialColor);
        canvas.drawCircle(dialX1, dialY1, dialRadius, paintCircle);

        //开始时间三角形
        canvas.save();
        paintCircle.setColor(Color.WHITE);
        canvas.translate(dialX1, dialY1);
        canvas.rotate((float) (degrees1 - 180));
        canvas.drawPath(path, paintCircle);
        canvas.restore();
    }

    private void drawTime(Canvas canvas) {
        paintText.setTextSize(sp2px(24));
        String strHour;
        String strMinute;
        int minutes1 = ((int) (degrees1 * 2)) % AN_HOUR_AS_MINUTES;
        if (minutes1 % 5 == 4)//由于由  时间->弧度->角度->时间 过程会产生误差（比如15分钟转换后可能变成14分钟）
            minutes1++;
        if (minutes1 == 60)
            minutes1 = 55;
        minutes1 = minutes1 - minutes1 % 5;
        int temHour1 = ((int) degrees1 / 30) % HALF_DAY_AS_HOURS;
        if (!startAm)
            temHour1 = temHour1 + 12;

        int minutes2 = ((int) (degrees2 * 2)) % AN_HOUR_AS_MINUTES;
        if (minutes2 % 5 == 4)//由于由  时间->弧度->角度->时间 过程会产生误差（比如15分钟转换后可能变成14分钟）
            minutes2++;
        if (minutes2 == 60)
            minutes2 = 55;
        minutes2 = minutes2 - minutes2 % 5;
        int temHour2 = ((int) degrees2 / 30) % HALF_DAY_AS_HOURS;
        if (!endAm) {
            temHour2 = temHour2 + 12;
        }

        int hour = temHour2 - temHour1;
        int minute = minutes2 - minutes1;
        if (minute < 0) {
            hour--;
            minute = minute + 60;
        }
        if (hour < 0) {
            hour = 24 + hour;
        }

        strHour = hour == 0 ? "" : hour + "小时";
        minute = minute - minute % 5;
        strMinute = minute == 0 ? "" : minute + "分";

        if (TextUtils.isEmpty(strHour) && TextUtils.isEmpty(strMinute)) {
            time = "24小时";
            canvas.drawText(time, 0, paintText.getTextSize() / 4, paintText);
        } else {
            time = strHour + strMinute;
            canvas.drawText(time, 0, paintText.getTextSize() / 4, paintText);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        float posX = event.getX() - offset;
        float posY = event.getY() - offset;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isMoving = false;
                slideStart = false;
                slideEnd = false;
                int extArea = dip2px(5);
                if (posX >= (dialX1 - dialRadius - extArea) && posX <= (dialX1 + dialRadius + extArea) &&
                        posY >= (dialY1 - dialRadius - extArea) && posY <= (dialY1 + dialRadius + extArea)) {
                    calculatePointerPosition1(angle1);
                    slopX1 = posX - dialX1;
                    slopY1 = posY - dialY1;
                    isMoving = true;
                    slideStart = true;
                    invalidate();
                } else if (posX >= (dialX2 - dialRadius - extArea) && posX <= (dialX2 + dialRadius + extArea) &&
                        posY >= (dialY2 - dialRadius - extArea) && posY <= (dialY2 + dialRadius + extArea)) {
                    calculatePointerPosition2(angle2);
                    slopX2 = posX - dialX2;
                    slopY2 = posY - dialY2;
                    isMoving = true;
                    slideEnd = true;
                    invalidate();
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isMoving) {
                    // Math.atan2(对边边长,临边边长)
                    if (slideStart) {
                        angle1 = (float) Math.atan2(posY - slopY1, posX - slopX1);
                    } else if (slideEnd) {
                        angle2 = (float) Math.atan2(posY - slopY2, posX - slopX2);
                    }
                    invalidate();
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return true;
    }

    public void initTime(int startHour, int startMinutes, int endHour, int endMinutes) {
        if (startHour < 0 || startHour > 23)
            startHour = 0;
        startAm = startHour < 12;
        preHour1 = startHour;
        if (startMinutes < 0 || startMinutes > 59)
            startMinutes = 0;
        startMinutes = startMinutes - startMinutes % 5;
        degrees1 = ((startHour % HALF_DAY_AS_HOURS) * 30) + ((startMinutes % AN_HOUR_AS_MINUTES) / 2f);
        angle1 = Math.toRadians(degrees1) - (Math.PI / 2);

        if (endHour < 0 || endHour > 23)
            endHour = 0;
        endAm = endHour < 12;
        preHour2 = endHour;
        if (endMinutes < 0 || endMinutes > 59)
            endMinutes = 0;
        endMinutes = endMinutes - endMinutes % 5;
        degrees2 = ((endHour % HALF_DAY_AS_HOURS) * 30) + ((endMinutes % AN_HOUR_AS_MINUTES) / 2f);
        angle2 = Math.toRadians(degrees2) - (Math.PI / 2);
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private OnTimeChangeListener mListener;

    public void setOnTimeChangeListener(OnTimeChangeListener listener) {
        mListener = listener;
    }

    public interface OnTimeChangeListener {
        void onStartTimeChange(String startTime);

        void onEndTimeChange(String endTime);
    }
}