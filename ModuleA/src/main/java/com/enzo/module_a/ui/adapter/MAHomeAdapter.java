package com.enzo.module_a.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enzo.commonlib.base.BaseRecyclerViewAdapter;
import com.enzo.commonlib.base.BaseViewHolder;
import com.enzo.commonlib.utils.common.DensityUtil;
import com.enzo.commonlib.utils.imageloader.ImageLoader;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.banner.mzbanner.MZBannerView;
import com.enzo.commonlib.widget.banner.mzbanner.holder.MZHolderCreator;
import com.enzo.commonlib.widget.banner.mzbanner.holder.MZViewHolder;
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

        private MZBannerView mMZBanner;
        private final List<Integer> bannerList;

        public HomeBannerHolder(View itemView) {
            super(itemView);
            mMZBanner = itemView.findViewById(R.id.banner);
            bannerList = new ArrayList<>();
            bannerList.add(R.mipmap.ma_banner1);
            bannerList.add(R.mipmap.ma_banner2);
            bannerList.add(R.mipmap.ma_banner3);
            bannerList.add(R.mipmap.ma_banner4);
            bannerList.add(R.mipmap.ma_banner5);
        }

        @Override
        public void setUpView(MAHomeBannerBean model, int position, RecyclerView.Adapter adapter) {
            mMZBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
                @Override
                public void onPageClick(View view, int position) {
                    ToastUtil.show("click page:" + position, Toast.LENGTH_LONG);
                }
            });

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
            mMZBanner.start();
        }

        public static class BannerViewHolder implements MZViewHolder<Integer> {
            private ImageView mImageView;

            @Override
            public View createView(Context context) {
                // 返回页面布局文件
                View view = LayoutInflater.from(context).inflate(R.layout.ma_item_banner_item, null);
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

    private static class HomeGoodsViewHolder extends BaseViewHolder<MAHomeGoodsBean> {

        private ImageView imageView;
        private TextView textView;

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
                    if (adapter instanceof MAHomeAdapter) {
                        MAHomeAdapter adapter2 = (MAHomeAdapter) adapter;
                        if (adapter2.onItemClickListener != null) {
                            adapter2.onItemClickListener.onItemClick(position);
                        }
                    }

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
