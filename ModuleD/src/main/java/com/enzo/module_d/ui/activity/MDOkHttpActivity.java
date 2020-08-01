package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.net.okhttp.BaseExecutor;
import com.enzo.commonlib.utils.common.ToastUtils;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;
import com.enzo.module_d.model.GetSubjectExecutor;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.Map;

import static com.enzo.module_d.model.GetSubjectExecutor.API_KEY;

public class MDOkHttpActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_okhttp;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.notification_header);
        headWidget.setTitle("网络");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                Map<String, String> params = new HashMap<>();
                params.put("apikey", API_KEY);

                Map<String, String> headers = new HashMap<>();
                headers.put("Access-User-Token", "e5cHLWScbto3VfvYTU1llVZgl/WniA4QZZ8epmn8k/o=");

                new GetSubjectExecutor()
                        .params(params)
                        .headers(headers)
                        .callback(new BaseExecutor.JsonCallback<LinkedTreeMap<String, String>>() {
                            @Override
                            public void onSuccess(LinkedTreeMap<String, String> subjectBean) {
                                ToastUtils.showToast(MDOkHttpActivity.this, subjectBean.toString());
                            }

                            @Override
                            public void onFailed(Exception e) {

                            }
                        }).execute();
            }
        });
    }
}
