package com.ifenglian.main.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.common.ToastUtils;
import com.enzo.commonlib.widget.noscrollviewpager.NoScrollViewPager;
import com.enzo.commonlib.widget.tablayout.TabEntityConfig;
import com.enzo.commonlib.widget.tablayout.TabLayout;
import com.enzo.commonlib.widget.tablayout.TabView;
import com.ifenglian.main.R;
import com.ifenglian.main.adapter.SAHomeFragmentAdapter;
import com.ifenglian.main.plugin.SAFactoryManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: SAMainActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/23
 * 邮   箱: xiaofy@ifenglian.com
 */
public class SAMainActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private NoScrollViewPager viewPager;
    private FragmentManager mFragmentManager;

    @Override
    public int getLayoutId() {
        return R.layout.sa_activity_main;
    }

    @Override
    public void initView() {
        mFragmentManager = getSupportFragmentManager();
        mTabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        SAHomeFragmentAdapter adapter = new SAHomeFragmentAdapter(mFragmentManager, getFragments());
        viewPager.setAdapter(adapter);
        mTabLayout.initData(TabEntityConfig.getEntities());
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
                LogUtil.e("onTabClick: " + position);
                viewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabReClick(TabView view, int position) {
                LogUtil.e("onTabReClick: " + position);
            }
        });
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < SAFactoryManager.getInstance().getFactoryList().size(); i++) {
            fragments.add(SAFactoryManager.getInstance().getFactoryList().get(i).getFragment());
        }
        return fragments;
    }

    private long firstTime = 0; //点击两次退出应用计时

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                ToastUtils.showToast("再按一次退出程序");
                firstTime = System.currentTimeMillis();
                return true;
            } else {
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
