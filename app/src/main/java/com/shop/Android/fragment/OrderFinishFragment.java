package com.shop.Android.fragment;

import android.content.DialogInterface;
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
import com.shop.Android.activity.MainActivity;
import com.shop.Android.activity.OrderDetailsActivity;
import com.shop.Android.base.BaseFragment;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.Android.widget.NoScrollListView;
import com.shop.Android.widget.RefreshListView;
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
public class OrderFinishFragment extends BaseFragment {
    private String TAG = "finish";
    private AnimNoLineRefreshListView mListRv;
    private RelativeLayout mRelayoutRl;
    private FinishOrderAdapter finishOrderAdapter;
    private FinishGoodsAdapter finishGoodsAdapter;
    private OrderBean orderBean;
    private List<OrderBean.DataBean.GoodsBean> goodsBeen;
    private boolean isFirst = true;
    @Override
    protected int loadLayout() {
        kingData.registerWatcher(Config.FINISH_ORDER, new KingData.KingCallBack() {
            @Override
            public void onChange() {
                CallServer.Post(ActionKey.ORDER_INDEX+"DATA",ActionKey.ORDER_INDEX, new OrderWaitPayParam("3"), OrderBean.class,OrderFinishFragment.this);
            }
        });
        return R.layout.fragment_finish_order;
    }

    @Override
    protected void init() {
        F();
        mListRv.setPullLoadEnable(false);
        mListRv.setListener(new AnimNoLineRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                CallServer.Post(ActionKey.ORDER_INDEX+"REFRESH",ActionKey.ORDER_INDEX, new OrderWaitPayParam("3"), OrderBean.class,OrderFinishFragment.this);
            }

            @Override
            public void onLoadMore() {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirst){
            Post(ActionKey.ORDER_INDEX, new OrderWaitPayParam("3"), OrderBean.class);
            isFirst=false;
        }else {
        }

    }



