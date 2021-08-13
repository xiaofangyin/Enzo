package com.enzo.commonlib.widget.banner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.enzo.commonlib.utils.common.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: UGCBannerAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/3/16
 * 邮   箱: xiaofangyinwork@163.com
 */
public abstract class IGGBaseBannerAdapter extends PagerAdapter {

    protected Context context;
    private final List<IGGBannerBean> mData = new ArrayList<>();
    private final ArrayList<View> mViewCaches = new ArrayList<>();
    private IGGBanner.OnBannerClickListener mClickListener;

    public IGGBaseBannerAdapter(Context context) {
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
        if (mData.size() > 0) {
            return mData.size() == 1 ? 1 : Short.MAX_VALUE;
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view;
        if (mViewCaches.isEmpty()) {
            view = generateItem(mData.get(position % mData.size()), position % mData.size());
        } else {
            view = mViewCaches.remove(0);
        }
        bindItem(view, mData.get(position % mData.size()), position % mData.size());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onBannerClick(mData.get(position % mData.size()));
                }
            }
        });
        container.addView(view);
        return view;
    }

    public abstract View generateItem(IGGBannerBean bean, int position);

    public abstract void bindItem(View view, IGGBannerBean bean, int position);

    public abstract int getIndicatorResource();

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
        mViewCaches.add(view);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
