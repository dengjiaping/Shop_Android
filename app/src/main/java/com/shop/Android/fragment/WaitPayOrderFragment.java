package com.shop.Android.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.king.Base.KingData;
import com.king.Dialog.CustomDialog;
import com.king.Internet.user_interface.xCallback;
import com.king.Internet.user_method.CallServer;
import com.shop.Android.Config;
import com.shop.Android.DataKey;
import com.shop.Android.activity.LoginActivity;
import com.shop.Android.activity.OrderDetailsActivity;
import com.shop.Android.activity.SubmitOrderActivity;
import com.shop.Android.base.BaseFragment;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.Android.widget.NoScrollListView;
import com.shop.Android.wxapi.WXPayEntryActivity;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.BaseBean;
import com.shop.Net.Bean.OrderBean;
import com.shop.Net.Param.OrderDetailsParam;
import com.shop.Net.Param.OrderWaitPayParam;
import com.shop.R;

import java.util.List;

/**
 * Created by admin on 2016/9/26.
 */
public class WaitPayOrderFragment extends BaseFragment {
    private String TAG = "wait";
    private AnimNoLineRefreshListView mListRv;
    private RelativeLayout mRelayoutRl;
    private WaitPayOrderAdapter waitPayOrderAdapter;
    private GoodsAdapter goodsAdapter;
    private OrderBean orderBean ;
    private List<OrderBean.DataBean.GoodsBean> goodsBean;
    private int page = 0;
    @Override
    protected int loadLayout() {

        return R.layout.fragment_wait_pay_order;

    }

    @Override
    protected void init() {
        F();
        Post(ActionKey.ORDER_INDEX,new OrderWaitPayParam("1",String.valueOf(page)), OrderBean.class);
        mListRv.setListener(new AnimNoLineRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                Post(ActionKey.ORDER_INDEX,new OrderWaitPayParam("1",String.valueOf(page)), OrderBean.class);
            }

            @Override
            public void onLoadMore() {
                page++;
                Post(ActionKey.ORDER_INDEX,new OrderWaitPayParam("1",String.valueOf(page)), OrderBean.class);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        kingData.registerWatcher(Config.ORDER, new KingData.KingCallBack() {
            @Override
            public void onChange() {
                Post(ActionKey.ORDER_INDEX,new OrderWaitPayParam("1",String.valueOf(page)), OrderBean.class);
            }
        });
    }

    @Override
    public void onSuccess(String what, Object result) {
        mListRv.onLoadComplete();
        mListRv.onRefreshComplete();
        switch (what){
            case ActionKey.ORDER_INDEX:
               orderBean = (OrderBean) result;
                if (orderBean.getCode()==200){
                    try {
                        waitPayOrderAdapter = new WaitPayOrderAdapter(orderBean.getData().size(),R.layout.fragment_order_item,new WaitPayViewHolder());
                        mListRv.setAdapter(waitPayOrderAdapter);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }else {
                    ToastInfo(orderBean.getMsg());
                }
                break;
            case ActionKey.CANCEL_ORDER:
               BaseBean bean = (BaseBean) result;
                if (bean.getCode()==200){
                    ToastInfo("取消成功");
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

    }

    class WaitPayOrderAdapter extends KingAdapter{

        public WaitPayOrderAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);

        }

        @Override
        public void padData(int i, Object o) {
            WaitPayViewHolder viewHolder = (WaitPayViewHolder) o;
            final OrderBean.DataBean bean = orderBean.getData().get(i);
            try {
                goodsBean = orderBean.getData().get(i).getGoods();
                goodsAdapter = new GoodsAdapter(goodsBean.size(),R.layout.item_order_goods,new GoodsViewHolder());
            }catch (Exception ex){
                ex.printStackTrace();
            }

            viewHolder.mListSv.setAdapter(goodsAdapter);
            viewHolder.mNameTv.setText(bean.getShop_name());
            viewHolder.mTotalTv.setText("￥"+bean.getTotal_price());
            viewHolder.mFeeTv.setText("配送费: ￥"+bean.getExpenses());
            viewHolder.mNumTv.setText("共 "+goodsBean.size()+"件");
            switch (orderBean.getData().get(i).getStatus()){
                case 1:
                    viewHolder.mTypeTv.setText("未付款");
                    viewHolder.mDelTv.setText("取消订单");
                    break;
                case 2:
                    viewHolder.mTypeTv.setText("货到付款");
                    viewHolder.mTimeTv.setVisibility(View.GONE);
                    break;
            }
            viewHolder.mPayTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    kingData.putData(DataKey.ID,bean.getId());
                    kingData.putData(DataKey.PRICE,bean.getTotal_price());
                    kingData.putData(DataKey.TIME,bean.getEnd_time());
                    kingData.sendBroadCast("ZZREFRESHPAY");
                openActivity(WXPayEntryActivity.class);
                }
            });
           viewHolder.mDelTv.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   final CustomDialog.Builder ibuilder = new CustomDialog.Builder(mContext);
                   ibuilder.setTitle("取消订单");
                   ibuilder.setMessage("你确定要取消订单吗？");
                   ibuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           Post(ActionKey.CANCEL_ORDER,new OrderDetailsParam(bean.getId()),BaseBean.class);
                           dissLoadingDialog();
                           kingData.sendBroadCast(Config.ORDER);
                       }
                   });
                   ibuilder.setNegativeButton("取消", null);
                   ibuilder.create().show();
               }
           });
            viewHolder.mListSv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    kingData.putData(DataKey.ORDER,bean.getId());
                    kingData.sendBroadCast(Config.ORDER_ID);
                    openActivity(OrderDetailsActivity.class);

                }
            });

        }
    }
    class WaitPayViewHolder{
        String TAG = "order";
        NoScrollListView mListSv;
        TextView mNameTv;
        TextView mTimeTv;
        TextView mTypeTv;
        TextView mTotalTv;
        TextView mNumTv;
        TextView mFeeTv;
        TextView mPayTv;
        TextView mDelTv;


    }
    class GoodsAdapter extends KingAdapter{

        public GoodsAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            GoodsViewHolder viewHolder = (GoodsViewHolder) o;
            viewHolder.mNameTv.setText(goodsBean.get(i).getSubtitle());
            viewHolder.mNumTv.setText("x"+goodsBean.get(i).getNumber());
            viewHolder.mPriceTv.setText("￥"+goodsBean.get(i).getPrice());
            viewHolder.mWeightTv.setText(goodsBean.get(i).getTitle());
            Glide(goodsBean.get(i).getImage(),viewHolder.mImgIv);
        }
    }
    class GoodsViewHolder{
        String TAG = "goods";
        ImageView mImgIv;
        TextView mNameTv;
        TextView mWeightTv;
        TextView mPriceTv;
        TextView mNumTv;
    }
}
