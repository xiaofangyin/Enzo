package com.enzo.commonlib.widget.headerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enzo.commonlib.R;
import com.nineoldandroids.view.ViewHelper;


/**
 * 文 件 名:
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/5/20
 * 邮   箱: xiaofangyinwork@163.com
 */
public class HeadWidget extends FrameLayout {

    private LinearLayout llTitleLayout;
    private TextView tvTitle1, tvTitle2;
    protected RelativeLayout mLeftLayout, mRightImageLayout, mRightTextLayout;
    protected TextView mTitleText, mLeftText, mRightText;
    protected ImageView mLeftImage, mRightImage;
    private View bottomLine;

    public HeadWidget(Context context) {
        this(context, null);
    }

    public HeadWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.lib_layout_header, this);
        llTitleLayout = rootView.findViewById(R.id.ugc_header_title_layout);
        tvTitle1 = rootView.findViewById(R.id.ugc_header_title_1);
        tvTitle2 = rootView.findViewById(R.id.ugc_header_title_2);
        mTitleText = rootView.findViewById(R.id.ugc_header_title);
        mLeftImage = rootView.findViewById(R.id.ugc_header_left_image);
        mLeftLayout = rootView.findViewById(R.id.ugc_header_left_layout);
        mLeftText = rootView.findViewById(R.id.ugc_header_left_text);
        mRightTextLayout = rootView.findViewById(R.id.ugc_header_right_text_layout);
        mRightText = rootView.findViewById(R.id.ugc_header_right_text);
        mRightImageLayout = rootView.findViewById(R.id.ugc_header_right_image_layout);
        mRightImage = rootView.findViewById(R.id.ugc_header_right_image);
        bottomLine = rootView.findViewById(R.id.ugc_header_bottom_line);
        setBackgroundColor(getResources().getColor(R.color.color_white));
    }

    public void setBackButtonVisible(int visible) {
        mLeftLayout.setVisibility(visible);
    }

    public void setLeftImage(int imageId) {
        if (mLeftLayout != null && mLeftImage != null) {
            mLeftImage.setBackgroundResource(imageId);
            mLeftLayout.setVisibility(View.VISIBLE);
        }
    }

    public void setLeftText(String text) {
        if (mLeftLayout != null && mLeftText != null) {
            mLeftImage.setVisibility(View.GONE);
            mLeftText.setVisibility(View.VISIBLE);
            mLeftText.setText(text);
            mLeftLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置title文字
     */
    public void setTitle(String title) {
        llTitleLayout.setVisibility(View.GONE);
        mTitleText.setVisibility(View.VISIBLE);
        if (mTitleText != null) {
            mTitleText.setText(title);
        }
    }

    public void setTitle(String title, String subTitle) {
        llTitleLayout.setVisibility(View.VISIBLE);
        mTitleText.setVisibility(View.GONE);
        tvTitle1.setText(title);
        tvTitle2.setText(subTitle);
    }

    /**
     * 设置title文字size
     */
    public void setTitleSize(int titleSize) {
        if (mTitleText != null) {
            mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize);
        }
    }

    public void setTitleColor(int color) {
        mTitleText.setTextColor(color);
    }

    /**
     * 设置title右侧图片按钮
     */
    public void setRightImage(int imageID) {
        if (mRightImageLayout != null && mRightImage != null) {
            mRightImage.setBackgroundResource(imageID);
            mRightImageLayout.setVisibility(View.VISIBLE);
        }
    }

    public void setRightImageVisible(boolean visible) {
        mRightImageLayout.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 设置title右侧文字按钮
     */
    public void setRightText(String rightText) {
        if (mRightTextLayout != null && mRightText != null) {
            mRightText.setText(rightText);
            mRightTextLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置title右侧文字按钮是否可见
     */
    public void setRightTextVisible(int visible) {
        if (mRightTextLayout != null && mRightText != null) {
            mRightText.setVisibility(visible);
        }
    }

    /**
     * 设置title右侧文字颜色
     */
    public void setRightTextColor(int color) {
        if (mRightTextLayout != null && mRightText != null) {
            mRightText.setTextColor(color);
        }
    }

    /**
     * 设置title右侧文字按钮是否可用
     *
     * @param enable
     */
    public void setRightTextEnable(boolean enable) {
        if (mRightTextLayout != null && mRightText != null) {
            mRightTextLayout.setEnabled(enable);
            ViewHelper.setAlpha(mRightTextLayout, enable ? 1.0f : 0.5f);
        }
    }

    public void setBottomLineVisiable(boolean visiable) {
        bottomLine.setVisibility(visiable ? View.VISIBLE : View.GONE);
    }

    public void setLeftLayoutClickListener(final OnClickListener listener) {
        mLeftLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
    }

    public void setRightTextClickListener(final OnClickListener listener) {
        mRightTextLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
    }

    public void setRightImageClickListener(final OnClickListener listener) {
        mRightImageLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
    }
}
