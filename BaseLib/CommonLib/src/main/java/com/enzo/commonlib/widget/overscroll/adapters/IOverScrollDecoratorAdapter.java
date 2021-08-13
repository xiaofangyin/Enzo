package com.enzo.commonlib.widget.overscroll.adapters;

import android.view.View;

public interface IOverScrollDecoratorAdapter {

    View getView();

    boolean isInAbsoluteStart();


    boolean isInAbsoluteEnd();
}
