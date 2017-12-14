package com.ifenglian.module_d.adapter;

import android.support.v4.app.*;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by MJJ on 2015/7/29.
 */
public class PagerAdapter extends FragmentPagerAdapter{


    private List<Fragment> mFragmentList;

    public PagerAdapter(FragmentManager fm, List<Fragment> fragList) {
        super(fm);
        mFragmentList=fragList;
    }

    @Override
    public Fragment getItem(int arg0) {
        return mFragmentList.get(arg0);
    }
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
