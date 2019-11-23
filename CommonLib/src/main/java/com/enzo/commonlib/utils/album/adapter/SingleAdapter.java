package com.enzo.commonlib.utils.album.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.enzo.commonlib.R;
import com.enzo.commonlib.utils.album.bean.AlbumImage;

import java.io.File;
import java.util.List;

/**
 * 文 件 名: ImagesAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/3
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SingleAdapter extends RecyclerView.Adapter<SingleAdapter.ViewHolder> {

    private Context mContext;
    private List<AlbumImage> mImages;
    private LayoutInflater mInflater;

    private OnItemClickListener mItemClickListener;

    public SingleAdapter(Context context) {
        mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }


    public List<AlbumImage> getData() {
        return mImages;
    }

    public void refresh(List<AlbumImage> data) {
        mImages = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_single_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final AlbumImage image = mImages.get(position);
        Glide.with(mContext).load(new File(image.getImagePath()))
                .apply(new RequestOptions().placeholder(R.mipmap.ugc_default_photo))
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(holder.ivImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.OnItemClick(image, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImages == null ? 0 : mImages.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;

        ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(AlbumImage image, int position);
    }
}
