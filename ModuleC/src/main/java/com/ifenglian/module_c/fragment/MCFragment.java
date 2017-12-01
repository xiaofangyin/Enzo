package com.ifenglian.module_c.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ifenglian.commonlib.base.BaseFragment;
import com.ifenglian.commonlib.widget.view.danmu.FLDanMuBean;
import com.ifenglian.commonlib.widget.view.danmu.FLDanMuView;
import com.ifenglian.commonlib.widget.view.danmu.FLSendDanMuDialog;
import com.ifenglian.commonlib.widget.view.speedtest.SRSpeedTestView;
import com.ifenglian.module_c.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MCFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MCFragment extends BaseFragment implements View.OnClickListener {

    private FLDanMuView danMuView;
    private ImageView ivEditDanMu;
    private FLSendDanMuDialog sendDanMuDialog;
    private SRSpeedTestView speedTestView;

    @Override
    public int getLayoutId() {
        return R.layout.mc_fragment_c;
    }

    @Override
    public void initView(View rootView) {
        danMuView = rootView.findViewById(R.id.fl_dan_mu);
        speedTestView = rootView.findViewById(R.id.speed_test_view);
        ivEditDanMu = rootView.findViewById(R.id.sr_iv_edit_dan_mu);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        speedTestView.updateSpeed(102400);

        List<FLDanMuBean> danMuList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            FLDanMuBean danMuBean = new FLDanMuBean();
            danMuBean.setContent("我是弹幕" + i);
            danMuList.add(danMuBean);
        }
        danMuView.loadMoreDanMu(danMuList);
        danMuView.start();
    }

    @Override
    public void initListener(View rootView) {
        ivEditDanMu.setOnClickListener(this);
        rootView.findViewById(R.id.sr_iv_show_dan_mu).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sr_iv_edit_dan_mu) {
            sendDanMuDialog = new FLSendDanMuDialog("请输入内容", new FLSendDanMuDialog.DanMuDialogListener() {
                @Override
                public void onSendBack(String inputText) {
                    FLDanMuBean bean = new FLDanMuBean();
                    bean.setContent(inputText);
                    danMuView.insertDanMu(bean);
                    sendDanMuDialog.dismiss();
                }

                @Override
                public void onTextChanged(CharSequence inputTest) {

                }
            });
            sendDanMuDialog.show(getChildFragmentManager(), "dialog");
        } else if (id == R.id.sr_iv_show_dan_mu) {
            if (danMuView.isWorking()) {
                ObjectAnimator alpha = ObjectAnimator.ofFloat(ivEditDanMu, "alpha", 1f, 0f);
                alpha.setDuration(200);
                alpha.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ivEditDanMu.setVisibility(View.GONE);
                    }
                });
                alpha.start();
                danMuView.hide();
            } else {
                ivEditDanMu.setVisibility(View.VISIBLE);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(ivEditDanMu, "alpha", 0f, 1f);
                alpha.setDuration(200);
                alpha.start();
                danMuView.start();
            }
        }
    }
}
