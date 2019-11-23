package com.enzo.commonlib.utils.album.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enzo.commonlib.R;
import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.album.adapter.PreviewAdapter;
import com.enzo.commonlib.utils.album.bean.AlbumImage;
import com.enzo.commonlib.utils.album.constant.SelectImageConstants;
import com.enzo.commonlib.utils.album.utils.SelectImagesManager;
import com.enzo.commonlib.utils.album.view.MyViewPager;
import com.enzo.commonlib.utils.common.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: PreviewActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/3
 * 邮   箱: xiaofangyinwork@163.com
 */
public class PreviewActivity extends BaseActivity {

    private MyViewPager viewPager;
    private TextView tvIndicator;
    private TextView tvConfirm;
    private TextView tvSelect;
    private RelativeLayout rlTopBar;
    private RelativeLayout rlSelectLayout;
    private FrameLayout rlBottomBar;

    private List<AlbumImage> mImages;
    private boolean isShowBar = true;
    private int mMaxCount;

    private BitmapDrawable mSelectDrawable;
    private BitmapDrawable mUnSelectDrawable;

    @Override
    public int getStatusBarColor() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, flag);
        return R.layout.activity_preview;
    }

    @Override
    public void initView() {
        viewPager = findViewById(R.id.vp_image);
        tvIndicator = findViewById(R.id.tv_indicator);
        tvConfirm = findViewById(R.id.tv_confirm);
        tvSelect = findViewById(R.id.tv_select);
        rlTopBar = findViewById(R.id.rl_top_bar);
        rlSelectLayout = findViewById(R.id.rl_select_layout);
        rlBottomBar = findViewById(R.id.rl_bottom_bar);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mMaxCount = intent.getIntExtra(SelectImageConstants.MAX_SELECT_COUNT, 0);
        mImages = intent.getParcelableArrayListExtra(SelectImageConstants.IMAGES);
        boolean canSelect = intent.getBooleanExtra(SelectImageConstants.CAN_SELECT, false);

        Resources resources = getResources();
        Bitmap selectBitmap = BitmapFactory.decodeResource(resources, R.mipmap.icon_checkbox_checked);
        mSelectDrawable = new BitmapDrawable(resources, selectBitmap);
        mSelectDrawable.setBounds(0, 0, selectBitmap.getWidth(), selectBitmap.getHeight());

        Bitmap unSelectBitmap = BitmapFactory.decodeResource(resources, R.mipmap.icon_checkbox_unchecked);
        mUnSelectDrawable = new BitmapDrawable(resources, unSelectBitmap);
        mUnSelectDrawable.setBounds(0, 0, unSelectBitmap.getWidth(), unSelectBitmap.getHeight());

        initViewPager();

        tvIndicator.setText(1 + "/" + mImages.size());
        changeSelect(mImages.get(0));
        viewPager.setCurrentItem(intent.getIntExtra(SelectImageConstants.POSITION, 0));

        if (canSelect) {
            rlBottomBar.setVisibility(View.VISIBLE);
            rlSelectLayout.setVisibility(View.VISIBLE);
        } else {
            rlBottomBar.setVisibility(View.GONE);
            rlSelectLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(SelectImageConstants.IMAGES,
                        (ArrayList<? extends Parcelable>) SelectImagesManager.getManager().getImages());
                setResult(RESULT_OK, intent);
                finish();
                SelectImagesManager.getManager().clear();
            }
        });
        tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSelect();
            }
        });
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        PreviewAdapter adapter = new PreviewAdapter(this, mImages);
        viewPager.setAdapter(adapter);
        adapter.setOnItemClickListener(new PreviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AlbumImage image) {
                if (isShowBar) {
                    hideBar();
                } else {
                    showBar();
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tvIndicator.setText(position + 1 + "/" + mImages.size());
                changeSelect(mImages.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 显示头部和尾部栏
     */
    private void showBar() {
        isShowBar = true;
        ObjectAnimator.ofFloat(rlTopBar, "translationY",
                rlTopBar.getTranslationY(), 0).setDuration(300).start();
        ObjectAnimator.ofFloat(rlBottomBar, "translationY",
                rlBottomBar.getTranslationY(), 0).setDuration(300).start();
    }

    /**
     * 隐藏头部和尾部栏
     */
    private void hideBar() {
        isShowBar = false;
        ObjectAnimator.ofFloat(rlTopBar, "translationY",
                0, -rlTopBar.getHeight()).setDuration(300).start();
        ObjectAnimator.ofFloat(rlBottomBar, "translationY", 0, rlBottomBar.getHeight())
                .setDuration(300).start();
    }

    private void clickSelect() {
        int position = viewPager.getCurrentItem();
        if (mImages != null && mImages.size() > position) {
            AlbumImage image = mImages.get(position);
            if (image.isSelected()) {
                image.setSelected(false);
                SelectImagesManager.getManager().remove(image);
            } else if (mMaxCount <= 0 || getSelectCount() < mMaxCount) {
                image.setSelected(true);
                SelectImagesManager.getManager().add(image);
            } else {
                ToastUtils.showToast("无法选择更多图片");
            }
            changeSelect(image);
        }
    }

    private void changeSelect(AlbumImage image) {
        tvSelect.setCompoundDrawables(image.isSelected() ?
                mSelectDrawable : mUnSelectDrawable, null, null, null);
        int count = getSelectCount();
        if (count == 0) {
            tvConfirm.setEnabled(false);
            tvConfirm.setText("确定");
        } else {
            tvConfirm.setEnabled(true);
            if (mMaxCount > 0) {
                tvConfirm.setText("确定(" + count + "/" + mMaxCount + ")");
            } else {
                tvConfirm.setText("确定(" + count + ")");
            }
        }
    }

    private int getSelectCount() {
        return SelectImagesManager.getManager().size();
    }
}
