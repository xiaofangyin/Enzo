package com.enzo.commonlib.utils.statusbar.ultimate.annotation;

import androidx.annotation.IntDef;

import com.enzo.commonlib.utils.statusbar.ultimate.UltimateBarX;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Deprecated
@IntDef({UltimateBarX.STATUS_BAR, UltimateBarX.NAVIGATION_BAR})
@Retention(RetentionPolicy.SOURCE)
public @interface Type {
}
