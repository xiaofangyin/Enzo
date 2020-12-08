package com.enzo.module_d.utils.themes;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.enzo.commonlib.utils.common.ExternalCacheUtil;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.statusbar.stateappbar.bar.StateAppBar;
import com.enzo.module_d.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.enzo.skin.manager.listener.ILoaderListener;
import com.enzo.skin.manager.loader.SkinManager;

public class ThemesHelper {

    public static void applyTheme(final Activity context) {
        new AsyncTask<Void, Integer, File>() {

            @Override
            protected File doInBackground(Void... voids) {
                String skinName = "major_v1.0.0.skin";
                File toFile = new File(ExternalCacheUtil.getThemeDir(context).getAbsolutePath(), skinName);
                if (toFile.exists()) {
                    toFile.delete();
                }
                if (copyAssetsFile(context, skinName, toFile.getAbsolutePath())) {
                    return toFile;
                }
                return null;
            }

            @Override
            protected void onPostExecute(File file) {
                super.onPostExecute(file);
                if (file == null) {
                    return;
                }
                SkinManager.getInstance().load(file.getAbsolutePath(), new ILoaderListener() {
                    @Override
                    public void onStart() {
                        LogUtil.d("start load skin");
                    }

                    @Override
                    public void onSuccess() {
                        LogUtil.d("load skin success");
                        StateAppBar.setStatusBarColor(context, SkinManager.getInstance().getColor(R.color.color_major_c1));
                    }

                    @Override
                    public void onFailed() {
                        LogUtil.d("load skin failed");
                    }
                });
            }
        }.execute();
    }

    private static boolean copyAssetsFile(Context context, String fileFromName, String toDir) {
        try {
            InputStream its = context.getAssets().open(fileFromName);
            int fileLength = its.available();
            File book_file = new File(toDir);
            if (!book_file.exists()) {
                book_file.createNewFile();
            }

            FileOutputStream fots = new FileOutputStream(book_file, true);
            byte[] buffer = new byte[fileLength];
            int readCount = 0; // 已经成功读取的字节的个数
            while (readCount < fileLength) {
                readCount += its.read(buffer, readCount, fileLength - readCount);
            }
            fots.write(buffer, 0, fileLength);

            its.close();
            fots.close();
            return true;
        } catch (Exception e1) {
            e1.printStackTrace();
            return false;
        }
    }
}
