package com.enzo.commonlib.utils.dtoast.inner;

import androidx.annotation.IdRes;
import android.view.View;

import com.enzo.commonlib.utils.dtoast.DToast;

/**
 * @Date: 2018/11/20
 * @Author: heweizong
 * @Description:
 */
public interface IToast {
    void show();

    void showLong();

    void cancel();

    IToast setView(View mView);

    View getView();

    IToast setDuration(@DToast.Duration int duration);

    IToast setGravity(int gravity);

    IToast setGravity(int gravity, int xOffset, int yOffset);

    IToast setAnimation(int animation);

    IToast setPriority(int mPriority);

    IToast setText(@IdRes int id, String text);
}
