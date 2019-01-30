package com.ifenglian.module_d.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.env.EnvConstants;
import com.enzo.commonlib.utils.album.constant.SelectImageConstants;
import com.enzo.commonlib.utils.album.utils.PhotoCropConfig;
import com.enzo.commonlib.utils.album.utils.SelectImagesUtils;
import com.enzo.commonlib.utils.common.FileProvider7;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.common.SDCardUtils;
import com.enzo.commonlib.utils.common.ToastUtils;
import com.enzo.commonlib.utils.imageloader.ImageLoader;
import com.enzo.commonlib.utils.permission.PermissionsConfig;
import com.enzo.commonlib.utils.permission.PermissionsManager;
import com.enzo.commonlib.utils.permission.PermissionsResultAction;
import com.enzo.commonlib.widget.alertdialog.BottomAlertDialog;
import com.enzo.commonlib.widget.alertdialog.CenterAlertDialog;
import com.ifenglian.module_d.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;

/**
 * 文 件 名: MDImgSingleSelectActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MDImgSingleSelectActivity extends BaseActivity {

    private static final int CAMERA_REQUEST_CODE = 999;
    private static final int RENAME_REQUEST_CODE = 1;
    private static final int SIGNATURE_REQUEST_CODE = 2;
    private File imageFile;
    private Uri imageUri;
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
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(
                MDImgSingleSelectActivity.this,
                PermissionsConfig.PERMISSIONS_STORAGE,
                new PermissionsResultAction() {

                    @Override
                    public void onGranted() {
                        LogUtil.d("PERMISSIONS_TAKE_PHOTO onGranted...");
                        if (SDCardUtils.isAvailable()) {
                            SelectImagesUtils.single(MDImgSingleSelectActivity.this,
                                    SelectImageConstants.AVATAR_CROP_REQUEST_CODE, true);
                        } else {
                            ToastUtils.showToast("设备没有SD卡！");
                        }
                    }

                    @Override
                    public void onDenied(String permission) {
                        LogUtil.d("PERMISSIONS_TAKE_PHOTO onDenied..." + permission);
                        showTip("打开相册异常", "请检查应用是否具有读取sd卡权限");
                    }
                }
        );
    }

    private void takePicture() {
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(
                MDImgSingleSelectActivity.this,
                PermissionsConfig.PERMISSIONS_TAKE_PHOTO,
                new PermissionsResultAction() {

                    @Override
                    public void onGranted() {
                        LogUtil.d("PERMISSIONS_TAKE_PHOTO onGranted...");
                        if (SDCardUtils.isAvailable()) {
                            //如果没有(shianxia)目录 创建一个
                            File parent = new File(SDCardUtils.getShiAnXiaPath());
                            if (!parent.exists()) {
                                parent.mkdirs();
                            }
                            //通过FileProvider创建一个content类型的Uri
                            imageUri = FileProvider7.getUriForFile(MDImgSingleSelectActivity.this,
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
                    public void onDenied(String permission) {
                        LogUtil.d("PERMISSIONS_TAKE_PHOTO onDenied..." + permission);
                        showTip("打开相机异常", "请检查应用是否具有开启相机的权限");
                    }
                }
        );
    }

    /**
     * 提示没有权限
     */
    public void showTip(String title, String message) {
        CenterAlertDialog.Builder builder = new CenterAlertDialog.Builder(MDImgSingleSelectActivity.this);
        builder.title(title)
                .content(message)
                .confirm("确定")
                .build()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
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
