package com.ifenglian.module_d.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.commonlib.widget.view.tablayout.TabLayout;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.fragment.NavigationFragment;

/**
 * 文 件 名: MDNavigationActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDNavigationActivity extends BaseActivity implements View.OnClickListener {

    private int mCurrentTab = -1;
    private TabLayout mTabLayout;
    private FragmentManager mFragmentManager;
    private NavigationFragment firstFragment;
    private NavigationFragment secondFragment;
    private NavigationFragment thirdFragment;
    private NavigationFragment fourthFragment;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_main;
    }

    @Override
    public void initView() {
        mFragmentManager = getSupportFragmentManager();
        mTabLayout = findViewById(R.id.tab_layout);
    }

    @Override
    public void initData() {
        switchFragment(0);
        mTabLayout.setCurrentItem(0, false);
        mHandler.sendEmptyMessage(0);
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mTabLayout.addMessageNum(3, 1);
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
    };

    @Override
    public void initListener() {
        mTabLayout.setOnTabClickListener(new TabLayout.OnTabClickListener() {
            @Override
            public void onTabClick(View view, int position) {
                Log.e("AAA", "onTabClick: " + position);
                switchFragment(position);
            }

            @Override
            public void onTabReClick(View view, int position) {
                Log.e("AAA", "onTabReClick: " + position);
            }
        });
    }

    public void switchFragment(int tab) {
        if (isFinishing() || mCurrentTab == tab) {
            return;
        }
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (tab) {
            case 0:
                if (firstFragment == null) {
                    firstFragment = new NavigationFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("number", 0);
                    firstFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.fl_content, firstFragment);
                } else {
                    fragmentTransaction.show(firstFragment);
                }
                break;
            case 1:
                if (secondFragment == null) {
                    secondFragment = new NavigationFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("number", 1);
                    secondFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.fl_content, secondFragment);
                } else {
                    fragmentTransaction.show(secondFragment);
                }
                break;
            case 2:
                if (thirdFragment == null) {
                    thirdFragment = new NavigationFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("number", 2);
                    thirdFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.fl_content, thirdFragment);
                } else {
                    fragmentTransaction.show(thirdFragment);
                }
                break;
            case 3:
                if (fourthFragment == null) {
                    fourthFragment = new NavigationFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("number", 3);
                    fourthFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.fl_content, fourthFragment);
                } else {
                    fragmentTransaction.show(fourthFragment);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
        mFragmentManager.executePendingTransactions();
        mCurrentTab = tab;
    }

    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (firstFragment != null) {
            fragmentTransaction.hide(firstFragment);
        }
        if (secondFragment != null) {
            fragmentTransaction.hide(secondFragment);
        }
        if (thirdFragment != null) {
            fragmentTransaction.hide(thirdFragment);
        }
        if (fourthFragment != null) {
            fragmentTransaction.hide(fourthFragment);
        }
    }

    @Override
    public void onClick(View v) {

    }

}
