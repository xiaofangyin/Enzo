package com.enzo.commonlib.utils.album.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.enzo.commonlib.R;
import com.enzo.commonlib.utils.album.bean.AlbumFolder;
import com.enzo.commonlib.utils.album.bean.AlbumImage;

import java.io.File;
import java.util.List;

/**
 * 文 件 名: FoldersAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/3
 * 邮   箱: xiaofangyinwork@163.com
 */
public class FoldersAdapter extends RecyclerView.Adapter<FoldersAdapter.ViewHolder> {

    private Context mContext;
    private List<AlbumFolder> mFolders;
    private LayoutInflater mInflater;
    private OnFolderSelectListener mListener;

    public FoldersAdapter(Context context, List<AlbumFolder> folders) {
        mContext = context;
        mFolders = folders;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_folder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final AlbumFolder folder = mFolders.get(position);
        List<AlbumImage> images = folder.getImages();
        holder.tvFolderName.setText(folder.getName());
        if (images != null && !images.isEmpty()) {
            holder.tvFolderSize.setText(images.size() + "张");
            Glide.with(mContext).load(new File(images.get(0).getImagePath()))
                    .apply(new RequestOptions().placeholder(R.mipmap.ugc_default_photo))
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .into(holder.ivImage);
        } else {
            holder.tvFolderSize.setText("0张");
            holder.ivImage.setImageBitmap(null);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                if (mListener != null) {
                    mListener.OnFolderSelect(folder);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFolders == null ? 0 : mFolders.size();
    }

    public void setOnFolderSelectListener(OnFolderSelectListener listener) {
        this.mListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvFolderName;
        TextView tvFolderSize;

        ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            tvFolderName = itemView.findViewById(R.id.tv_folder_name);
            tvFolderSize = itemView.findViewById(R.id.tv_folder_size);
        }
    }

    public interface OnFolderSelectListener {
        void OnFolderSelect(AlbumFolder folder);
    }

}
