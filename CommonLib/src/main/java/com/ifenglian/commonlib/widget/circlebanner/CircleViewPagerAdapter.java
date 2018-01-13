package com.ifenglian.commonlib.widget.circlebanner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: CircleViewPagerAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/1/13
 * 邮   箱: xiaofy@ifenglian.com
 */
public class CircleViewPagerAdapter extends PagerAdapter {

    private final ArrayList<Object> mViewCaches = new ArrayList<>();
    private List<String> mData;
    private Context context;

    public CircleViewPagerAdapter(List<String> data, Context context) {
        this.mData = data;
        this.context = context;
    }

    public void setmData(List<String> data) {
        this.mData = data;
    }

    @Override
    public int getCount() {
        if (null != mData) {
            if (mData.size() == 1) {
                return 1;
            } else {
                return Integer.MAX_VALUE;
            }
        } else {
            return 0;
        }
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mData != null && mData.size() > 0) {
            ImageView imageView;
            if (mViewCaches.isEmpty()) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {
                imageView = (ImageView) mViewCaches.remove(0);
            }
            Picasso.with(context).load(mData.get(position % mData.size())).into(imageView);

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
