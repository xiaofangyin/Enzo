package com.ifenglian.module_d.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ifenglian.commonlib.base.BaseFragment;
import com.ifenglian.commonlib.widget.view.viewpagerindicator.ViewPagerIndicator;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.adapter.MDViewPagerIndicatorAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文 件 名: MDFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDFragment extends BaseFragment {

    private AnimationDrawable anim;
    private List<String> itemTitles = Arrays.asList("短信", "收藏", "推荐", "发现");
    private ViewPager mViewPager;
    private ViewPagerIndicator mIndicator;

    @Override
    public int getLayoutId() {
        return R.layout.md_fragment_indicator;
    }

    @Override
    public void initView(View rootView) {
        anim = (AnimationDrawable) rootView.getBackground();
        anim.setEnterFadeDuration(2000);
        anim.setExitFadeDuration(1000);
        mViewPager = rootView.findViewById(R.id.view_pager);
        mIndicator = rootView.findViewById(R.id.indicator);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        FragmentPagerAdapter mAdapter = new MDViewPagerIndicatorAdapter(getChildFragmentManager(), getFragments());
        mViewPager.setAdapter(mAdapter);
        //设置Tab上的标题
        mIndicator.setTabItemTitles(itemTitles);
        //设置关联的ViewPager
        mIndicator.setViewPager(mViewPager, 0);
    }

    @Override
    public void initListener(View rootView) {
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
    public void onResume() {
        super.onResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MDViewPagerFragment1());
        fragments.add(new MDViewPagerFragment2());
        fragments.add(new MDViewPagerFragment3());
        fragments.add(new MDViewPagerFragment4());
        return fragments;
    }
}
