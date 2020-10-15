package com.enzo.module_d.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.EditText;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.module_c.aidl.ICalInterface;
import com.enzo.module_d.R;

/**
 * 文 件 名: MDAidlActivity
 * 创 建 人: xiaofy
 * 创建日期: 2020/10/15
 * 邮   箱: xiaofywork@163.com
 */
public class MDAidlActivity extends BaseActivity {

    private EditText edtText1;
    private EditText edtText2;
    private EditText edtResult;
    private ICalInterface iCallInterface;

    private final ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.d("MDAidlActivity onServiceConnected...");
            iCallInterface = ICalInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.d("MDAidlActivity onServiceDisconnected...");
            iCallInterface = null;
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_aidl;
    }

    @Override
    public void initView() {
        edtText1 = findViewById(R.id.edt_num1);
        edtText2 = findViewById(R.id.edt_num2);
        edtResult = findViewById(R.id.edt_result);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.enzo.module_c", "com.enzo.module_c.aidl.ICalInterface"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_calculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num1 = Integer.parseInt(edtText1.getText().toString());
                int num2 = Integer.parseInt(edtText2.getText().toString());
                try {
                    if (iCallInterface != null) {
                        edtResult.setText(iCallInterface.add(num1, num2));
                    } else {
                        edtResult.setText("iCallInterface = null");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
