package com.ifenglian.commonlib.widget.circlebanner;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ifenglian.commonlib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: CircleBanner
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/1/13
 * 邮   箱: xiaofy@ifenglian.com
 */
public class CircleBanner extends RelativeLayout {

    private int mSelectedIndex = 0;     // 当前下标
    private Handler mUIHandler;
    private List<String> mData = new ArrayList<>();
    private ViewPager viewPager;
    private LinearLayout indicatorLayout;
    private ImageView[] indicators;

    public CircleBanner(Context context) {
        this(context, null);
    }

    public CircleBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        viewPager = new ViewPager(context);
        addView(viewPager, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        indicatorLayout = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.bottomMargin = dip2px(context, 10);
        indicatorLayout.setLayoutParams(layoutParams);
        addView(indicatorLayout);
    }

    public void play(List<String> data) {
        this.mData = data;
        mUIHandler = new Handler(Looper.getMainLooper());
        CircleViewPagerAdapter adapter = new CircleViewPagerAdapter(data, getContext());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(mOnPageChangeListener);

        // 设置指示器
        indicators = new ImageView[data.size()];
        indicatorLayout.removeAllViews();
        for (int i = 0; i < indicators.length; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.lib_selector_circler_view_pager_indicator);
            indicators[i] = imageView;
            indicatorLayout.addView(imageView);
            if (i != 0) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.leftMargin = dip2px(getContext(), 5);
                indicators[i].setLayoutParams(layoutParams);
            }
        }

        viewPager.setCurrentItem(getInitPosition());
        setIndicator(0);
        if (data.size() >= 1) {
            startAdvertPlay();
        }
    }

    /**
     * 轮播图片状态监听器
     */
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            Log.d("TAG", position + "");
            // 获取当前的位置
            mSelectedIndex = position;
            setIndicator(position % mData.size());
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                startAdvertPlay();
            } else {
                stopAdvertPlay();
            }
        }
    };

    /**
     * 自动播放任务
     */
    private Runnable timerTask = new Runnable() {
        @Override
        public void run() {
            if (mSelectedIndex == Integer.MAX_VALUE) {
                // 当滑到最右边时，返回返回第一个元素
                // 当然，几乎不可能滑到
                int rightPos = mSelectedIndex % mData.size();
                viewPager.setCurrentItem(getInitPosition() + rightPos + 1, true);
            } else {
                // 常规执行这里
                viewPager.setCurrentItem(mSelectedIndex + 1, true);
            }
        }
    };

    /**
     * 开始广告滚动任务
     */
    private void startAdvertPlay() {
        stopAdvertPlay();
        mUIHandler.postDelayed(timerTask, 3000);
    }

    /**
     * 停止广告滚动任务
     */
    private void stopAdvertPlay() {
        mUIHandler.removeCallbacks(timerTask);
    }

    private void setIndicator(int selectedPosition) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setEnabled(i == selectedPosition);
        }
    }

    /**
     * 获取banner的初始位置,即0-Integer.MAX_VALUE之间的大概中间位置
     * 保证初始位置和数据源的第1个元素的取余为0
     *
     * @return position
     */
    private int getInitPosition() {
        if (mData.isEmpty()) {
            return 0;
        }
        int halfValue = Integer.MAX_VALUE / 2;
        int position = halfValue % mData.size();
        // 保证初始位置和数据源的第1个元素的取余为0
        return halfValue - position;
    }

    private int dip2px(Context context, int dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dip + 0.5);
    }
}
