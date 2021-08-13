package com.enzo.module_c.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.module_c.R;
import com.enzo.module_c.model.ColumnBean;

/**
 * 文 件 名: MCFragment_3
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MCFragment_3 extends BaseFragment {

    private TextView textView;

    @Override
    public int getLayoutId() {
        return R.layout.mc_fragment_c_child_3;
    }

    @Override
    public void initView(View rootView) {
        textView = rootView.findViewById(R.id.text_view);
    }

    @Override
    public void initListener(View rootView) {

    }

    @Override
    public void lazyLoad() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            ColumnBean bean = (ColumnBean) getArguments().getSerializable("entity");
            if (bean != null) {
                textView.setText(bean.getColumn_name());
            }
        }
    }
}
