package com.enzo.commonlib.widget.timeclock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 文 件 名: TimeClock
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/15
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SHTimeClock extends View {

    private int bgColor1 = Color.parseColor("#404040");
    private int bgColor2 = Color.parseColor("#2e2f30");
    private int amArcWidth = dip2px(19);
    private int pmArcWidth = dip2px(23);
    private Paint paintLine;
    private Paint paintNum;
    private Paint paintArc;
    private RectF rectF;
    private int radius;
    private int rectWidth;
    private List<SHScheduleBean> mData;
    private Bitmap cacheBitmap;

    public SHTimeClock(Context context) {
        this(context, null);
    }

    public SHTimeClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_HARDWARE, null);

        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setStyle(Paint.Style.FILL);

        paintNum = new Paint();
        paintNum.setColor(Color.WHITE);
        paintNum.setAntiAlias(true);
        paintNum.setTextSize(sp2px(14));
        paintNum.setStyle(Paint.Style.FILL);
        paintNum.setTextAlign(Paint.Align.CENTER);

        paintArc = new Paint();
        paintArc.setAntiAlias(true);
        paintArc.setStyle(Paint.Style.STROKE);
        paintArc.setStrokeCap(Paint.Cap.ROUND);

        rectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension((int) (size * 0.77f), (int) (size * 0.77f));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float min = Math.min(w, h);
        radius = (int) min / 2;
        rectWidth = radius - dip2px(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (cacheBitmap == null) {
            cacheBitmap = Bitmap.createBitmap(radius * 2, radius * 2, Bitmap.Config.ARGB_8888);
            Canvas singleUseCanvas = new Canvas(cacheBitmap);
            singleUseCanvas.translate(radius, radius);
            singleUseCanvas.drawColor(Color.TRANSPARENT);

            //绘制背景
            drawBg(singleUseCanvas);

            //绘制圆心
            drawCenterOfCircle(singleUseCanvas);

            //绘制刻度
            drawLines(singleUseCanvas);

            //绘制整点
            drawText(singleUseCanvas);
        }
        canvas.drawBitmap(cacheBitmap, 0, 0, null);
        canvas.translate(radius, radius);

        drawArc(canvas);

        try {
            initCurrentTime(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }

        postInvalidateDelayed(200);
    }


    /**
     * 绘制背景
     *
     * @param canvas 画布
     */
    private void drawBg(Canvas canvas) {
        paintLine.setColor(bgColor1);
        canvas.drawCircle(0, 0, radius, paintLine);
        paintLine.setColor(bgColor2);
        canvas.drawCircle(0, 0, rectWidth, paintLine);
    }

    /**
     * 绘制圆心
     *
     * @param canvas 画布
     */
    private void drawCenterOfCircle(Canvas canvas) {
        paintLine.setColor(Color.WHITE);
        canvas.drawCircle(0, 0, dip2px(6), paintLine);
    }

    /**
     * 绘制时钟刻度和分钟刻度
     *
     * @param canvas 画布
     */
    private void drawLines(Canvas canvas) {
        canvas.save();
        paintLine.setStrokeWidth(dip2px(1));
        for (int i = 0; i < 60; i++) {
            if (i % 5 != 0) {
                //绘制分钟刻度
                canvas.drawLine(0, -radius + dip2px(1), 0, -radius + dip2px(12), paintLine);
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
        // 获取文字高度用于设置文本垂直居中
        float textSize = (paintNum.getFontMetrics().bottom - paintNum.getFontMetrics().top);
        // 数字离圆心的距离
        int distance = radius - dip2px(14);
        // 数字的坐标(a,b)
        float a, b;
        // 每30°写一个数字
        for (int i = 0; i < 12; i++) {
            //弧度 = 2 * PI / 360 * 角度；
            a = (float) (distance * Math.sin(i * 30 * Math.PI / 180));
            b = (float) (-distance * Math.cos(i * 30 * Math.PI / 180));

            if (i == 0) {
                canvas.drawText("12", a, b + textSize / 3, paintNum);
            } else {
                canvas.drawText(String.valueOf(i), a, b + textSize / 3, paintNum);
            }
        }
    }

    private void drawArc(Canvas canvas) {
        if (mData != null && mData.size() > 0) {
            for (int i = 0; i < mData.size(); i++) {
                List<SHScheduleBean.ItemEntity> list = mData.get(i).getItem();
                switch (i) {
                    case 0:
                        for (int j = 0; j < list.size(); j++) {
                            if (list.get(j).isEnable() == 1)
                                calculateArc(0, canvas, list.get(j), mData.get(0).getType());
                        }
                        break;
                    case 1:
                        for (int j = 0; j < list.size(); j++) {
                            if (list.get(j).isEnable() == 1)
                                calculateArc(1, canvas, list.get(j), mData.get(1).getType());
                        }
                        break;
                    case 2:
                        for (int j = 0; j < list.size(); j++) {
                            if (list.get(j).isEnable() == 1)
                                calculateArc(2, canvas, list.get(j), mData.get(2).getType());
                        }
                        break;
                }
            }
        }
    }

    /**
     * @param position 0:最外层 1:中间 2:最里边
     */
    private void calculateArc(int position, Canvas canvas, SHScheduleBean.ItemEntity bean, String type) {
        canvas.save();
        canvas.rotate(-90);
        int strokeWidth;
        boolean startAm = Integer.parseInt(bean.getOn().split(":")[0]) < 12;
        if (startAm) {
            strokeWidth = amArcWidth;
        } else {
            strokeWidth = pmArcWidth;
        }
        paintArc.setStrokeWidth(strokeWidth);
        int margin = 0;
        switch (position) {
            case 0:
                margin = strokeWidth / 2;
                break;
            case 1:
                margin = pmArcWidth + dip2px(6) + strokeWidth / 2;
                break;
            case 2:
                margin = pmArcWidth + dip2px(6) + pmArcWidth + dip2px(6) + strokeWidth / 2;
                break;
        }
        rectF.set(-rectWidth + margin, -rectWidth + margin, rectWidth - margin, rectWidth - margin);

        //计算开始角度跟需要旋转的角度
        String[] startTime = bean.getOn().split(":");
        String[] endTime = bean.getOff().split(":");
        int startHour = Integer.parseInt(startTime[0]);
        int startMinute = Integer.parseInt(startTime[1]);
        int endHour = Integer.parseInt(endTime[0]);
        int endMinute = Integer.parseInt(endTime[1]);
        int startDegrees = startHour * 30 + startMinute % 60 / 2;
        int endDegrees = endHour * 30 + endMinute % 60 / 2;
        int sweepDegrees;
        if (startDegrees == endDegrees) {
            sweepDegrees = 720;
        } else {
            sweepDegrees = (endDegrees - startDegrees + 720) % 720;
        }

        int total = 50;//圆弧平均分总数
        float averageDegrees = sweepDegrees * 1.0f / total;
        for (int i = 0; i < total; i++) {
            paintArc.setColor(SHTimePickerUtils.getPickerColor(i, total, startAm, type));
            canvas.drawArc(rectF, startDegrees + averageDegrees * i + 1, averageDegrees, false, paintArc);
        }
        canvas.restore();
    }

    /**
     * 获取当前系统时间
     *
     * @param canvas 画布
     */
    private void initCurrentTime(Canvas canvas) {
        //获取系统当前时间
        SimpleDateFormat format = new SimpleDateFormat("HH-mm-ss");
        String time = format.format(new Date(System.currentTimeMillis()));
        String[] split = time.split("-");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        int second = Integer.parseInt(split[2]);
        //时针走过的角度
        int hourDegrees = (hour % 12 * 30 + minute / 2);
        //分针走过的角度
        int minuteDegrees = minute * 6 + second / 10;
        //秒针走过的角度
        int secondAngle = second * 6;

        //绘制时钟,以12整点为0°参照点
        canvas.save();
        paintLine.setColor(Color.WHITE);
        paintLine.setStrokeWidth(dip2px(4));
        canvas.rotate(hourDegrees + 180, 0, 0);
        canvas.drawLine(0, 0, 0, rectWidth * 0.45f, paintLine);
        canvas.restore();

        //绘制分钟
        canvas.save();
        paintLine.setColor(Color.WHITE);
        paintLine.setStrokeWidth(dip2px(3));
        canvas.rotate(minuteDegrees + 180, 0, 0);
        canvas.drawLine(0, 0, 0, rectWidth * 0.72f, paintLine);
        canvas.restore();

        //绘制秒钟
        canvas.save();
        paintLine.setColor(Color.RED);
        paintLine.setStrokeWidth(dip2px(2));
        canvas.rotate(secondAngle + 180, 0, 0);
        canvas.drawLine(0, 0, 0, rectWidth * 0.9f, paintLine);
        canvas.restore();

        paintLine.setColor(Color.RED);
        canvas.drawCircle(0, 0, dip2px(3), paintLine);
    }

    public int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void setData(List<SHScheduleBean> data) {
        mData = data;
        invalidate();
    }
}
