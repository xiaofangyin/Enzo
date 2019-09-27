package com.enzo.commonlib.utils.preview;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.enzo.commonlib.R;
import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.common.DensityUtil;
import com.enzo.commonlib.utils.common.ToastUtils;
import com.enzo.commonlib.utils.imageloader.ImageLoader;
import com.enzo.commonlib.widget.loadingdialog.LoadingDialog;
import com.enzo.commonlib.widget.photoview.PhotoView;
import com.enzo.commonlib.widget.photoview.PhotoViewAttacher;

import java.util.ArrayList;

/**
 * 文 件 名: PicPreviewActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/7/13
 * 邮   箱: xiaofangyinwork@163.com
 */
public class PicPreviewActivity extends BaseActivity {

    private ViewPager viewPager;
    private TextView tvPhotoOrder;
    private ArrayList<PicsBean> imageList;
    private int curPosition = -1;

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, flag);
        return R.layout.activity_news_detail_pics_preview;
    }

    @Override
    public void initView() {
        tvPhotoOrder = findViewById(R.id.pic_preview_order);
        viewPager = findViewById(R.id.pic_preview_view_pager);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        imageList = (ArrayList<PicsBean>) getIntent().getSerializableExtra("pic_list");
        curPosition = getIntent().getIntExtra("position", 0);

        viewPager.setPageMargin(DensityUtil.dip2px(15));
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                if (!TextUtils.isEmpty(imageList.get(position).getOriginal())) {
                    View rootView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_news_detail_pic_preview, null);
                    PhotoView imageView = rootView.findViewById(R.id.pic_preview_photo_view);

                    ImageLoader.Builder builder = new ImageLoader.Builder(PicPreviewActivity.this);
                    builder.load(imageList.get(position).getOriginal())
                            .clearAnimate()//不取消动画的话 图片会被拉伸
                            .build()
                            .into(imageView);
                    container.addView(rootView);

                    imageView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                        @Override
                        public void onViewTap(View view, float x, float y) {
                            finish();
                        }
                    });
                    return rootView;
                }
                return null;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

        });
        viewPager.setCurrentItem(curPosition);
        viewPager.setTag(curPosition);
        tvPhotoOrder.setText(String.format("%d/%d", curPosition + 1, imageList.size()));//设置页面的编号
    }

    @Override
    public void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curPosition = position;
                tvPhotoOrder.setText(String.format("%d/%d", position + 1, imageList.size()));//设置页面的编号
                viewPager.setTag(position);//为当前view设置tag
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        findViewById(R.id.ugc_photo_browser_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoadImage(imageList.get(curPosition).getOriginal());
            }
        });
    }

    private void downLoadImage(String url) {
        LoadingDialog.show(PicPreviewActivity.this);
        ToastUtils.showToast("开始下载...");
        UGCFileUtils.downLoadImageAndSave(getApplicationContext(),
                url,
                UGCFileUtils.UGC_FILE_PARENT,
                System.currentTimeMillis() + ".jpg",
                new UGCFileUtils.OnImageDownLoadCallBack() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.dismiss();
                                ToastUtils.showToast("下载成功");
                            }
                        });
                    }

                    @Override
                    public void onFailed() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.dismiss();
                                ToastUtils.showToast("下载失败，请重试");
                            }
                        });
                    }
                });
    }
}
