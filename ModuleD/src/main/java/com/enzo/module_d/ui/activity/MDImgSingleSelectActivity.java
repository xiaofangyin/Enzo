package com.enzo.module_d.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.album.constant.SelectImageConstants;
import com.enzo.commonlib.utils.album.utils.PhotoCropConfig;
import com.enzo.commonlib.utils.album.utils.SelectImagesUtils;
import com.enzo.commonlib.utils.common.FileProvider7;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.common.SDCardUtils;
import com.enzo.commonlib.utils.common.ToastUtils;
import com.enzo.commonlib.utils.imageloader.ImageLoader;
import com.enzo.commonlib.widget.alertdialog.BottomAlertDialog;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import rx.functions.Action1;

/**
 * 文 件 名: MDImgSingleSelectActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MDImgSingleSelectActivity extends BaseActivity {

    private static final int CAMERA_REQUEST_CODE = 999;
    private File imageFile;
    private ImageView imageView;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_img_single_select;
    }

    @Override
    public void initView() {
        imageView = findViewById(R.id.md_img_single);
    }

    @Override
    public void initHeader() {
        HeadWidget headWidget = findViewById(R.id.img_picker_header);
        headWidget.setTitle("图片选择");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        imageFile = new File(SDCardUtils.getShiAnXiaPath(), "avatar.jpg");
    }

    @Override
    public void initListener() {
        findViewById(R.id.md_btn_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomAlertDialog.Builder builder = new BottomAlertDialog.Builder(MDImgSingleSelectActivity.this);
                builder.add("拍照")
                        .add("从手机相册选择")
                        .cancel("取消")
                        .listener(new BottomAlertDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(int i, String data) {
                                LogUtil.d("i: " + i + "...data: " + data);
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
                                SelectImagesUtils.single(MDImgSingleSelectActivity.this,
                                        SelectImageConstants.AVATAR_CROP_REQUEST_CODE, true);
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
            Uri imageUri = FileProvider7.getUriForFile(MDImgSingleSelectActivity.this, imageFile);
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
        switch (requestCode) {
            case SelectImageConstants.AVATAR_CROP_REQUEST_CODE://相册选择完图片、裁剪后
            case UCrop.REQUEST_CROP://拍完照、裁剪后
                if (resultCode == RESULT_OK) {
                    File file = new File(UCrop.getOutput(data).getPath());
                    ImageLoader.Builder builder = new ImageLoader.Builder(MDImgSingleSelectActivity.this);
                    builder.load(file)
                            .signature(String.valueOf(System.currentTimeMillis()))
                            .build()
                            .into(imageView);
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = com.yalantis.ucrop.UCrop.getError(data);
                    if (cropError != null) {
                        Log.e(TAG, "handleCropError: ", cropError);
                        ToastUtils.showToast(cropError.getMessage());
                    } else {
                        ToastUtils.showToast("Unexpected error");
                    }
                }
                break;
            case CAMERA_REQUEST_CODE://拍完照片 执行裁剪
                if (resultCode == RESULT_OK) {
                    Uri sourceUri = Uri.fromFile(new File(imageFile.getPath()));
                    Uri uri = Uri.fromFile(PhotoCropConfig.getAvatarCroppedFile(MDImgSingleSelectActivity.this));

                    UCrop.of(sourceUri, uri)
                            .withOptions(PhotoCropConfig.getOptions())
                            .start(MDImgSingleSelectActivity.this);
                }
                break;
        }
    }
}
