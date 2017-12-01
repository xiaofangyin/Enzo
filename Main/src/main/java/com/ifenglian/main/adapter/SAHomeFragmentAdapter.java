package com.ifenglian.main.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * 文 件 名: SAHomeFragmentAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/16
 * 邮   箱: xiaofy@ifenglian.com
 */
public class SAHomeFragmentAdapter extends FragmentPagerAdapter {

    private FragmentManager fragmentManager;
    private List<Fragment> fragments;

    public SAHomeFragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        fragments = list;
        fragmentManager = fm;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = fragments.get(position);
        fragmentManager.beginTransaction().hide(fragment).commit();
    }

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