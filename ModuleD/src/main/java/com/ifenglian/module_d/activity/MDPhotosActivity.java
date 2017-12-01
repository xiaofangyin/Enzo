package com.ifenglian.module_d.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.adapter.MDPhotosAdapter;
import com.ifenglian.module_d.bean.MDImageItem;
import com.ifenglian.module_d.utils.MDAlbumUtils;

import java.util.ArrayList;

/**
 * 文 件 名: MDPhotosActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDPhotosActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_photos;
    }

    @Override
    public void initView() {
        GridView gridView = findViewById(R.id.sa_gv_photos);
        ArrayList<MDImageItem> imageList = MDAlbumUtils.getLstAlbums(getApplicationContext());
        MDPhotosAdapter adapter = new MDPhotosAdapter(getApplicationContext(), gridView, imageList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
