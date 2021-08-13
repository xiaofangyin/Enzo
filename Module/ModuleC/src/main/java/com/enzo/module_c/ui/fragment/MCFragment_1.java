package com.enzo.module_c.ui.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.widget.togglebutton.android.RMSwitch;
import com.enzo.commonlib.widget.togglebutton.android.RMTristateSwitch;
import com.enzo.module_c.R;

/**
 * 文 件 名: MCFragment_1
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MCFragment_1 extends BaseFragment {

    private TextView mTxtRMSwitchState1;

    private TextView mTxtRMSwitchState2;

    private TextView mTxtRMSwitchState3;

    private TextView mTxtRMSwitchState4;

    private TextView mTxtRMSwitchState5;

    private TextView mTxtRMSwitchState6;

    private TextView mTxtRMSwitchState7;

    private TextView mTxtRMSwitchState8;

    private TextView mTxtRMSwitchState9;


    private TextView mTxtRMTristateSwitchState1;

    private TextView mTxtRMTristateSwitchState2;

    private TextView mTxtRMTristateSwitchState3;

    private TextView mTxtRMTristateSwitchState4;

    private TextView mTxtRMTristateSwitchState5;

    private TextView mTxtRMTristateSwitchState6;

    @Override
    public int getLayoutId() {
        return R.layout.mc_fragment_c_child_1;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView(View rootView) {
        RMTristateSwitch mRMTristateSwitch1 = rootView.findViewById(R.id.rm_triswitch1);
        RMTristateSwitch mRMTristateSwitch2 = rootView.findViewById(R.id.rm_triswitch2);
        RMTristateSwitch mRMTristateSwitch3 = rootView.findViewById(R.id.rm_triswitch3);
        RMTristateSwitch mRMTristateSwitch4 = rootView.findViewById(R.id.rm_triswitch4);
        RMTristateSwitch mRMTristateSwitch5 = rootView.findViewById(R.id.rm_triswitch5);
        RMTristateSwitch mRMTristateSwitch6 = rootView.findViewById(R.id.rm_triswitch6);

        mTxtRMTristateSwitchState1 = rootView.findViewById(R.id.txt_rm_triswitch_state1);
        mTxtRMTristateSwitchState2 = rootView.findViewById(R.id.txt_rm_triswitch_state2);
        mTxtRMTristateSwitchState3 = rootView.findViewById(R.id.txt_rm_triswitch_state3);
        mTxtRMTristateSwitchState4 = rootView.findViewById(R.id.txt_rm_triswitch_state4);
        mTxtRMTristateSwitchState5 = rootView.findViewById(R.id.txt_rm_triswitch_state5);
        mTxtRMTristateSwitchState6 = rootView.findViewById(R.id.txt_rm_triswitch_state6);

        RMSwitch mRMSwitch1 = rootView.findViewById(R.id.rm_switch1);
        RMSwitch mRMSwitch2 = rootView.findViewById(R.id.rm_switch2);
        RMSwitch mRMSwitch3 = rootView.findViewById(R.id.rm_switch3);
        RMSwitch mRMSwitch4 = rootView.findViewById(R.id.rm_switch4);
        RMSwitch mRMSwitch5 = rootView.findViewById(R.id.rm_switch5);
        RMSwitch mRMSwitch6 = rootView.findViewById(R.id.rm_switch6);
        RMSwitch mRMSwitch7 = rootView.findViewById(R.id.rm_switch7);
        RMSwitch mRMSwitch8 = rootView.findViewById(R.id.rm_switch8);
        RMSwitch mRMSwitch9 = rootView.findViewById(R.id.rm_switch9);

        mTxtRMSwitchState1 = rootView.findViewById(R.id.txt_rm_switch_state1);
        mTxtRMSwitchState2 = rootView.findViewById(R.id.txt_rm_switch_state2);
        mTxtRMSwitchState3 = rootView.findViewById(R.id.txt_rm_switch_state3);
        mTxtRMSwitchState4 = rootView.findViewById(R.id.txt_rm_switch_state4);
        mTxtRMSwitchState5 = rootView.findViewById(R.id.txt_rm_switch_state5);
        mTxtRMSwitchState6 = rootView.findViewById(R.id.txt_rm_switch_state6);
        mTxtRMSwitchState7 = rootView.findViewById(R.id.txt_rm_switch_state7);
        mTxtRMSwitchState8 = rootView.findViewById(R.id.txt_rm_switch_state8);
        mTxtRMSwitchState9 = rootView.findViewById(R.id.txt_rm_switch_state9);


        mRMSwitch1.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                mTxtRMSwitchState1.setText("Checked: " + isChecked);
            }
        });
        mRMSwitch1.setChecked(true);
        mRMSwitch1.setForceAspectRatio(false);
        mRMSwitch1.setSwitchBkgCheckedColor(ContextCompat.getColor(rootView.getContext(), R.color.color_green));
        mRMSwitch1.setSwitchBkgNotCheckedColor(ContextCompat.getColor(rootView.getContext(), R.color.color_red));
        mRMSwitch1.setSwitchToggleCheckedColor(ContextCompat.getColor(rootView.getContext(), R.color.color_white));
        mRMSwitch1.setSwitchToggleNotCheckedColor(ContextCompat.getColor(rootView.getContext(), R.color.color_white));

        mTxtRMSwitchState1.setText("Checked: " + mRMSwitch1.isChecked());


        mRMSwitch2.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                mTxtRMSwitchState2.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState2.setText("Checked: " + mRMSwitch2.isChecked());


        mRMSwitch3.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                mTxtRMSwitchState3.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState3.setText("Checked: " + mRMSwitch3.isChecked());


        mRMSwitch4.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                mTxtRMSwitchState4.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState4.setText("Checked: " + mRMSwitch4.isChecked());


        mRMSwitch5.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                mTxtRMSwitchState5.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState5.setText("Checked: " + mRMSwitch5.isChecked());


        mRMSwitch6.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                mTxtRMSwitchState6.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState6.setText("Checked: " + mRMSwitch6.isChecked());


        mRMSwitch7.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                mTxtRMSwitchState7.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState7.setText("Checked: " + mRMSwitch7.isChecked());


        mRMSwitch8.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                mTxtRMSwitchState8.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState8.setText("Checked: " + mRMSwitch9.isChecked());

        mRMSwitch9.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                mTxtRMSwitchState9.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState9.setText("Checked: " + mRMSwitch9.isChecked());


        int state = mRMTristateSwitch1.getState();
        mTxtRMTristateSwitchState1.setText(state == RMTristateSwitch.STATE_LEFT ?
                "Left" :
                state == RMTristateSwitch.STATE_MIDDLE ?
                        "Middle" :
                        "Right");
        mRMTristateSwitch1.addSwitchObserver(new RMTristateSwitch.RMTristateSwitchObserver() {
            @Override
            public void onCheckStateChange(RMTristateSwitch switchView,
                                           @RMTristateSwitch.State int state) {
                mTxtRMTristateSwitchState1.setText(state == RMTristateSwitch.STATE_LEFT ?
                        "Left" :
                        state == RMTristateSwitch.STATE_MIDDLE ?
                                "Middle" :
                                "Right");
            }
        });

        state = mRMTristateSwitch2.getState();
        mTxtRMTristateSwitchState2.setText(state == RMTristateSwitch.STATE_LEFT ?
                "Left" :
                state == RMTristateSwitch.STATE_MIDDLE ?
                        "Middle" :
                        "Right");
        mRMTristateSwitch2.addSwitchObserver(new RMTristateSwitch.RMTristateSwitchObserver() {
            @Override
            public void onCheckStateChange(RMTristateSwitch switchView,
                                           @RMTristateSwitch.State int state) {
                mTxtRMTristateSwitchState2.setText(state == RMTristateSwitch.STATE_LEFT ?
                        "Left" :
                        state == RMTristateSwitch.STATE_MIDDLE ?
                                "Middle" :
                                "Right");
            }
        });

        state = mRMTristateSwitch3.getState();
        mTxtRMTristateSwitchState3.setText(state == RMTristateSwitch.STATE_LEFT ?
                "Left" :
                state == RMTristateSwitch.STATE_MIDDLE ?
                        "Middle" :
                        "Right");
        mRMTristateSwitch3.addSwitchObserver(new RMTristateSwitch.RMTristateSwitchObserver() {
            @Override
            public void onCheckStateChange(RMTristateSwitch switchView,
                                           @RMTristateSwitch.State int state) {
                mTxtRMTristateSwitchState3.setText(state == RMTristateSwitch.STATE_LEFT ?
                        "Left" :
                        state == RMTristateSwitch.STATE_MIDDLE ?
                                "Middle" :
                                "Right");
            }
        });

        state = mRMTristateSwitch4.getState();
        mTxtRMTristateSwitchState4.setText(state == RMTristateSwitch.STATE_LEFT ?
                "Left" :
                state == RMTristateSwitch.STATE_MIDDLE ?
                        "Middle" :
                        "Right");
        mRMTristateSwitch4.addSwitchObserver(new RMTristateSwitch.RMTristateSwitchObserver() {
            @Override
            public void onCheckStateChange(RMTristateSwitch switchView,
                                           @RMTristateSwitch.State int state) {
                mTxtRMTristateSwitchState4.setText(state == RMTristateSwitch.STATE_LEFT ?
                        "Left" :
                        state == RMTristateSwitch.STATE_MIDDLE ?
                                "Middle" :
                                "Right");
            }
        });

        mRMTristateSwitch5.setSwitchToggleLeftDrawableRes(R.drawable.theme_night);
        mRMTristateSwitch5.setSwitchToggleMiddleDrawableRes(R.drawable.theme_auto);
        mRMTristateSwitch5.setSwitchToggleRightDrawable(ContextCompat.getDrawable(rootView.getContext(),
                R.drawable.theme_day));
        state = mRMTristateSwitch5.getState();
        mTxtRMTristateSwitchState5.setText(state == RMTristateSwitch.STATE_LEFT ?
                "Left" :
                state == RMTristateSwitch.STATE_MIDDLE ?
                        "Middle" :
                        "Right");
        mRMTristateSwitch5.addSwitchObserver(new RMTristateSwitch.RMTristateSwitchObserver() {
            @Override
            public void onCheckStateChange(RMTristateSwitch switchView,
                                           @RMTristateSwitch.State int state) {
                mTxtRMTristateSwitchState5.setText(state == RMTristateSwitch.STATE_LEFT ?
                        "Left" :
                        state == RMTristateSwitch.STATE_MIDDLE ?
                                "Middle" :
                                "Right");
            }
        });

        state = mRMTristateSwitch6.getState();
        mTxtRMTristateSwitchState6.setText(state == RMTristateSwitch.STATE_LEFT ?
                "Left" :
                state == RMTristateSwitch.STATE_MIDDLE ?
                        "Middle" :
                        "Right");
        mRMTristateSwitch6.addSwitchObserver(new RMTristateSwitch.RMTristateSwitchObserver() {
            @Override
            public void onCheckStateChange(RMTristateSwitch switchView,
                                           @RMTristateSwitch.State int state) {
                mTxtRMTristateSwitchState6.setText(state == RMTristateSwitch.STATE_LEFT ?
                        "Left" :
                        state == RMTristateSwitch.STATE_MIDDLE ?
                                "Middle" :
                                "Right");
            }
        });
    }

    @Override
    public void initListener(View rootView) {

    }

    @Override
    public void lazyLoad() {

    }
}
