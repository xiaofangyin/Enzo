package com.ifenglian.module_d.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.utils.common.ToastUtils;
import com.enzo.commonlib.widget.alertdialog.BottomAlertDialog;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.activity.MDLoadingLayoutActivity;
import com.ifenglian.module_d.activity.MDPullToRefreshLvActivity;

/**
 * 文 件 名: MDViewPagerFragment4
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDViewPagerFragment4 extends BaseFragment implements View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.md_fragment_d4;
    }

    @Override
    public void initView(View rootView) {
        rootView.findViewById(R.id.btn_pull_to_refresh_rv).setOnClickListener(this);
        rootView.findViewById(R.id.btn_pull_to_refresh_lv).setOnClickListener(this);
        rootView.findViewById(R.id.btn_update_version).setOnClickListener(this);
        rootView.findViewById(R.id.btn_album).setOnClickListener(this);
        rootView.findViewById(R.id.btn_loading_layout).setOnClickListener(this);
        rootView.findViewById(R.id.btn_jni).setOnClickListener(this);
        rootView.findViewById(R.id.btn_touch_event).setOnClickListener(this);
        rootView.findViewById(R.id.btn_photo_select).setOnClickListener(this);
        rootView.findViewById(R.id.btn_alert_view).setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener(View rootView) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_pull_to_refresh_lv) {
            Intent intent = new Intent(getContext(), MDPullToRefreshLvActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_update_version) {

        } else if (id == R.id.btn_album) {

        } else if (id == R.id.btn_loading_layout) {
            Intent intent = new Intent(getContext(), MDLoadingLayoutActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_pull_to_refresh_rv) {

        } else if (id == R.id.btn_jni) {

        } else if (id == R.id.btn_touch_event) {

        } else if (id == R.id.btn_photo_select) {
            BottomAlertDialog.Builder builder = new BottomAlertDialog.Builder(getActivity());
            BottomAlertDialog dialog = builder.
                    add("啦啦啦").
                    add("略略略").
                    add("嘿嘿嘿").
                    cancel("取消").
                    listener(new BottomAlertDialog.OnItemClickListener() {
                        @Override
                        public void onItemClick(int i, String data) {
                            ToastUtils.showToast(data);
                        }
                    }).
                    build();
            dialog.show();
        } else if (id == R.id.btn_alert_view) {

        }
    }
}
