package com.enzo.module_d.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.common.DensityUtil;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.commonlib.widget.shadow.ShadowConfig;
import com.enzo.commonlib.widget.shadow.ShadowHelper;
import com.enzo.module_d.R;

public class MDShadowActivity extends BaseActivity {

    private EditText etColor;
    private EditText etRadius;
    private EditText etShadowColor;
    private EditText etOffsetX;
    private EditText etOffsetY;
    private Button btnDone;

    private int[] mColor;
    private int mRadius;
    private int mShadowColor;
    private int mOffsetX;
    private int mOffsetY;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_shadow;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("阴影");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        etColor = findViewById(R.id.et_color);
        etRadius = findViewById(R.id.et_radius);
        etShadowColor = findViewById(R.id.et_shadowcolor);
        etOffsetX = findViewById(R.id.et_offsetX);
        etOffsetY = findViewById(R.id.et_offsetY);
        btnDone = findViewById(R.id.btn_done);

        etColor.setText("#ffa726");
        etRadius.setText("30");
        etShadowColor.setText("#ffa726");
        etOffsetX.setText("2");
        etOffsetY.setText("2");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mColor = new int[]{Color.parseColor("#ffa726")};
        mRadius = DensityUtil.dip2px(this, 30);
        mShadowColor = Color.parseColor("#ffa726");
        mOffsetX = DensityUtil.dip2px(this, 2);
        mOffsetY = DensityUtil.dip2px(this, 2);

        setShadow();
    }

    @Override
    public void initListener() {
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShadow();
            }
        });
    }

    private void setShadow() {

        initData();

        RelativeLayout textView1 = findViewById(R.id.tv_1);
        ShadowConfig.Builder config = new ShadowConfig.Builder()
                .setColor(mColor[0])
                .setShadowColor(mShadowColor)
                .setGradientColorArray(mColor)
                .setRadius(mRadius)
                .setOffsetX(mOffsetX)
                .setOffsetY(mOffsetY);
        ShadowHelper.setShadowBgForView(textView1, config);
    }


    private void initData() {
        try {
            if (!TextUtils.isEmpty(etColor.getText().toString())) {
                String[] colors = etColor.getText().toString().split(",");
                mColor = new int[colors.length];
                for (int i = 0; i < colors.length; i++) {
                    mColor[i] = Color.parseColor(colors[i]);
                }
            }

            if (!TextUtils.isEmpty(etShadowColor.getText().toString())) {
                mShadowColor = Color.parseColor(etShadowColor.getText().toString());
            }
        } catch (Exception e) {
            ToastUtil.show(e + "", Toast.LENGTH_SHORT);
            mColor = new int[]{Color.parseColor("#ffa726")};
            mShadowColor = Color.parseColor("#ffa726");
        }

        if (!TextUtils.isEmpty(etRadius.getText().toString())) {
            mRadius = DensityUtil.dip2px(this, Integer.valueOf(etRadius.getText().toString()));
        }

        if (!TextUtils.isEmpty(etOffsetX.getText().toString())) {
            mOffsetX = DensityUtil.dip2px(this, Integer.valueOf(etOffsetX.getText().toString()));
        }

        if (!TextUtils.isEmpty(etOffsetY.getText().toString())) {
            mOffsetY = DensityUtil.dip2px(this, Integer.valueOf(etOffsetY.getText().toString()));
        }


    }
}
