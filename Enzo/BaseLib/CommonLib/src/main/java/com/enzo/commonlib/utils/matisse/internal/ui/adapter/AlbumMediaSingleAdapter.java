/*
 * Copyright 2017 Zhihu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.enzo.commonlib.utils.matisse.internal.ui.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enzo.commonlib.R;
import com.enzo.commonlib.utils.matisse.internal.entity.Item;
import com.enzo.commonlib.utils.matisse.internal.entity.SelectionSpec;
import com.enzo.commonlib.utils.matisse.internal.ui.widget.CheckView;
import com.enzo.commonlib.utils.matisse.internal.ui.widget.MediaGrid;

public class AlbumMediaSingleAdapter extends
        RecyclerViewCursorAdapter<RecyclerView.ViewHolder> implements
        MediaGrid.OnMediaGridClickListener {

    private static final int VIEW_TYPE_CAPTURE = 0x01;
    private static final int VIEW_TYPE_MEDIA = 0x02;
    private final Drawable mPlaceholder;
    private SelectionSpec mSelectionSpec;
    private OnMediaClickListener mOnMediaClickListener;
    private RecyclerView mRecyclerView;
    private int mImageResize;

    public AlbumMediaSingleAdapter(Context context, RecyclerView recyclerView) {
        super(null);
        mSelectionSpec = SelectionSpec.getInstance();

        TypedArray ta = context.getTheme().obtainStyledAttributes(new int[]{R.attr.item_placeholder});
        mPlaceholder = ta.getDrawable(0);
        ta.recycle();

        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_CAPTURE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.matisse_photo_capture_item, parent, false);
            CaptureViewHolder holder = new CaptureViewHolder(v);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getContext() instanceof OnPhotoCapture) {
                        ((OnPhotoCapture) v.getContext()).capture();
                    }
                }
            });
            return holder;
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.matisse_media_grid_item, parent, false);
            return new MediaViewHolder(v);
        }

    }

    @Override
    protected void onBindViewHolder(final RecyclerView.ViewHolder holder, Cursor cursor) {
        if (holder instanceof CaptureViewHolder) {
            CaptureViewHolder captureViewHolder = (CaptureViewHolder) holder;
            Drawable[] drawables = captureViewHolder.mHint.getCompoundDrawables();
            TypedArray ta = holder.itemView.getContext().getTheme().obtainStyledAttributes(
                    new int[]{R.attr.capture_textColor});
            int color = ta.getColor(0, 0);
            ta.recycle();

            for (int i = 0; i < drawables.length; i++) {
                Drawable drawable = drawables[i];
                if (drawable != null) {
                    final Drawable.ConstantState state = drawable.getConstantState();
                    if (state == null) {
                        continue;
                    }

                    Drawable newDrawable = state.newDrawable().mutate();
                    newDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                    newDrawable.setBounds(drawable.getBounds());
                    drawables[i] = newDrawable;
                }
            }
            captureViewHolder.mHint.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        } else if (holder instanceof MediaViewHolder) {
            MediaViewHolder mediaViewHolder = (MediaViewHolder) holder;

            final Item item = Item.valueOf(cursor);
            mediaViewHolder.mMediaGrid.preBindMedia(new MediaGrid.PreBindInfo(
                    getImageResize(mediaViewHolder.mMediaGrid.getContext()),
                    mPlaceholder,
                    mSelectionSpec.countable,
                    holder
            ));
            mediaViewHolder.mMediaGrid.bindMedia(item);
            mediaViewHolder.mMediaGrid.setOnMediaGridClickListener(this);
        }
    }


    @Override
    public void onThumbnailClicked(ImageView thumbnail, Item item, RecyclerView.ViewHolder holder) {
        if (mOnMediaClickListener != null) {
            mOnMediaClickListener.onMediaClick(null, item, holder.getAdapterPosition());
        }
    }

    @Override
    public void onCheckViewClicked(CheckView checkView, Item item, RecyclerView.ViewHolder holder) {

    }

    @Override
    public int getItemViewType(int position, Cursor cursor) {
        return Item.valueOf(cursor).isCapture() ? VIEW_TYPE_CAPTURE : VIEW_TYPE_MEDIA;
    }

    public void registerOnMediaClickListener(OnMediaClickListener listener) {
        mOnMediaClickListener = listener;
    }

    public void unregisterOnMediaClickListener() {
        mOnMediaClickListener = null;
    }


    private int getImageResize(Context context) {
        if (mImageResize == 0) {
            RecyclerView.LayoutManager lm = mRecyclerView.getLayoutManager();
            int spanCount = ((GridLayoutManager) lm).getSpanCount();
            int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            int availableWidth = screenWidth - context.getResources().getDimensionPixelSize(
                    R.dimen.matisse_media_grid_spacing) * (spanCount - 1);
            mImageResize = availableWidth / spanCount;
            mImageResize = (int) (mImageResize * mSelectionSpec.thumbnailScale);
        }
        return mImageResize;
    }

    private static class MediaViewHolder extends RecyclerView.ViewHolder {

        private MediaGrid mMediaGrid;

        MediaViewHolder(View itemView) {
            super(itemView);
            mMediaGrid = itemView.findViewById(R.id.media_grid);
            mMediaGrid.setCheckViewVisibility(false);
        }
    }

    private static class CaptureViewHolder extends RecyclerView.ViewHolder {

        private TextView mHint;

        CaptureViewHolder(View itemView) {
            super(itemView);

            mHint = (TextView) itemView.findViewById(R.id.hint);
        }
    }

}
