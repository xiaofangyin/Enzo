package com.enzo.module_d.ui.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.utils.appupgrade.AppUpgradeUtil;
import com.enzo.commonlib.utils.appupgrade.bean.AndroidBean;
import com.enzo.commonlib.utils.common.DensityUtil;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.common.PermissionsUtils;
import com.enzo.commonlib.utils.imageloader.ImageLoader;
import com.enzo.commonlib.utils.matisse.Matisse;
import com.enzo.commonlib.utils.matisse.MimeType;
import com.enzo.commonlib.utils.matisse.engine.impl.GlideEngine;
import com.enzo.commonlib.utils.matisse.internal.entity.CaptureStrategy;
import com.enzo.commonlib.utils.statusbar.stateappbar.bar.StateAppBar;
import com.enzo.commonlib.utils.statusbar.stateappbar.utils.StatusBarUtils;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.popup.EasyPopup;
import com.enzo.commonlib.widget.popup.XGravity;
import com.enzo.commonlib.widget.popup.YGravity;
import com.enzo.flkit.plugin.FLHostDelegateManager;
import com.enzo.flkit.router.MainRouterPath;
import com.enzo.flkit.router.ModuleDRouterPath;
import com.enzo.module_d.BuildConfig;
import com.enzo.module_d.R;
import com.enzo.module_d.ui.activity.MDAVLoadingActivity;
import com.enzo.module_d.ui.activity.MDAidlActivity;
import com.enzo.module_d.ui.activity.MDBannerActivity;
import com.enzo.module_d.ui.activity.MDBoltsActivity;
import com.enzo.module_d.ui.activity.MDBossCompanyDetailActivity;
import com.enzo.module_d.ui.activity.MDCalendarActivity;
import com.enzo.module_d.ui.activity.MDContentProviderActivity;
import com.enzo.module_d.ui.activity.MDContentProviderTestActivity;
import com.enzo.module_d.ui.activity.MDCreateQRCodeActivity;
import com.enzo.module_d.ui.activity.MDFlowLayoutActivity;
import com.enzo.module_d.ui.activity.MDGroupItemDecorationActivity;
import com.enzo.module_d.ui.activity.MDHandlerThreadActivity;
import com.enzo.module_d.ui.activity.MDMatisseActivity;
import com.enzo.module_d.ui.activity.MDNotificationActivity;
import com.enzo.module_d.ui.activity.MDProgressActivity;
import com.enzo.module_d.ui.activity.MDRetrofitActivity;
import com.enzo.module_d.ui.activity.MDRoomTestActivity;
import com.enzo.module_d.ui.activity.MDRulerActivity;
import com.enzo.module_d.ui.activity.MDScanBarCodeActivity;
import com.enzo.module_d.ui.activity.MDScanQrCodeActivity;
import com.enzo.module_d.ui.activity.MDScrollingImageViewActivity;
import com.enzo.module_d.ui.activity.MDShadowActivity;
import com.enzo.module_d.ui.activity.MDTaskQueueActivity;
import com.enzo.module_d.ui.activity.MDTimePickerActivity;
import com.enzo.module_d.ui.activity.MDTimePickerActivity2;
import com.enzo.module_d.ui.activity.MDUltimateActivity;
import com.enzo.module_d.ui.activity.MDViewPagerIndicatorActivity;
import com.enzo.module_d.ui.activity.lighter.MDLighterActivity;
import com.enzo.module_d.ui.dialog.CommonBottomSheetDialog;
import com.enzo.module_d.utils.themes.ThemesHelper;
import com.enzo.skin.manager.entity.AttrFactory;
import com.enzo.skin.manager.entity.DynamicAttr;
import com.enzo.skin.manager.loader.SkinManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDFragment
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/14
 * 邮   箱: xiaofywork@163.com
 */
@Route(path = ModuleDRouterPath.MODULE_D_FRAGMENT)
public class MDFragment extends BaseFragment implements View.OnClickListener, View.OnLongClickListener {

    private static final int REQUEST_CODE_CHOOSE_AVATAR = 101;
    private TextView tvName;
    private ImageView ivAvatar;
    private LinearLayout llButtonContainer;

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("fragment d on resume...");
        StatusBarUtils.StatusBarLightMode(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d("fragment d on pause...");
    }

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_boss_mine;
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

