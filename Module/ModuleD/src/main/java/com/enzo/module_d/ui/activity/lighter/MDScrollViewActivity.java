package com.enzo.module_d.ui.activity.lighter;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.commonlib.widget.lighter.Lighter;
import com.enzo.commonlib.widget.lighter.parameter.Direction;
import com.enzo.commonlib.widget.lighter.parameter.LighterParameter;
import com.enzo.commonlib.widget.lighter.shape.OvalShape;
import com.enzo.module_d.R;

public class MDScrollViewActivity extends BaseActivity {

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showGuide(v);
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_lighter_scrollview;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_widget);
        headWidget.setTitle("引导");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        MDLighterHelper.setupToolBarBackAction(this, (Toolbar) findViewById(R.id.toolbar));
        showGuide(findViewById(R.id.iv2));
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {
        findViewById(R.id.iv1).setOnClickListener(mClickListener);
        findViewById(R.id.iv2).setOnClickListener(mClickListener);
        findViewById(R.id.iv3).setOnClickListener(mClickListener);
        findViewById(R.id.iv4).setOnClickListener(mClickListener);
        findViewById(R.id.iv5).setOnClickListener(mClickListener);
        findViewById(R.id.iv6).setOnClickListener(mClickListener);
        findViewById(R.id.iv7).setOnClickListener(mClickListener);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ToastUtil.show("Click to show highlight(点击显示高亮)~", Toast.LENGTH_LONG);
    }

    private void showGuide(View highlightedView) {
        if (highlightedView == null) {
            return;
        }

        OvalShape ovalShape = new OvalShape();
        ovalShape.setPaint(MDLighterHelper.getDashPaint());

        Lighter.with((ViewGroup) findViewById(R.id.root))
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedView(highlightedView)
                        .setTipLayoutId(R.layout.md_layout_lighter_tip_5)
                        .setTipViewRelativeDirection(Direction.TOP)
                        .setLighterShape(ovalShape)
                        .build())
                .show();
    }
}

