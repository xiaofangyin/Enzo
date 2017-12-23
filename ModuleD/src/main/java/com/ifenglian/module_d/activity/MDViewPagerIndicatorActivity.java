package com.ifenglian.module_d.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.commonlib.widget.view.viewpagerindicator.ViewPagerIndicator;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.adapter.MDViewPagerIndicatorAdapter;
import com.ifenglian.module_d.fragment.MDViewPagerFragment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文 件 名: SAMainActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDViewPagerIndicatorActivity extends BaseActivity {

    private List<String> itemTitles = Arrays.asList("短信", "收藏", "推荐", "发现");
    private ViewPager mViewPager;
    private ViewPagerIndicator mIndicator;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_indicator;
    }

    @Override
    public void initView() {
        mViewPager = findViewById(R.id.view_pager);
        mIndicator = findViewById(R.id.indicator);
    }

    @Override
    public void initData() {
        FragmentPagerAdapter mAdapter = new MDViewPagerIndicatorAdapter(getSupportFragmentManager(), getFragments());
        mViewPager.setAdapter(mAdapter);
        //设置Tab上的标题
        mIndicator.setTabItemTitles(itemTitles);
        //设置关联的ViewPager
        mIndicator.setViewPager(mViewPager, 0);
    }

    @Override
    public void initListener() {
        mIndicator.setOnPageChangeListener(new ViewPagerIndicator.PageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("AAA", "SAMainActivity onNewIntent...");
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            fragments.add(new MDViewPagerFragment1());
        }
        return fragments;
    }
}
