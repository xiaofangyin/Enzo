package com.enzo.commonlib.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.enzo.skin.manager.base.SkinBaseFragment;

/**
 * 文 件 名: BaseFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/9/26
 * 邮   箱: xiaofangyinwork@163.com
 */
public abstract class BaseFragment extends SkinBaseFragment implements IBaseFragment {

    public boolean isFirstLoad = true;
    //发生Fragment重叠的根本原因在于FragmentState没有保存Fragment的显示状态，
    //即mHidden，导致页面重启后，该值为默认的false，即show状态，所以导致了Fragment的重叠。
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            isFirstLoad = false;
            lazyLoad();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), null);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener(view);
    }

}
