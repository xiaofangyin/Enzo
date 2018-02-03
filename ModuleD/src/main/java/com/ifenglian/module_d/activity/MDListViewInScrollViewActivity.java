package com.ifenglian.module_d.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.fragment.TabFragment;

/**
 * 文 件 名: MDListViewInScrollViewActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/2/2
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDListViewInScrollViewActivity extends BaseActivity {
    private String[] mTitles = new String[]{"啦啦啦"};
    private ViewPager mViewPager;
    private TabFragment[] mFragments = new TabFragment[mTitles.length];

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_listview_in_scrollview;
    }

    @Override
    public void initView() {
        mViewPager = findViewById(R.id.id_stickynavlayout_viewpager);
    }

    @Override
    public void initData() {
        for (int i = 0; i < mTitles.length; i++) {
            mFragments[i] = TabFragment.newInstance(mTitles[i]);
        }

        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void initListener() {

    }
}
