package com.enzo.commonlib.widget.snowview;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.enzo.commonlib.utils.common.BitmapUtils;

import java.util.Random;

/**
 * 文 件 名: SnowModel
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SnowModel {

    private Random random;
    private int parentWidth;//父容器宽度
    private int parentHeight;//父容器高度
    private float snowWidth;//下落物体宽度
    private float snowHeight;//下落物体高度

    private int initSpeed;//初始下降速度

    private float presentX;//当前位置X坐标
    private float presentY;//当前位置Y坐标
    private float presentSpeed;//当前下降速度
    private float angle;//物体下落角度

    private Bitmap bitmap;

    private boolean isSpeedRandom;//物体初始下降速度比例是否随机
    private boolean isSizeRandom;//物体初始大小比例是否随机
    private boolean isWindChange;//物体下落过程中风向和风力是否产生随机变化

    private static final int defaultSpeed = 10;//默认下降速度
    private static final int defaultWindSpeed = 10;//默认单位风速
    private static final float HALF_PI = (float) Math.PI / 2;//π/2

    private SnowModel(Builder builder) {
        this.parentWidth = builder.parentWidth;
        this.parentHeight = builder.parentHeight;
        random = new Random();
        presentX = random.nextInt(builder.parentWidth);
        presentY = random.nextInt(builder.parentHeight) - parentHeight;

        bitmap = builder.bitmap;
        isSpeedRandom = builder.isSpeedRandom;
        isSizeRandom = builder.isSizeRandom;
        isWindChange = builder.isWindChange;

        initSpeed = builder.initSpeed;
        randomSpeed();
        randomSize();
        randomWind();
    }

    public static final class Builder {
        private int initSpeed;
        private Bitmap bitmap;

        private boolean isSpeedRandom;
        private boolean isSizeRandom;
        private boolean isWindChange;
        private int parentWidth;//父容器宽度
        private int parentHeight;//父容器高度

        Builder(Bitmap bitmap) {
            this.initSpeed = defaultSpeed;
            this.bitmap = bitmap;
            this.isSpeedRandom = false;
            this.isSizeRandom = false;
            this.isWindChange = false;
        }

        /**
         * 设置物体的初始下落速度
         *
         * @param speed         下落速度
         * @param isRandomSpeed 物体初始下降速度比例是否随机
         * @return Builder
         */
        Builder setSpeed(int speed, boolean isRandomSpeed) {
            this.initSpeed = speed;
            this.isSpeedRandom = isRandomSpeed;
            return this;
        }

        Builder setParentSize(int w, int h) {
            this.parentWidth = w;
            this.parentHeight = h;
            return this;
        }

        /**
         * 设置物体大小随机
         */
        Builder randomSize(boolean random) {
            this.isSizeRandom = random;
            return this;
        }

        public SnowModel build() {
            return new SnowModel(this);
        }
    }

    /**
     * 绘制物体对象
     */
    public void drawSnow(Canvas canvas) {
        moveSnow();
        canvas.drawBitmap(bitmap, presentX, presentY, null);
    }

    /**
     * 移动物体对象
     */
    private void moveSnow() {
        moveX();
        moveY();
        if (presentY > parentHeight || presentX < -bitmap.getWidth() || presentX > parentWidth + bitmap.getWidth()) {
            reset();
        }
    }

    /**
     * X轴上的移动逻辑
     */
    private void moveX() {
        presentX += defaultWindSpeed * Math.sin(angle);
        if (isWindChange) {
            angle += (float) (random.nextBoolean() ? -1 : 1) * Math.random() * 0.0025;
        }
    }

    /**
     * Y轴上的移动逻辑
     */
    private void moveY() {
        presentY += presentSpeed;
    }

    /**
     * 重置object位置
     */
    private void reset() {
        presentX = random.nextInt(parentWidth);
        presentY = -snowHeight;
        randomSpeed();//重置速度
        randomWind();//重置初始角度
    }

    /**
     * 随机物体初始下落速度
     */
    private void randomSpeed() {
        if (isSpeedRandom) {
            presentSpeed = (float) ((Math.random() + 1) / 2) * initSpeed;
        } else {
            presentSpeed = initSpeed;
        }
    }

    /**
     * 随机物体初始大小比例
     */
    private void randomSize() {
        if (isSizeRandom) {
            float r = (random.nextInt(10) + 1) * 0.1f;
            float rW = r * bitmap.getWidth();
            float rH = r * bitmap.getHeight();
            bitmap = BitmapUtils.changeBitmapSize(bitmap, (int) rW, (int) rH);
        }
        snowWidth = bitmap.getWidth();
        snowHeight = bitmap.getHeight();
    }

    /**
     * 随机风的风向和风力大小比例，即随机物体初始下落角度
     */
    private void randomWind() {
        angle = (float) ((random.nextBoolean() ? -1 : 1) * Math.random() / 12);
        //限制angle的最大最小值
        if (angle > HALF_PI) {
            angle = HALF_PI;
        } else if (angle < -HALF_PI) {
            angle = -HALF_PI;
        }
    }

}
