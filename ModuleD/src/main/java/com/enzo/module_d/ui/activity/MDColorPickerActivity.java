package com.enzo.module_d.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.colorpicker.ColorObserver;
import com.enzo.commonlib.widget.colorpicker.ColorPickerView;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;

import java.util.Locale;

public class MDColorPickerActivity extends BaseActivity {

    private static final String SAVED_STATE_KEY_COLOR = "saved_state_key_color";
    private static final int INITIAL_COLOR = 0xFFFF8000;

    private ColorPickerView colorPickerView;
    private TextView tvHexValue;
    private TextView tvRgbValue;

    @Override
    public int getLayoutId() {
        return R.layout.activity_color_picker;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.color_picker_header);
        headWidget.setTitle("调色板");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        colorPickerView = findViewById(R.id.colorPicker);
        tvHexValue = findViewById(R.id.color_picker_hex_value);
        tvRgbValue = findViewById(R.id.color_picker_rgb_value);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        colorPickerView.subscribe(new ColorObserver() {
            @Override
            public void onColor(int color, boolean fromUser, boolean shouldPropagate) {
                tvHexValue.setText(colorHex(color));
                tvRgbValue.setText(colorRGB(color));
            }
        });

        int color = INITIAL_COLOR;
        if (savedInstanceState != null) {
            color = savedInstanceState.getInt(SAVED_STATE_KEY_COLOR, INITIAL_COLOR);
        }
        colorPickerView.setInitialColor(color);
    }

    @Override
    public void initListener() {

    }

    private String colorHex(int color) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return String.format(Locale.getDefault(), "0x%02X%02X%02X%02X", a, r, g, b);
    }

    private String colorRGB(int color) {
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        return "RGB（" + red + "," + green + "," + blue + "）";
    }
}
