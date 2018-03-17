package com.ifenglian.module_d.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.ifenglian.commonlib.base.BaseFragment;
import com.ifenglian.commonlib.widget.circlebanner.CircleBanner;
import com.ifenglian.commonlib.widget.snowview.SnowView;
import com.ifenglian.module_d.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDViewPagerFragment1
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDViewPagerFragment1 extends BaseFragment {

    private CircleBanner circleBanner;
    private SnowView fallingView;

    @Override
    public int getLayoutId() {
        return R.layout.md_fragment_d1;
    }

    @Override
    public void initView(View rootView) {
        circleBanner = rootView.findViewById(R.id.md_circle_banner);
        fallingView = rootView.findViewById(R.id.fall_view);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        List<String> data = new ArrayList<>();
        data.add("http://file06.16sucai.com/2016/0428/cd094f5623151c096b820400fc71eac3.jpg");
        data.add("http://file06.16sucai.com/2016/0425/67a5159babcb1df3ecf68197a513af61.jpg");
        data.add("http://file06.16sucai.com/2016/0425/73e2fc8d7871ae4952ea2789f3f5b24f.jpg");
        data.add("http://file06.16sucai.com/2016/0425/006fb503a3ec0822c2b1a10405b069a8.jpg");
        data.add("http://file06.16sucai.com/2016/0425/bbdec65210c15d347dbc17d88c5535be.jpg");
        data.add("http://file06.16sucai.com/2016/0425/a768086eef8c2abb98eabbcee8ecd578.jpg");
        circleBanner.play(data);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.md_icon_snow);
        fallingView.initSnow(bitmap, 60);
    }

    @Override
    public void initListener(View rootView) {

    }
}
