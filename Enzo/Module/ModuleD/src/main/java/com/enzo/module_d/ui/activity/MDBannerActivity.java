package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.common.DensityUtil;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.imageloader.ImageLoader;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.banner.IGGBanner;
import com.enzo.commonlib.widget.banner.IGGBannerBean;
import com.enzo.commonlib.widget.banner.IGGBaseBannerAdapter;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDUGCBannerActivity
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/2
 * 邮   箱: xiaofywork@163.com
 */
public class MDBannerActivity extends BaseActivity {

    private IGGBanner banner;
    private IGGBanner banner1;
    private IGGBanner banner2;
    private IGGBanner banner3;
    private IGGBanner banner4;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_ugc_banner;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("轮播图");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        banner = findViewById(R.id.md_circle_banner);
        banner.setIndicatorAlign(IGGBanner.IndicatorAlign.LEFT);
        banner.setHorizontalStackStyle(3);
        banner.setAdapter(new IGGBaseBannerAdapter(this) {
            @Override
            public View generateItem(IGGBannerBean bean, int position) {
                LogUtil.d("generateItem position: " + position);
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }

            @Override
            public void bindItem(View view, IGGBannerBean bean, int position) {
                LogUtil.d("bindItem...");
                new ImageLoader.Builder(context)
                        .load(bean.getResourceId())
                        .build()
                        .into((ImageView) view);
            }

            @Override
            public int getIndicatorResource() {
                return 0;
            }
        });

        banner1 = findViewById(R.id.md_circle_banner1);
        banner1.setIndicatorAlign(IGGBanner.IndicatorAlign.RIGHT);
        banner1.setRotationStyle();
        banner1.setAdapter(new IGGBaseBannerAdapter(this) {
            @Override
            public View generateItem(IGGBannerBean bean, int position) {
                LogUtil.d("generateItem position: " + position);
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }

            @Override
            public void bindItem(View view, IGGBannerBean bean, int position) {
                LogUtil.d("bindItem...");
                new ImageLoader.Builder(context)
                        .load(bean.getResourceId())
                        .build()
                        .into((ImageView) view);
            }

            @Override
            public int getIndicatorResource() {
                return R.drawable.lib_selector_banner_indicator;
            }
        });

        banner2 = findViewById(R.id.md_circle_banner2);
        banner2.setZoomOutStyle();
        banner2.setAdapter(new IGGBaseBannerAdapter(this) {
            @Override
            public View generateItem(IGGBannerBean bean, int position) {
                LogUtil.d("generateItem position: " + position);
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }

            @Override
            public void bindItem(View view, IGGBannerBean bean, int position) {
                LogUtil.d("bindItem...");
                new ImageLoader.Builder(context)
                        .load(bean.getPic())
                        .build()
                        .into((ImageView) view);
            }

            @Override
            public int getIndicatorResource() {
                return R.drawable.lib_selector_banner_indicator;
            }
        });

        banner3 = findViewById(R.id.md_circle_banner3);
        banner3.setMeiZuStyle();
        banner3.setAdapter(new IGGBaseBannerAdapter(this) {
            @Override
            public View generateItem(IGGBannerBean bean, int position) {
                LogUtil.d("generateItem position: " + position);
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }

            @Override
            public void bindItem(View view, IGGBannerBean bean, int position) {
                LogUtil.d("bindItem...");
                new ImageLoader.Builder(context)
                        .load(bean.getResourceId())
                        .build()
                        .into((ImageView) view);
            }

            @Override
            public int getIndicatorResource() {
                return R.drawable.lib_selector_banner_indicator;
            }
        });

