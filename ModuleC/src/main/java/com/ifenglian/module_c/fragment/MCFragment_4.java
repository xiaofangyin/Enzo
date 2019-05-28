package com.ifenglian.module_c.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.widget.barragephoto.adapter.AdapterListener;
import com.enzo.commonlib.widget.barragephoto.adapter.BarrageAdapter;
import com.enzo.commonlib.widget.barragephoto.ui.BarrageView;
import com.ifenglian.module_c.R;
import com.ifenglian.module_c.model.BarrageData;

/**
 * 文 件 名: MCFragment_4
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MCFragment_4 extends BaseFragment {

    private String text[] = {"666666666666666", "大盖伦无敌！", "这波操作不亏，50血极限反杀，我们还有机会！"
            , "雷瑟守备钻石守门员求带～", "反向操作！！！！！", "谢谢金克丝送的一发火箭"};

    private String name[] = {"逍遥子送了一架飞机", "盐城小王送了一辆UFO", "无敌的VN送了一辆宝马", "快乐的皮皮虾送了一发火箭"};

    private final int ICON_RESOURCES[] = {R.mipmap.mc_plane, R.mipmap.mc_ufo, R.mipmap.mc_car, R.mipmap.mc_rocket};

    private BarrageView barrageView;
    private BarrageAdapter<BarrageData> mAdapter;

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
        for (int i = 0; i < 50; i++) {
            if (i == 0 || i % 9 != 0)
                if (i % 4 != 0)
                    mAdapter.add(new BarrageData(text[i % 6], 0, i));
                else
                    mAdapter.add(new BarrageData(text[i % 6], 1, i));
            else
                mAdapter.add(new BarrageData(name[i % name.length], 2, i));
        }
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
        barrageView.setAdapter(mAdapter = new BarrageAdapter<BarrageData>(null, getActivity()) {
            @Override
            public BarrageViewHolder<BarrageData> onCreateViewHolder(View root, int type) {
                if (type == R.layout.barrage_item_text || type == R.layout.barrage_item_text_vip) {
                    return new TextViewHolder(root);
                } else {
                    return new BigHolder(root);
                }
            }

            @Override
            public int getItemLayout(BarrageData barrageData) {
                switch (barrageData.getType()) {
                    case 0:
                        return R.layout.barrage_item_text;
                    case 1:
                        return R.layout.barrage_item_text_vip;
                    default:
                        return R.layout.barrage_item_big;
                }
            }
        });
        // 设置监听器
        mAdapter.setAdapterListener(new AdapterListener<BarrageData>() {
            @Override
            public void onItemClick(BarrageAdapter.BarrageViewHolder<BarrageData> holder, BarrageData item) {
                Toast.makeText(getActivity(), item.getContent() + "点击了一次", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class TextViewHolder extends BarrageAdapter.BarrageViewHolder<BarrageData> {

        private TextView mContent;

        public TextViewHolder(View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.content);
        }

        @Override
        protected void onBind(BarrageData data) {
            mContent.setText(data.getContent());
        }
    }

    class BigHolder extends BarrageAdapter.BarrageViewHolder<BarrageData> {

        private ImageView mHeadView;
        private TextView mContent;

        public BigHolder(View itemView) {
            super(itemView);
            mHeadView = itemView.findViewById(R.id.image);
            mContent = itemView.findViewById(R.id.content);
        }

        @Override
        protected void onBind(BarrageData data) {
            mHeadView.setImageResource(ICON_RESOURCES[data.getPos() % ICON_RESOURCES.length]);
            mContent.setText(data.getContent());
        }
    }
}
