package com.ifenglian.commonlib.widget.circlebanner;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: CircleBanner
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/1/13
 * 邮   箱: xiaofy@ifenglian.com
 */
public class CircleViewPager extends ViewPager {

    private int mSelectedIndex = 0;     // 当前下标
    private Handler mUIHandler;
    private List<String> mData = new ArrayList<>();

    public CircleViewPager(Context context) {
        this(context, null);
    }

    public CircleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void play(List<String> data) {
        if (data != null && data.size() > 0) {
            this.mData = data;
            mUIHandler = new Handler(Looper.getMainLooper());
            CircleViewPagerAdapter adapter = new CircleViewPagerAdapter(data, getContext());
            setAdapter(adapter);
            addOnPageChangeListener(mOnPageChangeListener);
            setCurrentItem(getInitPosition());
            if (data.size() >= 1) {
                startAdvertPlay();
            }
        }
    }

    /**
     * 轮播图片状态监听器
     */
    private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            Log.d("TAG", position + "");
            // 获取当前的位置
            mSelectedIndex = position;
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
                setCurrentItem(getInitPosition() + rightPos + 1, true);
            } else {
                // 常规执行这里
                setCurrentItem(mSelectedIndex + 1, true);
            }
        }
    };

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
}
