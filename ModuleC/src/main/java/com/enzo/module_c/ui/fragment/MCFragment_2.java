package com.enzo.module_c.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.allangleexpandablebutton.ExpandableMenu;
import com.enzo.commonlib.widget.allangleexpandablebutton.ButtonData;
import com.enzo.commonlib.widget.allangleexpandablebutton.ButtonEventListener;
import com.enzo.module_c.R;
import com.enzo.module_c.model.ColumnBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MCFragment_2
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MCFragment_2 extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.mc_fragment_c_child_2;
    }

    @Override
    public void initView(View rootView) {
        installButton90to90(rootView);
        installButton90to180(rootView);
        installButton110to250(rootView);
        installButton0to360(rootView);
    }

    @Override
    public void initListener(View rootView) {

    }

    @Override
    public void lazyLoad() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            ColumnBean bean = (ColumnBean) getArguments().getSerializable("entity");
        }
    }

    private void installButton90to90(View rootView) {
        final ExpandableMenu button = (ExpandableMenu) rootView.findViewById(R.id.button_expandable_90_90);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.plus, R.drawable.mark, R.drawable.settings, R.drawable.heart};
        int[] color = {R.color.mc_blue, R.color.mc_red, R.color.mc_green, R.color.mc_yellow};
        for (int i = 0; i < 4; i++) {
            ButtonData buttonData;
            if (i == 0) {
                buttonData = ButtonData.buildIconButton(rootView.getContext(), drawable[i], 15);
            } else {
                buttonData = ButtonData.buildIconButton(rootView.getContext(), drawable[i], 0);
            }
            buttonData.setBackgroundColorId(rootView.getContext(), color[i]);
            buttonDatas.add(buttonData);
        }
        button.setButtonDatas(buttonDatas);
        setListener(button);
    }

    private void installButton90to180(View rootView) {
        final ExpandableMenu button = (ExpandableMenu) rootView.findViewById(R.id.button_expandable_90_180);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.plus, R.drawable.mark, R.drawable.settings, R.drawable.heart};
        int[] color = {R.color.mc_blue, R.color.mc_red, R.color.mc_green, R.color.mc_yellow};
        for (int i = 0; i < 4; i++) {
            ButtonData buttonData;
            if (i == 0) {
                buttonData = ButtonData.buildIconButton(rootView.getContext(), drawable[i], 15);
            } else {
                buttonData = ButtonData.buildIconButton(rootView.getContext(), drawable[i], 0);
            }
            buttonData.setBackgroundColorId(rootView.getContext(), color[i]);
            buttonDatas.add(buttonData);
        }
        button.setButtonDatas(buttonDatas);
        setListener(button);
    }

    private void installButton110to250(View rootView) {
        final ExpandableMenu button = (ExpandableMenu) rootView.findViewById(R.id.button_expandable_110_250);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.edit, R.drawable.refresh, R.drawable.mark, R.drawable.settings, R.drawable.heart, R.drawable.search, R.drawable.copy};
        int[] color = {R.color.mc_black, R.color.mc_orange, R.color.mc_blue, R.color.mc_pink, R.color.mc_yellow, R.color.mc_red, R.color.mc_green};
        for (int i = 0; i < 7; i++) {
            ButtonData buttonData;
            if (i == 0) {
                buttonData = ButtonData.buildIconButton(rootView.getContext(), drawable[i], 15);
            } else {
                buttonData = ButtonData.buildIconButton(rootView.getContext(), drawable[i], 0);
            }
            buttonData.setBackgroundColorId(rootView.getContext(), color[i]);
            buttonDatas.add(buttonData);
        }
        button.setButtonDatas(buttonDatas);
        setListener(button);
    }

    private void installButton0to360(View rootView) {
        final ExpandableMenu button = (ExpandableMenu) rootView.findViewById(R.id.button_expandable_0_360);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.edit, R.drawable.refresh, R.drawable.mark, R.drawable.settings, R.drawable.heart, R.drawable.search, R.drawable.copy};
        int[] color = {R.color.mc_black, R.color.mc_orange, R.color.mc_blue, R.color.mc_pink, R.color.mc_yellow, R.color.mc_red, R.color.mc_green};
        for (int i = 0; i < 7; i++) {
            ButtonData buttonData;
            if (i == 0) {
                buttonData = ButtonData.buildIconButton(rootView.getContext(), drawable[i], 15);
            } else {
                buttonData = ButtonData.buildIconButton(rootView.getContext(), drawable[i], 0);
            }
            buttonData.setBackgroundColorId(rootView.getContext(), color[i]);
            buttonDatas.add(buttonData);
        }
        button.setButtonDatas(buttonDatas);
        setListener(button);
    }

    private void setListener(ExpandableMenu button) {
        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
                showToast("clicked index:" + index);
            }

            @Override
            public void onExpand() {
//                showToast("onExpand");
            }

            @Override
            public void onCollapse() {
//                showToast("onCollapse");
            }
        });
    }

    private void showToast(String toast) {
        ToastUtil.show(toast);
    }
}
