package com.enzo.module_d.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.enzo.commonlib.base.BaseActivity;
import com.enzo.flkit.router.ModuleDRouterPath;
import com.enzo.module_d.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

/**
 * Boss公司详情
 */
@Route(path = ModuleDRouterPath.MODULE_D_BOSS_COMPANY)
public class MDBossCompanyDetailActivity extends BaseActivity {

    private BottomSheetBehavior bottomSheetBehavior;
    private NestedScrollView contentLayout;
    private int mTouchSlop;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_company_detail;
    }

    @Override
    public void initView() {
        contentLayout = findViewById(R.id.content_view);

        //设置标题
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Boss");
        toolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //设置底部
        View bottomSheet = findViewById(R.id.boss_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            /**
             * 状态的改变
             */
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            /**
             * 拖拽回调
             */
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        contentLayout.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                /**
                 * 上滑
                 */
                if (scrollY - oldScrollY > mTouchSlop) {
                    Log.e("onScrollChange", "上滑");
                    if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN && bottomSheetBehavior.isHideable()) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                }

                /**
                 * 下滑
                 */
                else if (scrollY - oldScrollY < -mTouchSlop) {
                    Log.e("onScrollChange", "下滑");
                    if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
            }
        });
    }

    @Override
    public void initListener() {

    }

    public void changeBottom(View view) {
        /**
         * 根据当前状态切换 开关
         */
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }


    public boolean isShow() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            return false;
        }
        return true;
    }

}