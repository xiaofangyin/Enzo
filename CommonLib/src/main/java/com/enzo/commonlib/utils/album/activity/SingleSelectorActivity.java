package com.enzo.commonlib.utils.album.activity;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.View;

import com.enzo.commonlib.R;
import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.album.adapter.SingleAdapter;
import com.enzo.commonlib.utils.album.bean.AlbumImage;
import com.enzo.commonlib.utils.album.constant.SelectImageConstants;
import com.enzo.commonlib.utils.album.utils.PhotoCropConfig;
import com.enzo.commonlib.utils.album.utils.SelectImageModel;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.common.ToastUtils;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

import rx.functions.Action1;

/**
 * 文 件 名: SingleSelectorActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/3
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SingleSelectorActivity extends BaseActivity {

    private HeadWidget headWidget;
    private RecyclerView recyclerView;
    private SingleAdapter mAdapter;
    private String bucket;
    private int mMaxCount;
    private boolean needCrop;

    @Override
    public int getLayoutId() {
        return R.layout.activity_single_select;
    }

    @Override
    public void initHeader() {
        Intent intent = getIntent();
        bucket = getIntent().getStringExtra(SelectImageConstants.BUCKET);
        mMaxCount = intent.getIntExtra(SelectImageConstants.MAX_SELECT_COUNT, 1);
        needCrop = intent.getBooleanExtra(SelectImageConstants.NEED_CROP, false);

        headWidget = findViewById(R.id.single_select_header);
        if (TextUtils.isEmpty(bucket)) {
            headWidget.setTitle("相机胶卷");
            headWidget.setRightText("相册");
        } else {
            headWidget.setTitle(bucket);
        }
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.single_select_rv);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initImageList();
        rxPermissions.request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            //有权限，加载图片。
                            loadImageForSDCard();
                        } else {
                            ToastUtils.showToast("该应用缺少读取sd卡权限");
                        }
                    }
                });
    }

    @Override
    public void initListener() {
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        headWidget.setRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleSelectorActivity.this, FoldersActivity.class);
                intent.putExtra(SelectImageConstants.MAX_SELECT_COUNT, mMaxCount);
                intent.putExtra(SelectImageConstants.IS_SINGLE, true);
                intent.putExtra(SelectImageConstants.NEED_CROP, needCrop);
                startActivityForResult(intent, SelectImageConstants.AVATAR_CROP_REQUEST_CODE);
            }
        });

        mAdapter.setOnItemClickListener(new SingleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(AlbumImage image, int position) {
                if (needCrop) {
                    Uri sourceUri = Uri.fromFile(new File(image.getImagePath()));
                    Uri uri = Uri.fromFile(PhotoCropConfig.getAvatarCroppedFile(SingleSelectorActivity.this));

                    UCrop.of(sourceUri, uri)
                            .withOptions(PhotoCropConfig.getOptions())
                            .start(SingleSelectorActivity.this);
                } else {
                    Intent intent = new Intent();
                    Uri uri = Uri.fromFile(new File(image.getImagePath()));
                    intent.setData(uri);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }


    /**
     * 初始化图片列表
     */
    private void initImageList() {
        // 判断屏幕方向
        Configuration configuration = getResources().getConfiguration();
        GridLayoutManager mLayoutManager;
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager = new GridLayoutManager(this, 3);
        } else {
            mLayoutManager = new GridLayoutManager(this, 5);
        }

        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SingleAdapter(this);
        recyclerView.setAdapter(mAdapter);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    /**
     * 从SDCard加载图片。
     */
    private void loadImageForSDCard() {
        if (TextUtils.isEmpty(bucket)) {
            SelectImageModel.getAllAlbums(this, new SelectImageModel.ImagesCallBack() {
                @Override
                public void onSuccess(List<AlbumImage> images) {
                    mAdapter.refresh(images);
                }
            });
        } else {
            SelectImageModel.getAlbumsByBucket(this, bucket, new SelectImageModel.ImagesCallBack() {
                @Override
                public void onSuccess(List<AlbumImage> images) {
                    mAdapter.refresh(images);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d("requestCode: " + requestCode + "...resultCode: " + resultCode);
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        } else if (requestCode == SelectImageConstants.AVATAR_CROP_REQUEST_CODE && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
