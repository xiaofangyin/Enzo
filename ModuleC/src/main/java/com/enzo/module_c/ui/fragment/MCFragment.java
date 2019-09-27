package com.enzo.module_c.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.util.TypedValue;
import android.view.View;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.widget.indicator.magicindicator.MagicIndicator;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.titles.ScaleTransitionPagerTitleView;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import com.enzo.module_c.R;
import com.enzo.module_c.ui.adapter.MCViewPagerAdapter;
import com.enzo.module_c.model.ColumnBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MCFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MCFragment extends BaseFragment implements View.OnClickListener {

    private MagicIndicator magicIndicator;
    private ViewPager viewPager;
    private List<ColumnBean> columnList;

    @Override
    public int getLayoutId() {
        return R.layout.mc_fragment_c;
    }

    @Override
    public void initView(View rootView) {
        magicIndicator = rootView.findViewById(R.id.mc_indicator);
        viewPager = rootView.findViewById(R.id.mc_view_pager);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        MCViewPagerAdapter adapter = new MCViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        columnList = new ArrayList<>();
        ColumnBean bean1 = new ColumnBean();
        bean1.setColumn_name("焦点");
        bean1.setColumn_id("1");
        ColumnBean bean2 = new ColumnBean();
        bean2.setColumn_name("风险解读");
        bean2.setColumn_id("2");
        ColumnBean bean3 = new ColumnBean();
        bean3.setColumn_name("舆情");
        bean3.setColumn_id("3");
        ColumnBean bean4 = new ColumnBean();
        bean4.setColumn_name("舆情解读");
        bean4.setColumn_id("4");
        ColumnBean bean5 = new ColumnBean();
        bean5.setColumn_name("健康");
        bean5.setColumn_id("5");
        ColumnBean bean6 = new ColumnBean();
        bean6.setColumn_name("国内舆情");
        bean6.setColumn_id("6");
        columnList.add(bean1);
        columnList.add(bean2);
        columnList.add(bean3);
        columnList.add(bean4);
        columnList.add(bean5);
        columnList.add(bean6);
        adapter.setNewData(columnList);

        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return columnList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.color_666));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.color_blue));
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
                simplePagerTitleView.setText(columnList.get(index).getColumn_name());
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
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(getResources().getColor(R.color.color_blue));
                return linePagerIndicator;
            }
        });

        magicIndicator.setNavigator(commonNavigator, viewPager);
    }

    @Override
    public void initListener(View rootView) {

    }

    @Override
    public void onClick(View v) {

    }
}
