package com.ifenglian.module_d.activity;

import android.view.View;

import com.ifenglian.commonlib.base.BaseActivity;
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
                                ToastUtils.showShortToast("已经是最新版本了!");
                                break;
                            case UpdateStatus.NOWIFI:
                                //当前是非wifi网络
                                ToastUtils.showShortToast("只有在wifi下更新！");
                                break;
                            case UpdateStatus.ERROR:
                                //检测失败
                                ToastUtils.showShortToast("检测失败，请稍后重试！");
                                break;
                            case UpdateStatus.TIMEOUT:
                                //链接超时
                                ToastUtils.showShortToast("链接超时，请检查网络设置!");
                                break;
                        }
                    }
                });
            }
        });
    }
}
