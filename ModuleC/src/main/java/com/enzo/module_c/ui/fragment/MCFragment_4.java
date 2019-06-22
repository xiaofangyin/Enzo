package com.enzo.module_c.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.widget.barrage.adapter.AdapterListener;
import com.enzo.commonlib.widget.barrage.adapter.BaseBarrageAdapter;
import com.enzo.commonlib.widget.barrage.model.BarrageData;
import com.enzo.commonlib.widget.barrage.ui.BarrageView;
import com.enzo.module_c.R;
import com.enzo.module_c.model.BarrageDataHelper;
import com.enzo.module_c.ui.adapter.MCBarrageAdapter;

import java.util.Random;

/**
 * 文 件 名: MCFragment_4
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MCFragment_4 extends BaseFragment {

    private BarrageView barrageView;
    private BaseBarrageAdapter<BarrageData> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.mc_fragment_c_child_4;
    }

    @Override
    public void initView(View rootView) {
        barrageView = rootView.findViewById(R.id.barrage);
        initBarrage();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        handler.sendEmptyMessage(new Random().nextInt(3));
    }

    @Override
    public void initListener(View rootView) {

    }

    private void initBarrage() {
        BarrageView.Options options = new BarrageView.Options()
                .setGravity(BarrageView.GRAVITY_FULL)                // 设置弹幕的位置
                .setInterval(50)                                     // 设置弹幕的发送间隔
                .setModel(BarrageView.MODEL_COLLISION_DETECTION)     // 设置弹幕生成模式
                .setClick(true);                                     // 设置弹幕是否可以点击
        barrageView.setOptions(options);
        // 设置适配器 第一个参数是点击事件的监听器
        mAdapter = new MCBarrageAdapter(getActivity());
        barrageView.setAdapter(mAdapter);
        // 设置监听器
        mAdapter.setAdapterListener(new AdapterListener<BarrageData>() {
            @Override
            public void onItemClick(BaseBarrageAdapter.BarrageViewHolder<BarrageData> holder, BarrageData item) {
                Toast.makeText(getActivity(), item.getContent() + "点击了一次", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mAdapter.add(BarrageDataHelper.buildBarrageData(msg.what));
            int random = new Random().nextInt(3);
            LogUtil.d("random: " + random);
            sendEmptyMessageDelayed(random, 400);
        }
    };
}
