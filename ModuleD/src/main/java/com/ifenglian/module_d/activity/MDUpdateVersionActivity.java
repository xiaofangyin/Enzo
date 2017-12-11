package com.ifenglian.module_d.activity;

import android.Manifest;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.commonlib.utils.permission.PermissionsManager;
import com.ifenglian.commonlib.utils.permission.PermissionsResultAction;
import com.ifenglian.commonlib.utils.toast.ToastUtils;
import com.ifenglian.commonlib.utils.updateversion.UpdateStatus;
import com.ifenglian.commonlib.utils.updateversion.UpdateVersionUtil;
import com.ifenglian.commonlib.utils.updateversion.VersionInfo;
import com.ifenglian.module_d.R;

/**
 * 文 件 名: MDUpdateVersionActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/8
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDUpdateVersionActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_upate_version;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(
                        MDUpdateVersionActivity.this,
                        PERMISSIONS_STORAGE, new PermissionsResultAction() {
                            @Override
                            public void onGranted() {
                                //本地测试检测是否有新版本发布
                                UpdateVersionUtil.localCheckedVersion(MDUpdateVersionActivity.this, new UpdateVersionUtil.UpdateListener() {

                                    @Override
                                    public void onUpdateReturned(int updateStatus, VersionInfo versionInfo) {
                                        //判断回调过来的版本检测状态
                                        switch (updateStatus) {
                                            case UpdateStatus.YES:
                                                //弹出更新提示
                                                UpdateVersionUtil.showDialog(MDUpdateVersionActivity.this, versionInfo);
                                                break;
                                            case UpdateStatus.NO:
                                                //没有新版本
                                                ToastUtils.showToast("已经是最新版本了!");
                                                break;
                                            case UpdateStatus.NOWIFI:
                                                //当前是非wifi网络
                                                ToastUtils.showToast("只有在wifi下更新！");
                                                break;
                                            case UpdateStatus.ERROR:
                                                //检测失败
                                                ToastUtils.showToast("检测失败，请稍后重试！");
                                                break;
                                            case UpdateStatus.TIMEOUT:
                                                //链接超时
                                                ToastUtils.showToast("链接超时，请检查网络设置!");
                                                break;
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onDenied(String permission) {
                                ToastUtils.showToast("需要获取sd卡权限才能完成下载...");
                            }
                        });
            }
        });
    }

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "Activity-onRequestPermissionsResult() PermissionsManager.notifyPermissionsChange()");
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
