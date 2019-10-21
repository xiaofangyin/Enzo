package com.enzo.commonlib.utils.imageloader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.enzo.commonlib.utils.common.LogUtil;

import java.io.File;

/**
 * 文 件 名: ImageLoader
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/12
 * 邮   箱: xiaofangyinwork@163.com
 */
public class ImageLoader {

    private RequestBuilder<Drawable> glideRequest;
    private Builder mBuilder;

    private ImageLoader(Builder builder) {
        mBuilder = builder;
        loadWithGlide(builder);
    }

    /**
     * Glide使用的方法汇总
     * load(Uri uri)
     * load(File file)
     * load(Integer resourceId)
     * load(URL url)
     * load(byte[] model)
     * load(T model)
     * loadFromMediaStore(Uri uri)
     */
    @SuppressLint("CheckResult")
    private void loadWithGlide(final Builder builder) {
        if (builder.context != null) {
            //如果传进来的是activity,并且该activity destroy了,不在继续执行后边逻辑
            if (builder.context instanceof Activity) {
                Activity activity = (Activity) builder.context;
                if (activity.isFinishing()) {
                    return;
                }
            }
            RequestManager requestManager = Glide.with(builder.context);
            if (builder.file != null) {
                glideRequest = requestManager.load(builder.file);
                LogUtil.d("glide load file: " + builder.file.getAbsolutePath());
            } else if (builder.drawable != 0) {
                glideRequest = requestManager.load(builder.drawable);
                LogUtil.d("glide load drawable: " + builder.drawable);
            } else if (builder.path != null) {
                glideRequest = requestManager.load(builder.path);
                LogUtil.d("glide load url: " + builder.path);
            } else if (builder.uri != null) {
                glideRequest = requestManager.load(builder.uri);
                LogUtil.d("glide load uri: " + builder.uri.getPath());
            }

            if (glideRequest != null) {
                if (builder.placeHolder != 0) {
                    glideRequest.apply(RequestOptions.placeholderOf(builder.placeHolder));
                }
                if (builder.errorImage != 0) {
                    glideRequest.apply(RequestOptions.errorOf(builder.errorImage));
                }
                if (builder.clearAnimate) {
                    glideRequest.apply(new RequestOptions().dontAnimate());
                }
                if (!TextUtils.isEmpty(builder.signature)) {
                    glideRequest.apply(RequestOptions.signatureOf(new ObjectKey(builder.signature)));
                }
                if (builder.isCircle) {
                    glideRequest.apply(RequestOptions.bitmapTransform(new GlideCircleTransform()));
                } else if (builder.radius != 0) {
                    glideRequest.apply(RequestOptions.bitmapTransform(new GlideRoundTransform(builder.radius)));
                }
                if (builder.listener != null) {
                    glideRequest.listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            LogUtil.d("glide load success...");
                            builder.listener.onSuccess();
                            return false;
                        }

                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                            LogUtil.d("glide load failed...");
                            builder.listener.onFailed();
                            return false;
                        }
                    });
                }
            }
        }
    }

    public void into(ImageView imageView) {
        if (glideRequest != null) {
            if (mBuilder != null && mBuilder.listener != null) {
                LogUtil.d("glide load start...");
                mBuilder.listener.onStart();
            }
            try {
                glideRequest.into(imageView);
            } catch (Exception e) {
                LogUtil.e("glide load error: " + e.getMessage());
            }
        }
    }

    public static class Builder {
        private Context context;
        private File file;//要展示的本地图片
        private int drawable;//要展示的drawable资源
        private String path;//要展示的图片的url地址
        private Uri uri;

        private int placeHolder;//加载中图片
        private int errorImage;//加载失败图片
        private boolean clearAnimate;//加载不使用动画
        private String signature;//添加签名
        private boolean isCircle;//圆形
        private int radius;//圆角半径
        private LoadListener listener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder load(File file) {
            this.file = file;
            return this;
        }

        public Builder load(int drawable) {
            this.drawable = drawable;
            return this;
        }

        public Builder load(String path) {
            this.path = path;
            return this;
        }

        public Builder load(Uri uri) {
            this.uri = uri;
            return this;
        }

        /**
         * 加载中显示的图片
         */
        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        /**
         * 加载失败显示的图片
         */
        public Builder error(int errorImage) {
            this.errorImage = errorImage;
            return this;
        }

        /**
         * Glide:
         * 因为若使用了place holder则加载完成后图片的大小被限制为holder图片的大小
         * 解决办法：取消加载时的crossFade()动画效果。
         */
        public Builder clearAnimate() {
            this.clearAnimate = true;
            return this;
        }

        /**
         * Glide:
         * 如果更换了图片然而网络地址或文件名称没有更改，imageview会还加载之前的图片，
         * 因为文件名称或url路径没有改变，所以还加载缓存里面的图片，导致图片不更改。
         * 解决办法：增加签名,每次图片更改就不用修改url了，直接修改version，相当于修改版本号了，版本号一改，那么glide就会去重新加载现在的图片啦！
         */
        public Builder signature(String signature) {
            this.signature = signature;
            return this;
        }

        /**
         * 圆形
         */
        public Builder circle() {
            this.isCircle = true;
            return this;
        }

        /**
         * 圆形直角
         *
         * @param radius 圆形直角半径(dp)
         */
        public Builder round(int radius) {
            this.radius = radius;
            return this;
        }

        /**
         * 加载成功、失败回调
         */
        public Builder listener(LoadListener listener) {
            this.listener = listener;
            return this;
        }

        public ImageLoader build() {
            return new ImageLoader(this);
        }
    }

    public interface LoadListener {
        void onStart();

        void onSuccess();

        void onFailed();
    }
}
