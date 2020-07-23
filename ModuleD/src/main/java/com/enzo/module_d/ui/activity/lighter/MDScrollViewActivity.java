package com.enzo.module_d.ui.activity.lighter;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.enzo.commonlib.widget.lighter.Lighter;
import com.enzo.commonlib.widget.lighter.parameter.Direction;
import com.enzo.commonlib.widget.lighter.parameter.LighterParameter;
import com.enzo.commonlib.widget.lighter.shape.OvalShape;
import com.enzo.module_d.R;

public class MDScrollViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        setContentView(R.layout.activity_lighter_scrollview);
        MDLighterHelper.setupToolBarBackAction(this, (Toolbar) findViewById(R.id.toolbar));

        showGuide(findViewById(R.id.iv2));

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

        Toast.makeText(this, "Click to show highlight(点击显示高亮)~", Toast.LENGTH_LONG).show();
    }

    private void showGuide(View highlightedView){
        if (highlightedView == null){
            return;
        }

        OvalShape ovalShape = new OvalShape();
        ovalShape.setPaint(MDLighterHelper.getDashPaint());

        Lighter.with((ViewGroup) findViewById(R.id.root))
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedView(highlightedView)
                        .setTipLayoutId(R.layout.layout_lighter_tip_5)
                        .setTipViewRelativeDirection(Direction.TOP)
                        .setLighterShape(ovalShape)
                        .build())
                .show();
    }


    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showGuide(v);
        }
    };
}

