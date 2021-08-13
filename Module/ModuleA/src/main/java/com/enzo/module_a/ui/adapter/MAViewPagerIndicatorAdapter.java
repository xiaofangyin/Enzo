package com.enzo.module_a.ui.adapter;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 文 件 名: SAHomeFragmentAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/16
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MAViewPagerIndicatorAdapter extends FragmentPagerAdapter {

    private FragmentManager fragmentManager;
    private List<Fragment> fragments;

    public MAViewPagerIndicatorAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        fragments = list;
        fragmentManager = fm;
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {
        Fragment fragment = fragments.get(position);
        fragmentManager.beginTransaction().hide(fragment).commit();
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragments.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("id", "" + position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}