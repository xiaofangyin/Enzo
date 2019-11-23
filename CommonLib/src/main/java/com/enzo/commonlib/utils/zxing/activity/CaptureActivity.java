package com.enzo.commonlib.utils.zxing.activity;

import android.Manifest;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import androidx.appcompat.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.enzo.commonlib.R;
import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.album.constant.SelectImageConstants;
import com.enzo.commonlib.utils.album.utils.SelectImagesUtils;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.common.SDCardUtils;
import com.enzo.commonlib.utils.common.ToastUtils;
import com.enzo.commonlib.utils.zxing.camera.CameraManager;
import com.enzo.commonlib.utils.zxing.decoding.CaptureActivityHandler;
import com.enzo.commonlib.utils.zxing.decoding.InactivityTimer;
import com.enzo.commonlib.utils.zxing.decoding.RGBLuminanceSource;
import com.enzo.commonlib.utils.zxing.view.BarCordQueryDialog;
import com.enzo.commonlib.utils.zxing.view.ViewfinderView;
import com.enzo.commonlib.widget.alertdialog.CenterAlertDialog;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import rx.functions.Action1;

/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
public abstract class CaptureActivity extends BaseActivity implements Callback {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private AppCompatImageView flashLightIv;
    private BarCordQueryDialog dialog;

    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private String photo_path;

    //页面标题
    public abstract String getTitleText();

    //是否是条形码样式
    public abstract boolean isBarCode();

    public abstract String getStatusText();

    public abstract String getHint();

    public abstract void onSearchInput(String inputText);

    public abstract void onHandleDecode(String result);


    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = findViewById(R.id.scanner_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_scanner;
    }

    @Override
    public void initHeader() {
        HeadWidget headWidget = findViewById(R.id.scanner_header);
        headWidget.setBackgroundColor(getResources().getColor(com.enzo.commonlib.R.color.color_green));
        headWidget.setLeftImage(com.enzo.commonlib.R.mipmap.flc_icon_back_default);
        headWidget.setTitle(getTitleText());
        headWidget.setRightText("相册中选取");
        headWidget.setTitleColor(getResources().getColor(com.enzo.commonlib.R.color.color_white));
        headWidget.setRightTextColor(getResources().getColor(com.enzo.commonlib.R.color.color_white));
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
        CameraManager.init(getApplication());
        viewfinderView = findViewById(R.id.viewfinder_content);
        viewfinderView.setIsBarCode(isBarCode());

        flashLightIv = findViewById(R.id.flashLightIv);
        flashLightIv.setTag(false);

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        viewfinderView.setLabelText(getStatusText());

        rxPermissions.request(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (!aBoolean) {
                            finish();
                            ToastUtils.showToast("打开相机异常");
                        }
                    }
                });
    }

    @Override
    public void initListener() {
        findViewById(R.id.bar_cord_light_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean flag = (boolean) flashLightIv.getTag();
                    boolean isSuccess = CameraManager.get().setFlashLight(!flag);
                    if (!isSuccess) {
                        Toast.makeText(CaptureActivity.this, "暂时无法开启闪光灯", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (flag) {//关闭闪光灯
                        flashLightIv.setImageResource(R.mipmap.icon_bar_code_light_close);
                    } else {//打开闪光灯
                        flashLightIv.setImageResource(R.mipmap.icon_bar_code_light_open);
                    }
                    flashLightIv.setTag(!flag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SelectImageConstants.PICK_IMAGE_REQUEST_CODE) {
                handleAlbumPic(data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    /**
     * 处理选择的图片
     */
    private void handleAlbumPic(Intent data) {
        //获取选中图片的路径
        if (data.getData() == null) {
            ToastUtils.showToast("抱歉，解析失败,换个图片试试.");
        } else {
            photo_path = data.getData().getPath();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Result result = scanningImage(photo_path);
                    if (result != null) {

                        onHandleDecode(result.getText());
                    } else {
                        ToastUtils.showToast("抱歉，解析失败,换个图片试试.");
                    }
                }
            });
        }
    }

    /**
     * 扫描二维码图片的方法
     */
    public Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); //设置二维码内容的编码

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        Bitmap scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Handler scan result
     */
    public void handleDecode(Result result) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (TextUtils.isEmpty(resultString)) {
            Toast.makeText(CaptureActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
        } else {
            onHandleDecode(resultString);
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    // When the beep has finished playing, rewind to queue up another one.
                    mediaPlayer.seekTo(0);
                }
            });

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }
}