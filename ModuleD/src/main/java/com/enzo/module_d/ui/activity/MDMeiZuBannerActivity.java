package com.enzo.module_d.ui.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.banner.mzbanner.MZBannerView;
import com.enzo.commonlib.widget.banner.mzbanner.holder.MZHolderCreator;
import com.enzo.commonlib.widget.banner.mzbanner.holder.MZViewHolder;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDMeiZuBannerActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-05-29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MDMeiZuBannerActivity extends BaseActivity {

    public static final String TAG = "MZModeBannerFragment";
    public static final int[] RES = new int[]{R.mipmap.image5, R.mipmap.image2, R.mipmap.image3, R.mipmap.image4, R.mipmap.image6, R.mipmap.image7, R.mipmap.image8};
    public static final int[] BANNER = new int[]{R.mipmap.banner1, R.mipmap.banner2, R.mipmap.banner3, R.mipmap.banner4, R.mipmap.banner5};
    private MZBannerView mMZBanner;
    private MZBannerView mNormalBanner;

    @Override
    public void onPause() {
        super.onPause();
        mMZBanner.pause();
        mNormalBanner.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();
        mNormalBanner.start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_meizu_banner;
    }

    @Override
    public void initHeader() {
        HeadWidget headWidget = findViewById(R.id.meizu_header);
        headWidget.setTitle("魅族轮播图");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        mMZBanner = findViewById(R.id.banner);
        mMZBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "click page:" + position, Toast.LENGTH_LONG).show();
            }
        });
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
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < RES.length; i++) {
            list.add(RES[i]);
        }

        List<Integer> bannerList = new ArrayList<>();
        for (int i = 0; i < BANNER.length; i++) {
            bannerList.add(BANNER[i]);
        }
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

        mNormalBanner = findViewById(R.id.banner_normal);
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
    public void initListener() {

    }

    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.md_banner_item, null);
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
