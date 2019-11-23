package com.enzo.commonlib.utils.album.activity;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.enzo.commonlib.R;
import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.album.adapter.ImagesAdapter;
import com.enzo.commonlib.utils.album.bean.AlbumImage;
import com.enzo.commonlib.utils.album.constant.SelectImageConstants;
import com.enzo.commonlib.utils.album.utils.SelectImageModel;
import com.enzo.commonlib.utils.album.utils.SelectImagesManager;
import com.enzo.commonlib.utils.album.utils.SelectImagesUtils;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.common.ToastUtils;
import com.enzo.commonlib.widget.headerview.HeadWidget;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * 文 件 名: MultipleSelectorActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/3
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MultipleSelectorActivity extends BaseActivity {

    private HeadWidget headWidget;
    private RecyclerView recyclerView;
    private ImagesAdapter mAdapter;
    private String bucket;
    private int mMaxCount;
    private TextView tvPreview;
    private TextView tvNum;
    private TextView tvConfirm;

    @Override
    public int getLayoutId() {
        return R.layout.activity_images_select;
    }

    @Override
    public void initHeader() {
        Intent intent = getIntent();
        bucket = getIntent().getStringExtra(SelectImageConstants.BUCKET);
        mMaxCount = intent.getIntExtra(SelectImageConstants.MAX_SELECT_COUNT, 0);

        headWidget = findViewById(R.id.images_select_header);
        if (TextUtils.isEmpty(bucket)) {
            headWidget.setTitle("相机胶卷");
            headWidget.setRightText("相册");
        } else {
            headWidget.setTitle(bucket);
        }
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.images_select_rv);
        tvPreview = findViewById(R.id.photo_select_preview);
        tvNum = findViewById(R.id.photo_select_num);
        tvConfirm = findViewById(R.id.photo_select_confirm);
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
                if (TextUtils.isEmpty(bucket)) {
                    SelectImagesManager.getManager().clear();
                }
                finish();
            }
        });
        headWidget.setRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MultipleSelectorActivity.this, FoldersActivity.class);
                intent.putExtra(SelectImageConstants.MAX_SELECT_COUNT, mMaxCount);
                intent.putExtra(SelectImageConstants.IS_SINGLE, false);
                startActivityForResult(intent, SelectImageConstants.IMAGES_SELECT_REQUEST_CODE);
            }
        });

        mAdapter.setOnImageSelectListener(new ImagesAdapter.OnImageSelectListener() {
            @Override
            public void OnImageSelect(AlbumImage image, boolean isSelect, int selectCount) {
                if (isSelect) {
                    SelectImagesManager.getManager().add(image);
                } else {
                    SelectImagesManager.getManager().remove(image);
                }
                tvPreview.setEnabled(SelectImagesManager.getManager().size() != 0);
                tvNum.setText("(" + SelectImagesManager.getManager().size() + "/" + mMaxCount + ")");
                tvConfirm.setEnabled(SelectImagesManager.getManager().size() != 0);
            }
        });
        mAdapter.setOnItemClickListener(new ImagesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(AlbumImage image, int position) {
                toPreviewActivity(mAdapter.getData(), position, true);
            }
        });

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(SelectImageConstants.IMAGES,
                        (ArrayList<? extends Parcelable>) SelectImagesManager.getManager().getImages());
                setResult(RESULT_OK, intent);
                finish();
                SelectImagesManager.getManager().clear();
            }
        });
        tvPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toPreviewActivity(SelectImagesManager.getManager().getImages(), 0, false);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.refresh(merge(mAdapter.getData()));
    }

    private List<AlbumImage> merge(List<AlbumImage> images) {
        if (images != null && !images.isEmpty()) {
            for (int i = 0; i < images.size(); i++) {
                images.get(i).setSelected(false);
            }
            if (SelectImagesManager.getManager().size() != 0) {
                List<AlbumImage> selectList = SelectImagesManager.getManager().getImages();
                for (int i = 0; i < selectList.size(); i++) {
                    for (int j = 0; j < images.size(); j++) {
                        if (selectList.get(i).getImagePath().equals(images.get(j).getImagePath())) {
                            images.get(j).setSelected(true);
                            break;
                        }
                    }
                }
                tvPreview.setEnabled(true);
                tvNum.setText("(" + selectList.size() + "/" + mMaxCount + ")");
                tvConfirm.setEnabled(selectList.size() > 0);
            } else {
                tvPreview.setEnabled(false);
                tvNum.setText("(0/" + mMaxCount + ")");
                tvConfirm.setEnabled(false);
            }
        }
        return images;
    }

    /**
     * 初始化图片列表
     */
    private void initImageList() {
        tvNum.setText("(" + SelectImagesManager.getManager().size() + "/" + mMaxCount + ")");
        tvConfirm.setEnabled(SelectImagesManager.getManager().size() != 0);

        // 判断屏幕方向
        Configuration configuration = getResources().getConfiguration();
        GridLayoutManager mLayoutManager;
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager = new GridLayoutManager(this, 3);
        } else {
            mLayoutManager = new GridLayoutManager(this, 5);
        }

        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ImagesAdapter(this, mMaxCount);
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
                    mAdapter.refresh(merge(images));
                }
            });
        } else {
            SelectImageModel.getAlbumsByBucket(this, bucket, new SelectImageModel.ImagesCallBack() {
                @Override
                public void onSuccess(List<AlbumImage> images) {
                    mAdapter.refresh(merge(images));
                }
            });
        }
    }

    private void toPreviewActivity(List<AlbumImage> images, int position, boolean canSelect) {
        if (images != null && !images.isEmpty()) {
            SelectImagesUtils.toPreview(MultipleSelectorActivity.this, images, position, mMaxCount, canSelect);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d("requestCode: " + requestCode + "...resultCode: " + resultCode);
        if (requestCode == SelectImageConstants.IMAGES_SELECT_REQUEST_CODE && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        } else if (requestCode == SelectImageConstants.PREVIEW_REQUEST_CODE && resultCode == RESULT_OK) {
            //预览页面回来的操作在 onStart() 里完成
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (TextUtils.isEmpty(bucket)) {
            SelectImagesManager.getManager().clear();
        }
    }
}
