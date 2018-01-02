package com.ifenglian.commonlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifenglian.commonlib.utils.common.LogUtil;

/**
 * 文 件 名: BaseFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/9/26
 * 邮   箱: xiaofy@ifenglian.com
 */
public abstract class BaseFragment extends Fragment implements IBaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData(savedInstanceState);
        initListener(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e(BaseFragment.this.getClass().getSimpleName() + "...onResume...");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.e(BaseFragment.this.getClass().getSimpleName() + "...onPause...");
    }
}
