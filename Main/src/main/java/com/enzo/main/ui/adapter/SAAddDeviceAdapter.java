package com.enzo.main.ui.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.enzo.commonlib.base.BaseRecyclerViewAdapter;
import com.enzo.commonlib.base.BaseViewHolder;
import com.enzo.commonlib.utils.common.DensityUtil;
import com.enzo.flkit.plugin.FLPluginBaseCell;
import com.enzo.flkit.plugin.FLPluginBaseObject;
import com.enzo.flkit.plugin.FLPluginCellStyle;
import com.enzo.main.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 文 件 名: SAAddDeviceAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-05-29
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SAAddDeviceAdapter extends BaseRecyclerViewAdapter<FLPluginBaseObject> {

    private Context context;
    private SparseArray<LinkedList<FLPluginBaseCell>> mViewCache;
    private List<Integer> heightList;

    public SAAddDeviceAdapter(Context context) {
        this.context = context;
        mViewCache = new SparseArray<>();
        heightList = new ArrayList<>();
    }

    @Override
    public void setNewData(List<FLPluginBaseObject> list) {
        if (list != null) {
            heightList.clear();
            for (int i = 0; i < list.size(); i++) {
                int height1 = DensityUtil.dip2px(context,60);
                int random = new Random().nextInt(100);
                heightList.add(height1 + random);
            }
        }
        super.setNewData(list);
    }

    @Override
    public void setLoadMoreData(List<FLPluginBaseObject> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                int height1 = DensityUtil.dip2px(context,60);
                int random = new Random().nextInt(100);
                heightList.add(height1 + random);
            }
        }
        super.setLoadMoreData(list);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sa_item_add_device, viewGroup, false);
        return new AddDeviceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.setUpView(mData.get(i), i, this);
        ViewGroup.LayoutParams layoutParams = baseViewHolder.itemView.getLayoutParams();
        layoutParams.height = heightList.get(i);
        baseViewHolder.itemView.setLayoutParams(layoutParams);
    }

    private class AddDeviceHolder extends BaseViewHolder<FLPluginBaseObject> {

        private FrameLayout flContainer;

        AddDeviceHolder(View itemView) {
            super(itemView);
            flContainer = itemView.findViewById(R.id.sa_add_device_container);
        }

        @Override
        public void setUpView(FLPluginBaseObject model, int position, RecyclerView.Adapter adapter) {
            if (flContainer.getChildCount() != 0) {
                FLPluginBaseCell view = (FLPluginBaseCell) flContainer.getChildAt(0);
                ((ViewGroup) view.getParent()).removeAllViews();
                putCell(view);
            }

            FLPluginBaseCell baseCell = getCell(model.type);
            if (baseCell == null) {
                baseCell = model.buildCellWithStyle(FLPluginCellStyle.FLPluginCellStyleNormal);
            }
            if (baseCell != null) {
                baseCell.layoutWithModel(model);
                flContainer.addView(baseCell);
            }
        }

        private void putCell(FLPluginBaseCell cell) {
            if (mViewCache.get(cell.getType()) == null) {
                LinkedList<FLPluginBaseCell> linkedList = new LinkedList<>();
                linkedList.add(cell);
                mViewCache.put(cell.getType(), linkedList);
            } else {
                mViewCache.get(cell.getType()).add(cell);
            }
        }

        private FLPluginBaseCell getCell(int type) {
            if (mViewCache.indexOfKey(type) >= 0) {
                return mViewCache.get(type).poll();
            } else {
                return null;
            }
        }
    }
}
