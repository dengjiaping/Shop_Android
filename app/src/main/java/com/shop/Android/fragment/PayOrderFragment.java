package com.shop.Android.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.shop.Android.activity.LoginActivity;
import com.shop.Android.base.BaseFragment;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.Android.widget.NoScrollListView;
import com.shop.Android.widget.RefreshListView;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.OrderBean;
import com.shop.Net.Param.OrderWaitPayParam;
import com.shop.R;

import java.util.List;

/**
 * Created by admin on 2016/9/26.
 */
public class PayOrderFragment extends BaseFragment {
    private String TAG = "pay";
    private AnimNoLineRefreshListView mListRv;
    private RelativeLayout mRelayoutRl;
    private PayOrderAdapter payOrderAdapter;
    private PayGoodsAdapter payGoodsAdapter;
    private int page = 0;
    private OrderBean orderBean;
    private List<OrderBean.DataBean.GoodsBean> goodBean;

    @Override
    protected int loadLayout() {
        return R.layout.fragment_pay_order;
    }

    @Override
    protected void init() {
        F();
        Post(ActionKey.ORDER_INDEX,new OrderWaitPayParam("2",String.valueOf(page)), OrderBean.class);
        mListRv.setListener(new AnimNoLineRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                Post(ActionKey.ORDER_INDEX,new OrderWaitPayParam("2",String.valueOf(page)), OrderBean.class);
            }

            @Override
            public void onLoadMore() {
                page++;
                Post(ActionKey.ORDER_INDEX,new OrderWaitPayParam("2",String.valueOf(page)), OrderBean.class);
            }
        });
    }

    @Override
    public void onSuccess(String what, Object result) {
        mListRv.onRefreshComplete();
        mListRv.onLoadComplete();
        switch (what){
            case ActionKey.ORDER_INDEX:
                orderBean = (OrderBean) result;
                if (orderBean.getCode()==200){
                    payOrderAdapter = new PayOrderAdapter(orderBean.getData().size(),R.layout.fragment_order_item,new PayViewHolder());
                    mListRv.setAdapter(payOrderAdapter);
                }else if (orderBean.getCode()==2001){
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                }else {
                    ToastInfo(orderBean.getMsg());
                }
                break;
        }
    }

    @Override
    protected void onClickSet(int i) {

    }

    class PayOrderAdapter extends KingAdapter {

        public PayOrderAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            PayViewHolder viewHolder = (PayViewHolder) o;
            goodBean = orderBean.getData().get(i).getGoods();
            payGoodsAdapter = new PayGoodsAdapter(goodBean.size(),R.layout.item_order_goods,new PayGoodsViewHolder());
            viewHolder.mListSv.setAdapter(payGoodsAdapter);
            viewHolder.mNameTv.setText(orderBean.getData().get(i).getShop_name());
            viewHolder.mTotalTv.setText("￥"+orderBean.getData().get(i).getTotal_price());
            viewHolder.mFeeTv.setText("配送费: ￥"+orderBean.getData().get(i).getExpenses());
            viewHolder.mNumTv.setText("共 "+goodBean.size()+"件");
            switch (orderBean.getData().get(i).getStatus()){
                case 3:
                    viewHolder.mTypeTv.setText("送货中");
                    break;
                case 4:
                    viewHolder.mTypeTv.setText("未送货");
                    break;
            }
            viewHolder.mTimeTv.setVisibility(View.GONE);
        }
    }

    class PayViewHolder{
        String TAG = "order";
        NoScrollListView mListSv;
        TextView mNameTv;
        TextView mTimeTv;
        TextView mTypeTv;
        TextView mTotalTv;
        TextView mNumTv;
        TextView mFeeTv;
    }
    class PayGoodsAdapter extends KingAdapter{

        public PayGoodsAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);

        }

        @Override
        public void padData(int i, Object o) {
            PayGoodsViewHolder viewHolder = (PayGoodsViewHolder) o;
            viewHolder.mNameTv.setText(goodBean.get(i).getSubtitle());
            viewHolder.mNumTv.setText("x"+goodBean.get(i).getNumber());
            viewHolder.mPriceTv.setText("￥"+goodBean.get(i).getPrice());
            viewHolder.mWeightTv.setText(goodBean.get(i).getTitle());
            Glide(goodBean.get(i).getImage(),viewHolder.mImgIv);
        }
    }
    class PayGoodsViewHolder{
        String TAG = "goods";
        ImageView mImgIv;
        TextView mNameTv;
        TextView mWeightTv;
        TextView mPriceTv;
        TextView mNumTv;
    }
}