    @Override
    public void onSuccess(String what, Object result) {
        mListRv.onLoadComplete();
        mListRv.onRefreshComplete();
        switch (what) {
            case ActionKey.ORDER_INDEX:
                orderBean = (OrderBean) result;
                    if (orderBean.getCode() == 200) {
                        if (orderBean.getData() == null || orderBean.getData().size()==0) {
                            mRelayoutRl.setVisibility(View.VISIBLE);
                        } else {
                            mRelayoutRl.setVisibility(View.GONE);
                            try {
                                finishOrderAdapter = new FinishOrderAdapter(orderBean.getData().size(), R.layout.fragment_order_item, new FinishViewHolder());
                                mListRv.setAdapter(finishOrderAdapter);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }
                    }else {
                        ToastInfo(orderBean.getMsg());
                    }
                break;
            case ActionKey.ORDER_INDEX+"DATA":
                orderBean = (OrderBean) result;
                if (orderBean.getCode() == 200) {
                    if (orderBean.getData() == null || orderBean.getData().size()==0) {
                        mRelayoutRl.setVisibility(View.VISIBLE);
                    } else {
                        mRelayoutRl.setVisibility(View.GONE);
                        try {
                            finishOrderAdapter = new FinishOrderAdapter(orderBean.getData().size(), R.layout.fragment_order_item, new FinishViewHolder());
                            mListRv.setAdapter(finishOrderAdapter);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }else if (2001==orderBean.getCode()){
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                }else {
                    ToastInfo(orderBean.getMsg());

                }
                break;
            case ActionKey.ORDER_INDEX+"REFRESH":
                orderBean = (OrderBean) result;
                if (orderBean.getCode() == 200) {
                    if (orderBean.getData() == null || orderBean.getData().size()==0) {
                        mRelayoutRl.setVisibility(View.VISIBLE);
                    } else {
                        mRelayoutRl.setVisibility(View.GONE);
                        try {
                            finishOrderAdapter = new FinishOrderAdapter(orderBean.getData().size(), R.layout.fragment_order_item, new FinishViewHolder());
                            mListRv.setAdapter(finishOrderAdapter);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }else if (2001==orderBean.getCode()){
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

    class FinishOrderAdapter extends KingAdapter {

        public FinishOrderAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            FinishViewHolder viewHolder = (FinishViewHolder) o;
           final OrderBean.DataBean bean= orderBean.getData().get(i);
            goodsBeen = orderBean.getData().get(i).getGoods();
            finishGoodsAdapter = new FinishGoodsAdapter(goodsBeen.size(), R.layout.item_order_goods, new FinishGoodsViewHolder());
            viewHolder.mListSv.setAdapter(finishGoodsAdapter);
            viewHolder.mNameTv.setText(bean.getShop_name());
            viewHolder.mTotalTv.setText("￥" + bean.getTotal_price());
            viewHolder.mFeeTv.setText("配送费: ￥" + bean.getExpenses());
            viewHolder.mNumTv.setText("共 " + goodsBeen.size() + "件");
            viewHolder.mListSv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    kingData.putData(DataKey.ORDER, bean.getId());
                    kingData.sendBroadCast(Config.ORDER_ID);
                    openActivity(OrderDetailsActivity.class);
                }
            });
            switch (orderBean.getData().get(i).getStatus()) {
                case 7://已评价
                    viewHolder.mTypeTv.setText("已完成");
                    viewHolder.mDelTv.setVisibility(View.VISIBLE);
                    viewHolder.mPayTv.setVisibility(View.GONE);
                    viewHolder.mDelTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final CustomDialog.Builder ibuilder = new CustomDialog.Builder(mActivity);
                            ibuilder.setTitle("删除订单");
                            ibuilder.setMessage("你确定要删除订单吗？");
                            ibuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(final DialogInterface dialogInterface, final int i) {
                                    CallServer.Post(ActionKey.DEL_ORDER, ActionKey.DEL_ORDER, new OrderDetailsParam(bean.getId()), BaseBean.class, new xCallback() {
                                        @Override
                                        public void onSuccess(String s, Object o) {
                                            mListRv.onLoadComplete();
                                            mListRv.onRefreshComplete();
                                            BaseBean baseBean = (BaseBean) o;
                                            if (baseBean.getCode() == 200) {
                                                dialogInterface.dismiss();
                                                ToastInfo("删除成功 ");
                                                kingData.sendBroadCast(Config.FINISH_ORDER);
                                            } else if (baseBean.getCode() == 2001) {
                                                ToastInfo("请登录");
                                                openActivity(LoginActivity.class);
                                            } else {
                                                ToastInfo(baseBean.getMsg());
                                            }

                                        }

                                        @Override
                                        public void onFinished(String s) {
                                            mListRv.onLoadComplete();
                                            mListRv.onRefreshComplete();
                                        }

                                        @Override
                                        public void onError(String s) {
                                            mListRv.onLoadComplete();
                                            mListRv.onRefreshComplete();
                                        }

                                        @Override
                                        public void onCancelled(String s) {

                                        }

                                        @Override
                                        public void onStart(String s) {

                                        }
                                    });

                                }
                            });
                            ibuilder.setNegativeButton("取消", null);
                            ibuilder.create().show();
                        }
                    });
                    break;
                case 5://未评价
                    viewHolder.mTypeTv.setText("已完成");
                    viewHolder.mPayTv.setText("商品评价");
                    viewHolder.mDelTv.setVisibility(View.GONE);
                    viewHolder.mPayTv.setVisibility(View.VISIBLE);
                    viewHolder.mPayTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    break;
            }
            viewHolder.mTimeTv.setVisibility(View.GONE);
        }
    }

    class FinishViewHolder {
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

    class FinishGoodsAdapter extends KingAdapter {

        public FinishGoodsAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            FinishGoodsViewHolder viewHolder = (FinishGoodsViewHolder) o;
            try {
                viewHolder.mNameTv.setText(goodsBeen.get(i).getTitle());
                viewHolder.mNumTv.setText("x" + goodsBeen.get(i).getNumber());
                viewHolder.mPriceTv.setText("￥" + goodsBeen.get(i).getPrice());
                viewHolder.mWeightTv.setText(goodsBeen.get(i).getSubtitle());
                Glide(goodsBeen.get(i).getImage(), viewHolder.mImgIv);
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
    }

    class FinishGoodsViewHolder {
        String TAG = "goods";
        ImageView mImgIv;
        TextView mNameTv;
        TextView mWeightTv;
        TextView mPriceTv;
        TextView mNumTv;
    }
}
