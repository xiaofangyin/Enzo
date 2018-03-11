package com.ifenglian.commonlib.widget.circlebanner;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ifenglian.commonlib.R;
import com.ifenglian.commonlib.utils.common.LogUtil;
import com.ifenglian.commonlib.utils.toast.ToastUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: CircleBanner
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/1/13
 * 邮   箱: xiaofy@ifenglian.com
 */
public class CircleBanner extends RelativeLayout {

    private int mSelectedIndex = 0;
    private Handler mUIHandler;
    private List<String> mData;
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
        mData = new ArrayList<>();
        viewPager = new ViewPager(context);
        viewPager.setOffscreenPageLimit(4);
//        viewPager.setPageTransformer(true, new CoverModeTransformer(viewPager));
//        LayoutParams layoutParams1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        layoutParams1.setMargins(dip2px(50), dip2px(30), dip2px(50), dip2px(30));
//        viewPager.setLayoutParams(layoutParams1);
        addView(viewPager);

        initViewPagerScroll();

        indicatorLayout = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        layoutParams.rightMargin = dip2px(10);
        layoutParams.bottomMargin = dip2px(10);
        indicatorLayout.setLayoutParams(layoutParams);
        addView(indicatorLayout);
    }

    /**
     * 设置ViewPager的滑动速度
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller mViewPagerScroller = new ViewPagerScroller(
                    viewPager.getContext());
            mScroller.set(viewPager, mViewPagerScroller);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void play(List<String> data) {
        this.mData = data;
        mUIHandler = new Handler(Looper.getMainLooper());
        CircleBannerAdapter adapter = new CircleBannerAdapter(data, getContext());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
        adapter.setOnImageClickListener(new ImageClickListener() {
            @Override
            public void onImageClick(String url, int position) {
                ToastUtils.showToast("position: " + position + " url:" + url);
            }
        });

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
                layoutParams.leftMargin = dip2px(5);
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
            mSelectedIndex = position;
            setIndicator(position % mData.size());
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            LogUtil.d("onPageScrollStateChanged: " + state);
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                startAdvertPlay();
            } else {
                stopAdvertPlay();
            }
        }
    };

    private Runnable timerTask = new Runnable() {
        @Override
        public void run() {
            if (mSelectedIndex == Integer.MAX_VALUE) {
                int rightPos = mSelectedIndex % mData.size();
                viewPager.setCurrentItem(getInitPosition() + rightPos + 1, false);
            } else if (mSelectedIndex == 0) {
                viewPager.setCurrentItem(getInitPosition() - 1, false);
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

    private int getInitPosition() {
        if (mData.isEmpty()) {
            return 0;
        }
        int halfValue = Integer.MAX_VALUE / 2;
        int position = halfValue % mData.size();
        return halfValue - position;
    }

    public interface ImageClickListener {
        void onImageClick(String url, int position);
    }

    private int dip2px(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (scale * dip + 0.5);
    }
}
