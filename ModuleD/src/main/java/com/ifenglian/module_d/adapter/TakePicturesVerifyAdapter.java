package com.ifenglian.module_d.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.enzo.commonlib.utils.album.bean.AlbumImage;
import com.ifenglian.module_d.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: TakePicturesVerifyAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/6
 * 邮   箱: xiaofy@ifenglian.com
 */
public class TakePicturesVerifyAdapter extends BaseAdapter {

    private Context context;
    private static final int TYPE_ADD_IMAGE = 0;
    private static final int TYPE_SELECTED_IMAGE = 1;
    private List<AlbumImage> mData;
    private int maxShowCount = 3;

    public TakePicturesVerifyAdapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();
    }

    public void setMaxShowCount(int count) {
        maxShowCount = count;
    }

    public List<AlbumImage> getData() {
        return mData;
    }

    public void remove(int i) {
        mData.remove(i);
        notifyDataSetChanged();
    }

    public void add(List<AlbumImage> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void add(AlbumImage image) {
        if (!TextUtils.isEmpty(image.getImagePath())) {
            mData.add(image);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.isEmpty()) {
            return TYPE_ADD_IMAGE;
        } else if (mData.size() >= maxShowCount) {
            return TYPE_SELECTED_IMAGE;
        } else {
            if (position == mData.size()) {
                return TYPE_ADD_IMAGE;
            } else {
                return TYPE_SELECTED_IMAGE;
            }
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        if (mData.isEmpty()) {
            return 1;
        } else if (mData.size() >= maxShowCount) {
            return maxShowCount;
        } else {
            return mData.size() + 1;
        }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.md_item_take_picture_verify, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        setData(i, viewHolder);
        return view;
    }

    private void setData(int i, ViewHolder viewHolder) {
        int viewType = getItemViewType(i);
        switch (viewType) {
            case TYPE_ADD_IMAGE:
                Log.d("AAA", "TYPE_ADD_IMAGE i: " + i);
                viewHolder.ivClose.setVisibility(View.GONE);
                viewHolder.imageView.setImageResource(R.mipmap.icon_add_photo);
                viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.add();
                        }
                    }
                });
                break;
            case TYPE_SELECTED_IMAGE:
                Log.d("AAA", "TYPE_SELECTED_IMAGE i: " + i);
                Glide.with(context).load(new File(mData.get(i).getImagePath()))
                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                        .into(viewHolder.imageView);
                final int j = i;
                viewHolder.ivClose.setVisibility(View.VISIBLE);
                viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.imageClick(j);
                        }
                    }
                });
                viewHolder.ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.imageRemove(j);
                        }
                    }
                });
                break;
        }
    }

    private static class ViewHolder {

        ImageView imageView;
        ImageView ivClose;

        ViewHolder(View view) {
            imageView = view.findViewById(R.id.take_pictures_verify_iv);
            ivClose = view.findViewById(R.id.iv_close);
        }
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {

        void add();

        void imageClick(int position);

        void imageRemove(int position);
    }
}
