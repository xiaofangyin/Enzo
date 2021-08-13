package com.enzo.commonlib.widget.shadow;

import android.view.View;
import androidx.core.view.ViewCompat;

public class ShadowHelper {

    public static void setShadowBgForView(View view, ShadowConfig.Builder config) {
        //关闭硬件加速
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ViewCompat.setBackground(view, config.builder());
    }
}
