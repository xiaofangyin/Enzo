package com.enzo.commonlib.widget.indicator.noscroll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * 文 件 名: ViewPagerIndicator
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/11
 * 邮   箱: xiaofangyinwork@163.com
 */
public class ViewPagerIndicator extends LinearLayout {

    private ViewPager mViewPager;
    private Paint mPaint;
    private Path mPath;
    private int marginLeft;//指示器初始偏移量
    private int tabCount;//指示器个数
    private int translateX;//指示器偏移量
    private static int COLOR_TEXT_NORMAL = 0x77FFFFFF;
    private static int COLOR_TEXT_HIGHLIGHT = 0xFFFFFFFF;
    private static int COLOR_INDICATOR = 0xFFFFFFFF;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (tabCount != 0) {
            //单个tab宽度的2/3
            int indicatorWidth = (int) (w / tabCount * 2.0f / 3);
            marginLeft = getWidth() / tabCount / 2 - indicatorWidth / 2;

            int indicatorHeight = dip2px(getContext(), 2);
            mPath = new Path();
            mPath.moveTo(0, 0);
            mPath.lineTo(indicatorWidth, 0);
            mPath.lineTo(indicatorWidth, -indicatorHeight);
            mPath.lineTo(0, -indicatorHeight);
            mPath.close();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        canvas.translate(marginLeft + translateX, getHeight());
        mPaint.setColor(COLOR_INDICATOR);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
    }

    public void setNormalColor(int color) {
        COLOR_TEXT_NORMAL = color;
        invalidate();
    }

    public void setHighlightColor(int color) {
        COLOR_TEXT_HIGHLIGHT = color;
        invalidate();
    }

    public void setIndicatorColor(int color) {
        COLOR_INDICATOR = color;
        invalidate();
    }

    public void setTabItemTitles(List<String> datas) {
        if (datas != null && datas.size() > 0) {
            this.removeAllViews();
            tabCount = datas.size();
            for (int i = 0; i < datas.size(); i++) {
                addView(generateTextView(i, datas.get(i)));
            }

        }
    }

    // 设置关联的ViewPager
    public void setViewPager(ViewPager viewPager, int pos) {
        this.mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextViewColor();
                highLightTextView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // 设置当前页
        mViewPager.setCurrentItem(pos);
        // 高亮
        highLightTextView(pos);
    }

    /**
     * 高亮文本
     *
     * @param position
     */
    private void highLightTextView(int position) {
        View view = getChildAt(position);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(COLOR_TEXT_HIGHLIGHT);
        }
    }

    /**
     * 重置文本颜色
     */
    private void resetTextViewColor() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(COLOR_TEXT_NORMAL);
            }
        }
    }

    /**
     * 根据标题生成我们的TextView
     *
     * @param text
     * @return
     */
    private TextView generateTextView(int position, String text) {
        TextView tv = new TextView(getContext());
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / tabCount;
        tv.setTag(position);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(COLOR_TEXT_NORMAL);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setLayoutParams(lp);
        tv.setOnClickListener(mTabClickListener);
        return tv;
    }

    private final OnClickListener mTabClickListener = new OnClickListener() {
        public void onClick(View view) {
            if (mViewPager != null) {
                int oldSelected = mViewPager.getCurrentItem();
                int newSelected = (int) view.getTag();
                if (tabClickListener != null) {
                    if (oldSelected != newSelected) {
                        tabClickListener.onClick(newSelected);
                    } else {
                        tabClickListener.onReClick(newSelected);
                    }
                }
            }
        }
    };

    /**
     * 指示器跟随手指滚动，以及容器滚动
     *
     * @param position
     * @param offset
     */
    private void scroll(int position, float offset) {
        translateX = (int) (getWidth() / tabCount * (position + offset));
        invalidate();
    }

    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    private int dip2px(Context context, float dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5);
    }

    private OnTabClickListener tabClickListener;

    public void setOnTabClickListener(OnTabClickListener listener) {
        this.tabClickListener = listener;
    }

    public interface OnTabClickListener {
        void onClick(int position);

        void onReClick(int position);
    }
}
