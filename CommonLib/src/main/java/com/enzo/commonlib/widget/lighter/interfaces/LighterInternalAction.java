package com.enzo.commonlib.widget.lighter.interfaces;

public interface LighterInternalAction {
    /**
     * Check if there is any next highlight
     */
    boolean hasNext();

    /**
     * Make the next highlight.
     */
    void next();

    /**
     * Start to show highlight.
     */
    void show();

    /**
     * Check if it is showing
     */
    boolean isShowing();

    /**
     * Dismiss highlight.
     */
    void dismiss();
}
