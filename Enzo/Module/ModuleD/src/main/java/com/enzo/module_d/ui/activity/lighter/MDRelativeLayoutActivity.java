package com.enzo.module_d.ui.activity.lighter;

import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.commonlib.widget.lighter.Lighter;
import com.enzo.commonlib.widget.lighter.interfaces.OnLighterListener;
import com.enzo.commonlib.widget.lighter.parameter.Direction;
import com.enzo.commonlib.widget.lighter.parameter.LighterParameter;
import com.enzo.commonlib.widget.lighter.parameter.MarginOffset;
import com.enzo.commonlib.widget.lighter.shape.CircleShape;
import com.enzo.commonlib.widget.lighter.shape.OvalShape;
import com.enzo.commonlib.widget.lighter.shape.RectShape;
import com.enzo.module_d.R;

public class MDRelativeLayoutActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_lighter_normal_layout;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_widget);
        headWidget.setTitle("引导");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        showGuide();
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }

    private void showGuide() {
        TranslateAnimation translateAnimation = new TranslateAnimation(-500, 0, 0, 0);
        translateAnimation.setDuration(500);
        translateAnimation.setInterpolator(new BounceInterpolator());

        CircleShape circleShape = new CircleShape(25);
        circleShape.setPaint(MDLighterHelper.getDashPaint()); //set custom paint


        RectShape rectShape = new RectShape();
        rectShape.setPaint(MDLighterHelper.getDiscretePaint());

        Lighter.with(this)
                .setBackgroundColor(0xB3000000)
                .setOnLighterListener(new OnLighterListener() {
                    @Override
                    public void onShow(int index) {
                        ToastUtil.show( "正在显示第" + (index + 1) + "高亮");
                    }

                    @Override
                    public void onDismiss() {
                        ToastUtil.show("高亮已全部显示完毕");
                    }
                })
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.iv_head_photo)
                        .setTipLayoutId(R.layout.md_layout_lighter_tip_1)
                        .setLighterShape(circleShape)
                        .setTipViewRelativeDirection(Direction.RIGHT)
                        .setTipViewDisplayAnimation(MDLighterHelper.getScaleAnimation())
                        .setTipViewRelativeOffset(new MarginOffset(30, 0, 40, 0))
                        .build())
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.layout_balance)
                        .setTipLayoutId(R.layout.md_layout_lighter_tip_2)
                        .setLighterShape(rectShape)
                        .setTipViewRelativeDirection(Direction.TOP)
                        .setShapeXOffset(10)
                        .setShapeYOffset(10)
                        .setTipViewDisplayAnimation(translateAnimation)
                        .setTipViewRelativeOffset(new MarginOffset(0, 10, 0, 30))
                        .build())
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.btn_highlight2)
                        .setTipView(MDLighterHelper.createCommonTipView(this, R.mipmap.lighter_icon_tip_4, "向左移动"))
                        .setLighterShape(rectShape)
                        .setTipViewRelativeDirection(Direction.LEFT)
                        .setTipViewDisplayAnimation(MDLighterHelper.getScaleAnimation())
                        .setTipViewRelativeOffset(new MarginOffset(0, 20, 0, 0))
                        .build())
                .addHighlight(new LighterParameter.Builder()
                                .setHighlightedViewId(R.id.btn_highlight1)
                                .setTipLayoutId(R.layout.md_layout_lighter_tip_3)
                                .setLighterShape(new RectShape(0, 0, 25))
                                .setTipViewRelativeDirection(Direction.TOP)
                                .setTipViewDisplayAnimation(MDLighterHelper.getScaleAnimation())
                                .setTipViewRelativeOffset(new MarginOffset(100, 10, 0, 20))
                                .build(),

                        new LighterParameter.Builder()
                                .setHighlightedViewId(R.id.btn_highlight3)
                                .setTipLayoutId(R.layout.md_layout_lighter_tip_1)
                                .setLighterShape(new OvalShape())
                                .setTipViewRelativeDirection(Direction.BOTTOM)
                                .setTipViewDisplayAnimation(MDLighterHelper.getScaleAnimation())
                                .setTipViewRelativeOffset(new MarginOffset(300, 0, -150, 0))
                                .build())
                .show();
    }
}
