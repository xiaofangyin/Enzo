package com.ifenglian.module_b.plugin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ifenglian.flkit.FLPluginBaseCell;
import com.ifenglian.flkit.FLPluginBaseObject;
import com.ifenglian.module_b.R;

/**
 * 文 件 名: MANormalPluginItem
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-05-29
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MBNormalPluginItem extends FLPluginBaseCell {

    private TextView tvName;

    public MBNormalPluginItem(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.mb_item_add_device, this, true);
        tvName = view.findViewById(R.id.ma_device_name);
    }

    @Override
    public void layoutWithModel(FLPluginBaseObject model) {
        tvName.setText(model.alias);
    }

    @Override
    public void cellPressed() {

    }
}
