package com.shop.Android.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.king.Utils.GsonUtil;
import com.shop.Android.Config;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.NoScrollListView;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.ChooseAddressBean;
import com.shop.Net.Param.ChooseAddressParam;
import com.shop.R;

/**
 * Created by admin on 2016/10/9.
 */
public class ChooseAddressActivity extends BaseActvity {
    private String TAG = "choose";
    private NoScrollListView mAddressLv;
    private ChooseAddressAdapter chooseAddressAdapter;
    private int id;
    private int type;
    private ChooseAddressBean chooseAddressBean;

    @Override
    protected int loadLayout() {
        id = getIntent().getIntExtra("id", 0);
        type = getIntent().getIntExtra("type", 1);
        return R.layout.activity_choose_address;
    }

    @Override
    protected void initTitleBar() {
        initTitle("选择地区");
    }

    @Override
    protected void init() {
        F();
        Post(ActionKey.CHOOSE_ADDRESS,new ChooseAddressParam(String.valueOf(id),"02dd2b6cf803dfa77f2dd5cc95e69651"),ChooseAddressBean.class);
        mAddressLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (type){
                    case 1:
                        Config.DATA = GsonUtil.Bean2Str(chooseAddressBean.getData().get(i));
                        Config.TYPE= 1;
                        animFinsh();
                        break;
                }
            }
        });
    }

    @Override
    public void onSuccess(String what, Object result) {
        switch (what){
            case ActionKey.CHOOSE_ADDRESS:
                chooseAddressBean = (ChooseAddressBean) result;
                if (chooseAddressBean.getCode()==200){
                    chooseAddressAdapter = new ChooseAddressAdapter(chooseAddressBean.getData().size(),R.layout.activity_choose_address_item,new ViewHolder());
                    mAddressLv.setAdapter(chooseAddressAdapter);
                }else {
                    ToastInfo(chooseAddressBean.getMsg());
                }
                break;
        }
    }

    @Override
    protected void onClickSet(int i) {

    }

    class ChooseAddressAdapter extends KingAdapter {

        public ChooseAddressAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            ViewHolder viewHolder = (ViewHolder) o;
            viewHolder.name .setText(chooseAddressBean.getData().get(i).getName());
        }
    }

    class ViewHolder {
        String TAG = "choose";
        TextView name;

    }
}
