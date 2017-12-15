package com.ifenglian.module_d.activity;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.ifenglian.module_d.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: TabLayout
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class TabLayout extends FrameLayout implements View.OnClickListener {

    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;
    private int mLastPosition;

    private String mTitles[] = {"家庭", "安全", "发现", "我"};
    private int mIconRes[][] = {
            {R.mipmap.sa_tab_home_normal, R.mipmap.sa_tab_home_select},
            {R.mipmap.sa_tab_security_normal, R.mipmap.sa_tab_security_select},
            {R.mipmap.sa_tab_find_normal, R.mipmap.sa_tab_find_select},
            {R.mipmap.sa_tab_personalcenter_normal, R.mipmap.sa_tab_personalcenter_select}
    };

    private List<TabButton> tabList;

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
        tabList = new ArrayList<>();
        View view = LayoutInflater.from(context).inflate(R.layout.layout_bottomtab, this);
        TabButton tab1 = view.findViewById(R.id.tab_first);
        TabButton tab2 = view.findViewById(R.id.tab_second);
        TabButton tab3 = view.findViewById(R.id.tab_third);
        TabButton tab4 = view.findViewById(R.id.tab_fourth);
        tab1.init(mTitles[0], mIconRes[0][0], mIconRes[0][1]);
        tab2.init(mTitles[1], mIconRes[1][0], mIconRes[1][1]);
        tab3.init(mTitles[2], mIconRes[2][0], mIconRes[2][1]);
        tab4.init(mTitles[3], mIconRes[3][0], mIconRes[3][1]);
        tabList.add(tab1);
        tabList.add(tab2);
        tabList.add(tab3);
        tabList.add(tab4);
        for (int i = 0; i < tabList.size(); i++) {
            tabList.get(i).setOnClickListener(this);
            tabList.get(i).setTag(i);
        }
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        if (viewPager != null && viewPager.getAdapter() != null) {
            viewPager.addOnPageChangeListener(new InternalViewPagerListener());
        }
    }

    @Override
    public void onClick(View v) {
        int number = (int) v.getTag();
        tabList.get(mLastPosition).setSelected(false);
        tabList.get(number).setSelected(true);
        mLastPosition = number;
        mViewPager.setCurrentItem(mLastPosition, false);
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            tabList.get(mLastPosition).setSelected(false);
            tabList.get(position).setSelected(true);
            mLastPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mViewPagerPageChangeListener = listener;
    }
}
