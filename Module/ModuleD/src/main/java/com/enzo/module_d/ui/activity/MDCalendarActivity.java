package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.calendarview.bean.DateBean;
import com.enzo.commonlib.widget.calendarview.listener.OnPagerChangeListener;
import com.enzo.commonlib.widget.calendarview.listener.OnSingleChooseListener;
import com.enzo.commonlib.widget.calendarview.utils.CalendarUtil;
import com.enzo.commonlib.widget.calendarview.weiget.CalendarView;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;

/**
 * 文 件 名: MDCalendarActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-05-29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MDCalendarActivity extends BaseActivity implements View.OnClickListener {

    private TextView calendarTitle;
    private CalendarView calendarView;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_calendar;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_widget);
        headWidget.setTitle("日历");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        calendarTitle = findViewById(R.id.title);
        calendarView = findViewById(R.id.calendar);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        int[] cDate = CalendarUtil.getCurrentDate();
        DateBean dateBean = new DateBean();
        dateBean.setSolar(cDate[0], cDate[1], cDate[2]);
        calendarView.setStartEndDate("2016.1", "2028.12")
                .setDisableStartEndDate("2016.10.10", "2028.10.10")
                .setInitDate(cDate[0] + "." + cDate[1])
                .setSingleDate(cDate[0] + "." + cDate[1] + "." + cDate[2])
                .init();

    }

    @Override
    public void initListener() {
        findViewById(R.id.diet_plan_pre_month).setOnClickListener(this);
        findViewById(R.id.diet_plan_next_month).setOnClickListener(this);
        findViewById(R.id.diet_plan_today).setOnClickListener(this);
        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                calendarTitle.setText(date[0] + "年" + date[1] + "月");
            }
        });

        calendarView.setOnSingleChooseListener(new OnSingleChooseListener() {
            @Override
            public void onSingleChoose(View view, DateBean date) {
                LogUtil.d(date.getSolar()[0] + "年" + date.getSolar()[1] + "月" + date.getSolar()[2] + "日");
                calendarTitle.setText(date.getSolar()[0] + "年" + date.getSolar()[1] + "月");
                ToastUtil.show(date.getSolar()[1] + "月" + date.getSolar()[2] + "日");
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.diet_plan_pre_month) {
            calendarView.lastMonth();
        } else if (i == R.id.diet_plan_next_month) {
            calendarView.nextMonth();
        } else if (i == R.id.diet_plan_today) {
            calendarView.today();
        }
    }
}
