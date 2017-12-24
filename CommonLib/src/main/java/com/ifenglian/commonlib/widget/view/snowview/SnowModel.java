package com.ifenglian.commonlib.widget.view.snowview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.Random;

/**
 * 文 件 名: SnowModel
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class SnowModel {

    private Random random;
    private int parentWidth;//父容器宽度
    private int parentHeight;//父容器高度
    private float objectWidth;//下落物体宽度
    private float objectHeight;//下落物体高度

    private int initSpeed;//初始下降速度
    private int initWindLevel;//初始风力等级

    private float presentX;//当前位置X坐标
    private float presentY;//当前位置Y坐标
    private float presentSpeed;//当前下降速度
    private float angle;//物体下落角度

    private Bitmap bitmap;

    private boolean isSpeedRandom;//物体初始下降速度比例是否随机
    private boolean isSizeRandom;//物体初始大小比例是否随机
    private boolean isWindRandom;//物体初始风向和风力大小比例是否随机
    private boolean isWindChange;//物体下落过程中风向和风力是否产生随机变化

    private static final int defaultSpeed = 10;//默认下降速度
    private static final int defaultWindLevel = 0;//默认风力等级
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
        isWindRandom = builder.isWindRandom;
        isWindChange = builder.isWindChange;

        initSpeed = builder.initSpeed;
        initWindLevel = builder.initWindLevel;
        randomSpeed();
        randomSize();
        randomWind();
    }

    public static final class Builder {
        private int initSpeed;
        private int initWindLevel;
        private Bitmap bitmap;

        private boolean isSpeedRandom;
        private boolean isSizeRandom;
        private boolean isWindRandom;
        private boolean isWindChange;
        private int parentWidth;//父容器宽度
        private int parentHeight;//父容器高度

        public Builder(Bitmap bitmap) {
            this.initSpeed = defaultSpeed;
            this.initWindLevel = defaultWindLevel;
            this.bitmap = bitmap;

            this.isSpeedRandom = false;
            this.isSizeRandom = false;
            this.isWindRandom = false;
            this.isWindChange = false;
        }

        /**
         * 设置物体的初始下落速度
         *
         * @param speed 下落速度
         * @return Builder
         */
        public Builder setSpeed(int speed) {
            this.initSpeed = speed;
            return this;
        }

        /**
         * 设置物体的初始下落速度
         *
         * @param speed         下落速度
         * @param isRandomSpeed 物体初始下降速度比例是否随机
         * @return Builder
         */
        public Builder setSpeed(int speed, boolean isRandomSpeed) {
            this.initSpeed = speed;
            this.isSpeedRandom = isRandomSpeed;
            return this;
        }

        /**
         * 设置物体大小
         */
        public Builder setSize(int w, int h) {
            this.bitmap = changeBitmapSize(this.bitmap, w, h);
            return this;
        }

        /**
         * 设置物体大小
         */
        public Builder setSize(int w, int h, boolean isRandomSize) {
            this.bitmap = changeBitmapSize(this.bitmap, w, h);
            this.isSizeRandom = isRandomSize;
            return this;
        }

        public Builder setParentSize(int w, int h) {
            parentWidth = w;
            parentHeight = h;
            return this;
        }

        /**
         * 设置风力等级、方向以及随机因素
         *
         * @param level        风力等级（绝对值为 5 时效果会比较好），为正时风从左向右吹（物体向X轴正方向偏移），为负时则相反
         * @param isWindRandom 物体初始风向和风力大小比例是否随机
         * @param isWindChange 在物体下落过程中风的风向和风力是否会产生随机变化
         */
        public Builder setWind(int level, boolean isWindRandom, boolean isWindChange) {
            this.initWindLevel = level;
            this.isWindRandom = isWindRandom;
            this.isWindChange = isWindChange;
            return this;
        }

        public SnowModel build() {
            return new SnowModel(this);
        }
    }

    /**
     * 绘制物体对象
     */
    public void drawObject(Canvas canvas) {
        moveObject();
        canvas.drawBitmap(bitmap, presentX, presentY, null);
    }

    /**
     * 移动物体对象
     */
    private void moveObject() {
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
        presentY = -objectHeight;
        randomSpeed();//记得重置时速度也一起重置，这样效果会好很多
        randomWind();//记得重置一下初始角度，不然雪花会越下越少（因为角度累加会让雪花越下越偏）
    }

    /**
     * 随机物体初始下落速度
     */
    private void randomSpeed() {
        if (isSpeedRandom) {
            presentSpeed = (float) ((random.nextInt(3) + 1) * 0.1 + 1) * initSpeed;//这些随机数大家可以按自己的需要进行调整
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
            bitmap = changeBitmapSize(bitmap, (int) rW, (int) rH);
        }
        objectWidth = bitmap.getWidth();
        objectHeight = bitmap.getHeight();
    }

    /**
     * 随机风的风向和风力大小比例，即随机物体初始下落角度
     */
    private void randomWind() {
        if (isWindRandom) {
            angle = (float) ((random.nextBoolean() ? -1 : 1) * Math.random() * initWindLevel / 50);
        } else {
            angle = (float) initWindLevel / 50;
        }

        //限制angle的最大最小值
        if (angle > HALF_PI) {
            angle = HALF_PI;
        } else if (angle < -HALF_PI) {
            angle = -HALF_PI;
        }
    }

    /**
     * 改变bitmap的大小
     *
     * @param bitmap 目标bitmap
     * @param newW   目标宽度
     * @param newH   目标高度
     * @return Bitmap
     */
    static Bitmap changeBitmapSize(Bitmap bitmap, int newW, int newH) {
        int oldW = bitmap.getWidth();
        int oldH = bitmap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newW) / oldW;
        float scaleHeight = ((float) newH) / oldH;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, oldW, oldH, matrix, true);
        return bitmap;
    }
}
