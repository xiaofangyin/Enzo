package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.net.okhttp.BaseExecutor;
import com.enzo.commonlib.utils.common.ToastUtils;
import com.enzo.module_d.R;
import com.enzo.module_d.model.GetSubjectExecutor;
import com.google.gson.internal.LinkedTreeMap;

public class MDOkHttpActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_okhttp;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_get_subject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetSubjectExecutor().callback(new BaseExecutor.JsonCallback<LinkedTreeMap<String, String>>() {
                    @Override
                    public void onSuccess(LinkedTreeMap<String, String> subjectBean) {
                        ToastUtils.showToast(MDOkHttpActivity.this, subjectBean.toString());
                    }

                    @Override
                    public void onFailed(Exception e) {

                    }
                }).executor();
            }
        });
    }
}
