package com.enzo.commonlib.utils.preview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.enzo.commonlib.utils.common.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 文 件 名: UGCFileUtils
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/5/24
 * 邮   箱: xiaofangyinwork@163.com
 */
public class UGCFileUtils {

    public static final String UGC_FILE_PARENT = getSDPath() + "/yishian/download";

    public static String getSDPath() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            File sdDir = Environment.getExternalStorageDirectory();//获取跟目录
            return sdDir.getAbsolutePath();
        }
        return "";
    }

    public static void downLoadImageAndSave(final Context context, String url, final String path, final String fileName, final OnImageDownLoadCallBack callBack) {
        downLoadImage(url, new OnImageDownLoadCallBack() {
            @Override
            public void onSuccess(final Bitmap bitmap) {
                if (callBack != null) {
                    if (bitmap != null) {
                        saveImageToGallery(context, bitmap, path, fileName, new SaveResultCallback() {
                            @Override
                            public void onSavedSuccess(String path) {
                                callBack.onSuccess(bitmap);
                            }

                            @Override
                            public void onSavedFailed() {
                                callBack.onFailed();
                            }
                        });
                    } else {
                        callBack.onFailed();
                    }
                }
            }

            @Override
            public void onFailed() {
                if (callBack != null) {
                    callBack.onFailed();
                }
            }
        });
    }

    /**
     * 下载图片
     */
    private static void downLoadImage(String url, final OnImageDownLoadCallBack callBack) {
        LogUtil.d("downLoadImage: " + url);
        if (TextUtils.isEmpty(url)) {
            callBack.onFailed();
            return;
        }
        Request request = new Request.Builder().url(url).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.onFailed();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取流
                InputStream in = response.body().byteStream();
                //转化为bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                if (callBack != null) {
                    if (bitmap != null) {
                        callBack.onSuccess(bitmap);
                    } else {
                        callBack.onFailed();
                    }
                }
            }
        });
    }

    /**
     * 保存图片到本地
     */
    private static void saveImageToGallery(Context context, Bitmap bmp, String path, String fileName, SaveResultCallback saveResultCallback) {
        // 首先保存图片
        File appDir = new File(path);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            saveResultCallback.onSavedSuccess(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            saveResultCallback.onSavedFailed();
            e.printStackTrace();
        } catch (IOException e) {
            saveResultCallback.onSavedFailed();
            e.printStackTrace();
        }

        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }

    /**
     * 获取文件md5值
     */
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bytesToHexString(digest.digest());
    }

    /**
     * @return 16进制
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public interface SaveResultCallback {
        void onSavedSuccess(String path);

        void onSavedFailed();
    }

    public interface OnImageDownLoadCallBack {
        void onSuccess(Bitmap bitmap);

        void onFailed();
    }
}
