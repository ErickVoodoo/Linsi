package com.linzon.ru.common;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

public class TopCropImageView extends ImageView {
    public TopCropImageView(Context context)
    {
        super(context);
        setScaleType(ScaleType.MATRIX);
    }

    public TopCropImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setScaleType(ScaleType.MATRIX);
    }

    public TopCropImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setScaleType(ScaleType.MATRIX);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        recomputeImgMatrix();
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        recomputeImgMatrix();
        return super.setFrame(l, t, r, b);
    }

    private void recomputeImgMatrix() {
        final Matrix matrix = getImageMatrix();

        float scale;
        final int viewWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        final int viewHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        if(viewWidth != 0 && viewHeight != 0) {
            if(getDrawable() != null) {
                final int drawableWidth = getDrawable().getIntrinsicWidth();
                final int drawableHeight = getDrawable().getIntrinsicHeight();

                if (drawableWidth * viewHeight > drawableHeight * viewWidth) {
                    scale = (float) viewHeight / (float) drawableHeight;
                } else {
                    scale = (float) viewWidth / (float) drawableWidth;
                }

                matrix.setScale(scale, scale);
                setImageMatrix(matrix);
            }
        }
    }
}