package com.enzo.module_c.ui.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.module_c.ui.fragment.MCFragment_1;
import com.enzo.module_c.ui.fragment.MCFragment_2;
import com.enzo.module_c.ui.fragment.MCFragment_3;
import com.enzo.module_c.ui.fragment.MCFragment_4;
import com.enzo.module_c.ui.fragment.MCFragment_5;
import com.enzo.module_c.ui.fragment.MCFragment_6;
import com.enzo.module_c.model.ColumnBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MCViewPagerAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/1
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MCViewPagerAdapter extends BasePagerAdapter {

    private List<BaseFragment> mFragments;
    private List<ColumnBean> mColumns;

    public MCViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setNewData(List<ColumnBean> titles) {
        this.mFragments = generateFragments(titles);
        this.mColumns = titles;
        notifyDataSetChanged();
    }

    public List<BaseFragment> getFragments() {
        return mFragments;
    }

    public List<ColumnBean> getColumns() {
        return mColumns;
    }

    private List<BaseFragment> generateFragments(List<ColumnBean> titles) {
        List<BaseFragment> temp = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            String id = titles.get(i).getColumn_id();
            BaseFragment fragment = (BaseFragment) mFragmentManager.
                    findFragmentByTag(makeFragmentName(id));
            if (fragment == null) {
                switch (id) {
                    case "1":
                        fragment = new MCFragment_1();
                        break;
                    case "2":
                        fragment = new MCFragment_2();
                        break;
                    case "3":
                        fragment = new MCFragment_3();
                        break;
                    case "4":
                        fragment = new MCFragment_4();
                        break;
                    case "5":
                        fragment = new MCFragment_5();
                        break;
                    case "6":
                        fragment = new MCFragment_6();
                        break;
                }
            }
            temp.add(fragment);
        }
        return temp;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragments.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", mColumns.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public String getItemId(int position) {
        return String.valueOf(mColumns.get(position).getColumn_id());
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}