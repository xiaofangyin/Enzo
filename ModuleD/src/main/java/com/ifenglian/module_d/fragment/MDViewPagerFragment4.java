package com.ifenglian.module_d.fragment;

import android.os.Bundle;
import android.view.View;

import com.ifenglian.commonlib.base.BaseFragment;
import com.ifenglian.commonlib.utils.toast.ToastUtils;
import com.ifenglian.commonlib.widget.view.alertdialog.AlertDialogCallBack;
import com.ifenglian.commonlib.widget.view.alertdialog.BottomAlertDialog;
import com.ifenglian.commonlib.widget.view.alertdialog.CenterAlertDialog;
import com.ifenglian.module_d.R;

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
        if (id == R.id.btn_photo_select) {
            BottomAlertDialog.Builder builder = new BottomAlertDialog.Builder(getActivity());
            BottomAlertDialog dialog = builder.
                    add("啦啦啦").
                    add("略略略").
                    add("嘿嘿嘿").
                    cancel("取消").
                    setOnItemClickListener(new BottomAlertDialog.OnItemClickListener() {
                        @Override
                        public void onItemClick(int i, String data) {
                            ToastUtils.showToast(data);
                        }
                    }).
                    build();
            dialog.show();
        } else if (id == R.id.btn_alert_view) {
            new CenterAlertDialog(getActivity(), "确认", "确定退出吗？", "取消", "确定", new AlertDialogCallBack() {
                @Override
                public void onNegClick() {

                }

                @Override
                public void onPosClick() {

                }
            }).show();
        }
    }
}
