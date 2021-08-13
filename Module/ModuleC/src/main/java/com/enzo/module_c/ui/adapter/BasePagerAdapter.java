package com.enzo.module_c.ui.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * 文 件 名: BasePagerAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/10
 * 邮   箱: xiaofangyinwork@163.com
 */
public abstract class BasePagerAdapter extends FragmentPagerAdapter {

    public FragmentManager mFragmentManager;

    public BasePagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mFragmentManager = fm;
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
        mFragmentManager.executePendingTransactions();
        return fragment;
    }

    @Override
    public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {
        Fragment fragment = getItem(position);
        mFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
        mFragmentManager.executePendingTransactions();
    }
}