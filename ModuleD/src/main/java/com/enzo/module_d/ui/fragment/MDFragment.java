package com.enzo.module_d.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.utils.statusbar.utils.StatusBarUtils;
import com.enzo.commonlib.widget.indicator.noscroll.ViewPagerIndicator;
import com.enzo.flkit.router.ModuleDRouterPath;
import com.enzo.module_d.R;
import com.enzo.module_d.ui.adapter.MDViewPagerIndicatorAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文 件 名: MDFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
@Route(path = ModuleDRouterPath.MODULE_D_FRAGMENT)
public class MDFragment extends BaseFragment {

    private List<String> itemTitles = Arrays.asList("短信", "收藏", "推荐", "发现");
    private ViewPager mViewPager;
    private ViewPagerIndicator mIndicator;

    @Override
    public int getLayoutId() {
        return R.layout.md_fragment_indicator;
    }

    @Override
    public void initView(View rootView) {
        View view = new View(rootView.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                StatusBarUtils.getStatusBarHeight(rootView.getContext()));
        view.setLayoutParams(layoutParams);
        ((ViewGroup)rootView).addView(view,0);

        mViewPager = rootView.findViewById(R.id.view_pager);
        mIndicator = rootView.findViewById(R.id.indicator);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Context context = getContext();
        if (context != null) {
            mIndicator.setNormalColor(ContextCompat.getColor(context, com.enzo.commonlib.R.color.color_666));
            mIndicator.setHighlightColor(ContextCompat.getColor(context, com.enzo.commonlib.R.color.color_333));
            mIndicator.setIndicatorColor(ContextCompat.getColor(context, com.enzo.commonlib.R.color.color_333));
        }
        FragmentPagerAdapter mAdapter = new MDViewPagerIndicatorAdapter(getChildFragmentManager(), getFragments());
        mViewPager.setAdapter(mAdapter);
        //设置Tab上的标题
        mIndicator.setTabItemTitles(itemTitles);
        //设置关联的ViewPager
        mIndicator.setViewPager(mViewPager, 0);
    }

    @Override
    public void initListener(View rootView) {
        mIndicator.setOnTabClickListener(new ViewPagerIndicator.OnTabClickListener() {
            @Override
            public void onClick(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onReClick(int position) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
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
