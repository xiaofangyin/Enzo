package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.commonlib.widget.indicator.noscroll.ViewPagerIndicator;
import com.enzo.commonlib.widget.indicator.scroll.IndicatorBean;
import com.enzo.commonlib.widget.indicator.scroll.ScrollViewPagerIndicator;
import com.enzo.module_d.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDViewPagerIndicatorActivity
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/2
 * 邮   箱: xiaofywork@163.com
 */
public class MDViewPagerIndicatorActivity extends BaseActivity {

    private ViewPagerIndicator viewPagerIndicator;
    private ScrollViewPagerIndicator scrollViewPagerIndicator;
    private ViewPager viewPager;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_viewpager_indicator;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("事件分发");
        headWidget.setLeftLayoutClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        viewPagerIndicator = findViewById(R.id.md_indicator);
        scrollViewPagerIndicator = findViewById(R.id.md_scroll_indicator);
        viewPager = findViewById(R.id.view_pager);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        viewPagerIndicator.setTabItemTitles(buildTabs());
        viewPagerIndicator.bindViewPager(viewPager);

        scrollViewPagerIndicator.setTitles(buildTabs());
        scrollViewPagerIndicator.bindViewPager(viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter();
        adapter.setNewData(buildTabs());
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(2);
    }

    @Override
    public void initListener() {
        viewPagerIndicator.setOnTabClickListener(new ViewPagerIndicator.OnTabClickListener() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onReClick(int position) {

            }
        });
        scrollViewPagerIndicator.setOnTabClickListener(new ScrollViewPagerIndicator.OnTabClickListener() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onReClick(int position) {

            }
        });
    }

    private List<IndicatorBean> buildTabs() {
        List<IndicatorBean> list = new ArrayList<>();

        IndicatorBean bean1 = new IndicatorBean();
        bean1.setId(0);
        bean1.setTitle("焦点");
        IndicatorBean bean2 = new IndicatorBean();
        bean2.setId(1);
        bean2.setTitle("舆情");
        IndicatorBean bean3 = new IndicatorBean();
        bean3.setId(2);
        bean3.setTitle("健康");
        IndicatorBean bean4 = new IndicatorBean();
        bean4.setId(3);
        bean4.setTitle("国内");
        IndicatorBean bean5 = new IndicatorBean();
        bean5.setId(4);
        bean5.setTitle("国外");

        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);
        return list;
    }

    private static class ViewPagerAdapter extends PagerAdapter {

        List<IndicatorBean> list = new ArrayList<>();

        public void setNewData(List<IndicatorBean> data) {
            this.list.clear();
            this.list.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(
                    R.layout.md_layout_view_pager_indicator, null);
            TextView textView = view.findViewById(R.id.text_view);
            textView.setText(list.get(position).getTitle());
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
