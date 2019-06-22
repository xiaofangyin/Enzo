package com.enzo.module_c.ui.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 文 件 名: BasePagerAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/10
 * 邮   箱: xiaofangyinwork@163.com
 */
public abstract class BasePagerAdapter extends PagerAdapter {

    final FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;

    BasePagerAdapter(FragmentManager mFragmentManager) {
        this.mFragmentManager = mFragmentManager;
    }

    public abstract Fragment getItem(int position);

    /**
     * 作为fragment的标识,每个fragment都有单独的标识，不能直接用position，否则在排序和删除会出现fragment错乱
     */
    public abstract String getItemId(int position);

    @Override
    public void startUpdate(ViewGroup container) {

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        String itemId = getItemId(position);

        String name = makeFragmentName(itemId);
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            mCurTransaction.show(fragment);
        } else {
            fragment = getItem(position);
            mCurTransaction.add(container.getId(), fragment, makeFragmentName(itemId));
        }
        if (fragment != mCurrentPrimaryItem) {
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }

        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        mCurTransaction.hide((Fragment) object);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment) object;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        //每次都刷新都调用instantiateItem 和destroyItem方法
        return POSITION_NONE;
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        if (mCurTransaction != null) {
            mCurTransaction.commitAllowingStateLoss();
            mCurTransaction = null;
            mFragmentManager.executePendingTransactions();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment) object).getView() == view;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }

    String makeFragmentName(String id) {
        return "android:switcher:" + ":" + id;
    }
}