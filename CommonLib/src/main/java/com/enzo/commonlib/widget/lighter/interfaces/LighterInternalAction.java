package com.enzo.commonlib.widget.lighter.interfaces;

public interface LighterInternalAction {

    boolean hasNext();

    void next();

    void show();

    boolean isShowing();

    void dismiss();
}
