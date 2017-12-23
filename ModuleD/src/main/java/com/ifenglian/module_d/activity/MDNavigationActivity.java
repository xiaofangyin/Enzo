package com.ifenglian.module_d.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.commonlib.widget.view.tablayout.TabView;
import com.ifenglian.commonlib.widget.view.tablayout.TabLayout;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.fragment.MDViewPagerFragment1;

/**
 * 文 件 名: MDNavigationActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDNavigationActivity extends BaseActivity {

    private String mTitles[] = {"家庭", "安全", "发现", "我"};
    private int mIconRes[][] = {
            {com.ifenglian.commonlib.R.mipmap.sa_tab_home_normal, com.ifenglian.commonlib.R.mipmap.sa_tab_home_select},
            {com.ifenglian.commonlib.R.mipmap.sa_tab_security_normal, com.ifenglian.commonlib.R.mipmap.sa_tab_security_select},
            {com.ifenglian.commonlib.R.mipmap.sa_tab_find_normal, com.ifenglian.commonlib.R.mipmap.sa_tab_find_select},
            {com.ifenglian.commonlib.R.mipmap.sa_tab_personalcenter_normal, com.ifenglian.commonlib.R.mipmap.sa_tab_personalcenter_select}
    };
    private TabLayout mTabLayout;
    private FragmentManager mFragmentManager;
    private MDViewPagerFragment1 firstFragment;
    private MDViewPagerFragment1 secondFragment;
    private MDViewPagerFragment1 thirdFragment;
    private MDViewPagerFragment1 fourthFragment;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_main;
    }

    @Override
    public void initView() {
        mFragmentManager = getSupportFragmentManager();
        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.initData(mTitles, mIconRes);
    }

    @Override
    public void initData() {
        switchFragment(0);
        mTabLayout.setCurrentItem(0, false);
        mTabLayout.showRedPoint(2);
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
            public void onTabClick(TabView view, int position) {
                Log.e("AAA", "onTabClick: " + position);
                switchFragment(position);
            }

            @Override
            public void onTabReClick(TabView view, int position) {
                Log.e("AAA", "onTabReClick: " + position);
            }
        });
    }

    public void switchFragment(int tab) {
        if (isFinishing()) {
            return;
        }
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (tab) {
            case 0:
                if (firstFragment == null) {
                    firstFragment = new MDViewPagerFragment1();
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
                    secondFragment = new MDViewPagerFragment1();
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
                    thirdFragment = new MDViewPagerFragment1();
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
                    fourthFragment = new MDViewPagerFragment1();
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

    public void showTabLayout(boolean show) {
        if (show) {
            mTabLayout.showTabLayout();
        } else {
            mTabLayout.hideTabLayout();
        }
    }
}
