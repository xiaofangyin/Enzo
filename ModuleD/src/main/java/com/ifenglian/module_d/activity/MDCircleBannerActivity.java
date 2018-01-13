package com.ifenglian.module_d.activity;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.commonlib.widget.circlebanner.CircleViewPager;
import com.ifenglian.module_d.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDCircleBannerActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/1/13
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDCircleBannerActivity extends BaseActivity{

    private CircleViewPager circleBanner;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_circle_banner;
    }

    @Override
    public void initView() {
        circleBanner = findViewById(R.id.md_circle_banner);
    }

    @Override
    public void initData() {
        List<String> data = new ArrayList<>();
        data.add("http://onq81n53u.bkt.clouddn.com/photo1.jpg");
        data.add("http://onq81n53u.bkt.clouddn.com/photo2.jpg");
        circleBanner.play(data);
    }

    @Override
    public void initListener() {

    }
}
