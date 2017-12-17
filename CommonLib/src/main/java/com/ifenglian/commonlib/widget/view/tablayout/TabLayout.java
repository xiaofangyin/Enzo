package com.ifenglian.commonlib.widget.view.tablayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import com.ifenglian.commonlib.R;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: TabLayout
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class TabLayout extends FrameLayout implements View.OnClickListener {

    private boolean isShow = true;
    private int mLastPosition = 0;
    private List<TabButton> mTabList;

    private String mTitles[] = {"家庭", "安全", "发现", "我"};
    private int mIconRes[][] = {
            {R.mipmap.sa_tab_home_normal, R.mipmap.sa_tab_home_select},
            {R.mipmap.sa_tab_security_normal, R.mipmap.sa_tab_security_select},
            {R.mipmap.sa_tab_find_normal, R.mipmap.sa_tab_find_select},
            {R.mipmap.sa_tab_personalcenter_normal, R.mipmap.sa_tab_personalcenter_select}
    };
    private ObjectAnimator objectAnimator;

    public TabLayout(Context context) {
        this(context, null);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mTabList = new ArrayList<>();
        View view = LayoutInflater.from(context).inflate(R.layout.lib_tab_layout, this);
        TabButton tab1 = view.findViewById(R.id.tab_first);
        TabButton tab2 = view.findViewById(R.id.tab_second);
        TabButton tab3 = view.findViewById(R.id.tab_third);
        TabButton tab4 = view.findViewById(R.id.tab_fourth);
        mTabList.add(tab1);
        mTabList.add(tab2);
        mTabList.add(tab3);
        mTabList.add(tab4);
        for (int i = 0; i < mTabList.size(); i++) {
            mTabList.get(i).initTab(mTitles[i], mIconRes[i][0], mIconRes[i][1]);
            mTabList.get(i).setOnClickListener(this);
            mTabList.get(i).setTag(i);
        }
    }

    @Override
    public void onClick(View v) {
        int number = (int) v.getTag();
        if (number != mLastPosition) {
            setCurrentItem(number, true);
            if (mListener != null) {
                mListener.onTabClick(mTabList.get(number), number);
            }
        } else {
            if (mListener != null) {
                mListener.onTabReClick(mTabList.get(number), number);
            }
        }
    }

    public void setCurrentItem(int currentItem, boolean animate) {
        mTabList.get(mLastPosition).setSelected(false, animate);
        mTabList.get(currentItem).setSelected(true, animate);
        mLastPosition = currentItem;
    }

    public void addMessageNum(int position, int messageNum) {
        if (position >= 0 && position < mTabList.size()) {
            mTabList.get(position).addMessageNumber(messageNum);
        }
    }

    public void showRedPoint(int position, boolean showRedPoint) {
        if (position >= 0 && position < mTabList.size()) {
            mTabList.get(position).showRedPoint(showRedPoint);
        }
    }

    public void showTabLayout() {
        if (!isShow) {
            isShow = true;
            getObjectAnimator().reverse();
        }
    }

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
        void onTabClick(View view, int position);

        void onTabReClick(View view, int position);
    }
}
