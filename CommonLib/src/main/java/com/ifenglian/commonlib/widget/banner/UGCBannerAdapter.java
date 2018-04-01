package com.ifenglian.commonlib.widget.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: UGCBannerAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/3/16
 * 邮   箱: xiaofy@ifenglian.com
 */
public class UGCBannerAdapter extends PagerAdapter {

    private ArrayList<ImageView> mViewCaches;
    private List<String> mData;
    private Context context;
    private UGCBanner.OnBannerClickListener mClickListener;

    UGCBannerAdapter(Context context) {
        mData = new ArrayList<>();
        this.mViewCaches = new ArrayList<>();
        this.context = context;
    }

    void setNewData(List<String> list) {
        if (list != null && list.size() > 0) {
            mData.clear();
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }

    void setOnBannerClickListener(UGCBanner.OnBannerClickListener clickListener) {
        mClickListener = clickListener;
    }

    @Override
    public int getCount() {
        if (mData != null && mData.size() > 0) {
            return mData.size() == 1 ? 1 : Short.MAX_VALUE;
        } else {
            return 0;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        if (mData != null && mData.size() > 0) {
            ImageView imageView;
            if (mViewCaches.isEmpty()) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView = mViewCaches.remove(0);
            }
            Picasso.with(context).load(mData.get(position % mData.size())).into(imageView);
            if (mClickListener != null) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mClickListener.onBannerClick(position % mData.size());
                    }
                });
            }
            container.addView(imageView);
            return imageView;
        } else {
            return null;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView imageView = (ImageView) object;
        container.removeView(imageView);
        mViewCaches.add(imageView);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
