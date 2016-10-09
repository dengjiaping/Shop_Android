package com.shop.Android.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Utils.GsonUtil;
import com.kyleduo.switchbutton.SwitchButton;
import com.shop.Android.Config;
import com.shop.Android.base.BaseActvity;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.AddressBean;
import com.shop.Net.Bean.BaseBean;
import com.shop.Net.Bean.ChooseAddressBean;
import com.shop.Net.Param.AddAddressParam;
import com.shop.Net.Param.AddressParam;
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
    private int type;
    private int sex;
    private int is_default;
    private AddressBean.DataBean addressBean;
    private ChooseAddressBean.DataBean chooseAdddress;



    @Override
    protected int loadLayout() {
        type = getIntent().getIntExtra("type", 0);
        if (null != getIntent().getStringExtra("address")) {
            addressBean = GsonUtil.Str2Bean(getIntent().getStringExtra("address"), AddressBean.DataBean.class);
        }
        return R.layout.activity_address_editor;
    }

    @Override
    protected void initTitleBar() {
        if (type == 1) {
            initTitle("修改收货地址");
            mTitleLeftIv.setImageResource(R.mipmap.back);
            mTitleRightTv.setText("保存");
            mTitleRightTv.setVisibility(View.VISIBLE);
            mNameEt.setText(addressBean.getContact());
            mPhoneEt.setText(addressBean.getPhone());
            if (addressBean.getSex().equals("0")) {
                mBoysRb.setChecked(true);
                sex = 0;
            } else {
                mGirlsRb.setChecked(true);
                sex = 1;
            }
            mCityEt.setText(addressBean.getCity());
            mAreaEt.setText(addressBean.getArea());
            mCommunityEt.setText(addressBean.getVillage());
            mLayerEt.setText(addressBean.getFloor());
            mUnitEt.setText(addressBean.getUnit());
            mBuildingEt.setText(addressBean.getBuild());
            mRoomEt.setText(addressBean.getRoom());
        } else {
            initTitle("新增收货地址");
            mTitleLeftIv.setImageResource(R.mipmap.back);
            mTitleRightTv.setText("保存");
            mTitleRightTv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void init() {
        F();
        setOnClicks(mCityLl, mAreaLl, mBuildingLl, mCommunityLl, mLayerLl, mRoomLl, mUnitLl);
        mTitleRightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mNameEt.getText().toString().trim();
                String phone = mPhoneEt.getText().toString().trim();
                if (mBoysRb.isChecked()) {
                    sex = 1;
                } else {
                    sex = 0;
                }
                String city = mCityEt.getText().toString().trim();
                String area = mAreaEt.getText().toString().trim();
                String community = mCommunityEt.getText().toString().trim();
                String building = mBuildingEt.getText().toString().trim();
                String unit = mUnitEt.getText().toString().trim();
                String layer = mLayerEt.getText().toString().trim();
                String room = mRoomEt.getText().toString().trim();
                if (mDefaultSb.isChecked()) {
                    is_default = 1;
                } else {
                    is_default = 0;
                }
                Post(ActionKey.ADDRESS_ADD, new AddAddressParam(name, phone, String.valueOf(is_default),String.valueOf(sex),city,area,community,unit,layer,room,""), BaseBean.class);


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (Config.TYPE){
            case 1:
                if (!Config.DATA.isEmpty()){
                    chooseAdddress = GsonUtil.Str2Bean(Config.DATA,ChooseAddressBean.DataBean.class);
                    mNameEt.setText(chooseAdddress.getName());
                    clearData();
                }
                break;
        }
    }

    @Override
    public void onSuccess(String what, Object result) {
        switch (what){
            case ActionKey.ADDRESS_ADD:
                BaseBean baseBean = (BaseBean) result;
                if (baseBean.getCode()==200){
                    ToastInfo("添加成功");
                }else {
                    ToastInfo(baseBean.getMsg());
                }
                break;
        }
    }

    @Override
    protected void onClickSet(int i) {
        Intent intent;
        switch (i) {
            case R.id.ay_add_area_ll:

                break;
            case R.id.ay_add_building_ll:
                break;
            case R.id.ay_add_city_ll:
                intent = new Intent(EditorAddressActivity.this,ChooseAddressActivity.class);
                intent.putExtra("id",0);
                intent.putExtra("type",1);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
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
