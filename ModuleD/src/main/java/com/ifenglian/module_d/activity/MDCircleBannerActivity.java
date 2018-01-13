package com.ifenglian.module_d.activity;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.commonlib.widget.circlebanner.CircleBanner;
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

    private CircleBanner circleBanner;

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
        data.add("http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg");
        data.add("http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg");
        data.add("http://pic18.nipic.com/20111215/577405_080531548148_2.jpg");
        data.add("http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg");
        data.add("http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg");
        circleBanner.play(data);
    }

    @Override
    public void initListener() {

    }
}
