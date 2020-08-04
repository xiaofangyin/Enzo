package com.enzo.module_d.ui.activity.lighter;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.commonlib.widget.lighter.Lighter;
import com.enzo.commonlib.widget.lighter.parameter.Direction;
import com.enzo.commonlib.widget.lighter.parameter.LighterParameter;
import com.enzo.commonlib.widget.lighter.parameter.MarginOffset;
import com.enzo.commonlib.widget.lighter.shape.OvalShape;
import com.enzo.commonlib.widget.lighter.shape.RectShape;
import com.enzo.module_d.R;

import java.util.ArrayList;
import java.util.List;

public class MDViewPagerActivity extends BaseActivity {

    private ViewPager mViewPager;
    private List<View> mViewList = new ArrayList<>();

    private Lighter mLighter1;
    private Lighter mLighter2;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_lighter_viewpager;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_widget);
        headWidget.setTitle("引导");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        MDLighterHelper.setupToolBarBackAction(this, (Toolbar) findViewById(R.id.toolbar));
        initViewPager();
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewList.clear();
        mViewPager.removeOnPageChangeListener(mPageChangeListener);
    }

    private void initViewPager() {
        mViewPager = findViewById(R.id.viewpager);

        View view1 = getLayoutInflater().inflate(R.layout.md_layout_lighter_viewpager_1, null);
        View view2 = getLayoutInflater().inflate(R.layout.md_layout_lighter_viewpager_2, null);

        mViewList.add(view1);
        mViewList.add(view2);

        mViewPager.setAdapter(new Adapter());

        showPage1Guide();
        mViewPager.addOnPageChangeListener(mPageChangeListener);
    }

    private void showPage1Guide() {
        if (mLighter1 != null && mLighter1.isShowing()) {
            return;
        }
        mLighter1 = Lighter.with((ViewGroup) mViewList.get(0))
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.vp_btn_1)
                        .setTipLayoutId(R.layout.md_layout_lighter_tip_1)
                        .setLighterShape(new OvalShape())
                        .setTipViewDisplayAnimation(MDLighterHelper.getScaleAnimation())
                        .setTipViewRelativeDirection(Direction.RIGHT)
                        .setTipViewRelativeOffset(new MarginOffset(30, 0, 80, 0))
                        .build())
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.vp_btn_2)
                        .setTipLayoutId(R.layout.md_layout_lighter_tip_7)
                        .setLighterShape(new OvalShape())
                        .setTipViewRelativeDirection(Direction.LEFT)
                        .setTipViewDisplayAnimation(MDLighterHelper.getScaleAnimation())
                        .setTipViewRelativeOffset(new MarginOffset(50, 0, 100, 0))
                        .build())
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.vp_btn_3)
                        .setTipLayoutId(R.layout.md_layout_lighter_tip_2)
                        .setLighterShape(new OvalShape())
                        .setTipViewRelativeDirection(Direction.TOP)
                        .setTipViewDisplayAnimation(MDLighterHelper.getScaleAnimation())
                        .setTipViewRelativeOffset(new MarginOffset(-400, 0, 0, 30))
                        .build())
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.vp_btn_4)
                        .setTipLayoutId(R.layout.md_layout_lighter_tip_3)
                        .setLighterShape(new OvalShape())
                        .setTipViewRelativeDirection(Direction.TOP)
                        .setTipViewRelativeOffset(new MarginOffset(80, 0, 0, 20))
                        .setTipViewDisplayAnimation(MDLighterHelper.getScaleAnimation())
                        .build())
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.vp_btn_5)
                        .setTipLayoutId(R.layout.md_layout_lighter_tip_4)
                        .setLighterShape(new OvalShape())
                        .setTipViewRelativeDirection(Direction.TOP)
                        .setTipViewRelativeOffset(new MarginOffset(0, 100, 0, 20))
                        .setTipViewDisplayAnimation(MDLighterHelper.getScaleAnimation())
                        .build());
        mLighter1.show();
    }

    private void showPage2Guide() {
        if (mLighter2 != null && mLighter2.isShowing()) {
            return;
        }

        mLighter2 = Lighter.with((ViewGroup) mViewList.get(1))
                .addHighlight(
                        //Show three at a time
                        new LighterParameter.Builder()
                                .setHighlightedViewId(R.id.vp_btn_1)
                                .setTipLayoutId(R.layout.md_layout_lighter_tip_1)
                                .setLighterShape(new RectShape())
                                .setTipViewRelativeDirection(Direction.BOTTOM)
                                .setTipViewRelativeOffset(new MarginOffset(200, 0, 30, 0))
                                .build(),

                        new LighterParameter.Builder()
                                .setHighlightedViewId(R.id.vp_btn_2)
                                .setTipLayoutId(R.layout.md_layout_lighter_tip_2)
                                .setLighterShape(new RectShape())
                                .setTipViewRelativeDirection(Direction.TOP)
                                .setTipViewRelativeOffset(new MarginOffset(-400, 0, 0, 30))
                                .build(),

                        new LighterParameter.Builder()
                                .setHighlightedViewId(R.id.vp_btn_3)
                                .setTipLayoutId(R.layout.md_layout_lighter_tip_3)
                                .setLighterShape(new RectShape())
                                .setTipViewRelativeDirection(Direction.TOP)
                                .setTipViewRelativeOffset(new MarginOffset(100, 0, 0, 30))
                                .build());
        mLighter2.show();
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int i) {
            if (i == 0) {
                showPage1Guide();
            } else {
                showPage2Guide();
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    public class Adapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mViewList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }
    }
}
