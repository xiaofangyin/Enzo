package com.ifenglian.module_c.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.widget.banner.mzbanner.MZBannerView;
import com.enzo.commonlib.widget.banner.mzbanner.holder.MZHolderCreator;
import com.enzo.commonlib.widget.banner.mzbanner.holder.MZViewHolder;
import com.enzo.commonlib.widget.progress.FLCSeekBar;
import com.enzo.commonlib.widget.progress.WaterWaveView;
import com.ifenglian.module_c.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MCFragment_1
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MCFragment_1 extends BaseFragment {

    public static final String TAG = "MZModeBannerFragment";
    private MZBannerView mMZBanner;
    private WaterWaveView waterWaveView;
    private FLCSeekBar seekBar;

    @Override
    public void onPause() {
        super.onPause();
        mMZBanner.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.mc_fragment_c_child_1;
    }

    @Override
    public void initView(View rootView) {
        mMZBanner = rootView.findViewById(R.id.banner);
        waterWaveView = rootView.findViewById(R.id.water_wave_view);
        seekBar = rootView.findViewById(R.id.seek_bar);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mMZBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Toast.makeText(getContext(), "click page:" + position, Toast.LENGTH_LONG).show();
            }
        });
        List<Integer> bannerList = new ArrayList<>();
        bannerList.add(R.mipmap.banner1);
        bannerList.add(R.mipmap.banner2);
        bannerList.add(R.mipmap.banner3);
        bannerList.add(R.mipmap.banner4);
        bannerList.add(R.mipmap.banner5);
        mMZBanner.setIndicatorVisible(true);
        // 代码中更改indicator 的位置
        //mMZBanner.setIndicatorAlign(MZBannerView.IndicatorAlign.LEFT);
        //mMZBanner.setIndicatorPadding(10,0,0,150);
        mMZBanner.setPages(bannerList, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

        waterWaveView.setProgress(50, 1000);
        seekBar.setProgress(50);
    }

    @Override
    public void initListener(View rootView) {
        mMZBanner.addPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG, "----->addPageChangeLisnter:" + position + "positionOffset:" + positionOffset + "positionOffsetPixels:" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG, "addPageChangeLisnter:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        seekBar.setOnSeekChangedListener(new FLCSeekBar.OnSeekBarChangedListener() {
            @Override
            public void onProgressChanged(FLCSeekBar seekBar, int percent) {
                waterWaveView.setProgress(percent);
            }

            @Override
            public void onStartTrackingTouch(FLCSeekBar seekBar, int percent) {

            }

            @Override
            public void onStopTrackingTouch(FLCSeekBar seekBar, int percent) {

            }
        });
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
