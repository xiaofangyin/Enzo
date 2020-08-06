package com.enzo.module_b.plugin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.flkit.plugin.FLPluginBaseCell;
import com.enzo.flkit.plugin.FLPluginBaseObject;
import com.enzo.module_b.R;

/**
 * 文 件 名: MANormalPluginItem
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-05-29
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MBNormalPluginItem extends FLPluginBaseCell implements View.OnClickListener {

    private FLPluginBaseObject baseObject;
    private TextView tvName;

    public MBNormalPluginItem(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.mb_item_add_device, this, true);
        tvName = view.findViewById(R.id.mb_device_name);
        tvName.setOnClickListener(this);
    }

    @Override
    public int getType() {
        if (baseObject != null) {
            return baseObject.type;
        }
        return 0;
    }

    @Override
    public void layoutWithModel(FLPluginBaseObject model) {
        this.baseObject = model;
        tvName.setText(model.alias);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.mb_device_name) {
            ToastUtil.show(baseObject.alias);
        }
    }
}
