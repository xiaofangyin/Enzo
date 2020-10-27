package com.enzo.commonlib.widget.banner.normal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.enzo.commonlib.utils.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: UGCBannerAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/3/16
 * 邮   箱: xiaofangyinwork@163.com
 */
public abstract class IGGBaseBannerAdapter extends PagerAdapter {

    protected ImageLoader.Builder builder;
    protected ArrayList<View> mViewCaches;
    protected List<IGGBannerBean> mData;
    protected Context context;
    protected IGGBanner.OnBannerClickListener mClickListener;

    public IGGBaseBannerAdapter(Context context) {
        builder = new ImageLoader.Builder(context);
        mData = new ArrayList<>();
        this.mViewCaches = new ArrayList<>();
        this.context = context;
    }

    void setNewData(List<IGGBannerBean> list) {
        if (list != null && list.size() > 0) {
            mData.clear();
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }

    void setOnBannerClickListener(IGGBanner.OnBannerClickListener clickListener) {
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
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        if (mData != null && mData.size() > 0) {
            View view;
            if (mViewCaches.isEmpty()) {
                view = generateItem(position % mData.size());
            } else {
                view = mViewCaches.remove(0);
            }
            bindItem(mData.get(position % mData.size()), view);
            container.addView(view);
            return view;
        } else {
            return null;
        }
    }

    public abstract View generateItem(int position);

    public abstract void bindItem(IGGBannerBean bean, View view);

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        View imageView = (View) object;
        container.removeView(imageView);
        mViewCaches.add(imageView);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
