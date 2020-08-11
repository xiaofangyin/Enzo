package com.enzo.module_d.ui.activity.lighter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.lighter.Lighter;
import com.enzo.commonlib.widget.lighter.interfaces.OnLighterViewClickListener;
import com.enzo.commonlib.widget.lighter.parameter.Direction;
import com.enzo.commonlib.widget.lighter.parameter.LighterParameter;
import com.enzo.commonlib.widget.lighter.parameter.MarginOffset;
import com.enzo.commonlib.widget.lighter.shape.OvalShape;
import com.enzo.module_d.R;

import java.util.ArrayList;
import java.util.List;

public class MDLighterFragment extends Fragment {

    private Lighter mLighter;
    private List<View> mTipViewList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.md_layout_lighter_viewpager_1, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTipViewList.clear();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button mButton1 = view.findViewById(R.id.vp_btn_1);
        Button mButton2 = view.findViewById(R.id.vp_btn_2);
        Button mButton3 = view.findViewById(R.id.vp_btn_3);
        Button mButton4 = view.findViewById(R.id.vp_btn_4);
        Button mButton5 = view.findViewById(R.id.vp_btn_5);

        mButton1.setOnClickListener(mClickListener);
        mButton2.setOnClickListener(mClickListener);
        mButton3.setOnClickListener(mClickListener);
        mButton4.setOnClickListener(mClickListener);
        mButton5.setOnClickListener(mClickListener);

        View tipView1 = getLayoutInflater().inflate(R.layout.md_layout_lighter_tip_1, null);
        View tipView2 = getLayoutInflater().inflate(R.layout.md_layout_lighter_tip_7, null);
        View tipView3 = getLayoutInflater().inflate(R.layout.md_layout_lighter_tip_2, null);
        View tipView4 = getLayoutInflater().inflate(R.layout.md_layout_lighter_tip_3, null);
        View tipView5 = getLayoutInflater().inflate(R.layout.md_layout_lighter_tip_4, null);

        tipView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("You click tip view 1");
                showNext();
            }
        });

        tipView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("You click tip view 2");
                showNext();
            }
        });

        tipView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("You click tip view 3");
                showNext();
            }
        });

        tipView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("You click tip view 4");
                showNext();
            }
        });

        tipView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("You click tip view 5");
                showNext();
            }
        });


        mTipViewList.add(tipView1);
        mTipViewList.add(tipView2);
        mTipViewList.add(tipView3);
        mTipViewList.add(tipView4);
        mTipViewList.add(tipView5);
        showGuide();
    }

    private void showGuide() {
        mLighter = Lighter.with((ViewGroup) getView())
                .setIntercept(true)
                //The callback interface will not be called if intercept is true.
                //If intercept is true, you need to call Lighter.next() by yourself
                .setOnClickListener(new OnLighterViewClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(getActivity(), "No show...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.vp_btn_1)
                        .setLighterShape(new OvalShape())
                        .setTipView(mTipViewList.get(0))
                        .setTipViewDisplayAnimation(MDLighterHelper.getScaleAnimation())
                        .setTipViewRelativeDirection(Direction.RIGHT)
                        .setTipViewRelativeOffset(new MarginOffset(30, 0, 80, 0))
                        .build())
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.vp_btn_2)
                        .setLighterShape(new OvalShape())
                        .setTipView(mTipViewList.get(1))
                        .setTipViewRelativeDirection(Direction.LEFT)
                        .setTipViewDisplayAnimation(MDLighterHelper.getScaleAnimation())
                        .setTipViewRelativeOffset(new MarginOffset(50, 0, 100, 0))
                        .build())
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.vp_btn_3)
                        .setLighterShape(new OvalShape())
                        .setTipView(mTipViewList.get(2))
                        .setTipViewRelativeDirection(Direction.TOP)
                        .setTipViewDisplayAnimation(MDLighterHelper.getScaleAnimation())
                        .setTipViewRelativeOffset(new MarginOffset(-400, 0, 0, 30))
                        .build())
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.vp_btn_4)
                        .setLighterShape(new OvalShape())
                        .setTipView(mTipViewList.get(3))
                        .setTipViewRelativeDirection(Direction.TOP)
                        .setTipViewRelativeOffset(new MarginOffset(80, 0, 0, 20))
                        .setTipViewDisplayAnimation(MDLighterHelper.getScaleAnimation())
                        .build())
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.vp_btn_5)
                        .setLighterShape(new OvalShape())
                        .setTipView(mTipViewList.get(4))
                        .setTipViewRelativeDirection(Direction.TOP)
                        .setTipViewRelativeOffset(new MarginOffset(0, 100, 0, 20))
                        .setTipViewDisplayAnimation(MDLighterHelper.getScaleAnimation())
                        .build());

        mLighter.show();
    }

    private void showNext() {
        if (mLighter != null) {
            mLighter.next();
        }
    }


    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.vp_btn_1) {
                showNext();
                ToastUtil.show("You click button 1", Toast.LENGTH_SHORT);
            } else if (id == R.id.vp_btn_2) {
                showNext();
                ToastUtil.show("You click button 2", Toast.LENGTH_SHORT);
            } else if (id == R.id.vp_btn_3) {
                showNext();
                ToastUtil.show("You click button 3", Toast.LENGTH_SHORT);
            } else if (id == R.id.vp_btn_4) {
                showNext();
                ToastUtil.show("You click button 4", Toast.LENGTH_SHORT);
            } else if (id == R.id.vp_btn_5) {
                showNext();
                ToastUtil.show("You click button 5", Toast.LENGTH_SHORT);
            }
        }
    };
}
