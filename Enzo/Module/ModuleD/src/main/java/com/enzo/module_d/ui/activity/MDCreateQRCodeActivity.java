package com.enzo.module_d.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.common.DensityUtil;
import com.enzo.commonlib.utils.zxing.encoding.EncodingHandler;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;

public class MDCreateQRCodeActivity extends BaseActivity {

    private EditText editText;
    private Button button;
    private ImageView imageView;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_create_qr_code;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("生成二维码");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        editText = findViewById(R.id.edit_text);
        imageView = findViewById(R.id.image_view);
        button = findViewById(R.id.btn_create);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    Bitmap logo = getRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.lighter_pic_2),
                            DensityUtil.dip2px(MDCreateQRCodeActivity.this, 20),
                            DensityUtil.dip2px(MDCreateQRCodeActivity.this, 8));
                    Bitmap bitmap = EncodingHandler.createQRCode(editText.getText().toString(),
                            imageView.getWidth(),
                            imageView.getHeight(),
                            logo);
                    imageView.setImageBitmap(bitmap);
                }
            }
        });
    }

    private Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx, float borderWidth) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect((int) (borderWidth / 2f),
                (int) (borderWidth / 2f),
                bitmap.getWidth() - (int) (borderWidth / 2f),
                bitmap.getHeight() - (int) (borderWidth / 2f));
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.WHITE);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        //添加边框
        RectF rectF1 = new RectF(rect);
        Paint paint1 = new Paint();
        paint1.setColor(Color.WHITE);
        paint1.setAntiAlias(true);
        paint1.setDither(true);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(borderWidth);
        canvas.drawRoundRect(rectF1, roundPx, roundPx, paint1);
        return output;
    }

}
