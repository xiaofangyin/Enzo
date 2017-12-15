package com.ifenglian.module_d.activity;

import android.content.Context;
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

    private int mLastPosition;
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
        tabList.add(tab1);
        tabList.add(tab2);
        tabList.add(tab3);
        tabList.add(tab4);
        for (int i = 0; i < tabList.size(); i++) {
            tabList.get(i).setOnClickListener(this);
            tabList.get(i).setTag(i);
        }
    }

    @Override
    public void onClick(View v) {
        int number = (int) v.getTag();
        if (number != mLastPosition) {
            setCurrentItem(number);
            if (mListener != null) {
                mListener.onTabClick(tabList.get(number), number);
            }
        } else {
            if (mListener != null) {
                mListener.onTabReClick(tabList.get(number), number);
            }
        }
    }

    public void setCurrentItem(int currentItem) {
        tabList.get(mLastPosition).setSelected(false);
        tabList.get(currentItem).setSelected(true);
        mLastPosition = currentItem;
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
