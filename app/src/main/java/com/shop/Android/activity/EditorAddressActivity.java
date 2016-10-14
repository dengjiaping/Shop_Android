package com.shop.Android.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.king.Utils.CheckUtil;
import com.king.Utils.GsonUtil;
import com.kyleduo.switchbutton.SwitchButton;
import com.shop.Android.Config;
import com.shop.Android.base.BaseActvity;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.AddressBean;
import com.shop.Net.Bean.BaseBean;
import com.shop.Net.Bean.ChooseAddressBean;
import com.shop.Net.Param.EditAddressParam;
import com.shop.Net.Param.AddAddressParam;
import com.shop.Net.Param.Token;
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
    private int sex=0;
    private int is_default;
    private AddressBean.DataBean addressBean;
    private ChooseAddressBean.DataBean chooseAdddress;
    private String city_id;
    private String community_id;
    private String area_id;
    private String building_id;
    private String layer_id;
    private String unit_id;
    private String room_id;



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
        if (type==1){
            mNameEt.setText(addressBean.getContact());
            mPhoneEt.setText(addressBean.getPhone());
            if (addressBean.getSex().equals("0")) {
                mGirlsRb.setChecked(true);
                sex = 0;
            } else {
                mBoysRb.setChecked(true);
                sex = 1;
            }
            if (addressBean.getIs_default().equals("0")){
                 mDefaultSb.setChecked(false);
                is_default = 0;
            }else {
                mDefaultSb.setChecked(true);
                is_default = 1;
            }
            mCityEt.setText(addressBean.getCity().getName());
            mAreaEt.setText(addressBean.getArea().getName());
            mCommunityEt.setText(addressBean.getVillage().getName());
            mLayerEt.setText(addressBean.getFloor().getName());
            mUnitEt.setText(addressBean.getUnit().getName());
            mBuildingEt.setText(addressBean.getBuild().getName());
            mRoomEt.setText(addressBean.getRoom().getName());

            city_id = addressBean.getCity().getId();
            area_id = addressBean.getArea().getId();
            community_id = addressBean.getVillage().getId();
            layer_id = addressBean.getFloor().getId();
            unit_id = addressBean.getUnit().getId();
            building_id = addressBean.getBuild().getId();
            room_id = addressBean.getRoom().getId();
        }
        setOnClicks(mCityEt, mAreaEt, mBuildingEt, mCommunityEt, mLayerEt, mRoomEt, mUnitEt);
        mTitleRightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type){
                    case 1://修改地址
                        String name = mNameEt.getText().toString().trim();
                        String phone = mPhoneEt.getText().toString().trim();
                        if (mBoysRb.isChecked()) {
                            sex = 1;
                        } else {
                            sex = 0;
                        }
                        String city = mCityEt.getText().toString().trim();
                        String cities  = city_id+"#"+city;

                        String area = mAreaEt.getText().toString().trim();
                        String areas = area_id+"#"+area;

                        String community = mCommunityEt.getText().toString().trim();
                        String communities = community_id+"#"+community;

                        String building = mBuildingEt.getText().toString().trim();
                        String builds = building_id + "#"+building;

                        String unit = mUnitEt.getText().toString().trim();
                        String units = unit_id+"#"+unit;

                        String layer = mLayerEt.getText().toString().trim();
                        String layers = layer_id+"#"+layer;

                        String room = mRoomEt.getText().toString().trim();
                        String rooms = room_id+ "#"+room;

                        if (mDefaultSb.isChecked()) {
                            is_default = 1;
                        } else {
                            is_default = 0;
                        }
                        Post(ActionKey.EDIT_ADDRESS, new EditAddressParam(addressBean.getId(),name, phone,String.valueOf(is_default),String.valueOf(sex),cities,areas,communities,builds,units,layers,rooms), BaseBean.class);
                        break;
                    case 2://添加地址
                        String add_name = mNameEt.getText().toString().trim();
                        String add_phone = mPhoneEt.getText().toString().trim();
                        if (mBoysRb.isChecked()) {
                            sex = 1;
                        } else {
                            sex = 0;
                        }
                        String add_city = mCityEt.getText().toString().trim();
                        String city_ = city_id+"#"+add_city;

                        String add_area = mAreaEt.getText().toString().trim();
                        String area_ =area_id+"#"+add_area;

                        String add_community = mCommunityEt.getText().toString().trim();
                        String community_ = community_id+"#"+add_community;

                        String add_building = mBuildingEt.getText().toString().trim();
                        String build_ = building_id + "#"+add_building;

                        String add_unit = mUnitEt.getText().toString().trim();
                        String unit_ = unit_id+"#"+add_unit;

                        String add_layer = mLayerEt.getText().toString().trim();
                        String layer_ = layer_id+"#"+add_layer;

                        String add_room = mRoomEt.getText().toString().trim();
                        String room_ = room_id+ "#"+add_room;

                        if (mDefaultSb.isChecked()) {
                            is_default = 1;
                        } else {
                            is_default = 0;
                        }
                        if (add_name.equals("")){
                            ToastInfo("联系人不能为空");
                            return;
                        }
                        if (!CheckUtil.isPhone(mPhoneEt)){
                            ToastInfo("联系人电话不能为空");
                            return;
                        }
                        Post(ActionKey.ADDRESS_ADD, new AddAddressParam(add_name, add_phone, String.valueOf(is_default),String.valueOf(sex),city_,area_,community_,unit_,layer_,room_,build_), BaseBean.class);

                        break;
                }

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
                    mCityEt.setText(chooseAdddress.getName());
                    city_id = "1";
                }
                break;
            case 2:
                if (!Config.DATA.isEmpty()){
                    chooseAdddress = GsonUtil.Str2Bean(Config.DATA,ChooseAddressBean.DataBean.class);
                    mAreaEt.setText(chooseAdddress.getName());
                    area_id = chooseAdddress.getId();
                    mCommunityEt.setText("");
                    mRoomEt.setText("");
                    mLayerEt.setText("");
                    mUnitEt.setText("");
                    mBuildingEt.setText("");
                }
                break;
            case 3:
                if (!Config.DATA.isEmpty()){
                    chooseAdddress = GsonUtil.Str2Bean(Config.DATA,ChooseAddressBean.DataBean.class);
                    mCommunityEt.setText(chooseAdddress.getName());
                    community_id = chooseAdddress.getId();
                }
                break;
            case 4:
                if (!Config.DATA.isEmpty()){
                    chooseAdddress = GsonUtil.Str2Bean(Config.DATA,ChooseAddressBean.DataBean.class);
                    mBuildingEt.setText(chooseAdddress.getName());
                    building_id = chooseAdddress.getId();
                }
                break;
            case 5:
                if (!Config.DATA.isEmpty()){
                    chooseAdddress = GsonUtil.Str2Bean(Config.DATA,ChooseAddressBean.DataBean.class);
                    mUnitEt.setText(chooseAdddress.getName());
                    unit_id = chooseAdddress.getId();
                }
                break;
            case 6:
                if (!Config.DATA.isEmpty()){
                    chooseAdddress = GsonUtil.Str2Bean(Config.DATA,ChooseAddressBean.DataBean.class);
                    mLayerEt.setText(chooseAdddress.getName());
                    layer_id = chooseAdddress.getId();
                }
                break;
            case 7:
                if (!Config.DATA.isEmpty()){
                    chooseAdddress = GsonUtil.Str2Bean(Config.DATA,ChooseAddressBean.DataBean.class);
                    mRoomEt.setText(chooseAdddress.getName());
                    room_id = chooseAdddress.getId();
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
                    animFinsh();
                    kingData.sendBroadCast(Config.ADD_ADDRESS);
                }else if (baseBean.getCode()==2001){
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                }else {
                    ToastInfo(baseBean.getMsg());
                }
                break;
            case ActionKey.EDIT_ADDRESS:
                BaseBean bean = (BaseBean) result;
                if (bean.getCode()==200){
                    ToastInfo("修改成功");
                    animFinsh();
                    kingData.sendBroadCast(Config.ADD_ADDRESS);
                }else if (bean.getCode()==2001){
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                }else {
                    ToastInfo(bean.getMsg());
                }
                break;
        }
    }

    @Override
    protected void onClickSet(int i) {
        Intent intent;
        switch (i) {
            case R.id.ay_add_area_et:
                if (!mCityEt.getText().toString().isEmpty()){
                    intent = new Intent(EditorAddressActivity.this,ChooseAddressActivity.class);
                    intent.putExtra("id",city_id);
                    intent.putExtra("type",2);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                }else {
                    ToastInfo("城市没有选择");
                }

                break;
            case R.id.ay_add_building_et:
                if ( !mCommunityEt.getText().toString().isEmpty()){
                    intent = new Intent(EditorAddressActivity.this,ChooseAddressActivity.class);
                    intent.putExtra("id",community_id);
                    intent.putExtra("type",4);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                }else {
                    ToastInfo("小区没有选择");
                }

                break;
            case R.id.ay_add_city_et:
                intent = new Intent(EditorAddressActivity.this,ChooseAddressActivity.class);
                intent.putExtra("id","0");
                intent.putExtra("type",1);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                break;
            case R.id.ay_add_community_et:
                if ( !mAreaEt.getText().toString().isEmpty() ){
                    intent = new Intent(EditorAddressActivity.this,ChooseAddressActivity.class);
                    intent.putExtra("id",area_id);
                    intent.putExtra("type",3);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                }else {
                    ToastInfo("区没有选择");
                }

                break;
            case R.id.ay_add_layer_et:
                if (!mUnitEt.getText().toString().isEmpty() ){
                    intent = new Intent(EditorAddressActivity.this,ChooseAddressActivity.class);
                    intent.putExtra("id",unit_id);
                    intent.putExtra("type",6);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                }else {
                    ToastInfo("单元没有选择");
                }

                break;
            case R.id.ay_add_room_et:
                if (!mLayerEt.getText().toString().isEmpty()){
                    intent = new Intent(EditorAddressActivity.this,ChooseAddressActivity.class);
                    intent.putExtra("id",layer_id);
                    intent.putExtra("type",7);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                }else {
                    ToastInfo("楼层没有选择");
                }

                break;
            case R.id.ay_add_unit_et:
                if (!mBuildingEt.getText().toString().isEmpty()){
                    intent = new Intent(EditorAddressActivity.this,ChooseAddressActivity.class);
                    intent.putExtra("id",building_id);
                    intent.putExtra("type",5);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                }else {
                    ToastInfo("幢没有选择");
                }

                break;
        }

    }
}