        tvName = rootView.findViewById(R.id.name);
        ivAvatar = rootView.findViewById(R.id.me_icon);
        llButtonContainer = rootView.findViewById(R.id.ll_button_content);
        dynamicAddThemeButton();
    }

    @Override
    public void initListener(View rootView) {
        rootView.findViewById(R.id.tv_my_resume).setOnLongClickListener(this);
        rootView.findViewById(R.id.me_icon).setOnClickListener(this);
        rootView.findViewById(R.id.btn_aidl).setOnClickListener(this);
        rootView.findViewById(R.id.btn_app_upgrade).setOnClickListener(this);
        rootView.findViewById(R.id.btn_boss_bottom_dialog).setOnClickListener(this);
        rootView.findViewById(R.id.btn_boss_company).setOnClickListener(this);
        rootView.findViewById(R.id.btn_add_device).setOnClickListener(this);
        rootView.findViewById(R.id.btn_av_loading).setOnClickListener(this);
        rootView.findViewById(R.id.btn_lighter).setOnClickListener(this);
        rootView.findViewById(R.id.btn_calendar).setOnClickListener(this);
        rootView.findViewById(R.id.btn_ugc_banner).setOnClickListener(this);
        rootView.findViewById(R.id.btn_notification).setOnClickListener(this);
        rootView.findViewById(R.id.btn_bar_code).setOnClickListener(this);
        rootView.findViewById(R.id.btn_qr_code).setOnClickListener(this);
        rootView.findViewById(R.id.btn_create_qr_code).setOnClickListener(this);
        rootView.findViewById(R.id.btn_matisse).setOnClickListener(this);
        rootView.findViewById(R.id.btn_shadow).setOnClickListener(this);
        rootView.findViewById(R.id.btn_task_queue).setOnClickListener(this);
        rootView.findViewById(R.id.btn_progress).setOnClickListener(this);
        rootView.findViewById(R.id.btn_scrolling_image_view).setOnClickListener(this);
        rootView.findViewById(R.id.btn_ruler).setOnClickListener(this);
        rootView.findViewById(R.id.btn_time_picker).setOnClickListener(this);
        rootView.findViewById(R.id.btn_time_picker2).setOnClickListener(this);
        rootView.findViewById(R.id.btn_flow_layout).setOnClickListener(this);
        rootView.findViewById(R.id.btn_viewpager_indicator).setOnClickListener(this);
        rootView.findViewById(R.id.btn_retrofit).setOnClickListener(this);
        rootView.findViewById(R.id.btn_group_item).setOnClickListener(this);
        rootView.findViewById(R.id.btn_handler_thread).setOnClickListener(this);
        rootView.findViewById(R.id.btn_bolts).setOnClickListener(this);
        rootView.findViewById(R.id.btn_ultimate).setOnClickListener(this);
        rootView.findViewById(R.id.btn_content_provider).setOnClickListener(this);
        rootView.findViewById(R.id.btn_content_resolver).setOnClickListener(this);
        rootView.findViewById(R.id.btn_room).setOnClickListener(this);
    }

    @Override
    public void lazyLoad() {
        tvName.setText(FLHostDelegateManager.getInstance().getHostDelegate().getAccountInfo().getNickName());
        new ImageLoader.Builder(getActivity())
                .load(FLHostDelegateManager.getInstance().getHostDelegate().getAccountInfo().getmAvatarUrl())
                .placeHolder(R.mipmap.icon_user_default_avatar)
                .build()
                .into(ivAvatar);
    }

    private void dynamicAddThemeButton() {
        Button button = new Button(getActivity());
        button.setText("切换主题");
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(param);
        button.setTextColor(SkinManager.getInstance().getColor(R.color.color_major_c4));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SkinManager.getInstance().isExternalSkin()) {
                    SkinManager.getInstance().restoreDefaultTheme();
                    StateAppBar.setStatusBarColor(getActivity(), SkinManager.getInstance().getColor(R.color.color_major_c1));
                } else {
                    ThemesHelper.applyTheme(getActivity());
                }
            }
        });
        llButtonContainer.addView(button, 0);

        List<DynamicAttr> mDynamicAttr = new ArrayList();
        mDynamicAttr.add(new DynamicAttr(AttrFactory.TEXT_COLOR, R.color.color_major_c4));
        dynamicAddView(button, mDynamicAttr);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                List<Uri> list = Matisse.obtainResult(data);
                if (list != null && !list.isEmpty()) {
                    ImageLoader.Builder builder = new ImageLoader.Builder(getActivity());
                    builder.load(list.get(0))
                            .placeHolder(R.mipmap.icon_user_default_avatar)
                            .signature(String.valueOf(System.currentTimeMillis()))
                            .build()
                            .into(ivAvatar);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.me_icon) {
            PermissionsUtils.requestExternalStoragePermission(getActivity(), new PermissionsUtils.OnCheckCallback() {
                @Override
                public void granted(boolean granted) {
                    if (granted) {
                        Matisse.from(MDFragment.this)
                                .choose(MimeType.ofImage(), false)
                                .theme(R.style.Matisse_XianYu)
                                .countable(true)
                                .singleChoose(true)
                                .crop(true)
                                .capture(true)
                                .captureStrategy(new CaptureStrategy(Environment.DIRECTORY_PICTURES))
                                .maxSelectable(9)
                                .spanCount(4)
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                .thumbnailScale(0.85f)
                                .imageEngine(new GlideEngine())
                                .showSingleMediaType(true)
                                .maxOriginalSize(10)
                                .autoHideToolbarOnSingleTap(true)
                                .forResult(REQUEST_CODE_CHOOSE_AVATAR);
                    }
                }
            });
        } else if (id == R.id.btn_app_upgrade) {
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
        } else if (id == R.id.btn_boss_bottom_dialog) {
            View content = LayoutInflater.from(getActivity()).inflate(R.layout.md_dialog_view, null);

            //默认的效果
//        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(getActivity());
//        bottomSheetDialog.setContentView(content);
//        bottomSheetDialog.show();

            //自定义的BottomSheetDialog 主要是解决高度问题
            int height = (int) (DensityUtil.getScreenHeight(v.getContext()) / 3 * 2f);
            CommonBottomSheetDialog bottomSheetDialog = new CommonBottomSheetDialog(v.getContext(), height, height);
            bottomSheetDialog.setContentView(content);
            bottomSheetDialog.show();
        } else if (id == R.id.btn_aidl) {
            Intent intent = new Intent(getContext(), MDAidlActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_boss_company) {
            Intent intent = new Intent(getContext(), MDBossCompanyDetailActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_add_device) {
            ARouter.getInstance().build(MainRouterPath.MAIN_ADD_DEVICE).navigation();
        } else if (id == R.id.btn_av_loading) {
            Intent intent = new Intent(getContext(), MDAVLoadingActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_lighter) {
            Intent intent = new Intent(getContext(), MDLighterActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_calendar) {
            Intent intent = new Intent(getContext(), MDCalendarActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_ugc_banner) {
            Intent intent = new Intent(getContext(), MDBannerActivity.class);
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
        } else if (id == R.id.btn_create_qr_code) {
            Intent intent = new Intent(getContext(), MDCreateQRCodeActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_matisse) {
            Intent intent = new Intent(getContext(), MDMatisseActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_shadow) {
            Intent intent = new Intent(getContext(), MDShadowActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_task_queue) {
            Intent intent = new Intent(getContext(), MDTaskQueueActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_progress) {
            Intent intent = new Intent(getContext(), MDProgressActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_scrolling_image_view) {
            Intent intent = new Intent(getContext(), MDScrollingImageViewActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_ruler) {
            Intent intent = new Intent(getContext(), MDRulerActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_time_picker) {
            Intent intent = new Intent(getContext(), MDTimePickerActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_time_picker2) {
            Intent intent = new Intent(getContext(), MDTimePickerActivity2.class);
            startActivity(intent);
        } else if (id == R.id.btn_flow_layout) {
            Intent intent = new Intent(getContext(), MDFlowLayoutActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_viewpager_indicator) {
            Intent intent = new Intent(getContext(), MDViewPagerIndicatorActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_retrofit) {
            Intent intent = new Intent(getContext(), MDRetrofitActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_group_item) {
            Intent intent = new Intent(getContext(), MDGroupItemDecorationActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_handler_thread) {
            Intent intent = new Intent(getContext(), MDHandlerThreadActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_bolts) {
            Intent intent = new Intent(getContext(), MDBoltsActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_ultimate) {
            Intent intent = new Intent(getContext(), MDUltimateActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_content_provider) {
            Intent intent = new Intent(getContext(), MDContentProviderActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_content_resolver) {
            Intent intent = new Intent(getContext(), MDContentProviderTestActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_room) {
            Intent intent = new Intent(getContext(), MDRoomTestActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_my_resume) {
            EasyPopup mCirclePop = EasyPopup.create()
                    .setContentView(v.getContext(), R.layout.md_popup_layout_copy)
//                    .setAnimationStyle(R.style.ma_RightTopPopAnim)
                    .setFocusAndOutsideEnable(true)
                    .setBackgroundDimEnable(false)
                    .setDimValue(0.4f)
                    .setOnViewListener(new EasyPopup.OnViewListener() {
                        @Override
                        public void initViews(View view, final EasyPopup popup) {
                            final TextView tvCopy = view.findViewById(R.id.tv_copy);
                            tvCopy.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData clipData = ClipData.newPlainText(null, tvCopy.getText().toString());// 把数据复到剪贴板
                                    clipboard.setPrimaryClip(clipData);
                                    ToastUtil.show("已复制");
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

            mCirclePop.showAtAnchorView(v, YGravity.ABOVE, XGravity.CENTER, 0, 0);
            return true;
        }
        return false;
    }
}
