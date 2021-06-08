package com.enzo.module_d.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;
import com.example.contentresolver.IMyAidlInterface;

/**
 * 文 件 名: MDAidlActivity
 * 创 建 人: xiaofy
 * 创建日期: 2020/10/15
 * 邮   箱: xiaofywork@163.com
 */
public class MDAidlActivity extends BaseActivity {

    private EditText edtText1;
    private EditText edtText2;
    private TextView tvResult;

    @Override
    public void initHeader() {
        super.initHeader();
        LogUtil.d("init header...123");
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("Aidl");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        LogUtil.d("get layout id...456");
        return R.layout.md_activity_aidl;
    }

    @Override
    public void initView() {
        LogUtil.d("init view...");
        edtText1 = findViewById(R.id.edt_num1);
        edtText2 = findViewById(R.id.edt_num2);
        tvResult = findViewById(R.id.tv_result);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        LogUtil.d("init data...");
        startAidl();
    }

    @Override
    public void initListener() {
        LogUtil.d("init listener...");
        findViewById(R.id.btn_calculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str1 = edtText1.getText().toString();
                String str2 = edtText2.getText().toString();
                if (!TextUtils.isEmpty(str1) && !TextUtils.isEmpty(str2)) {
                    int num1 = Integer.parseInt(str1);
                    int num2 = Integer.parseInt(str2);
                    try {
                        if (aidl != null) {
                            tvResult.setText(String.valueOf(aidl.service(num1, num2)));
                        } else {
                            tvResult.setText("服务未连接!");
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void startAidl() {
        Intent intent = new Intent();
        intent.setPackage("com.example.contentresolver");
        intent.setAction("com.example.contentresolver.aidlservice");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private IMyAidlInterface aidl;

    private final ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("xfy", "onServiceConnected");
            aidl = IMyAidlInterface.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("xfy", "onServiceDisconnected");
            aidl = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("on destroy...");
        unbindService(conn);
    }
}
