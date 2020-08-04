package com.enzo.module_d.ui.activity.lighter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;

public class MDLighterActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_lighter;
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

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }

    public void startNormalLayout(View view) {
        startActivity(new Intent(this, MDRelativeLayoutActivity.class));
    }

    public void startRecyclerView(View view) {
        startActivity(new Intent(this, MDRecyclerViewActivity.class));
    }

    public void startListView(View view) {
        startActivity(new Intent(this, MDListViewActivity.class));
    }

    public void startScrollView(View view) {
        startActivity(new Intent(this, MDScrollViewActivity.class));
    }

    public void startGridView(View view) {
        startActivity(new Intent(this, MDGridViewActivity.class));
    }

    public void startViewPager(View view) {
        startActivity(new Intent(this, MDViewPagerActivity.class));
    }

    public void startDialog(View view) {
        startActivity(new Intent(this, MDDialogActivity.class));
    }

    public void startFragment(View view) {
        startActivity(new Intent(this, MDFragmentActivity.class));
    }
}
