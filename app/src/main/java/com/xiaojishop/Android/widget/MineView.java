package com.xiaojishop.Android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Utils.UIUtil;
import com.xiaojishop.R;

/**
 * Created by admin on 2016/9/9.
 */
public class MineView extends RelativeLayout {




    public MineView(Context context) {
        this(context, null);
    }

    public MineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    private void init(Context context, AttributeSet attrs) {
        ImageView mLeftIconIv;
        TextView mContentTv;
        View mRightLineV;
        View mBottomLineV;
        View view = View.inflate(context, R.layout.view_mine, this);
        mLeftIconIv = UIUtil.findViewById(view, R.id.view_left_icon_iv);
        mContentTv = UIUtil.findViewById(view, R.id.view_content_text_tv);
        mRightLineV = UIUtil.findViewById(view, R.id.view_right_line_v);
        mBottomLineV = UIUtil.findViewById(view, R.id.view_bottom_line_v);
        mLeftIconIv.setImageResource(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res-auto", "mv_icon", R.drawable.default_image));
        mContentTv.setText(attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "mv_content"));
        if (!attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res-auto", "mv_right_line_isvisible", true)) {
            mRightLineV.setVisibility(INVISIBLE);
        }
        if (!attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res-auto", "mv_bottom_line_isvisible", true)) {
            mBottomLineV.setVisibility(INVISIBLE);
        }
    }
}
