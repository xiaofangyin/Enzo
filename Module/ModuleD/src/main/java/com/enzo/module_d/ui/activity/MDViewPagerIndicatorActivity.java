package com.enzo.module_d.ui.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.common.DensityUtil;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.commonlib.widget.indicator.magicindicator.MagicIndicator;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.indicators.TriangularPagerIndicator;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.titles.ScaleTransitionPagerTitleView;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
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
    private MagicIndicator magicIndicator;
    private MagicIndicator magicIndicator2;
    private ViewPager viewPager;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_viewpager_indicator;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("ViewPagerIndicator");
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
        magicIndicator = findViewById(R.id.md_magic_indicator);
        magicIndicator2 = findViewById(R.id.md_magic_indicator2);
        viewPager = findViewById(R.id.view_pager);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        final List<IndicatorBean> list = buildTabs();
        viewPagerIndicator.setTabItemTitles(list);
        viewPagerIndicator.bindViewPager(viewPager);

        scrollViewPagerIndicator.setTitles(list);
        scrollViewPagerIndicator.bindViewPager(viewPager);

        initMagicIndicator1(list);

        initMagicIndicator2(list);

        setAdapter(list);
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

    private void initMagicIndicator1(final List<IndicatorBean> list) {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(context, R.color.color_666));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(context, R.color.color_333));
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
                simplePagerTitleView.setText(list.get(index).getTitle());
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                TriangularPagerIndicator linePagerIndicator = new TriangularPagerIndicator(context);
                linePagerIndicator.setLineColor(ContextCompat.getColor(context, R.color.color_333));
                return linePagerIndicator;
            }
        });
        commonNavigator.setAdjustMode(true);
        magicIndicator.setNavigator(commonNavigator, viewPager);
    }

    private void initMagicIndicator2(final List<IndicatorBean> list) {
        CommonNavigator commonNavigator2 = new CommonNavigator(this);
        commonNavigator2.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(list.get(index).getTitle());
                simplePagerTitleView.setTextSize(20);
                simplePagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(context, R.color.color_333_55));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(context, R.color.color_333));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (viewPager.getCurrentItem() != index) {
                            viewPager.setCurrentItem(index);
                        }
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(DensityUtil.dip2px(context, 4));
                indicator.setLineWidth(DensityUtil.dip2px(context, 14));
                indicator.setRoundRadius(DensityUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(ContextCompat.getColor(context, R.color.color_333));
                return indicator;
            }
        });
        magicIndicator2.setNavigator(commonNavigator2, viewPager);
        LinearLayout titleContainer = commonNavigator2.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return DensityUtil.dip2px(magicIndicator.getContext(), 40);
            }
        });
    }

    private void setAdapter(List<IndicatorBean> list) {
        ViewPagerAdapter adapter = new ViewPagerAdapter();
        adapter.setNewData(list);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(2);
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
