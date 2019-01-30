package com.ifenglian.module_d.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.utils.common.ToastUtils;
import com.enzo.commonlib.widget.banner.normal.BannerBean;
import com.enzo.commonlib.widget.banner.normal.UGCBanner;
import com.enzo.commonlib.widget.snowview.SnowView;
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

    private UGCBanner banner;
    private SnowView fallingView;
    private List<BannerBean> mData;

    @Override
    public int getLayoutId() {
        return R.layout.md_fragment_d1;
    }

    @Override
    public void initView(View rootView) {
        banner = rootView.findViewById(R.id.md_circle_banner);
        fallingView = rootView.findViewById(R.id.fall_view);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mData = new ArrayList<>();
        BannerBean bannerBean1 = new BannerBean();
        bannerBean1.setId("1");
        bannerBean1.setPic("http://file06.16sucai.com/2016/0428/cd094f5623151c096b820400fc71eac3.jpg");

        BannerBean bannerBean2 = new BannerBean();
        bannerBean2.setId("2");
        bannerBean2.setPic("http://file06.16sucai.com/2016/0425/67a5159babcb1df3ecf68197a513af61.jpg");

        BannerBean bannerBean3 = new BannerBean();
        bannerBean3.setId("3");
        bannerBean3.setPic("http://file06.16sucai.com/2016/0425/73e2fc8d7871ae4952ea2789f3f5b24f.jpg");

        BannerBean bannerBean4 = new BannerBean();
        bannerBean4.setId("4");
        bannerBean4.setPic("http://file06.16sucai.com/2016/0425/006fb503a3ec0822c2b1a10405b069a8.jpg");

        BannerBean bannerBean5 = new BannerBean();
        bannerBean5.setId("5");
        bannerBean5.setPic("http://file06.16sucai.com/2016/0425/bbdec65210c15d347dbc17d88c5535be.jpg");

        BannerBean bannerBean6 = new BannerBean();
        bannerBean6.setId("6");
        bannerBean6.setPic("http://file06.16sucai.com/2016/0425/a768086eef8c2abb98eabbcee8ecd578.jpg");

        mData.add(bannerBean1);
        mData.add(bannerBean2);
        mData.add(bannerBean3);
        mData.add(bannerBean4);
        mData.add(bannerBean5);
        mData.add(bannerBean6);
        banner.play(mData);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.md_icon_snow);
        fallingView.initSnow(bitmap, 60);
    }

    @Override
    public void initListener(View rootView) {
        banner.setOnBannerClickListener(new UGCBanner.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                ToastUtils.showToast(mData.get(position).getPic());
            }
        });
    }
}
