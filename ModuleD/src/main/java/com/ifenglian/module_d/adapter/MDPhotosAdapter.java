package com.ifenglian.module_d.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.ifenglian.module_d.R;
import com.ifenglian.module_d.bean.MDImageItem;
import com.ifenglian.module_d.utils.MDNativeImageLoader;

import java.util.ArrayList;

/**
 * 文 件 名: MDPhotosAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/16
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDPhotosAdapter extends BaseAdapter implements OnScrollListener {

    private Context mContext;
    private GridView gridView;
    private MDNativeImageLoader mImageDownLoader;
    private ArrayList<MDImageItem> itemList;
    private int mFirstVisibleItem;
    private int mVisibleItemCount;
    private boolean isFirstEnter = true;

    public MDPhotosAdapter(Context context, GridView gridView, ArrayList<MDImageItem> itemList) {
        this.mContext = context;
        this.gridView = gridView;
        this.itemList = itemList;
        this.gridView.setOnScrollListener(this);
        mImageDownLoader = new MDNativeImageLoader(context);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewItemHolder gridHolder;
        String imagePath = itemList.get(position).getImagePath();
        if (convertView == null) {
            gridHolder = new GridViewItemHolder();
            convertView = View.inflate(mContext, R.layout.md_item_photo_grid_row, null);
            gridHolder.imageView = convertView.findViewById(R.id.iv_imageItem);
            gridHolder.imageView.setTag(imagePath);
            convertView.setTag(gridHolder);
        } else {
            gridHolder = (GridViewItemHolder) convertView.getTag();
            gridHolder.imageView.setTag(imagePath);
        }

        if (imagePath != null) {
            Bitmap bitmap = mImageDownLoader.showCacheBitmap(imagePath.substring(imagePath.lastIndexOf("/") + 1, imagePath.length()));
            if (bitmap != null) {
                gridHolder.imageView.setImageBitmap(bitmap);
            } else {
                gridHolder.imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.sa_default_photo));
            }
        }
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            showImage(mFirstVisibleItem, mVisibleItemCount);
        } else {
            cancelTask();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;
        mVisibleItemCount = visibleItemCount;
        if (visibleItemCount > 0 && isFirstEnter) {
            showImage(mFirstVisibleItem, mVisibleItemCount);
            isFirstEnter = false;
        }
    }

    private static class GridViewItemHolder {
        ImageView imageView;
    }

    private void showImage(int firstVisibleItem, int visibleItemCount) {
        Bitmap bitmap;
        for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++) {
            String mImageUrl = itemList.get(i).getImagePath();
            final ImageView mImageView = (ImageView) gridView.findViewWithTag(mImageUrl);
            final MDImageItem item = itemList.get(i);
            bitmap = mImageDownLoader.downloadImage(mImageUrl, String.valueOf(item.getImageId()), new MDNativeImageLoader.OnImageLoaderListener() {
                @Override
                public void onImageLoader(Bitmap bitmap, String url) {
                    if (mImageView != null && bitmap != null) {
                        mImageView.setImageBitmap(bitmap);
                    }
                }
            });

            if (bitmap != null) {
                mImageView.setImageBitmap(bitmap);
            } else {
                mImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.sa_default_photo));
            }
        }
    }

    /**
     * 取消下载任务
     */
    private void cancelTask() {
        mImageDownLoader.cancelTask();
    }
}
