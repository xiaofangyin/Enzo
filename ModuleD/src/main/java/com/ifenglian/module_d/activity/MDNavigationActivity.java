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


public class MDNavigationActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private List<android.support.v4.app.Fragment> mFragmentList;

    private List<TabButton> mTabButtonList = new ArrayList<TabButton>();

    private int mCurrentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.md_activity_main);

        initView();
        pagerAdapter();
        initEvent();

    }

    private void initView() {
        mViewPager = findViewById(R.id.viewpager);

        mTabButtonList.add((TabButton) findViewById(R.id.tab_first));
        mTabButtonList.add((TabButton) findViewById(R.id.tab_second));
        mTabButtonList.add((TabButton) findViewById(R.id.tab_third));
        mTabButtonList.add((TabButton) findViewById(R.id.tab_fourth));

        mTabButtonList.get(0).setSelected(true);
        mCurrentTab = 0;
        mTabButtonList.get(3).addMessageNumber(8);
    }

    private void pagerAdapter() {
        mFragmentList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mFragmentList.add(new Fragment(i));
        }
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(adapter);
    }

    private void initEvent() {
        for (int i = 0; i < mTabButtonList.size(); i++) {
            mTabButtonList.get(i).setOnClickListener(this);
            mTabButtonList.get(i).setTag(i);
        }
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        int number = (Integer) v.getTag();
        mTabButtonList.get(mCurrentTab).setSelected(false);
        mTabButtonList.get(number).setSelected(true);
        mCurrentTab = number;
        mViewPager.setCurrentItem(number, false);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.e("AAA", "onPageSelected: " + position);
        mTabButtonList.get(mCurrentTab).setSelected(false);
        mTabButtonList.get(position).setSelected(true);
        mCurrentTab = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
