package com.enzo.commonlib.utils.album.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.enzo.commonlib.R;
import com.enzo.commonlib.utils.album.bean.AlbumImage;
import com.enzo.commonlib.utils.album.utils.SelectImagesManager;
import com.enzo.commonlib.utils.common.ToastUtils;

import java.io.File;
import java.util.List;

/**
 * 文 件 名: ImagesAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/3
 * 邮   箱: xiaofangyinwork@163.com
 */
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private Context mContext;
    private List<AlbumImage> mImages;
    private LayoutInflater mInflater;

    private OnImageSelectListener mSelectListener;
    private OnItemClickListener mItemClickListener;
    private int mMaxCount;

    public ImagesAdapter(Context context, int maxCount) {
        mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        mMaxCount = maxCount;
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
        View view = mInflater.inflate(R.layout.adapter_images_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final AlbumImage image = mImages.get(position);
        Glide.with(mContext).load(new File(image.getImagePath()))
                .apply(new RequestOptions().placeholder(R.mipmap.ugc_default_photo))
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(holder.ivImage);

        setItemSelect(holder, image.isSelected(), false);
        //点击选中/取消选中图片
        holder.rlSelectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image.isSelected()) {
                    //如果图片已经选中，就取消选中
                    unSelectImage(image);
                    setItemSelect(holder, false, true);
                } else if (mMaxCount <= 0 || SelectImagesManager.getManager().size() < mMaxCount) {
                    //如果不限制图片的选中数量，或者图片的选中数量
                    // 还没有达到最大限制，就直接选中当前图片。
                    selectImage(image);
                    setItemSelect(holder, true, true);
                } else {
                    ToastUtils.showToast("无法选择更多图片");
                }
            }
        });

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

    /**
     * 选中图片
     */
    private void selectImage(AlbumImage image) {
        image.setSelected(true);
        SelectImagesManager.getManager().add(image);
        if (mSelectListener != null) {
            mSelectListener.OnImageSelect(image, true, SelectImagesManager.getManager().size());
        }
    }

    /**
     * 取消选中图片
     */
    private void unSelectImage(AlbumImage image) {
        image.setSelected(false);
        SelectImagesManager.getManager().remove(image);
        if (mSelectListener != null) {
            mSelectListener.OnImageSelect(image, false, SelectImagesManager.getManager().size());
        }
    }

    /**
     * 设置图片选中和未选中的效果
     */
    private void setItemSelect(ViewHolder holder, boolean isSelect, boolean animate) {
        if (isSelect) {
            holder.ivSelectIcon.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_checkbox_checked));
            holder.ivMasking.setAlpha(0.3f);
        } else {
            holder.ivSelectIcon.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_checkbox_unchecked));
            holder.ivMasking.setAlpha(0f);
        }
    }

    public void setOnImageSelectListener(OnImageSelectListener listener) {
        this.mSelectListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        ImageView ivMasking;
        ImageView ivSelectIcon;
        RelativeLayout rlSelectLayout;

        ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            ivSelectIcon = itemView.findViewById(R.id.iv_select);
            ivMasking = itemView.findViewById(R.id.iv_masking);
            rlSelectLayout = itemView.findViewById(R.id.rl_select_layout);
        }
    }

    public interface OnImageSelectListener {
        void OnImageSelect(AlbumImage image, boolean isSelect, int selectCount);
    }

    public interface OnItemClickListener {
        void OnItemClick(AlbumImage image, int position);
    }
}
