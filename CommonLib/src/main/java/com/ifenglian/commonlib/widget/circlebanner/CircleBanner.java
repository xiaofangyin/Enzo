package com.ifenglian.commonlib.widget.circlebanner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ifenglian.commonlib.R;

import java.util.List;

/**
 * 文 件 名: CircleBanner
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/1/13
 * 邮   箱: xiaofy@ifenglian.com
 */
public class CircleBanner extends RelativeLayout {

    private CircleViewPager viewPager;
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
        viewPager = new CircleViewPager(context);
        addView(viewPager, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        indicatorLayout = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.bottomMargin = dip2px(context, 10);
        indicatorLayout.setLayoutParams(layoutParams);
        addView(indicatorLayout);
    }

    public void play(final List<String> data) {
        viewPager.play(data);

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
        setIndicator(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position % data.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setIndicator(int selectedPosition) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setEnabled(i == selectedPosition);
        }
    }

    private int dip2px(Context context, int dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dip + 0.5);
    }
}
