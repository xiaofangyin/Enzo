package com.enzo.commonlib.widget.lighter.interfaces;

public interface OnLighterListener {
    /**
     * When the highlight is displayed, this method will be called back.
     *
     * @param index index of the number of highlights you configured.
     */
    void onShow(int index);

    /**
     * Call back this method when the all highlights has been displayed.
     */
    void onDismiss();
}
