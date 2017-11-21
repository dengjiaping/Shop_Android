package com.xiaojishop.Android.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.TextView;

import com.king.Utils.PixelUtil;
import com.xiaojishop.R;

/**
 * Created by admin on 2016/9/22.
 */
public class BgTextView extends TextView {

    public BgTextView(Context context, String content) {
        super(context);
        init(content);
    }

    private void init(String content) {
        setBackgroundResource(R.drawable.index_tv_bg_gray);
        if (content.length() == 2) {
            setPadding(PixelUtil.dp2px(23), PixelUtil.dp2px(4), PixelUtil.dp2px(23), PixelUtil.dp2px(4));
        } else if(content.length() == 1){
            setPadding(PixelUtil.dp2px(30), PixelUtil.dp2px(4), PixelUtil.dp2px(30), PixelUtil.dp2px(4));
        }else {
            setPadding(PixelUtil.dp2px(15), PixelUtil.dp2px(4), PixelUtil.dp2px(15), PixelUtil.dp2px(4));
        }
        setMaxEms(6);
        setEllipsize(TextUtils.TruncateAt.END);
        setSingleLine(true);
        setTextColor(Color.rgb(0x88, 0x88, 0x88));
        setTextSize(15);
        setText(content);
    }
}
