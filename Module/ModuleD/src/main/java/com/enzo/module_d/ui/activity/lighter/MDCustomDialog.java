package com.enzo.module_d.ui.activity.lighter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.enzo.commonlib.widget.lighter.Lighter;
import com.enzo.commonlib.widget.lighter.parameter.Direction;
import com.enzo.commonlib.widget.lighter.parameter.LighterParameter;
import com.enzo.commonlib.widget.lighter.parameter.MarginOffset;
import com.enzo.commonlib.widget.lighter.shape.RectShape;
import com.enzo.module_d.R;

public class MDCustomDialog extends AlertDialog {
    private View mView;
    protected MDCustomDialog(@NonNull Context context) {
        super(context);

        if (context instanceof Activity){
            setOwnerActivity((Activity) context);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = getLayoutInflater().inflate(R.layout.md_layout_lighter_dialog, null);
        setContentView(mView);
    }

    @Override
    public void show() {
        super.show();

        int screenWidth = getOwnerActivity().getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = getOwnerActivity().getWindowManager().getDefaultDisplay().getHeight();

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = (int) (screenWidth * 0.9f);
        layoutParams.height = (int) (screenHeight * 0.8f);
        getWindow().setAttributes(layoutParams);

        showGuide(mView);
    }

    private void showGuide(View view){
        Lighter.with((ViewGroup) view)
                .addHighlight(
                        //Show two at a time
                        new LighterParameter.Builder()
                                .setHighlightedViewId(R.id.vp_btn_1)
                                .setTipLayoutId(R.layout.md_layout_lighter_tip_1)
                                .setLighterShape(new RectShape(5, 5, 30))
                                .setTipViewRelativeDirection(Direction.BOTTOM)
                                .setTipViewRelativeOffset(new MarginOffset(150, 0, 30, 0))
                                .build(),

                        new LighterParameter.Builder()
                                .setHighlightedViewId(R.id.vp_btn_2)
                                .setTipLayoutId(R.layout.md_layout_lighter_tip_2)
                                .setLighterShape(new RectShape(5, 5, 30))
                                .setTipViewRelativeDirection(Direction.TOP)
                                .setTipViewRelativeOffset(new MarginOffset(-400, 0, 0, 30))
                                .build())
                .show();
    }
}
