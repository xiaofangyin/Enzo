package com.enzo.commonlib.utils.toast;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public final class SafeToastContext extends ContextWrapper {

    private LayoutInflater mInflater;

    public SafeToastContext(@NonNull Context base) {
        super(base);
    }

    @Override
    public Context getApplicationContext() {
        return new ApplicationContextWrapper(getBaseContext().getApplicationContext());
    }

    @Override
    public Object getSystemService(String name) {
        if (Context.LAYOUT_INFLATER_SERVICE.equals(name)) {
            if (mInflater == null) {
                mInflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
            }
            return mInflater;
        }

        return super.getSystemService(name);
    }

    private static final class ApplicationContextWrapper extends ContextWrapper {

        private ApplicationContextWrapper(@NonNull Context base) {
            super(base);
        }

        @Override
        public Object getSystemService(@NonNull String name) {
            if (Context.WINDOW_SERVICE.equals(name)) {
                return new WindowManagerWrapper((WindowManager) getBaseContext().getSystemService(name));
            }
            return super.getSystemService(name);
        }
    }


    private static final class WindowManagerWrapper implements WindowManager {

        private final @NonNull
        WindowManager base;

        private WindowManagerWrapper(@NonNull WindowManager base) {
            this.base = base;
        }

        @Override
        public Display getDefaultDisplay() {
            return base.getDefaultDisplay();
        }

        @Override
        public void removeViewImmediate(View view) {
            base.removeViewImmediate(view);
        }

        @Override
        public void addView(View view, ViewGroup.LayoutParams params) {
            try {
                base.addView(view, params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void updateViewLayout(View view, ViewGroup.LayoutParams params) {
            base.updateViewLayout(view, params);
        }

        @Override
        public void removeView(View view) {
            base.removeView(view);
        }
    }
}
