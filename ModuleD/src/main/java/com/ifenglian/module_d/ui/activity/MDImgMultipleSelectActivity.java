package com.ifenglian.module_d.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.GridView;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.env.EnvConstants;
import com.enzo.commonlib.utils.album.bean.AlbumImage;
import com.enzo.commonlib.utils.album.constant.SelectImageConstants;
import com.enzo.commonlib.utils.album.utils.SelectImagesUtils;
import com.enzo.commonlib.utils.common.FileProvider7;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.common.SDCardUtils;
import com.enzo.commonlib.utils.common.ToastUtils;
import com.enzo.commonlib.widget.alertdialog.BottomAlertDialog;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.ui.adapter.TakePicturesVerifyAdapter;

import java.io.File;
import java.util.List;

import rx.functions.Action1;

/**
 * 文 件 名: MDImgMultipleSelectActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MDImgMultipleSelectActivity extends BaseActivity {

    private static final int GALLERY_REQUEST_CODE = 998;
    private static final int CAMERA_REQUEST_CODE = 999;
    private static final int MAX_PICTURE = 3;
    private GridView gridView;
    private AlbumImage image;//拍照时存储图片的路径
    private TakePicturesVerifyAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_img_multiple_select;
    }

    @Override
    public void initHeader() {
        HeadWidget headWidget = findViewById(R.id.i_want_ask_header);
        headWidget.setTitle("提问");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        gridView = findViewById(R.id.i_want_ask_gv);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        adapter = new TakePicturesVerifyAdapter(MDImgMultipleSelectActivity.this);
        adapter.setMaxShowCount(3);
        gridView.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        adapter.setOnItemClickListener(new TakePicturesVerifyAdapter.OnItemClickListener() {
            @Override
            public void add() {
                BottomAlertDialog.Builder builder = new BottomAlertDialog.Builder(MDImgMultipleSelectActivity.this);
                builder.add("拍照")
                        .add("从手机相册选择")
                        .cancel("取消")
                        .listener(new BottomAlertDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(int i, String data) {
                                switch (i) {
                                    case 0:
                                        takePicture();
                                        break;
                                    case 1:
                                        chooseFromGallery();
                                        break;
                                }
                            }
                        })
                        .build()
                        .show();
            }

            @Override
            public void imageClick(int position) {
                SelectImagesUtils.toPreview(MDImgMultipleSelectActivity.this, adapter.getData(),
                        position, adapter.getData().size(), false);
            }

            @Override
            public void imageRemove(int position) {
                adapter.remove(position);
            }
        });

        findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void chooseFromGallery() {
        rxPermissions.request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            LogUtil.d("PERMISSIONS_TAKE_PHOTO onGranted...");
                            if (SDCardUtils.isAvailable()) {
                                SelectImagesUtils.images(MDImgMultipleSelectActivity.this, GALLERY_REQUEST_CODE,
                                        MAX_PICTURE - adapter.getData().size());
                            } else {
                                ToastUtils.showToast("设备没有SD卡！");
                            }
                        } else {
                            ToastUtils.showToast("该应用缺少读取sd卡权限");
                        }
                    }
                });
    }

    private void takePicture() {
        rxPermissions.request(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            startTakePhoto();
                        } else {
                            ToastUtils.showToast("打开相机异常");
                        }
                    }
                });
    }

    private void startTakePhoto() {
        if (SDCardUtils.isAvailable()) {
            //如果没有(shianxia)目录 创建一个
            File parent = new File(SDCardUtils.getShiAnXiaPath());
            if (!parent.exists()) {
                parent.mkdirs();
            }
            //通过FileProvider创建一个content类型的Uri
            File imageFile = new File(SDCardUtils.getShiAnXiaPath(), "verify_" + System.currentTimeMillis() + ".jpg");
            image = new AlbumImage();
            image.setImagePath(imageFile.getAbsolutePath());
            image.setSelected(true);
            Uri imageUri = FileProvider7.getUriForFile(MDImgMultipleSelectActivity.this,
                    EnvConstants.FILE_AUTHORITY, imageFile);
            //调用系统相机
            Intent intentCamera = new Intent();
            intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            //将拍照结果保存至photo_file的Uri中，不保留在相册中
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intentCamera, CAMERA_REQUEST_CODE);
        } else {
            ToastUtils.showToast("设备没有SD卡！");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d("requestCode: " + requestCode + "...resultCode: " + resultCode);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            List<AlbumImage> images = data.getParcelableArrayListExtra(SelectImageConstants.IMAGES);
            adapter.add(images);
        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            adapter.add(image);
        }
    }
}
