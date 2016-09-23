package com.shop.Android.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.shop.Android.base.BaseActvity;
import com.shop.R;

/**
 * Created by admin on 2016/9/21.
 */
public class EditorAddressActivity extends BaseActvity {
    private String TAG = "add";
    private EditText mNameEt;
    private EditText mPhoneEt;
    private RadioButton mBoysRb;
    private RadioButton mGirlsRb;
    private EditText mCityEt;
    private LinearLayout mCityLl;
    private EditText mAreaEt;
    private LinearLayout mAreaLl;
    private EditText mCommunityEt;
    private LinearLayout mCommunityLl;
    private EditText mBuildingEt;
    private LinearLayout mBuildingLl;
    private EditText mUnitEt;
    private LinearLayout mUnitLl;
    private EditText mLayerEt;
    private LinearLayout mLayerLl;
    private EditText mRoomEt;
    private LinearLayout mRoomLl;
    private SwitchButton mDefaultSb;



    @Override
    protected int loadLayout() {
        return R.layout.activity_address_editor;
    }

    @Override
    protected void initTitleBar() {
        initTitle("新增收货地址");
        mTitleLeftIv.setImageResource(R.mipmap.back);
        mTitleRightTv.setText("保存");
        mTitleRightTv.setVisibility(View.VISIBLE);
    }

    @Override
    protected void init() {
        F();
        setOnClicks(mCityLl,mAreaLl,mBuildingLl,mCommunityLl,mLayerLl,mRoomLl,mUnitLl);
    }

    @Override
    protected void onClickSet(int i) {
        switch (i){
            case R.id.ay_add_area_ll:
                break;
            case R.id.ay_add_building_ll:
                break;
            case R.id.ay_add_city_ll:
                break;
            case R.id.ay_add_community_ll:
                break;
            case R.id.ay_add_layer_ll:
                break;
            case R.id.ay_add_room_ll:
                break;
            case R.id.ay_add_unit_ll:
                break;
        }

    }
}
