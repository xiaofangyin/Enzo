package com.enzo.commonlib.widget.banner.transformer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.enzo.commonlib.utils.common.DensityUtil;

/**
 * 文 件 名: HorizontalStackTransformerWithRotation
 * 创 建 人: xiaofy
 * 创建日期: 2020/11/21
 * 邮   箱: xiaofywork@163.com
 */
public class HorizontalStackTransformerWithRotation implements ViewPager.PageTransformer {

    private static final float CENTER_PAGE_SCALE = 0.85f;
    private final int offscreenPageLimit;
    private final ViewPager boundViewPager;

    public HorizontalStackTransformerWithRotation(@NonNull ViewPager boundViewPager) {
        this.boundViewPager = boundViewPager;
        this.offscreenPageLimit = boundViewPager.getOffscreenPageLimit();
    }

    @Override
    public void transformPage(@NonNull View view, float position) {
        int pagerWidth = boundViewPager.getWidth();
        float horizontalOffsetBase = (pagerWidth - pagerWidth * CENTER_PAGE_SCALE)
                / 2 / offscreenPageLimit + DensityUtil.dip2px(view.getContext(), 15);

        if (position >= 0) {
            float translationX = (horizontalOffsetBase - view.getWidth()) * position;
            view.setTranslationX(translationX);
        }
        if (position > -1 && position < 0) {
            float rotation = position * 30;
            view.setRotation(rotation);
            view.setAlpha((position * position * position + 1));
        } else if (position > offscreenPageLimit - 1) {
            view.setAlpha((float) (1 - position + Math.floor(position)));
        } else {
            view.setRotation(0);
            view.setAlpha(1);
        }
        if (position == 0) {
            view.setScaleX(CENTER_PAGE_SCALE);
            view.setScaleY(CENTER_PAGE_SCALE);
        } else {
            float scaleFactor = Math.min(CENTER_PAGE_SCALE - position * 0.1f, CENTER_PAGE_SCALE);
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        }
        ViewCompat.setElevation(view, (offscreenPageLimit - position) * 5);
    }
}
