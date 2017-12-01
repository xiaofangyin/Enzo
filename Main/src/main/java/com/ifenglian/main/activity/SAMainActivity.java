package com.ifenglian.main.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.commonlib.utils.permission.Permission;
import com.ifenglian.commonlib.utils.permission.PermissionResult;
import com.ifenglian.commonlib.widget.view.viewpagerindicator.ViewPagerIndicator;
import com.ifenglian.main.R;
import com.ifenglian.main.adapter.SAHomeFragmentAdapter;
import com.ifenglian.main.plugin.SAFactoryManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文 件 名: SAMainActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
 */
public class SAMainActivity extends BaseActivity {

    private String[] REQUEST_PERMISSIONS = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE};

    private List<String> itemTitles = Arrays.asList("短信", "收藏", "推荐", "发现");
    private ViewPager mViewPager;
    private ViewPagerIndicator mIndicator;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mViewPager = findViewById(R.id.view_pager);
        mIndicator = findViewById(R.id.indicator);
    }

    @Override
    public void initData() {
        checkPermission();

        FragmentPagerAdapter mAdapter = new SAHomeFragmentAdapter(getSupportFragmentManager(), getFragments());
        mViewPager.setAdapter(mAdapter);
        //设置Tab上的标题
        mIndicator.setTabItemTitles(itemTitles);
        //设置关联的ViewPager
        mIndicator.setViewPager(mViewPager, 0);
    }

    @Override
    public void initListener() {
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("AAA", "SAMainActivity onNewIntent...");
        checkPermission();
    }

    private void checkPermission() {
        Permission.checkPermission(this, REQUEST_PERMISSIONS, new PermissionResult() {

            @Override
            public void success() {
                //成功
                Log.e("AAA", "SAMainActivity success...");
            }

            @Override
            public void fail() {
                //失败
                Log.e("AAA", "SAMainActivity fail...");
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
}
