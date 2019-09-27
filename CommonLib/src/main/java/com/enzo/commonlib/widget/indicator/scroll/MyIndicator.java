package com.enzo.commonlib.widget.indicator.scroll;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enzo.commonlib.utils.common.DensityUtil;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MyIndicator
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/8
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MyIndicator extends HorizontalScrollView {

    private ArrayList<IndicatorBean> entities;
    private ViewPager mViewPager;
    private LinearLayout myLinearLayout;
    private OnTabClickListener tabClickListener;
    private Runnable mTabSelector;

    public MyIndicator(Context context) {
        super(context);
        init(context);
    }

    public MyIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyIndicator(Context context, AttributeSet attrs, int defStyle) {
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

    public void setTitles(int selectedPosition, List<IndicatorBean> list) {
        if (list != null && list.size() > 0) {
            entities = (ArrayList<IndicatorBean>) list;
            myLinearLayout.removeAllViews();
            for (int i = 0; i < list.size(); i++) {
                addTab(i, list.get(i), i == list.size() - 1, i == selectedPosition);
            }
            requestLayout();
        }
    }

    public void setViewPager(ViewPager viewPager) {
        if (mViewPager == viewPager || viewPager.getAdapter() == null) {
            return;
        }
        mViewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurrentItem(position, true);
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
        tabView.setTextColor(getContext().getResources().getColor(isSelected ? com.enzo.commonlib.R.color.color_green : com.enzo.commonlib.R.color.color_333));
        ViewHelper.setScaleX(tabView, isSelected ? 1.1f : 1f);
        ViewHelper.setScaleY(tabView, isSelected ? 1.1f : 1f);

        int paddingLeft = DensityUtil.dip2px(index == 0 ? 16 : 10);
        int paddingRight = DensityUtil.dip2px(lastItem ? 60 : 10);
        tabView.setPadding(paddingLeft, 0, paddingRight, 0);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tabView.setLayoutParams(layoutParams);
        myLinearLayout.addView(tabView);
    }

    /**
     * 被选中的动画
     */
    private void animation(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.1f);
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

    public void setCurrentItem(int item, boolean animate) {
        if (mViewPager == null) {
            return;
        }
        mViewPager.setCurrentItem(item, animate);
        for (int i = 0; i < myLinearLayout.getChildCount(); i++) {
            TextView child = (TextView) myLinearLayout.getChildAt(i);
            boolean isSelected = (i == item);
            if (isSelected) {
                child.setTextColor(getContext().getResources().getColor(com.enzo.commonlib.R.color.color_green));
                animation(child);
                animateToTab(item, animate);
            } else {
                child.setTextColor(getContext().getResources().getColor(com.enzo.commonlib.R.color.color_333));
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