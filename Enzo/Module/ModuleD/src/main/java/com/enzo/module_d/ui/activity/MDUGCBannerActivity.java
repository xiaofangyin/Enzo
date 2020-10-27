package com.enzo.module_d.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.common.DensityUtil;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.imageloader.ImageLoader;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.banner.normal.IGGBanner;
import com.enzo.commonlib.widget.banner.normal.IGGBannerBean;
import com.enzo.commonlib.widget.banner.normal.IGGBaseBannerAdapter;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.commonlib.widget.snowview.SnowView;
import com.enzo.module_d.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDScrollingImageViewActivity
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/2
 * 邮   箱: xiaofywork@163.com
 */
public class MDUGCBannerActivity extends BaseActivity {

    private IGGBanner banner;
    private SnowView fallingView;

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
        banner.setNotClipToPadding(DensityUtil.dip2px(this, 28), DensityUtil.dip2px(this, 15));
        banner.setAdapter(new IGGBaseBannerAdapter(this) {
            @Override
            public View generateItem(int position) {
                LogUtil.d("generateItem position: " + position);
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }

            @Override
            public void bindItem(IGGBannerBean bean, View view) {
                LogUtil.d("bindItem...");
                new ImageLoader.Builder(context)
                        .load(bean.getPic())
                        .build()
                        .into((ImageView) view);
            }
        });
        fallingView = findViewById(R.id.fall_view);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        List<IGGBannerBean> mData = new ArrayList<>();
        IGGBannerBean IGGBannerBean1 = new IGGBannerBean();
        IGGBannerBean1.setId("1");
        IGGBannerBean1.setPic("http://file06.16sucai.com/2016/0428/cd094f5623151c096b820400fc71eac3.jpg");

        IGGBannerBean IGGBannerBean2 = new IGGBannerBean();
        IGGBannerBean2.setId("2");
        IGGBannerBean2.setPic("http://file06.16sucai.com/2016/0425/67a5159babcb1df3ecf68197a513af61.jpg");

        IGGBannerBean IGGBannerBean3 = new IGGBannerBean();
        IGGBannerBean3.setId("3");
        IGGBannerBean3.setPic("http://file06.16sucai.com/2016/0425/73e2fc8d7871ae4952ea2789f3f5b24f.jpg");

        IGGBannerBean IGGBannerBean4 = new IGGBannerBean();
        IGGBannerBean4.setId("4");
        IGGBannerBean4.setPic("http://file06.16sucai.com/2016/0425/006fb503a3ec0822c2b1a10405b069a8.jpg");

        IGGBannerBean IGGBannerBean5 = new IGGBannerBean();
        IGGBannerBean5.setId("5");
        IGGBannerBean5.setPic("http://file06.16sucai.com/2016/0425/bbdec65210c15d347dbc17d88c5535be.jpg");

        IGGBannerBean IGGBannerBean6 = new IGGBannerBean();
        IGGBannerBean6.setId("6");
        IGGBannerBean6.setPic("http://file06.16sucai.com/2016/0425/a768086eef8c2abb98eabbcee8ecd578.jpg");

        mData.add(IGGBannerBean1);
        mData.add(IGGBannerBean2);
        mData.add(IGGBannerBean3);
        mData.add(IGGBannerBean4);
        mData.add(IGGBannerBean5);
        mData.add(IGGBannerBean6);
        banner.play(mData);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.md_icon_snow);
        fallingView.initSnow(bitmap, 60);
    }

    @Override
    public void initListener() {
        banner.setOnBannerClickListener(new IGGBanner.OnBannerClickListener() {
            @Override
            public void onBannerClick(IGGBannerBean bean) {
                ToastUtil.show(bean.getPic());
            }
        });
    }
}