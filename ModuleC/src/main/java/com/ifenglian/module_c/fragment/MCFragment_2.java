package com.ifenglian.module_c.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.widget.banner.mzbanner.MZBannerView;
import com.enzo.commonlib.widget.banner.mzbanner.holder.MZHolderCreator;
import com.enzo.commonlib.widget.banner.mzbanner.holder.MZViewHolder;
import com.ifenglian.module_c.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MCFragment_2
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MCFragment_2 extends BaseFragment {

    public static final int[] RES = new int[]{R.mipmap.image5, R.mipmap.image2, R.mipmap.image3, R.mipmap.image4, R.mipmap.image6, R.mipmap.image7, R.mipmap.image8};
    private MZBannerView mNormalBanner;

    @Override
    public void onPause() {
        super.onPause();
        mNormalBanner.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mNormalBanner.start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.mc_fragment_c_child_2;
    }

    @Override
    public void initView(View rootView) {
        List<Integer> list = new ArrayList<>();
        for (int RE : RES) {
            list.add(RE);
        }

        mNormalBanner = rootView.findViewById(R.id.banner_normal);
        mNormalBanner.setPages(list, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener(View rootView) {

    }

    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }
}
