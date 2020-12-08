package com.enzo.module_b.ui.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.statusbar.stateappbar.utils.StatusBarUtils;
import com.enzo.commonlib.widget.togglebutton.ios.FLSwitchButton;
import com.enzo.commonlib.widget.togglebutton.ios.FlToggleButton;
import com.enzo.flkit.router.ModuleBRouterPath;
import com.enzo.module_b.BuildConfig;
import com.enzo.module_b.R;
import com.enzo.skin.manager.entity.AttrFactory;
import com.enzo.skin.manager.entity.DynamicAttr;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MBFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
@Route(path = ModuleBRouterPath.MODULE_B_FRAGMENT)
public class MBFragment extends BaseFragment {

    private FlToggleButton toggleButton;
    private FLSwitchButton switchButton;

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("fragment b on resume...");
        StatusBarUtils.StatusBarDarkMode(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d("fragment b on pause...");
    }

    @Override
    public int getLayoutId() {
        return R.layout.mb_fragment_b;
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

        toggleButton = rootView.findViewById(R.id.toggle_button);
        switchButton = rootView.findViewById(R.id.switch_button);
    }

    @Override
    public void initListener(View rootView) {
        switchButton.setOnCheckedChangeListener(new FLSwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(FLSwitchButton switchButton, boolean isChecked) {

            }
        });
        toggleButton.setOnToggleChanged(new FlToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {

            }
        });
    }

    @Override
    public void lazyLoad() {

    }
}
