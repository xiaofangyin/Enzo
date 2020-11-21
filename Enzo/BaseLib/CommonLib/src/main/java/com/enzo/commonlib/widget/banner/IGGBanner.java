package com.enzo.commonlib.widget.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

import com.enzo.commonlib.widget.banner.transformer.CoverModeTransformer;
import com.enzo.commonlib.widget.banner.transformer.HorizontalStackTransformerWithRotation;
import com.enzo.commonlib.widget.banner.transformer.RotationPageTransformer;
import com.enzo.commonlib.widget.banner.transformer.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: IGGBanner
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/3/16
 * 邮   箱: xiaofangyinwork@163.com
 */
public class IGGBanner extends RelativeLayout {

    private int mSelectedIndex = 0;
    private Handler mUIHandler;
    private List<IGGBannerBean> mData;
    private CustomViewPager viewPager;
    private LinearLayout indicatorLayout;
    private ImageView[] indicators;
    private OnBannerClickListener mClickListener;
    private IGGBaseBannerAdapter adapter;

    public enum IndicatorAlign {
        LEFT,//左对齐
        CENTER,//居中对齐
        RIGHT //右对齐
    }

    public IGGBanner(Context context) {
        this(context, null);
    }

    public IGGBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IGGBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mUIHandler = new Handler(Looper.getMainLooper());
        mData = new ArrayList<>();
        viewPager = new CustomViewPager(context);
        viewPager.setOffscreenPageLimit(4);
        addView(viewPager);

        indicatorLayout = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.bottomMargin = dip2px(10);
        indicatorLayout.setLayoutParams(layoutParams);
        addView(indicatorLayout);
    }

    public void setAdapter(IGGBaseBannerAdapter adapter) {
        this.adapter = adapter;
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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
        });
    }

    public void setIndicatorAlign(IndicatorAlign align) {
        RelativeLayout.LayoutParams lp;
        if (indicatorLayout.getLayoutParams() != null) {
            lp = (LayoutParams) indicatorLayout.getLayoutParams();
        } else {
            lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        if (align == IndicatorAlign.LEFT) {
            lp.addRule(RelativeLayout.ALIGN_PARENT_START);
            lp.leftMargin = dip2px(12);
        } else if (align == IndicatorAlign.CENTER) {
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        } else if (align == IndicatorAlign.RIGHT) {
            lp.addRule(RelativeLayout.ALIGN_PARENT_END);
            lp.rightMargin = dip2px(12);
        }
        lp.bottomMargin = dip2px(10);
        indicatorLayout.setLayoutParams(lp);
    }

    public void play(List<IGGBannerBean> data) {
        if (adapter == null) return;

        if (data != null && data.size() > 0) {
            stopAdvertPlay();
            mData = data;
            adapter.setNewData(mData);
            viewPager.setAdapter(adapter);
            adapter.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void onBannerClick(IGGBannerBean bean) {
                    if (mClickListener != null) {
                        mClickListener.onBannerClick(bean);
                    }
                }
            });

            indicatorLayout.removeAllViews();
            if (mData.size() > 1) {
                indicators = new ImageView[data.size()];
                for (int i = 0; i < indicators.length; i++) {
                    ImageView imageView = new ImageView(getContext());
                    imageView.setImageResource(adapter.getIndicatorResource());
                    indicators[i] = imageView;
                    indicatorLayout.addView(imageView);
                    if (i != 0) {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.leftMargin = dip2px(6);
                        indicators[i].setLayoutParams(layoutParams);
                    }
                }
                setIndicator(0);
                startAdvertPlay();
            }

            viewPager.setCurrentItem(getInitPosition());
        }
    }

    private final Runnable timerTask = new Runnable() {
        @Override
        public void run() {
            mSelectedIndex++;
            if (mSelectedIndex == adapter.getCount() - 1 || mSelectedIndex == 1) {
                mSelectedIndex = getInitPosition();
                viewPager.setCurrentItem(mSelectedIndex, false);
                setIndicator(0);
                startAdvertPlay();
            } else {
                // 常规执行这里
                viewPager.setCurrentItem(mSelectedIndex, true);
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
        int halfValue = adapter.getCount() / 2;
        int remainder = halfValue % mData.size();
        return halfValue - remainder;
    }

    //////////////////////////////////////////////设置Style//////////////////////////////////////////////
    public void setMeiZuStyle() {
        setClipChildren(false);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = dip2px(30);
        layoutParams.rightMargin = dip2px(30);
        viewPager.setLayoutParams(layoutParams);
        viewPager.setClipChildren(false);
        viewPager.setPageTransformer(true, new CoverModeTransformer(viewPager));
    }

    public void setZoomOutStyle() {
        setClipChildren(false);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = dip2px(30);
        layoutParams.rightMargin = dip2px(30);
        viewPager.setLayoutParams(layoutParams);
        viewPager.setClipChildren(false);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    public void setHorizontalStackStyle(int offscreenPageLimit) {
        viewPager.setOffscreenPageLimit(offscreenPageLimit);
        viewPager.setPageTransformer(true, new HorizontalStackTransformerWithRotation(viewPager));
    }

    public void setRotationStyle() {
        viewPager.setPageTransformer(true, new RotationPageTransformer());
    }

    /**
     * 默认情况clipToPadding为true,也就是把padding中的值进行裁剪
     */
    public void setNotClipToPadding(int padding, int pageMargin) {
        viewPager.setClipToPadding(false);
        viewPager.setPadding(padding, 0, padding, 0);
        viewPager.setPageMargin(pageMargin);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    public interface OnBannerClickListener {
        void onBannerClick(IGGBannerBean bean);
    }

    public void setOnBannerClickListener(OnBannerClickListener clickListener) {
        mClickListener = clickListener;
    }

    private int dip2px(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (scale * dip + 0.5);
    }
}
