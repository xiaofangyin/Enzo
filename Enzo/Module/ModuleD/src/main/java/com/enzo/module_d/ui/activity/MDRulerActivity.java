package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.core.content.ContextCompat;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.commonlib.widget.ruler.BoHeRulerView;
import com.enzo.commonlib.widget.ruler.MoneySelectRuleView;
import com.enzo.commonlib.widget.ruler.RuleView;
import com.enzo.commonlib.widget.ruler.TimeRuleView;
import com.enzo.module_d.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 文 件 名: MDRulerActivity
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/2
 * 邮   箱: xiaofywork@163.com
 */
public class MDRulerActivity extends BaseActivity {

    private TextView tvHeight;
    private BoHeRulerView rulerView;
    private TextView tvValue;
    private RuleView gvRule;
    private MoneySelectRuleView msrvMoney;
    private TextView tvMoney;
    private EditText etMoney;
    private TimeRuleView trvTime;
    private TextView tvTime;

    private float moneyBalance;
    private boolean isMoneySloped;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_ruler;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("尺子");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        tvHeight = findViewById(R.id.supply_height_current_height);
        rulerView = findViewById(R.id.supply_height_ruler);
        tvTime = findViewById(R.id.tv_time);
        trvTime = findViewById(R.id.trv_time);
        tvMoney = findViewById(R.id.tv_money);
        etMoney = findViewById(R.id.et_new_money);
        msrvMoney = findViewById(R.id.msrv_money);
        tvValue = findViewById(R.id.tv_value);
        gvRule = findViewById(R.id.gv_1);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tvHeight.setText(String.valueOf(rulerView.getValue()));
        rulerView.setLineColor(ContextCompat.getColor(rulerView.getContext(), R.color.color_green));
        rulerView.setTextColor(ContextCompat.getColor(rulerView.getContext(), R.color.color_green));

        tvValue.setText(String.valueOf(gvRule.getCurrentValue()));

        tvMoney.setText(String.valueOf(msrvMoney.getValue()));
        moneyBalance = msrvMoney.getBalance();

        // 模拟时间段数据
        List<TimeRuleView.TimePart> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TimeRuleView.TimePart part = new TimeRuleView.TimePart();
            part.startTime = i * 1000;
            part.endTime = part.startTime + new Random().nextInt(1000);
            list.add(part);
        }
        trvTime.setTimePartList(list);
    }

    @Override
    public void initListener() {
        rulerView.setOnValueChangeListener(new BoHeRulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tvHeight.setText(String.valueOf(value));
            }
        });
        gvRule.setOnValueChangedListener(new RuleView.OnValueChangedListener() {
            @Override
            public void onValueChanged(float value) {
                tvValue.setText(String.valueOf(value));
            }
        });
        msrvMoney.setOnValueChangedListener(new MoneySelectRuleView.OnValueChangedListener() {
            @Override
            public void onValueChanged(int newValue) {
                tvMoney.setText(String.valueOf(newValue));
                if (newValue > moneyBalance) {
                    if (!isMoneySloped) {
                        isMoneySloped = true;
                        Snackbar.make(msrvMoney, "超出额度", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    if (isMoneySloped) {
                        isMoneySloped = false;
                    }
                }
            }
        });
        trvTime.setOnTimeChangedListener(new TimeRuleView.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(int newTimeValue) {
                tvTime.setText(TimeRuleView.formatTimeHHmmss(newTimeValue));
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_rule_indicator) {
            toggleSettingsShow(R.id.ll_rule_settings);
        } else if (id == R.id.btn_50) {
            gvRule.setCurrentValue(gvRule.getMinValue() == 11 ? 15 : 50);
        } else if (id == R.id.btn_change) {
            toggleValue();
        } else if (id == R.id.tv_money_indicator) {
            toggleSettingsShow(R.id.ll_money_settings);
        } else if (id == R.id.btn_set_money) {
            float money = getMoney();
            msrvMoney.setValue(money);
        } else if (id == R.id.btn_set_balance) {
            moneyBalance = getMoney();
            msrvMoney.setBalance(moneyBalance);
            isMoneySloped = false;
        }
    }

    private void toggleSettingsShow(@IdRes int layoutId) {
        LinearLayout llSettings = findViewById(layoutId);
        llSettings.setVisibility(llSettings.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
    }

    private void toggleValue() {
        if (gvRule.getMinValue() == 11) {
            gvRule.setValue(0, 100, 50, 0.1f, 10);
        } else {
            gvRule.setValue(11, 20, 15, 0.2f, 5);
        }
    }

    private float getMoney() {
        String moneyStr = etMoney.getText().toString();
        if (moneyStr.isEmpty()) {
            moneyStr = "0";
        }
        return Float.parseFloat(moneyStr);
    }

}
