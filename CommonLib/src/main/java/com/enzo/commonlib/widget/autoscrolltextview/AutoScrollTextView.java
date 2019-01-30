package com.enzo.commonlib.widget.autoscrolltextview;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.List;

/**
 * 自动垂直滚动的TextView
 */
public class AutoScrollTextView extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private Context mContext;

    //mInUp,mOutUp分别构成向下翻页的进出动画
    private Rotate3dAnimation mInUp;
    private Rotate3dAnimation mOutUp;
    private Handler handler;
    private Runnable runnable;
    private List<String> list;
    private int position = 0;
    private int delayedTime = 3000;//滚动间隔
    private ItemClickListener mListener;

    public AutoScrollTextView(Context context) {
        this(context, null);
    }

    public AutoScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        setFactory(this);

        mInUp = createAnim(true, true);
        mOutUp = createAnim(false, true);

        setInAnimation(mInUp);//当View显示时动画资源ID
        setOutAnimation(mOutUp);//当View隐藏是动画资源ID。
        //采用handler进行计时滚动
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                position++;
                if (position == list.size())
                    position = 0;
                //设置显示数据并滚动
                next();
                setText(list.get(position));
                handler.postDelayed(runnable, delayedTime);
            }
        };
    }

    private Rotate3dAnimation createAnim(boolean turnIn, boolean turnUp) {
        Rotate3dAnimation rotation = new Rotate3dAnimation(turnIn, turnUp);
        rotation.setDuration(1200);//执行动画的时间
        rotation.setFillAfter(false);//是否保持动画完毕之后的状态
        rotation.setInterpolator(new AccelerateInterpolator());//设置加速模式
        return rotation;
    }

    /**
     * 设置滚动数据集合
     */
    public void setList(List<String> list) {
        this.list = list;
    }

    /**
     * 开始滚动
     */
    public void startScroll() {
        position = 0;
        handler.post(runnable);
    }

    /**
     * 开始滚动
     */
    public void startScroll(int delayedTime) {
        this.delayedTime = delayedTime;
        position = 0;
        handler.post(runnable);
    }

    /**
     * 停止滚动
     */
    public void stopScroll() {
        handler.removeCallbacks(runnable);
    }

    /**
     * 设置监听
     */
    public void setClickListener(ItemClickListener listener) {
        this.mListener = listener;
    }

    //定义动作，向上滚动翻页
    public void next() {
        //显示动画
        if (getInAnimation() != mInUp) {
            setInAnimation(mInUp);
        }
        //隐藏动画
        if (getOutAnimation() != mOutUp) {
            setOutAnimation(mOutUp);
        }
    }

    @Override
    public View makeView() {
        TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.LEFT);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setSingleLine(true);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClick(position);
                }
            }
        });
        return textView;
    }

    class Rotate3dAnimation extends Animation {
        private float mCenterX;
        private float mCenterY;
        private final boolean mTurnIn;
        private final boolean mTurnUp;
        private Camera mCamera;

        Rotate3dAnimation(boolean turnIn, boolean turnUp) {
            mTurnIn = turnIn;
            mTurnUp = turnUp;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
            mCenterY = getHeight();
            mCenterX = getWidth();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final float centerX = mCenterX;
            final float centerY = mCenterY;
            final Camera camera = mCamera;
            final int direction = mTurnUp ? 1 : -1;

            final Matrix matrix = t.getMatrix();

            camera.save();
            if (mTurnIn) {
                camera.translate(0.0f, direction * mCenterY * (interpolatedTime - 1.0f), 0.0f);
            } else {
                camera.translate(0.0f, direction * mCenterY * (interpolatedTime), 0.0f);
            }
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }

    public interface ItemClickListener {
        void onClick(int position);
    }
}
