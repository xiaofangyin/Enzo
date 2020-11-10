package com.enzo.module_a.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.enzo.commonlib.base.BaseRecyclerViewAdapter;
import com.enzo.commonlib.base.BaseViewHolder;
import com.enzo.commonlib.utils.common.DensityUtil;
import com.enzo.commonlib.utils.imageloader.ImageLoader;
import com.enzo.commonlib.widget.banner.IGGBanner;
import com.enzo.commonlib.widget.banner.IGGBannerBean;
import com.enzo.commonlib.widget.banner.IGGBaseBannerAdapter;
import com.enzo.flkit.router.ModuleDRouterPath;
import com.enzo.module_a.R;
import com.enzo.module_a.model.bean.MAHomeBannerBean;
import com.enzo.module_a.model.bean.MAHomeBaseBean;
import com.enzo.module_a.model.bean.MAHomeGoodsBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 文 件 名: MAHomeAdapter
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/9
 * 邮   箱: xiaofywork@163.com
 */
public class MAHomeAdapter extends BaseRecyclerViewAdapter<MAHomeBaseBean> {

    public static final int TYPE_BANNER = 1;
    public static final int TYPE_GOODS = 2;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getViewType();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ma_item_home_banner, parent, false);
            return new HomeBannerHolder(view);
        } else if (viewType == 2) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ma_item_home_goods, parent, false);
            return new HomeGoodsViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ma_item_home_, parent, false);
            return new HomeViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.setUpView(mData.get(position), position, this);
    }

    public static class HomeBannerHolder extends BaseViewHolder<MAHomeBannerBean> {

        private final IGGBanner iggBanner;

        public HomeBannerHolder(View itemView) {
            super(itemView);
            iggBanner = itemView.findViewById(R.id.banner);
            iggBanner.setMeiZuStyle();
            iggBanner.setAdapter(new IGGBaseBannerAdapter(getContext()) {
                @Override
                public View generateItem(IGGBannerBean bean, int position) {
                    ImageView imageView = new ImageView(context);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    return imageView;
                }

                @Override
                public void bindItem(View view, IGGBannerBean bean, int position) {
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
        public void setUpView(MAHomeBannerBean model, int position, RecyclerView.Adapter adapter) {
            List<IGGBannerBean> mData2 = new ArrayList<>();
            IGGBannerBean bannerBean21 = new IGGBannerBean();
            bannerBean21.setId("1");
            bannerBean21.setResourceId(R.mipmap.ma_banner1);

            IGGBannerBean bannerBean22 = new IGGBannerBean();
            bannerBean22.setId("2");
            bannerBean22.setResourceId(R.mipmap.ma_banner2);

            IGGBannerBean bannerBean23 = new IGGBannerBean();
            bannerBean23.setId("3");
            bannerBean23.setResourceId(R.mipmap.ma_banner3);

            IGGBannerBean bannerBean24 = new IGGBannerBean();
            bannerBean24.setId("4");
            bannerBean24.setResourceId(R.mipmap.ma_banner4);

            mData2.add(bannerBean21);
            mData2.add(bannerBean22);
            mData2.add(bannerBean23);
            mData2.add(bannerBean24);
            iggBanner.play(mData2);
        }
    }

    private static class HomeGoodsViewHolder extends BaseViewHolder<MAHomeGoodsBean> {

        private final ImageView imageView;
        private final TextView textView;

        public HomeGoodsViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ma_goods_thumbnail);
            textView = itemView.findViewById(R.id.ma_goods_author);
        }

        @Override
        public void setUpView(MAHomeGoodsBean model, final int position, final RecyclerView.Adapter adapter) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            int height = model.getHeight() % 100;
            layoutParams.height = DensityUtil.dip2px(getContext(), 150) + height;
            imageView.setLayoutParams(layoutParams);

            new ImageLoader.Builder(getContext())
                    .placeHolder(new ColorDrawable(getRandomColor()))
                    .load(model.getDownload_url())
                    .build()
                    .into(imageView);
            textView.setText(model.getAuthor());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(ModuleDRouterPath.MODULE_D_BOSS_COMPANY).navigation();
                }
            });
        }
    }

    private static class HomeViewHolder extends BaseViewHolder<MAHomeBaseBean> {

        public HomeViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setUpView(MAHomeBaseBean model, int position, RecyclerView.Adapter adapter) {

        }
    }

    public static int getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return Color.rgb(r, g, b);
    }
}