        banner4 = findViewById(R.id.md_circle_banner4);
        banner4.setNotClipToPadding(DensityUtil.dip2px(this, 28), DensityUtil.dip2px(this, 15));
        banner4.setAdapter(new IGGBaseBannerAdapter(this) {
            @Override
            public View generateItem(IGGBannerBean bean, int position) {
                LogUtil.d("generateItem position: " + position);
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }

            @Override
            public void bindItem(View view, IGGBannerBean bean, int position) {
                LogUtil.d("bindItem...");
                new ImageLoader.Builder(context)
                        .load(bean.getResourceId())
                        .build()
                        .into((ImageView) view);
            }

            @Override
            public int getIndicatorResource() {
                return R.drawable.lib_selector_banner_indicator;
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        List<IGGBannerBean> mData1 = getIggBannerBeans(R.mipmap.banner_1, R.mipmap.banner_2, R.mipmap.banner_3, R.mipmap.banner_4);
        banner.play(mData1);
        banner1.play(mData1);
        banner3.play(mData1);
        banner4.play(mData1);

        List<IGGBannerBean> mData2 = getIggBannerNetBeans();
        banner2.play(mData2);
    }

    @Override
    public void initListener() {
        banner1.setOnBannerClickListener(new IGGBanner.OnBannerClickListener() {
            @Override
            public void onBannerClick(IGGBannerBean bean) {
                ToastUtil.show(bean.getPic());
            }
        });
    }

    @NonNull
    private List<IGGBannerBean> getIggBannerNetBeans() {
        List<IGGBannerBean> mData = new ArrayList<>();
        IGGBannerBean bannerBean1 = new IGGBannerBean();
        bannerBean1.setId("1");
        bannerBean1.setPic("http://file06.16sucai.com/2016/0428/cd094f5623151c096b820400fc71eac3.jpg");
        bannerBean1.setResourceId(R.mipmap.banner1);

        IGGBannerBean bannerBean2 = new IGGBannerBean();
        bannerBean2.setId("2");
        bannerBean2.setPic("http://file06.16sucai.com/2016/0425/67a5159babcb1df3ecf68197a513af61.jpg");

        IGGBannerBean bannerBean3 = new IGGBannerBean();
        bannerBean3.setId("3");
        bannerBean3.setPic("http://file06.16sucai.com/2016/0425/73e2fc8d7871ae4952ea2789f3f5b24f.jpg");

        IGGBannerBean bannerBean4 = new IGGBannerBean();
        bannerBean4.setId("4");
        bannerBean4.setPic("http://file06.16sucai.com/2016/0425/006fb503a3ec0822c2b1a10405b069a8.jpg");

        IGGBannerBean bannerBean5 = new IGGBannerBean();
        bannerBean5.setId("5");
        bannerBean5.setPic("http://file06.16sucai.com/2016/0425/bbdec65210c15d347dbc17d88c5535be.jpg");

        IGGBannerBean bannerBean6 = new IGGBannerBean();
        bannerBean6.setId("6");
        bannerBean6.setPic("http://file06.16sucai.com/2016/0425/a768086eef8c2abb98eabbcee8ecd578.jpg");

        mData.add(bannerBean1);
        mData.add(bannerBean2);
        mData.add(bannerBean3);
        mData.add(bannerBean4);
        mData.add(bannerBean5);
        mData.add(bannerBean6);
        return mData;
    }

    @NonNull
    private List<IGGBannerBean> getIggBannerBeans(int p, int p2, int p3, int p4) {
        List<IGGBannerBean> mData2 = new ArrayList<>();
        IGGBannerBean bannerBean21 = new IGGBannerBean();
        bannerBean21.setId("1");
        bannerBean21.setResourceId(p);

        IGGBannerBean bannerBean22 = new IGGBannerBean();
        bannerBean22.setId("2");
        bannerBean22.setResourceId(p2);

        IGGBannerBean bannerBean23 = new IGGBannerBean();
        bannerBean23.setId("3");
        bannerBean23.setResourceId(p3);

        IGGBannerBean bannerBean24 = new IGGBannerBean();
        bannerBean24.setId("4");
        bannerBean24.setResourceId(p4);

        mData2.add(bannerBean21);
        mData2.add(bannerBean22);
        mData2.add(bannerBean23);
        mData2.add(bannerBean24);
        return mData2;
    }
}