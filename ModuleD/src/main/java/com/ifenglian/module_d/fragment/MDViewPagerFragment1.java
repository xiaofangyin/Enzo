package com.ifenglian.module_d.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.ifenglian.commonlib.base.BaseFragment;
import com.ifenglian.commonlib.widget.view.fallview.SnowModel;
import com.ifenglian.commonlib.widget.view.fallview.SnowView;
import com.ifenglian.module_d.R;

/**
 * 文 件 名: MDViewPagerFragment1
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDViewPagerFragment1 extends BaseFragment {

    private SnowView fallingView;

    @Override
    public int getLayoutId() {
        return R.layout.md_fragment_d1;
    }

    @Override
    public void initView(View rootView) {
        fallingView = rootView.findViewById(R.id.fall_view);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.md_icon_snow);
        SnowModel.Builder builder = new SnowModel.Builder(bitmap);
        builder.setSpeed(5, true)
                .setSize(bitmap.getWidth(), bitmap.getHeight(), true)
                .setWind(8, true, true);
        fallingView.addFallObject(builder, 50);
    }

    @Override
    public void initListener(View rootView) {

    }
}
