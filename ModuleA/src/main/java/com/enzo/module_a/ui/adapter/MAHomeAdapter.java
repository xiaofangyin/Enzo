package com.enzo.module_a.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enzo.commonlib.base.BaseRecyclerViewAdapter;
import com.enzo.commonlib.base.BaseViewHolder;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.banner.mzbanner.MZBannerView;
import com.enzo.commonlib.widget.banner.mzbanner.holder.MZHolderCreator;
import com.enzo.commonlib.widget.banner.mzbanner.holder.MZViewHolder;
import com.enzo.module_a.R;
import com.enzo.module_a.model.MAHomeBannerBean;
import com.enzo.module_a.model.MAHomeBaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MAHomeAdapter
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/9
 * 邮   箱: xiaofywork@163.com
 */
public class MAHomeAdapter extends BaseRecyclerViewAdapter<MAHomeBaseBean> {

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

    private static class HomeBannerHolder extends BaseViewHolder<MAHomeBannerBean> {

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

    private static class HomeViewHolder extends BaseViewHolder<MAHomeBaseBean> {

        public HomeViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setUpView(MAHomeBaseBean model, int position, RecyclerView.Adapter adapter) {

        }
    }

}
