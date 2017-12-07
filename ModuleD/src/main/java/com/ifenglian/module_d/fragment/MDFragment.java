package com.ifenglian.module_d.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ifenglian.commonlib.base.BaseFragment;
import com.ifenglian.commonlib.widget.view.alertdialog.BottomAlertDialog;
import com.ifenglian.commonlib.widget.view.alertdialog.CenterAlertDialog;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.activity.MDBaiDuProgressActivity;
import com.ifenglian.module_d.activity.MDLoadingLayoutActivity;
import com.ifenglian.module_d.activity.MDRoundImageViewActivity;
import com.ifenglian.module_d.activity.MDRulerActivity;
import com.ifenglian.module_d.activity.MDPhotosActivity;
import com.ifenglian.module_d.activity.MDProgressActivity;
import com.ifenglian.module_d.activity.MDSimpleActivity;

/**
 * 文 件 名: MDFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDFragment extends BaseFragment implements View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.md_fragment_d;
    }

    @Override
    public void initView(View rootView) {
        rootView.findViewById(R.id.btn_lottie).setOnClickListener(this);
        rootView.findViewById(R.id.btn_album).setOnClickListener(this);
        rootView.findViewById(R.id.btn_progress).setOnClickListener(this);
        rootView.findViewById(R.id.btn_loading_layout).setOnClickListener(this);
        rootView.findViewById(R.id.btn_round_image_view).setOnClickListener(this);
        rootView.findViewById(R.id.btn_ruler).setOnClickListener(this);
        rootView.findViewById(R.id.btn_baidu_progress).setOnClickListener(this);
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
        if (id == R.id.btn_lottie) {
            Intent intent = new Intent(getContext(), MDSimpleActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_album) {
            Intent intent = new Intent(getContext(), MDPhotosActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_progress) {
            Intent intent = new Intent(getContext(), MDProgressActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_loading_layout) {
            Intent intent = new Intent(getContext(), MDLoadingLayoutActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_round_image_view) {
            Intent intent = new Intent(getContext(), MDRoundImageViewActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_ruler) {
            Intent intent = new Intent(getContext(), MDRulerActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_baidu_progress) {
            Intent intent = new Intent(getContext(), MDBaiDuProgressActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_photo_select) {
            BottomAlertDialog dialog = new BottomAlertDialog(getContext());
            dialog.show();
        } else if (id == R.id.btn_alert_view) {
            CenterAlertDialog dialog = new CenterAlertDialog(getContext());
            dialog.show();
        }
    }
}
