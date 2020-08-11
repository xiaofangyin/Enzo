package com.enzo.module_a.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.utils.common.DensityUtil;
import com.enzo.commonlib.utils.statusbar.utils.StatusBarUtils;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.autoscrolltextview.AutoScrollTextView;
import com.enzo.commonlib.widget.indicator.magicindicator.MagicIndicator;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.titles.ScaleTransitionPagerTitleView;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import com.enzo.flkit.router.ModuleARouterPath;
import com.enzo.module_a.R;
import com.enzo.module_a.plugin.MAPluginFactory;
import com.enzo.module_a.ui.activity.MAScanQrCodeActivity;
import com.enzo.module_a.ui.adapter.MAViewPagerIndicatorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MAFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
@Route(path = ModuleARouterPath.MODULE_A_FRAGMENT2)
public class MAFragment2 extends BaseFragment {

    private AutoScrollTextView tvSearch;
    private MagicIndicator magicIndicator;
    private ViewPager viewPager;

    @Override
    public int getLayoutId() {
        return R.layout.ma_fragment_a2;
    }

    @Override
    public void initView(View rootView) {
        View view = new View(rootView.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                StatusBarUtils.getStatusBarHeight(rootView.getContext()));
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(ContextCompat.getColor(
                rootView.getContext(), R.color.color_yellow));
        ((ViewGroup) rootView).addView(view, 0);

        tvSearch = rootView.findViewById(R.id.ma_search);
        magicIndicator = rootView.findViewById(R.id.magic_indicator);
        viewPager = rootView.findViewById(R.id.ma_view_pager2);
        initMagicIndicator4();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        final List<String> list = new ArrayList<>();
        list.add("vivo Y83");
        list.add("iPhone 11");
        list.add("华为 P40");
        tvSearch.setText(list.get(0));
        tvSearch.setList(list);
        tvSearch.startScroll();
        tvSearch.setClickListener(new AutoScrollTextView.ItemClickListener() {
            @Override
            public void onClick(int position) {
                ToastUtil.show(list.get(position));
            }
        });


        FragmentPagerAdapter mAdapter = new MAViewPagerIndicatorAdapter(getChildFragmentManager(), getFragments());
        viewPager.setAdapter(mAdapter);

        viewPager.setCurrentItem(1);
    }

    private List<Fragment> getFragments() {
        List<Fragment> list = new ArrayList<>();
        list.add(new MAHomeSubFragment1());
        list.add(new MAHomeSubFragment2());
        list.add(new MAHomeSubFragment3());
        return list;
    }

    @Override
    public void initListener(View rootView) {
        rootView.findViewById(R.id.ma_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAPluginFactory.getInstance().hostDelegate.openDrawer(getActivity(), Gravity.START);
            }
        });
        rootView.findViewById(R.id.ma_qr_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MAScanQrCodeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initMagicIndicator4() {
        final List<String> mDataList = new ArrayList<>();
        mDataList.add("关注");
        mDataList.add("首页");
        mDataList.add("福州");
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setTextSize(20);
                simplePagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(context, R.color.color_333_55));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(context, R.color.color_333));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(viewPager.getCurrentItem() != index){
                            viewPager.setCurrentItem(index);
                        }else{

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
        magicIndicator.setNavigator(commonNavigator, viewPager);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return DensityUtil.dip2px(magicIndicator.getContext(), 0);
            }
        });
    }

}
