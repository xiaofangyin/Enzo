package com.ifenglian.module_d.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ifenglian.module_d.R;
import com.ifenglian.module_d.adapter.PagerAdapter;
import com.ifenglian.module_d.fragment.Fragment;

import java.util.ArrayList;
import java.util.List;


public class MDNavigationActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<android.support.v4.app.Fragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.md_activity_main);
        initView();
        pagerAdapter();
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tab_layout);
    }

    private void pagerAdapter() {
        mFragmentList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mFragmentList.add(new Fragment(i));
        }
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("AAA", "onPageSelected: " + position);
                mTabLayout.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.setOnTabClickListener(new TabLayout.OnTabClickListener() {
            @Override
            public void onTabClick(View view, int position) {
                Log.e("AAA", "onTabClick: " + position);
                mViewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabReClick(View view, int position) {
                Log.e("AAA", "onTabReClick: " + position);
            }
        });
    }


    @Override
    public void onClick(View v) {

    }

}
