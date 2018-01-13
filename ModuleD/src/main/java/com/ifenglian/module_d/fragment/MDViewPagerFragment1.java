package com.ifenglian.module_d.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.ifenglian.commonlib.base.BaseFragment;
import com.ifenglian.commonlib.widget.circlebanner.CircleBanner;
import com.ifenglian.commonlib.widget.snowview.SnowView;
import com.ifenglian.module_d.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDViewPagerFragment1
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDViewPagerFragment1 extends BaseFragment {

    private CircleBanner circleBanner;
    private SnowView fallingView;

    @Override
    public int getLayoutId() {
        return R.layout.md_fragment_d1;
    }

    @Override
    public void initView(View rootView) {
        circleBanner = rootView.findViewById(R.id.md_circle_banner);
        fallingView = rootView.findViewById(R.id.fall_view);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        List<String> data = new ArrayList<>();
        data.add("http://onq81n53u.bkt.clouddn.com/photo1.jpg");
        data.add("http://onq81n53u.bkt.clouddn.com/photo2.jpg");
        data.add("http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg");
        data.add("http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg");
        data.add("http://pic18.nipic.com/20111215/577405_080531548148_2.jpg");
        data.add("http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg");
        data.add("http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg");
        circleBanner.play(data);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.md_icon_snow);
        fallingView.initSnow(bitmap, 60);
    }

    @Override
    public void initListener(View rootView) {

    }
}
