package com.enzo.commonlib.widget.progress;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.Keep;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.enzo.commonlib.R;
import com.enzo.commonlib.utils.common.DensityUtil;

public class WaterWaveView extends AppCompatImageView {

    // Default values
    private static final float DEFAULT_AMPLITUDE_RATIO = 0.04f;//振幅
    private static final float DEFAULT_WATER_LEVEL_RATIO = 0.5f;//水位
    private static final float DEFAULT_WAVE_LENGTH_RATIO = 1.0f;//波长
    private static final float DEFAULT_WAVE_SHIFT_RATIO = 0.0f;//偏移
    public static final int DEFAULT_BORDER_WIDTH = DensityUtil.dip2px(1.5f);
    public static final int DEFAULT_BORDER_COLOR = Color.parseColor("#f4f3f8");

    // Dynamic Properties
    private int canvasSize;
    private float amplitudeRatio;
    private int waveColor;

    // Properties
    private float waterLevelRatio = 0f;
    private float waveShiftRatio = DEFAULT_WAVE_SHIFT_RATIO;
    private float defaultWaterLevel;

    // Object used to draw
    private Bitmap image;
    private Drawable drawable;
    private Paint paint;
    private Paint borderPaint;
    private Paint wavePaint;
    private BitmapShader waveShader;
    private Matrix waveShaderMatrix;

    // Animation
    private AnimatorSet animatorSetWave;

    private boolean firstLoadBitmap = true;

    public WaterWaveView(final Context context) {
        this(context, null);
    }

    public WaterWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        // Init paint
        paint = new Paint();
        paint.setAntiAlias(true);

        // Init Wave
        waveShaderMatrix = new Matrix();
        wavePaint = new Paint();
        wavePaint.setAntiAlias(true);

