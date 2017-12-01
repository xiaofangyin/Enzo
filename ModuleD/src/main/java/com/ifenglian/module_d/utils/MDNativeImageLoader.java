package com.ifenglian.module_d.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images.Thumbnails;
import android.support.v4.util.LruCache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 图片缓存加载器
 */
public class MDNativeImageLoader {

    private LruCache<String, Bitmap> mMemoryCache;
    private ExecutorService mImageThreadPool = null;
    private Context context;

    public MDNativeImageLoader(Context context) {
        this.context = context;
        // 系统分配给每个应用程序的最大内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int mCacheSize = maxMemory / 8;
        // 给LruCache分配1/8
        mMemoryCache = new LruCache<String, Bitmap>(mCacheSize) {
            // 必须重写此方法，来测量Bitmap的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    /**
     * 获取线程池的方法，因为涉及到并发的问题，我们加上同步锁
     *
     * @return
     */
    public ExecutorService getThreadPool() {
        if (mImageThreadPool == null) {
            synchronized (ExecutorService.class) {
                if (mImageThreadPool == null) {
                    mImageThreadPool = Executors.newFixedThreadPool(2);
                }
            }
        }
        return mImageThreadPool;
    }

    /**
     * 添加Bitmap到内存缓存
     *
     * @param key
     * @param bitmap
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null && bitmap != null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * 从内存缓存中获取一个Bitmap
     *
     * @param key
     * @return
     */
    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 先从内存缓存中获取Bitmap
     *
     * @param url
     * @param listener
     * @return
     */
    public Bitmap downloadImage(final String url, final OnImageLoaderListener listener, final String ImageId) {
        final String subUrl = url.substring(url.lastIndexOf("/") + 1, url.length());
        Bitmap bitmap = showCacheBitmap(subUrl);
        if (bitmap != null) {
            return bitmap;
        } else {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    listener.onImageLoader((Bitmap) msg.obj, url);
                }
            };

            getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap1 = getBitmapFromDB(ImageId);
                    Message msg = handler.obtainMessage();
                    msg.obj = bitmap1;
                    handler.sendMessage(msg);

                    // 将Bitmap 加入内存缓存
                    addBitmapToMemoryCache(subUrl, bitmap1);
                }
            });
        }

        return null;
    }

    /**
     * 获取Bitmap
     *
     * @param url
     * @return
     */
    public Bitmap showCacheBitmap(String url) {
        if (getBitmapFromMemCache(url) != null) {
            return getBitmapFromMemCache(url);
        }
        return null;
    }

    // 从数据库里去图片
    private Bitmap getBitmapFromDB(String ImageId) {
        Bitmap bit = Thumbnails.getThumbnail(context.getContentResolver(),
                Integer.valueOf(ImageId), Thumbnails.MINI_KIND, new BitmapFactory.Options());
        return bit;
    }

    /**
     * 取消正在下载的任务
     */
    public synchronized void cancelTask() {
        if (mImageThreadPool != null) {
            mImageThreadPool.shutdownNow();
            mImageThreadPool = null;
        }
    }

    /**
     * 异步下载图片的回调接口
     *
     * @author len
     */
    public interface OnImageLoaderListener {
        void onImageLoader(Bitmap bitmap, String url);
    }

}
