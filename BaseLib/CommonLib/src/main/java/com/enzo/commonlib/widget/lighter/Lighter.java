package com.enzo.commonlib.widget.lighter;

import android.app.Activity;
import android.view.ViewGroup;

import com.enzo.commonlib.widget.lighter.interfaces.LighterInternalAction;
import com.enzo.commonlib.widget.lighter.interfaces.OnLighterListener;
import com.enzo.commonlib.widget.lighter.interfaces.OnLighterViewClickListener;
import com.enzo.commonlib.widget.lighter.parameter.LighterParameter;
import com.enzo.commonlib.widget.lighter.util.Preconditions;

public class Lighter implements LighterInternalAction {
    private LighterInternalImpl mInternalImpl;

    private Lighter(ViewGroup rootView) {
        mInternalImpl = new LighterInternalImpl(rootView);
    }

    private Lighter(Activity activity) {
        mInternalImpl = new LighterInternalImpl(activity);
    }

    public static Lighter with(Activity activity) {
        Preconditions.checkNotNull(activity,
                "You can not show a highlight view on a null activity.");
        return new Lighter(activity);
    }

    public static Lighter with(ViewGroup rootView) {
        Preconditions.checkNotNull(rootView,
                "You can not show a highlight view on a null root view.");
        return new Lighter(rootView);
    }

    public Lighter addHighlight(LighterParameter... lighterParameters) {
        mInternalImpl.addHighlight(lighterParameters);
        return this;
    }

    public Lighter setBackgroundColor(int color) {
        mInternalImpl.setBackgroundColor(color);
        return this;
    }

    public Lighter setAutoNext(boolean autoNext) {
        mInternalImpl.setAutoNext(autoNext);
        return this;
    }

    public Lighter setIntercept(boolean intercept) {
        mInternalImpl.setIntercept(intercept);
        return this;
    }

    public Lighter setOnClickListener(OnLighterViewClickListener clickListener) {
        mInternalImpl.setOnClickListener(clickListener);
        return this;
    }

    public Lighter setOnLighterListener(OnLighterListener onLighterListener) {
        mInternalImpl.setOnLighterListener(onLighterListener);
        return this;
    }

    @Override
    public boolean hasNext() {
        return mInternalImpl.hasNext();
    }

    @Override
    public void next() {
        mInternalImpl.next();
    }

    public void show() {
        mInternalImpl.show();
    }

    @Override
    public boolean isShowing() {
        return mInternalImpl.isShowing();
    }

    @Override
    public void dismiss() {
        mInternalImpl.dismiss();
    }
}
