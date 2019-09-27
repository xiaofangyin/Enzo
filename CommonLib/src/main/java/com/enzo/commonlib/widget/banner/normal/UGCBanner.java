package com.enzo.commonlib.widget.banner.normal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.enzo.commonlib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: CircleBanner  375 / 200
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/3/16
 * 邮   箱: xiaofangyinwork@163.com
 */
public class UGCBanner extends RelativeLayout {

    private int mSelectedIndex = 0;
    private Handler mUIHandler;
    private List<BannerBean> mData;
    private ViewPager viewPager;
    private LinearLayout indicatorLayout;
    private ImageView[] indicators;
    private OnBannerClickListener mClickListener;
    private UGCBannerAdapter adapter;

    public UGCBanner(Context context) {
        this(context, null);
    }

    public UGCBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UGCBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mUIHandler = new Handler(Looper.getMainLooper());
        mData = new ArrayList<>();
        viewPager = new UGCViewPager(context);
        viewPager.setOffscreenPageLimit(4);
        addView(viewPager);

        indicatorLayout = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.bottomMargin = dip2px(10);
        indicatorLayout.setLayoutParams(layoutParams);
        addView(indicatorLayout);

        adapter = new UGCBannerAdapter(getContext());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    public void play(List<BannerBean> data) {
        if (data != null && data.size() > 0) {
            if (mData.size() != data.size()) {
                stopAdvertPlay();
                mData = data;
                adapter = new UGCBannerAdapter(getContext());
                adapter.setNewData(mData);
                viewPager.setAdapter(adapter);
                adapter.setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void onBannerClick(BannerBean bean) {
                        if (mClickListener != null) {
                            mClickListener.onBannerClick(bean);
                        }
                    }
                });

                indicatorLayout.removeAllViews();
                indicators = new ImageView[data.size()];
                if (mData.size() > 1) {
                    for (int i = 0; i < indicators.length; i++) {
                        ImageView imageView = new ImageView(getContext());
                        imageView.setImageResource(R.drawable.lib_selector_banner_indicator);
                        indicators[i] = imageView;
                        indicatorLayout.addView(imageView);
                        if (i != 0) {
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.leftMargin = dip2px(6);
                            indicators[i].setLayoutParams(layoutParams);
                        }
                    }
                    setIndicator(0);
                    startAdvertPlay();
                }

                viewPager.setCurrentItem(getInitPosition());
            } else {
                mData = data;
                adapter.setNewData(mData);

                adapter.setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void onBannerClick(BannerBean bean) {
                        if (mClickListener != null) {
                            mClickListener.onBannerClick(bean);
                        }
                    }
                });
            }
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
            if (mSelectedIndex == Short.MAX_VALUE - 1 || mSelectedIndex == 0) {
                viewPager.setCurrentItem(getInitPosition(), false);
                setIndicator(0);
                startAdvertPlay();
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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAdvertPlay();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAdvertPlay();
    }

    private void setIndicator(int selectedPosition) {
        if (indicators != null) {
            for (int i = 0; i < indicators.length; i++) {
                if (indicators[i] != null) {
                    indicators[i].setEnabled(i == selectedPosition);
                }
            }
        }
    }

    private int getInitPosition() {
        if (mData.isEmpty()) {
            return 0;
        }
        int halfValue = Short.MAX_VALUE / 2;
        int position = halfValue % mData.size();
        return halfValue - position;
    }

    public interface OnBannerClickListener {
        void onBannerClick(BannerBean bean);
    }

    public void setOnBannerClickListener(OnBannerClickListener clickListener) {
        mClickListener = clickListener;
    }

    private int dip2px(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (scale * dip + 0.5);
    }
}
