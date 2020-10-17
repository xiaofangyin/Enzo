package com.enzo.commonlib.widget.flipper;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.enzo.commonlib.R;
import com.enzo.commonlib.widget.flipper.adapter.FlipperAdapter;

/**
 * 文 件 名: AdvertFlipperView 垂直滚动广告条
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/23
 * 邮   箱: xiaofywork@163.com
 */
public class AdvertFlipperView extends FrameLayout {

    private ViewFlipper viewFlipper;
    private FlipperAdapter mAdapter;

    public AdvertFlipperView(@NonNull Context context) {
        this(context, null);
    }

    public AdvertFlipperView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdvertFlipperView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private DataSetObserver mObserver = new DataSetObserver() {

        @Override
        public void onChanged() {
            init();
        }

        @Override
        public void onInvalidated() {

        }
    };

    private void init() {
        removeAllViews();
        viewFlipper = new ViewFlipper(getContext());
        viewFlipper.setInAnimation(getContext(), R.anim.lib_flipper_anim_in);
        viewFlipper.setOutAnimation(getContext(), R.anim.lib_flipper_anim_out);
        addView(viewFlipper);
    }

    public void setAdapter(FlipperAdapter adapter) {
        if (mAdapter == adapter) {
            return;
        }
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mObserver);
        }
        mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(mObserver);
            mAdapter.notifyDataSetChanged();
            for (int i = 0; i < mAdapter.getCount(); i++) {
                View view = adapter.getItemView(getContext(), i);
                viewFlipper.addView(view);
            }
        }
    }

    public void startFlipping() {
        viewFlipper.startFlipping();
    }

    public void stopFlipping() {
        viewFlipper.stopFlipping();
    }

    public void setInterval(int interval) {
        viewFlipper.setFlipInterval(interval);
    }

    public void showNext() {
        viewFlipper.showNext();
    }

    public void showPrevious() {
        viewFlipper.showPrevious();
    }
}
