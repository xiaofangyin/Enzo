package com.ifenglian.module_d.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
        mTabLayout.setViewPager(mViewPager);
    }


    @Override
    public void onClick(View v) {

    }

}
