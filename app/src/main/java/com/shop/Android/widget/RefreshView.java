package com.shop.Android.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.bumptech.glide.Glide;
import com.king.KingApplication;
import com.king.Utils.PictureUtil;
import com.king.Utils.UIUtil;
import com.king.View.refreshview.callback.IHeaderCallBack;
import com.king.View.refreshview.ui.smileyloadingview.SmileyLoadingView;
import com.shop.R;

/**
 * Created by admin on 2016/9/21.
 */
public class RefreshView extends LinearLayout implements IHeaderCallBack {


    public RefreshView(Context context) {
        super(context);
        init(context);
    }


    private void init(Context context) {
        ImageView mIconIv;
        View view = View.inflate(context, R.layout.item_refresh_title, this);
        mIconIv = UIUtil.findViewById(view, R.id.pic);
        Glide(R.drawable.loading_data, mIconIv);

    }

    public static void Glide(Object url, ImageView imageView) {
        Glide.with(KingApplication.getAppContext()).load(url).skipMemoryCache(true).placeholder(com.king.Config.mDefaultImage).error(com.king.Config.mDefaultImage).centerCrop().dontAnimate().into(imageView);
    }


    @Override
    public void onStateNormal() {

    }

    @Override
    public void onStateReady() {

    }

    @Override
    public void onStateRefreshing() {

    }

    @Override
    public void onStateFinish(boolean b) {

    }

    @Override
    public void onHeaderMove(double v, int i, int i1) {

    }

    @Override
    public void setRefreshTime(long l) {

    }

    @Override
    public void hide() {
        setVisibility(8);
    }

    @Override
    public void show() {
        setVisibility(0);
    }

    @Override
    public int getHeaderHeight() {
        return getMeasuredHeight();
    }
}
