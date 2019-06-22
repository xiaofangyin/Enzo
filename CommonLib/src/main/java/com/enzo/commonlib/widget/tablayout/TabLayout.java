package com.enzo.commonlib.widget.tablayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: TabLayout
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofangyinwork@163.com
 */
public class TabLayout extends LinearLayout implements ITabLayout, View.OnClickListener {

    private boolean isShow = true;
    private int mLastPosition = -1;
    private List<TabView> mTabList;
    private ObjectAnimator objectAnimator;

    public TabLayout(Context context) {
        this(context, null);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.HORIZONTAL);
        mTabList = new ArrayList<>();
    }

    @Override
    public void initData(List<TabEntity> list) {
        for (int i = 0; i < list.size(); i++) {
            TabView tab = new TabView(getContext());
            tab.initTab(list.get(i));
            tab.setOnClickListener(this);
            tab.setTag(i);

            LayoutParams layoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            tab.setLayoutParams(layoutParams);
            addView(tab);
            mTabList.add(tab);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (position != mLastPosition) {
            setCurrentItem(position);
            if (mListener != null) {
                mListener.onTabClick(mTabList.get(position), position);
            }
        } else {
            if (mListener != null) {
                mListener.onTabReClick(mTabList.get(position), position);
            }
        }
    }

    @Override
    public void setCurrentItem(int currentItem) {
        if (mLastPosition >= 0 && mLastPosition < mTabList.size()) {
            mTabList.get(mLastPosition).setSelected(false);
        }
        if (currentItem >= 0 && currentItem < mTabList.size()) {
            mTabList.get(currentItem).setSelected(true);
            mLastPosition = currentItem;
        }
    }

    @Override
    public int getCurrentItem() {
        return mLastPosition;
    }

    @Override
    public void addMessageNum(int position, int messageNum) {
        if (position >= 0 && position < mTabList.size()) {
            mTabList.get(position).addMessageNumber(messageNum);
        }
    }

    @Override
    public void setMessageNum(int position, int messageNum) {
        if (position >= 0 && position < mTabList.size()) {
            mTabList.get(position).setMessageNumber(messageNum);
        }
    }

    @Override
    public void resetMessageNum(int position) {
        if (position >= 0 && position < mTabList.size()) {
            mTabList.get(position).resetMessageNumber();
        }
    }

    @Override
    public int getMessageNum(int position) {
        if (position >= 0 && position < mTabList.size()) {
            return mTabList.get(position).getMessageNumber();
        }
        return 0;
    }

    @Override
    public void showRedPoint(int position) {
        if (position >= 0 && position < mTabList.size()) {
            mTabList.get(position).showRedPoint(true);
        }
    }

    @Override
    public void hideRedPoint(int position) {
        if (position >= 0 && position < mTabList.size()) {
            mTabList.get(position).showRedPoint(false);
        }
    }

    @Override
    public void showTabLayout() {
        if (!isShow) {
            isShow = true;
            getObjectAnimator().reverse();
        }
    }

    @Override
    public void hideTabLayout() {
        if (isShow) {
            isShow = false;
            getObjectAnimator().start();
        }
    }

    private ObjectAnimator getObjectAnimator() {
        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofFloat(
                    TabLayout.this,
                    "translationY",
                    0,
                    TabLayout.this.getHeight());
            objectAnimator.setDuration(300);
            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        }
        return objectAnimator;
    }

    private OnTabClickListener mListener;

    public void setOnTabClickListener(OnTabClickListener listener) {
        mListener = listener;
    }

    public interface OnTabClickListener {
        void onTabClick(TabView view, int position);

        void onTabReClick(TabView view, int position);
    }
}
