package com.enzo.commonlib.utils.zbar;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;

import com.enzo.commonlib.R;
import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.album.constant.SelectImageConstants;
import com.enzo.commonlib.utils.album.utils.SelectImagesUtils;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.common.SDCardUtils;
import com.enzo.commonlib.utils.common.ToastUtils;
import com.enzo.commonlib.widget.alertdialog.CenterAlertDialog;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.tbruyelle.rxpermissions.RxPermissions;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;
import rx.functions.Action1;

public abstract class CaptureActivity extends BaseActivity {

    protected ZBarView mZBarView;
    private AppCompatImageView flashLightIv;
    private BarCordQueryDialog dialog;

    public abstract String getTitleText();

    public abstract String getStatusText();

    public abstract String getHint();

    public abstract void onSearchInput(String inputText);

    public abstract void onHandleDecode(String result);

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.color_green);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_capture;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.capture_header);
        headWidget.setBackgroundColor(getResources().getColor(R.color.color_green));
        headWidget.setLeftImage(R.mipmap.flc_icon_back_default);
        headWidget.setTitle(getTitleText());
        headWidget.setRightText("相册中选取");
        headWidget.setTitleColor(getResources().getColor(R.color.color_white));
        headWidget.setRightTextColor(getResources().getColor(R.color.color_white));
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        headWidget.setRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFromGallery();
            }
        });
    }

    @Override
    public void initView() {
        flashLightIv = findViewById(R.id.flashLightIv);
        flashLightIv.setTag(false);
        mZBarView = findViewById(R.id.z_bar_view);
        mZBarView.setDelegate(new QRCodeView.Delegate() {
            @Override
            public void onScanQRCodeSuccess(String result) {
                LogUtil.d("onScanQRCodeSuccess result:" + result);
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(200);
                if (!TextUtils.isEmpty(result)) {
                    onHandleDecode(result);
                    mZBarView.stopCamera();
                } else {
                    ToastUtils.showToast("抱歉，解析失败,换个图片试试.");
                }
            }

            @Override
            public void onCameraAmbientBrightnessChanged(boolean isDark) {
                String tipText = mZBarView.getScanBoxView().getTipText();
                String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
                if (isDark) {
                    if (!tipText.contains(ambientBrightnessTip)) {
                        mZBarView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
                    }
                } else {
                    if (tipText.contains(ambientBrightnessTip)) {
                        tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                        mZBarView.getScanBoxView().setTipText(tipText);
                    }
                }
            }

            @Override
            public void onScanQRCodeOpenCameraError() {
                LogUtil.d("onScanQRCodeOpenCameraError...");
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mZBarView.getScanBoxView().setTipText(getStatusText());

        if (RxPermissions.getInstance(CaptureActivity.this).isGranted(Manifest.permission.CAMERA)) {
            LogUtil.d("have camera permission");
            mZBarView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
            mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
        } else {
            RxPermissions.getInstance(CaptureActivity.this).request(Manifest.permission.CAMERA)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            if (aBoolean) {
                                mZBarView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
                                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
                            } else {
                                showTip();
                            }
                        }
                    });
        }
    }

    @Override
    public void initListener() {
        flashLightIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = (boolean) flashLightIv.getTag();
                if (flag) {
                    flashLightIv.setImageResource(R.mipmap.icon_bar_code_light_close);
                    mZBarView.closeFlashlight();//关闭闪光灯
                } else {
                    flashLightIv.setImageResource(R.mipmap.icon_bar_code_light_open);
                    mZBarView.openFlashlight(); //打开闪光灯
                }
                flashLightIv.setTag(!flag);
            }
        });
        findViewById(R.id.bar_cord_input_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog == null) {
                    dialog = new BarCordQueryDialog();
                }
                Bundle bundle = new Bundle();
                bundle.putString("hint", getHint());
                dialog.setArguments(bundle);
                dialog.setOnQueryCallBack(new BarCordQueryDialog.OnQueryCallBack() {
                    @Override
                    public void onQuery(String inputText) {
                        onSearchInput(inputText);
                    }

                    @Override
                    public void onTextChanged(CharSequence inputTest) {

                    }

                    @Override
                    public void onDismiss() {

                    }
                });
                if (!dialog.isAdded() && !dialog.isVisible() && !dialog.isRemoving()) {
                    dialog.show(getSupportFragmentManager(), "dialog");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZBarView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
        mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
    }

    @Override
    protected void onStop() {
        mZBarView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZBarView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    /**
     * 提示没有权限
     */
    public void showTip() {
        CenterAlertDialog.Builder builder = new CenterAlertDialog.Builder(CaptureActivity.this);
        builder.title("打开相机异常")
                .content("请检查应用是否具有开启相机的权限")
                .confirm("确定")
                .listener(new CenterAlertDialog.AlertDialogListener() {
                    @Override
                    public void onNegClick() {

                    }

                    @Override
                    public void onPosClick() {
                        finish();
                    }
                })
                .build()
                .show();
    }

    private void chooseFromGallery() {
        if (RxPermissions.getInstance(CaptureActivity.this).isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            LogUtil.d("PERMISSIONS_TAKE_PHOTO onGranted...");
            if (SDCardUtils.isAvailable()) {
                SelectImagesUtils.single(CaptureActivity.this,
                        SelectImageConstants.PICK_IMAGE_REQUEST_CODE, false);
            } else {
                ToastUtils.showToast("设备没有SD卡！");
            }
        } else {
            RxPermissions.getInstance(CaptureActivity.this).request(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            if (aBoolean) {
                                LogUtil.d("PERMISSIONS_TAKE_PHOTO onGranted...");
                                if (SDCardUtils.isAvailable()) {
                                    SelectImagesUtils.single(CaptureActivity.this,
                                            SelectImageConstants.PICK_IMAGE_REQUEST_CODE, false);
                                } else {
                                    ToastUtils.showToast("设备没有SD卡！");
                                }
                            } else {
                                LogUtil.d("PERMISSIONS_TAKE_PHOTO onDenied...");
                                CenterAlertDialog.Builder builder = new CenterAlertDialog.Builder(CaptureActivity.this);
                                builder.title("打开相册异常")
                                        .content("请检查应用是否具有读取sd卡权限")
                                        .confirm("确定")
                                        .build()
                                        .show();
                            }
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mZBarView.showScanRect();
        if (requestCode == SelectImageConstants.PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            String path = data.getData().getPath();
            mZBarView.stopCamera();
            mZBarView.decodeQRCode(path);
        }
    }

//    public void onClick(View v) {
//        int i = v.getId();
//        if (i == R.id.start_preview) {
//            mZBarView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//
//        } else if (i == R.id.stop_preview) {
//            mZBarView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
//
//        } else if (i == R.id.start_spot) {
//            mZBarView.startSpot(); // 延迟0.1秒后开始识别
//
//        } else if (i == R.id.stop_spot) {
//            mZBarView.stopSpot(); // 停止识别
//
//        } else if (i == R.id.start_spot_showrect) {
//            mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
//
//        } else if (i == R.id.stop_spot_hiddenrect) {
//            mZBarView.stopSpotAndHiddenRect(); // 停止识别，并且隐藏扫描框
//
//        } else if (i == R.id.show_scan_rect) {
//            mZBarView.showScanRect(); // 显示扫描框
//
//        } else if (i == R.id.hidden_scan_rect) {
//            mZBarView.hiddenScanRect(); // 隐藏扫描框
//
//        } else if (i == R.id.decode_scan_box_area) {
//            mZBarView.getScanBoxView().setOnlyDecodeScanBoxArea(true); // 仅识别扫描框中的码
//
//        } else if (i == R.id.decode_full_screen_area) {
//            mZBarView.getScanBoxView().setOnlyDecodeScanBoxArea(false); // 识别整个屏幕中的码
//
//        } else if (i == R.id.open_flashlight) {
//            mZBarView.openFlashlight(); // 打开闪光灯
//
//        } else if (i == R.id.close_flashlight) {
//            mZBarView.closeFlashlight(); // 关闭闪光灯
//
//        } else if (i == R.id.scan_one_dimension) {
//            mZBarView.changeToScanBarcodeStyle(); // 切换成扫描条码样式
//            mZBarView.setType(BarcodeType.ONE_DIMENSION, null); // 只识别一维条码
//            mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
//
//        } else if (i == R.id.scan_two_dimension) {
//            mZBarView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
//            mZBarView.setType(BarcodeType.TWO_DIMENSION, null); // 只识别二维条码
//            mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
//
//        } else if (i == R.id.scan_qr_code) {
//            mZBarView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
//            mZBarView.setType(BarcodeType.ONLY_QR_CODE, null); // 只识别 QR_CODE
//            mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
//
//        } else if (i == R.id.scan_code128) {
//            mZBarView.changeToScanBarcodeStyle(); // 切换成扫描条码样式
//            mZBarView.setType(BarcodeType.ONLY_CODE_128, null); // 只识别 CODE_128
//            mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
//
//        } else if (i == R.id.scan_ean13) {
//            mZBarView.changeToScanBarcodeStyle(); // 切换成扫描条码样式
//            mZBarView.setType(BarcodeType.ONLY_EAN_13, null); // 只识别 EAN_13
//            mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
//
//        } else if (i == R.id.scan_high_frequency) {
//            mZBarView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
//            mZBarView.setType(BarcodeType.HIGH_FREQUENCY, null); // 只识别高频率格式，包括 QR_CODE、ISBN13、UPC_A、EAN_13、CODE_128
//            mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
//
//        } else if (i == R.id.scan_all) {
//            mZBarView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
//            mZBarView.setType(BarcodeType.ALL, null); // 识别所有类型的码
//            mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
//
//        } else if (i == R.id.scan_custom) {
//            mZBarView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
//
//            List<BarcodeFormat> formatList = new ArrayList<>();
//            formatList.add(BarcodeFormat.QRCODE);
//            formatList.add(BarcodeFormat.ISBN13);
//            formatList.add(BarcodeFormat.UPCA);
//            formatList.add(BarcodeFormat.EAN13);
//            formatList.add(BarcodeFormat.CODE128);
//            mZBarView.setType(BarcodeType.CUSTOM, formatList); // 自定义识别的类型
//
//            mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
//
//        } else if (i == R.id.choose_qrcde_from_gallery) {/*
//                从相册选取二维码图片，这里为了方便演示，使用的是
//                https://github.com/bingoogolapple/BGAPhotoPicker-Android
//                这个库来从图库中选择二维码图片，这个库不是必须的，你也可以通过自己的方式从图库中选择图片
//                 */
////            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
////                    .cameraFileDir(null)
////                    .maxChooseCount(1)
////                    .selectedPhotos(null)
////                    .pauseOnScroll(false)
////                    .build();
////            startActivityForResult(photoPickerIntent, REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
//
//        }
//    }
}