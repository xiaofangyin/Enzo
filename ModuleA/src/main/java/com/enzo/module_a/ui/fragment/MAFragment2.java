package com.enzo.module_a.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.utils.common.DensityUtil;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.statusbar.utils.StatusBarUtils;
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

    private TextView tvSearch;
    private MagicIndicator magicIndicator;
    private ViewPager viewPager;
    private ViewGroup.MarginLayoutParams marginLayoutParams;

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
        ((ViewGroup) rootView).addView(view, 0);

        tvSearch = rootView.findViewById(R.id.ma_search);
        magicIndicator = rootView.findViewById(R.id.magic_indicator);
        viewPager = rootView.findViewById(R.id.ma_view_pager2);
        initMagicIndicator4();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        LL_SEARCH_MAX_TOP_MARGIN = DensityUtil.dip2px(getContext(), 55);
        LL_SEARCH_MIN_TOP_MARGIN = DensityUtil.dip2px(getContext(), 4.5f);//布局关闭时顶部距离
        marginLayoutParams = (ViewGroup.MarginLayoutParams) tvSearch.getLayoutParams();

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
                        viewPager.setCurrentItem(index);
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

    private int LL_SEARCH_MAX_TOP_MARGIN;
    private int LL_SEARCH_MIN_TOP_MARGIN;

    /**
     * 往上滑 正数
     * 往下滑 负数
     */
    public void onScroll(int dy) {
        LogUtil.d("dy: " + dy);
        float searchLayoutNewTopMargin = marginLayoutParams.topMargin - dy * 0.5f;
//        float searchLayoutNewWidth = LL_SEARCH_MAX_WIDTH - dy * 1.3f;//此处 * 1.3f 可以设置搜索框宽度缩放的速率
        if (searchLayoutNewTopMargin < LL_SEARCH_MIN_TOP_MARGIN) {
            searchLayoutNewTopMargin = LL_SEARCH_MIN_TOP_MARGIN;
        }
        if (searchLayoutNewTopMargin > LL_SEARCH_MAX_TOP_MARGIN) {
            searchLayoutNewTopMargin = LL_SEARCH_MAX_TOP_MARGIN;
        }
        marginLayoutParams.topMargin = (int) searchLayoutNewTopMargin;
        tvSearch.setLayoutParams(marginLayoutParams);
    }
}