        // Init Border
        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);

        // Init Animation
        initAnimation();

        // Load the styled attributes and set their properties
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.WaterWaveView, defStyleAttr, 0);

        // Init Wave
        waveColor = attributes.getColor(R.styleable.WaterWaveView_cfl_wave_color, getResources().getColor(R.color.color_green));
        float amplitudeRatioAttr = attributes.getFloat(R.styleable.WaterWaveView_cfl_wave_amplitude, DEFAULT_AMPLITUDE_RATIO);
        amplitudeRatio = (amplitudeRatioAttr > DEFAULT_AMPLITUDE_RATIO) ? DEFAULT_AMPLITUDE_RATIO : amplitudeRatioAttr;
        setProgress(attributes.getInteger(R.styleable.WaterWaveView_cfl_progress, 0));

        if (attributes.getBoolean(R.styleable.WaterWaveView_cfl_border, true)) {
            float defaultBorderSize = DEFAULT_BORDER_WIDTH * getContext().getResources().getDisplayMetrics().density;
            borderPaint.setStrokeWidth(attributes.getDimension(R.styleable.WaterWaveView_cfl_border_width, defaultBorderSize));
        } else {
            borderPaint.setStrokeWidth(0);
        }

        attributes.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasSize = w;
        if (h < canvasSize)
            canvasSize = h;
        if (image != null)
            updateShader();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        int imageSize = (width < height) ? width : height;
        setMeasuredDimension(imageSize, imageSize);
    }

    private int measureWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else {
            result = canvasSize;
        }
        return result;
    }

    private int measureHeight(int measureSpecHeight) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpecHeight);
        int specSize = MeasureSpec.getSize(measureSpecHeight);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else {
            result = canvasSize;
        }
        return (result + 2);
    }

    @Override
    public void onDraw(Canvas canvas) {
        loadBitmap();

        if (image == null)
            return;

        int circleCenter = canvasSize / 2;
        canvas.drawCircle(circleCenter, circleCenter, circleCenter - borderPaint.getStrokeWidth(), paint);

        if (waveShader != null) {
            if (wavePaint.getShader() == null) {
                wavePaint.setShader(waveShader);
            }

            waveShaderMatrix.setScale(1, amplitudeRatio / DEFAULT_AMPLITUDE_RATIO, 0, defaultWaterLevel);
            int width = getWidth();
            int height = getHeight();
            waveShaderMatrix.postTranslate(waveShiftRatio * width,
                    (DEFAULT_WATER_LEVEL_RATIO - waterLevelRatio) * height);

            waveShader.setLocalMatrix(waveShaderMatrix);

            borderPaint.setColor(DEFAULT_BORDER_COLOR);
            float borderWidth = borderPaint.getStrokeWidth();
            if (borderWidth > 0) {
                canvas.drawCircle(width / 2f, height / 2f, (width - borderWidth) / 2f - 1f, borderPaint);
            }

            // Draw Wave
            float radius = width / 2f - borderWidth;
            canvas.drawCircle(width / 2f, height / 2f, radius, wavePaint);
        } else {
            wavePaint.setShader(null);
        }
    }

    private void loadBitmap() {
        if (drawable == getDrawable() && !firstLoadBitmap)
            return;

        drawable = getDrawable();
        image = drawableToBitmap(drawable);
        firstLoadBitmap = false;
        updateShader();
    }

    private void updateShader() {
        if (image == null)
            return;

        image = cropBitmap(image);

        BitmapShader shader = new BitmapShader(image, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        Matrix matrix = new Matrix();
        matrix.setScale((float) canvasSize / (float) image.getWidth(), (float) canvasSize / (float) image.getHeight());
        shader.setLocalMatrix(matrix);

        paint.setShader(shader);

        updateWaveShader();
    }

    private void updateWaveShader() {
        int width = getWidth();
        int height = getHeight();

        double defaultAngularFrequency = 2.0f * Math.PI / DEFAULT_WAVE_LENGTH_RATIO / width;
        float defaultAmplitude = height * DEFAULT_AMPLITUDE_RATIO;
        defaultWaterLevel = height * DEFAULT_WATER_LEVEL_RATIO;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint wavePaint = new Paint();
        wavePaint.setStrokeWidth(2);
        wavePaint.setAntiAlias(true);

        // y=Asin(ωx+φ)+h
        final int endX = width + 1;
        final int endY = height + 1;

        float[] waveY = new float[endX];

        wavePaint.setColor(adjustAlpha(waveColor, 0.3f));
        for (int beginX = 0; beginX < endX; beginX++) {
            double wx = beginX * defaultAngularFrequency;
            float beginY = (float) (defaultWaterLevel + defaultAmplitude * Math.sin(wx));
            canvas.drawLine(beginX, beginY, beginX, endY, wavePaint);
            waveY[beginX] = beginY;
        }

        wavePaint.setColor(waveColor);
        final int wave2Shift = width / 4;
        for (int beginX = 0; beginX < endX; beginX++) {
            canvas.drawLine(beginX, waveY[(beginX + wave2Shift) % endX], beginX, endY, wavePaint);
        }

        waveShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        this.wavePaint.setShader(waveShader);
    }

    private Bitmap cropBitmap(Bitmap bitmap) {
        Bitmap bmp;
        if (bitmap.getWidth() >= bitmap.getHeight()) {
            bmp = Bitmap.createBitmap(
                    bitmap,
                    bitmap.getWidth() / 2 - bitmap.getHeight() / 2,
                    0,
                    bitmap.getHeight(), bitmap.getHeight());
        } else {
            bmp = Bitmap.createBitmap(
                    bitmap,
                    0,
                    bitmap.getHeight() / 2 - bitmap.getWidth() / 2,
                    bitmap.getWidth(), bitmap.getWidth());
        }
        return bmp;
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int intrinsicWidth;
        int intrinsicHeight;
        if (drawable == null) {
            intrinsicWidth = getWidth();
            intrinsicHeight = getHeight();
        } else {
            intrinsicWidth = drawable.getIntrinsicWidth();
            intrinsicHeight = drawable.getIntrinsicHeight();
        }

        if (!(intrinsicWidth > 0 && intrinsicHeight > 0))
            return null;

        try {
            Bitmap bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
            if (drawable != null) {
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
            }
            return bitmap;
        } catch (OutOfMemoryError e) {
            Log.e(getClass().toString(), "Encountered OutOfMemoryError while generating bitmap!");
            return null;
        }
    }

    //region Set Attr Method
    public void setColor(int color) {
        waveColor = color;
        updateWaveShader();
        invalidate();
    }

    public void setBorderWidth(float width) {
        borderPaint.setStrokeWidth(width);
        invalidate();
    }

    public void setAmplitudeRatio(float amplitudeRatio) {
        if (this.amplitudeRatio != amplitudeRatio) {
            this.amplitudeRatio = amplitudeRatio;
            invalidate();
        }
    }

    public void setProgress(int progress) {
        waterLevelRatio = progress / 100f;
        invalidate();
    }

    public void setProgress(int progress, int milliseconds) {
        ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(this, "waterLevelRatio", waterLevelRatio, (float) progress / 100);
        waterLevelAnim.setDuration(milliseconds);
        waterLevelAnim.setInterpolator(new DecelerateInterpolator());
        AnimatorSet animatorSetProgress = new AnimatorSet();
        animatorSetProgress.play(waterLevelAnim);
        animatorSetProgress.start();
    }

    private void startAnimation() {
        if (animatorSetWave != null) {
            animatorSetWave.start();
        }
    }

    private void initAnimation() {
        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(this, "waveShiftRatio", 0f, 1f);
        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        waveShiftAnim.setDuration(1500);
        waveShiftAnim.setInterpolator(new LinearInterpolator());

        animatorSetWave = new AnimatorSet();
        animatorSetWave.play(waveShiftAnim);
    }

    private void cancel() {
        if (animatorSetWave != null) {
            animatorSetWave.end();
        }
    }

    @Keep
    private void setWaveShiftRatio(float waveShiftRatio) {
        if (this.waveShiftRatio != waveShiftRatio) {
            this.waveShiftRatio = waveShiftRatio;
            invalidate();
        }
    }

    @Keep
    private void setWaterLevelRatio(float waterLevelRatio) {
        if (this.waterLevelRatio != waterLevelRatio) {
            this.waterLevelRatio = waterLevelRatio;
            invalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        cancel();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            startAnimation();
        } else {
            cancel();
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (getVisibility() != VISIBLE) {
            return;
        }

        if (visibility == VISIBLE) {
            startAnimation();
        } else {
            cancel();
        }
    }

    private int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
}