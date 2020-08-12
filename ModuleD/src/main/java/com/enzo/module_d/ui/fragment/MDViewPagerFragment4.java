package com.enzo.module_d.ui.fragment;

import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.utils.appupgrade.AppUpgradeUtil;
import com.enzo.commonlib.utils.appupgrade.bean.AndroidBean;
import com.enzo.flkit.router.MainRouterPath;
import com.enzo.module_d.R;
import com.enzo.module_d.ui.activity.MDAVLoadingActivity;
import com.enzo.module_d.ui.activity.MDCalendarActivity;
import com.enzo.module_d.ui.activity.MDMatisseActivity;
import com.enzo.module_d.ui.activity.MDMeiZuBannerActivity;
import com.enzo.module_d.ui.activity.MDNotificationActivity;
import com.enzo.module_d.ui.activity.MDOkHttpActivity;
import com.enzo.module_d.ui.activity.MDScanBarCodeActivity;
import com.enzo.module_d.ui.activity.MDScanQrCodeActivity;
import com.enzo.module_d.ui.activity.MDShadowActivity;
import com.enzo.module_d.ui.activity.MDTouchEventActivity;
import com.enzo.module_d.ui.activity.lighter.MDLighterActivity;

/**
 * 文 件 名: MDViewPagerFragment4
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MDViewPagerFragment4 extends BaseFragment implements View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.md_fragment_d4;
    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public void initListener(View rootView) {
        rootView.findViewById(R.id.btn_touch_event).setOnClickListener(this);
        rootView.findViewById(R.id.btn_app_upgrade).setOnClickListener(this);
        rootView.findViewById(R.id.btn_add_device).setOnClickListener(this);
        rootView.findViewById(R.id.btn_av_loading).setOnClickListener(this);
        rootView.findViewById(R.id.btn_lighter).setOnClickListener(this);
        rootView.findViewById(R.id.btn_calendar).setOnClickListener(this);
        rootView.findViewById(R.id.btn_meizu_banner).setOnClickListener(this);
        rootView.findViewById(R.id.btn_notification).setOnClickListener(this);
        rootView.findViewById(R.id.btn_bar_code).setOnClickListener(this);
        rootView.findViewById(R.id.btn_qr_code).setOnClickListener(this);
        rootView.findViewById(R.id.btn_matisse).setOnClickListener(this);
        rootView.findViewById(R.id.btn_okhttp).setOnClickListener(this);
        rootView.findViewById(R.id.btn_shadow).setOnClickListener(this);
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_app_upgrade) {
            AppUpgradeUtil.checkVersion(getContext(), new AppUpgradeUtil.UpdateListener() {
                @Override
                public void onNewVersion(AndroidBean versionInfo) {
                    AppUpgradeUtil.showDialog(getContext(), versionInfo);
                }

                @Override
                public void onNewest() {

                }

                @Override
                public void onFailed(Exception e) {

                }
            });
        } else if (id == R.id.btn_add_device) {
            ARouter.getInstance().build(MainRouterPath.MAIN_ADD_DEVICE).navigation();
        } else if (id == R.id.btn_touch_event) {
            Intent intent = new Intent(getContext(), MDTouchEventActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_av_loading) {
            Intent intent = new Intent(getContext(), MDAVLoadingActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_lighter) {
            Intent intent = new Intent(getContext(), MDLighterActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_calendar) {
            Intent intent = new Intent(getContext(), MDCalendarActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_meizu_banner) {
            Intent intent = new Intent(getContext(), MDMeiZuBannerActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_notification) {
            Intent intent = new Intent(getContext(), MDNotificationActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_bar_code) {
            Intent intent = new Intent(getContext(), MDScanBarCodeActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_qr_code) {
            Intent intent = new Intent(getContext(), MDScanQrCodeActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_matisse) {
            Intent intent = new Intent(getContext(), MDMatisseActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_okhttp) {
            Intent intent = new Intent(getContext(), MDOkHttpActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_touch_event) {
            Intent intent = new Intent(getContext(), MDTouchEventActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_shadow) {
            Intent intent = new Intent(getContext(), MDShadowActivity.class);
            startActivity(intent);
        }
    }
}
