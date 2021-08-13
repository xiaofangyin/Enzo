package com.enzo.commonlib.widget.indicator.scroll;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.enzo.commonlib.R;
import com.enzo.commonlib.utils.common.DensityUtil;
import com.enzo.commonlib.utils.common.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MyIndicator
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/8
 * 邮   箱: xiaofangyinwork@163.com
 */
public class ScrollViewPagerIndicator extends HorizontalScrollView {

    private static final float scaleValue = 1.2f;
    private ArrayList<IndicatorBean> entities;
    private ViewPager mViewPager;
    private LinearLayout myLinearLayout;
    private OnTabClickListener tabClickListener;
    private Runnable mTabSelector;

    public ScrollViewPagerIndicator(Context context) {
        super(context);
        init(context);
    }

    public ScrollViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollViewPagerIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setHorizontalScrollBarEnabled(false);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        myLinearLayout = new LinearLayout(context);
        myLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        myLinearLayout.setGravity(Gravity.CENTER_VERTICAL);

        addView(myLinearLayout, new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
    }

    public ArrayList<IndicatorBean> getEntities() {
        return entities;
    }

    public String getCurrentId() {
        return entities.get(mViewPager.getCurrentItem()).getId() + "";
    }

    public void setTitles(List<IndicatorBean> list) {
        if (list != null && list.size() > 0) {
            entities = (ArrayList<IndicatorBean>) list;
            myLinearLayout.removeAllViews();
            for (int i = 0; i < list.size(); i++) {
                addTab(i, list.get(i), i == list.size() - 1, i == 0);
            }
            requestLayout();
        }
    }

    public void bindViewPager(ViewPager viewPager) {
        if (mViewPager == viewPager) {
            return;
        }
        mViewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.d("scroll onPageSelected position: " + position);
                highLightTextView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addTab(int index, IndicatorBean bean, boolean lastItem, boolean isSelected) {
        TextView tabView = new TextView(getContext());
        tabView.setGravity(Gravity.CENTER);
        tabView.setTag(index);
        tabView.setFocusable(true);
        tabView.setOnClickListener(mTabClickListener);
        tabView.setText(bean.getTitle());
        tabView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tabView.setTextColor(ContextCompat.getColor(getContext(),
                isSelected ? R.color.color_red : R.color.color_333));
        tabView.setScaleX(isSelected ? scaleValue : 1f);
        tabView.setScaleY(isSelected ? scaleValue : 1f);
        int paddingLeft = DensityUtil.dip2px(getContext(), index == 0 ? 16 : 10);
        int paddingRight = DensityUtil.dip2px(getContext(), lastItem ? 60 : 10);
        tabView.setPadding(paddingLeft, 0, paddingRight, 0);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tabView.setLayoutParams(layoutParams);
        myLinearLayout.addView(tabView);
    }

    /**
     * 被选中的动画
     */
    private void animation(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", scaleValue);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", scaleValue);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(scaleX).with(scaleY);
        animSet.setDuration(250);
        animSet.start();
    }

    /**
     * 没选中的动画
     */
    private void animation2(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(scaleX).with(scaleY);
        animSet.setDuration(250);
        animSet.start();
    }

    private void highLightTextView(int item) {
        if (mViewPager == null) {
            return;
        }
        for (int i = 0; i < myLinearLayout.getChildCount(); i++) {
            TextView child = (TextView) myLinearLayout.getChildAt(i);
            boolean isSelected = (i == item);
            if (isSelected) {
                child.setTextColor(ContextCompat.getColor(getContext(), R.color.color_red));
                animation(child);
                animateToTab(item, true);
            } else {
                child.setTextColor(ContextCompat.getColor(getContext(), R.color.color_333));
                animation2(child);
            }
        }
    }

    private void animateToTab(final int position, final boolean animate) {
        final View tabView = myLinearLayout.getChildAt(position);
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            public void run() {
                int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
                if (animate) {
                    smoothScrollTo(scrollPos, 0);
                } else {
                    scrollTo(scrollPos, 0);
                }
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }

    private final OnClickListener mTabClickListener = new OnClickListener() {
        public void onClick(View view) {
            if (mViewPager != null) {
                int oldSelected = mViewPager.getCurrentItem();
                int newSelected = (int) view.getTag();
                if (tabClickListener != null) {
                    if (oldSelected != newSelected) {
                        mViewPager.setCurrentItem(newSelected);
                        tabClickListener.onClick(newSelected);
                    } else {
                        tabClickListener.onReClick(newSelected);
                    }
                }
            }
        }
    };

    public void setOnTabClickListener(OnTabClickListener listener) {
        tabClickListener = listener;
    }

    public interface OnTabClickListener {
        void onClick(int position);

        void onReClick(int position);
    }
}