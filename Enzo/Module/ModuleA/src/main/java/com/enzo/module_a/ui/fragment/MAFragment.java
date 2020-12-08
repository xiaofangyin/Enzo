package com.enzo.module_a.ui.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.utils.common.DensityUtil;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.statusbar.stateappbar.utils.StatusBarUtils;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.flipper.AdvertFlipperView;
import com.enzo.commonlib.widget.flipper.adapter.FlipperAdapter;
import com.enzo.commonlib.widget.indicator.magicindicator.MagicIndicator;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.titles.ScaleTransitionPagerTitleView;
import com.enzo.commonlib.widget.indicator.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import com.enzo.commonlib.widget.popup.EasyPopup;
import com.enzo.commonlib.widget.popup.XGravity;
import com.enzo.commonlib.widget.popup.YGravity;
import com.enzo.flkit.plugin.FLHostDelegateManager;
import com.enzo.flkit.router.ModuleARouterPath;
import com.enzo.flkit.router.ModuleDRouterPath;
import com.enzo.module_a.BuildConfig;
import com.enzo.module_a.R;
import com.enzo.module_a.ui.adapter.MAViewPagerIndicatorAdapter;
import com.enzo.skin.manager.entity.AttrFactory;
import com.enzo.skin.manager.entity.DynamicAttr;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MAFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
@Route(path = ModuleARouterPath.MODULE_A_FRAGMENT)
public class MAFragment extends BaseFragment {

    private AdvertFlipperView flipperView;
    private MagicIndicator magicIndicator;
    private ViewPager viewPager;

    @Override
    public void onResume() {
        LogUtil.d("fragment a on resume...");
        StatusBarUtils.StatusBarLightMode(getActivity());
        if (!isFirstLoad) {
            flipperView.startFlipping();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d("fragment a on pause...");
        flipperView.stopFlipping();
    }

    @Override
    public int getLayoutId() {
        return R.layout.ma_fragment_a;
    }

    @Override
    public void initView(View rootView) {
        if (!BuildConfig.IS_MODULE) {
            View view = new View(rootView.getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    StatusBarUtils.getStatusBarHeight(rootView.getContext()));
            view.setLayoutParams(layoutParams);
            ((ViewGroup) rootView).addView(view, 0);

            List<DynamicAttr> mDynamicAttr = new ArrayList();
            mDynamicAttr.add(new DynamicAttr(AttrFactory.BACKGROUND, R.color.color_major_c1));
            dynamicAddView(view, mDynamicAttr);
        }

        flipperView = rootView.findViewById(R.id.view_flipper);
        magicIndicator = rootView.findViewById(R.id.magic_indicator);
        viewPager = rootView.findViewById(R.id.ma_view_pager2);
        initMagicIndicator4();
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
                FLHostDelegateManager.getInstance().getHostDelegate().openDrawer(getActivity(), Gravity.START);
            }
        });
        rootView.findViewById(R.id.ma_qr_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyPopup mCirclePop = EasyPopup.create()
                        .setContentView(v.getContext(), R.layout.ma_layout_circle_comment)
                        .setAnimationStyle(R.style.ma_RightTopPopAnim)
                        .setFocusAndOutsideEnable(true)
                        .setBackgroundDimEnable(true)
                        .setDimValue(0.4f)
                        .setOnViewListener(new EasyPopup.OnViewListener() {
                            @Override
                            public void initViews(View view, final EasyPopup popup) {
                                view.findViewById(R.id.tv_zan).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ARouter.getInstance().build(ModuleDRouterPath.MODULE_D_QR_CODE).navigation();
                                        popup.dismiss();
                                    }
                                });
                                view.findViewById(R.id.tv_comment).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ARouter.getInstance().build(ModuleDRouterPath.MODULE_D_BAR_CODE).navigation();
                                        popup.dismiss();
                                    }
                                });
                            }
                        })
                        .apply();

                mCirclePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        LogUtil.e("onDismiss: mCirclePop");
                    }
                });

                mCirclePop.showAtAnchorView(v, YGravity.BELOW, XGravity.LEFT, 0, 0);
            }
        });
    }

    @Override
    public void lazyLoad() {
        final List<String> list = new ArrayList<>();
        list.add("vivo Y83");
        list.add("iPhone 11");
        list.add("华为 P40");

        flipperView.setAdapter(new FlipperAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public View getItemView(Context context, int position) {
                final TextView textView = new TextView(context);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                textView.setSingleLine(true);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setTextColor(ContextCompat.getColor(context, R.color.color_999));
                Drawable drawableRight = getResources().getDrawable(R.mipmap.icon_search_icon, context.getTheme());
                drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
                textView.setCompoundDrawables(drawableRight, null, null, null);
                textView.setCompoundDrawablePadding(DensityUtil.dip2px(context, 6));
                textView.setText(list.get(position));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtil.show(textView.getText().toString());
                    }
                });
                return textView;
            }
        });
        flipperView.setInterval(5000);
        flipperView.startFlipping();

        FragmentPagerAdapter mAdapter = new MAViewPagerIndicatorAdapter(getChildFragmentManager(), getFragments());
        viewPager.setAdapter(mAdapter);

        viewPager.setCurrentItem(1);
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
                        if (viewPager.getCurrentItem() != index) {
                            viewPager.setCurrentItem(index);
                        } else {

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
