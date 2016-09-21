package com.shop.Android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by admin on 2016/9/21.
 */
public class EImageView extends ImageView {
    public EImageView(Context context) {
        super(context);
    }

    public EImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
