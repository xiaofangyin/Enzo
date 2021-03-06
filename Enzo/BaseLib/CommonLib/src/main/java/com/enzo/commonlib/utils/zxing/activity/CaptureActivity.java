package com.enzo.commonlib.utils.zxing.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.enzo.commonlib.R;
import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.common.PermissionsUtils;
import com.enzo.commonlib.utils.matisse.Matisse;
import com.enzo.commonlib.utils.matisse.MimeType;
import com.enzo.commonlib.utils.matisse.engine.impl.GlideEngine;
import com.enzo.commonlib.utils.matisse.internal.entity.CaptureStrategy;
import com.enzo.commonlib.utils.toast.ToastUtil;
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
import java.util.List;
import java.util.Vector;

/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
public abstract class CaptureActivity extends BaseActivity implements Callback {

    private int PICK_IMAGE_REQUEST_CODE = 1003;

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

    //????????????
    public abstract String getTitleText();

    //????????????????????????
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
        headWidget.setBackgroundColor(ContextCompat.getColor(this, com.enzo.commonlib.R.color.color_green));
        headWidget.setLeftImage(com.enzo.commonlib.R.mipmap.icon_white_back_default);
        headWidget.setTitle(getTitleText());
        headWidget.setRightText("??????");
        headWidget.setTitleColor(ContextCompat.getColor(this, com.enzo.commonlib.R.color.color_white));
        headWidget.setRightTextColor(ContextCompat.getColor(this, com.enzo.commonlib.R.color.color_white));
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

        PermissionsUtils.requestCameraPermission(this, new PermissionsUtils.OnCheckCallback() {
            @Override
            public void granted(boolean granted) {
                if (!granted) {
                    finish();
                    ToastUtil.show("??????????????????");
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
                        ToastUtil.show("???????????????????????????", Toast.LENGTH_SHORT);
                        return;
                    }
                    if (flag) {//???????????????
                        flashLightIv.setImageResource(R.mipmap.icon_bar_code_light_close);
                    } else {//???????????????
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
            if (requestCode == PICK_IMAGE_REQUEST_CODE) {
                handleAlbumPic(data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void chooseFromGallery() {
        PermissionsUtils.requestExternalStoragePermission(this, new PermissionsUtils.OnCheckCallback() {
            @Override
            public void granted(boolean granted) {
                if (granted) {
                    Matisse.from(CaptureActivity.this)
                            .choose(MimeType.ofImage(), false)
                            .countable(true)
                            .singleChoose(true)
                            .crop(false)
                            .capture(false)
                            .captureStrategy(new CaptureStrategy(Environment.DIRECTORY_PICTURES))
                            .maxSelectable(9)
                            .spanCount(4)
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                            .thumbnailScale(0.85f)
                            .imageEngine(new GlideEngine())
                            .showSingleMediaType(true)
                            .maxOriginalSize(10)
                            .autoHideToolbarOnSingleTap(true)
                            .forResult(PICK_IMAGE_REQUEST_CODE);
                } else {
                    LogUtil.d("PERMISSIONS_TAKE_PHOTO onDenied...");
                    CenterAlertDialog.Builder builder = new CenterAlertDialog.Builder(CaptureActivity.this);
                    builder.title("??????????????????")
                            .content("?????????????????????????????????sd?????????")
                            .confirm("??????")
                            .build()
                            .show();
                }
            }
        });
    }

    /**
     * ?????????????????????
     */
    private void handleAlbumPic(Intent data) {
        //???????????????????????????
        List<String> list = Matisse.obtainPathResult(data);
        if (list != null && !list.isEmpty()) {
            photo_path = list.get(0);
            LogUtil.d("handleAlbumPic path: " + photo_path);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Result result = scanningImage(photo_path);
                    if (result != null) {
                        onHandleDecode(result.getText());
                    } else {
                        ToastUtil.show("?????????????????????,??????????????????.");
                    }
                }
            });
        } else {
            ToastUtil.show("?????????????????????,??????????????????.");
        }
    }

    /**
     * ??????????????????????????????
     */
    public Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); //??????????????????????????????

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // ??????????????????
        Bitmap scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // ??????????????????
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
            ToastUtil.show("Scan failed!", Toast.LENGTH_SHORT);
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