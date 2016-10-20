package com.shop.Android.activity;

import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.king.FlycoTableLayout.CommonTabLayout;
import com.king.FlycoTableLayout.listener.CustomTabEntity;
import com.king.FlycoTableLayout.listener.OnTabSelectListener;
import com.king.Utils.LogCat;
import com.king.Utils.SPrefUtil;
import com.shop.Android.DataKey;
import com.shop.Android.SPKey;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.fragment.CarFragment;
import com.shop.Android.fragment.IndexFragment;
import com.shop.Android.fragment.MineFragment;
import com.shop.Android.fragment.OrderFragment;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.BaseBean;
import com.shop.Net.Param.Token;
import com.shop.R;

import java.util.ArrayList;

public class MainActivity extends BaseActvity {


    //组件信息
    private String TAG = "main";
    private CommonTabLayout mTabCtl;
    public static int index = 0;

    //Tab信息
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private int[] icons = {//底部点击时显示的图标
            R.drawable.tab_index_icon_normal, R.drawable.tab_car_icon_normal,
            R.drawable.tab_order_icon_normal, R.drawable.tab_user_icon_normal};
    private int[] iconsSelect = {//未点击时显示的图标
            R.drawable.tab_index_icon_checked, R.drawable.tab_car_icon_checked,
            R.drawable.tab_order_icon_checked, R.drawable.tab_user_icon_checked};
    private ArrayList<CustomTabEntity> tabs = new ArrayList<>();
    private String[] texts = new String[]{"首页", "购物车", "订单", "我的"};

    @Override
    protected int loadLayout() {
        index = 0;
        NoSlide();
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        F();
        addTab();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            mTabCtl.setCurrentTab(index);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addTab() {
        //底部栏三个按钮对应的Fragment
        fragments = new ArrayList<>();
        fragments.add(new IndexFragment());
        fragments.add(new CarFragment());
        fragments.add(new OrderFragment());
        fragments.add(new MineFragment());
        for (int i = 0; i < texts.length; i++) {//给底部栏加入图标
            final int finalI = i;
            tabs.add(new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return texts[finalI];
                }

                @Override
                public int getTabSelectedIcon() {
                    return iconsSelect[finalI];
                }

                @Override
                public int getTabUnselectedIcon() {
                    return icons[finalI];
                }
            });
        }
        mTabCtl.setTabData(tabs, this, R.id.ay_main_content_fl, fragments);
        mTabCtl.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int i) {
                index = i;
                if (i==2){
                        Post(ActionKey.CHECK_TOKEN,new Token(), BaseBean.class);
                }else {

                }
            }
            @Override
            public void onTabReselect(int i) {

            }
        });
    }

    @Override
    public void onStart(String what) {
        switch (what){
            case ActionKey.CHECK_TOKEN:
                break;
            default:
                super.onStart(what);
        }
    }

    @Override
    public void onSuccess(String what, Object result) {
        switch (what){
            case ActionKey.CHECK_TOKEN:
                BaseBean baseBean = (BaseBean) result;
                if (200==baseBean.getCode()){

                }else if (2001==baseBean.getCode()){
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                }else {
                    ToastInfo(baseBean.getMsg());
                }
                break;
        }
    }

    @Override
    protected void onClickSet(int i) {

    }


}
