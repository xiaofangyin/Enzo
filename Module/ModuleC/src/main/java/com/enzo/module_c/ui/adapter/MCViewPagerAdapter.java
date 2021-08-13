package com.enzo.module_c.ui.adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.module_c.model.ColumnBean;
import com.enzo.module_c.ui.fragment.MCFragment_1;
import com.enzo.module_c.ui.fragment.MCFragment_2;
import com.enzo.module_c.ui.fragment.MCFragment_3;
import com.enzo.module_c.ui.fragment.MCFragment_4;
import com.enzo.module_c.ui.fragment.MCFragment_5;
import com.enzo.module_c.ui.fragment.MCFragment_6;

import org.jetbrains.annotations.NotNull;

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

    private List<BaseFragment> generateFragments(List<ColumnBean> titles) {
        List<BaseFragment> temp = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            int id = titles.get(i).getColumn_id();
            BaseFragment fragment = null;
            switch (id) {
                case 1:
                    fragment = new MCFragment_1();
                    break;
                case 2:
                    fragment = new MCFragment_2();
                    break;
                case 3:
                    fragment = new MCFragment_3();
                    break;
                case 4:
                    fragment = new MCFragment_4();
                    break;
                case 5:
                    fragment = new MCFragment_5();
                    break;
                case 6:
                    fragment = new MCFragment_6();
                    break;
            }
            if (fragment != null) {
                temp.add(fragment);
            }
        }
        return temp;
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragments.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", mColumns.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }
}