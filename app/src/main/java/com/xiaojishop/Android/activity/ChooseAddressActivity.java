package com.xiaojishop.Android.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.king.Utils.GsonUtil;
import com.xiaojishop.Android.Config;
import com.xiaojishop.Android.base.BaseActvity;
import com.xiaojishop.Android.widget.NoScrollListView;
import com.xiaojishop.Net.ActionKey;
import com.xiaojishop.Net.Bean.ChooseAddressBean;
import com.xiaojishop.Net.Param.ChooseAddressParam;
import com.xiaojishop.R;

/**
 * Created by admin on 2016/10/9.
 */
public class ChooseAddressActivity extends BaseActvity {
    private String TAG = "choose";
    private NoScrollListView mAddressLv;
    private ChooseAddressAdapter chooseAddressAdapter;
    private String id;
    private int type;
    private ChooseAddressBean chooseAddressBean;

    @Override
    protected int loadLayout() {
        id = getIntent().getStringExtra("id");
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
        switch (type){
            case 1:
                Post(ActionKey.CHOOSE_ADDRESS,new ChooseAddressParam(id),ChooseAddressBean.class);
                break;
            case 2:
                Post(ActionKey.CHOOSE_ADDRESS,new ChooseAddressParam(id),ChooseAddressBean.class);
                break;
            case 3:
                Post(ActionKey.CHOOSE_ADDRESS,new ChooseAddressParam(id),ChooseAddressBean.class);
                break;
            case 4:
                Post(ActionKey.CHOOSE_ADDRESS,new ChooseAddressParam(id),ChooseAddressBean.class);
                break;
            case 5:
                Post(ActionKey.CHOOSE_ADDRESS,new ChooseAddressParam(id),ChooseAddressBean.class);
                break;
            case 6:
                Post(ActionKey.CHOOSE_ADDRESS,new ChooseAddressParam(id),ChooseAddressBean.class);
                break;
            case 7:
                Post(ActionKey.CHOOSE_ADDRESS,new ChooseAddressParam(id),ChooseAddressBean.class);
                break;

        }

        mAddressLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (type){
                    case 1:
                        Config.DATA = GsonUtil.Bean2Str(chooseAddressBean.getData().get(i));
                        Config.TYPE= 1;
                        animFinsh();
                        break;
                    case 2:
                        Config.DATA = GsonUtil.Bean2Str(chooseAddressBean.getData().get(i));
                        Config.TYPE= 2;
                        animFinsh();
                        break;
                    case 3:
                        Config.DATA = GsonUtil.Bean2Str(chooseAddressBean.getData().get(i));
                        Config.TYPE= 3;
                        animFinsh();
                        break;
                    case 4:
                        Config.DATA = GsonUtil.Bean2Str(chooseAddressBean.getData().get(i));
                        Config.TYPE= 4;
                        animFinsh();
                        break;
                    case 5:
                        Config.DATA = GsonUtil.Bean2Str(chooseAddressBean.getData().get(i));
                        Config.TYPE= 5;
                        animFinsh();
                        break;
                    case 6:
                        Config.DATA = GsonUtil.Bean2Str(chooseAddressBean.getData().get(i));
                        Config.TYPE= 6;
                        animFinsh();
                        break;
                    case 7:
                        Config.DATA = GsonUtil.Bean2Str(chooseAddressBean.getData().get(i));
                        Config.TYPE= 7;
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
                }else if (chooseAddressBean.getCode()==2001){
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
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
            viewHolder.mAreaTv .setText(chooseAddressBean.getData().get(i).getName());
        }
    }

    class ViewHolder {
        String TAG = "choose";
        TextView mAreaTv;

    }
}
