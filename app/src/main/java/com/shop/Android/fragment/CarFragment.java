package com.shop.Android.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.king.Base.KingData;
import com.king.Dialog.CustomDialog;
import com.king.Internet.user_method.CallServer;
import com.king.KingApplication;
import com.king.Utils.DialogUtil;
import com.king.Utils.FUtil;
import com.king.Utils.GsonUtil;
import com.king.Utils.LogCat;
import com.king.Utils.SPrefUtil;
import com.king.Utils.UIUtil;
import com.king.View.slidelistview.KingSlideAdapter;
import com.king.View.slidelistview.SlideBaseAdapter;
import com.king.View.slidelistview.SlideListView;
import com.king.View.slidelistview.wrap.SlideItemWrapLayout;
import com.shop.Android.BaseApplication;
import com.shop.Android.DataKey;
import com.shop.Android.SPKey;
import com.shop.Android.activity.LoginActivity;
import com.shop.Android.activity.SubmitOrderActivity;
import com.shop.Android.base.BaseFragment;
import com.shop.Android.base.TestAdapter;
import com.shop.Android.base.TestTwoAdapter;
import com.shop.Android.widget.AnimRefreshListView;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.BaseBean;
import com.shop.Net.Bean.IndexBean;
import com.shop.Net.Bean.ShopCarBean;
import com.shop.Net.Param.CarSave;
import com.shop.Net.Param.Ids;
import com.shop.Net.Param.Token;
import com.shop.R;
import com.shop.ShopCar.Goods;
import com.shop.ShopCar.ShopCar;

import java.util.HashMap;

/**
 * Created by admin on 2016/9/9.
 */
public class CarFragment extends BaseFragment {

    private String TAG = "car";
    private AnimRefreshListView mListLv;
    private TextView mPriceTv;
    private TextView mOrderTv;


    @Override
    protected int loadLayout() {
        return R.layout.fragment_car;
    }

    @Override
    protected void initTitleBar() {
        initTitle("购物车");
        mTitleBgRl.setBackgroundColor(Color(R.color.my_color));
    }


    @Override
    protected void init() {
        F();
        kingData.registerWatcher("REFRESHCAR", new KingData.KingCallBack() {
            @Override
            public void onChange() {
                Post(ActionKey.CAREQUA, new Token(), ShopCarBean.class);
            }
        });
        Post(ActionKey.CAREQUA, new Token(), ShopCarBean.class);
        kingData.registerWatcher("CAR", new KingData.KingCallBack() {
            @Override
            public void onChange() {
                if (ShopCar.getMap().size() > 0) {
                    if (adapter == null) {
                        adapter = new CarAdapter(ShopCar.getMap().size() + 1);
                        mListLv.setAdapter(adapter);
                    } else {
                        adapter.setSize(ShopCar.getMap().size() + 1);
                        mListLv.setAdapter(adapter);
                    }
                    mPriceTv.setText("  ￥" + ShopCar.allPrice());
                } else {
                    //// TODO: 2016/10/10 购物车为空的操作
                }
            }
        });
        if (ShopCar.getMap().size() > 0) {
            if (adapter == null) {
                adapter = new CarAdapter(ShopCar.getMap().size() + 1);
                mListLv.setAdapter(adapter);
            } else {
                adapter.setSize(ShopCar.getMap().size() + 1);
                mListLv.setAdapter(adapter);
            }
        } else {
            //// TODO: 2016/10/10 购物车为空的操作
        }
        mPriceTv.setText("  ￥" + ShopCar.allPrice());

        mListLv.setPullLoadEnable(false);
        mListLv.setListener(new AnimRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListLv.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListLv.onLoadComplete();
                    }
                }, 1000);
            }
        });
        setOnClicks(mOrderTv);
    }

    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ft_car_order_tv:
                CallServer.Post("CARQUA", ActionKey.CAREQUA, new Token(), ShopCarBean.class, this);
                break;
        }
    }

    private Goods thing = new Goods();
    private boolean isAdd = false;

    @Override
    public void onSuccess(String what, Object result) {
        switch (what) {
            case ActionKey.CARSAVE:
                BaseBean baseBean = (BaseBean) result;
                if (baseBean.getCode() == 2001) {
                    ToastInfo(baseBean.getMsg());
                    openActivity(LoginActivity.class);
                    kingData.putData(DataKey.LOGIN, 1);
                }else if(baseBean.getCode() == 200){
                    SubmitOrderActivity.TYPE = 1;
                    openActivity(SubmitOrderActivity.class);
                }
                break;
            case "CARQUA":
                //比对成功
                ShopCarBean shopCarBean = (ShopCarBean) result;
                if (shopCarBean.getCode() == 200) {
                    if (shopCarBean.getData() != null && shopCarBean.getData().size() != 0) {
                        for (int i = 0; i < shopCarBean.getData().size(); i++) {
                            thing.setId(shopCarBean.getData().get(i).getId());
                            thing.setImage(shopCarBean.getData().get(i).getImage());
                            thing.setCount(shopCarBean.getData().get(i).getStock());
                            thing.setTitle(shopCarBean.getData().get(i).getTitle());
                            thing.setSubTitle(shopCarBean.getData().get(i).getSubtitled());
                            thing.setPrice(shopCarBean.getData().get(i).getPrice());
                            String msg = "";
                            switch (shopCarBean.getData().get(i).getErr()) {
                                case 2://库存不足
                                    msg = msg + thing.getTitle() + "库存不足,已调整到最大库存;";
                                    thing.setCount(shopCarBean.getData().get(i).getNewcount() + "");
                                    thing.setMaxNum(shopCarBean.getData().get(i).getNewcount() + "");
                                    break;
                                case 3://价格变动
                                    msg = msg + thing.getTitle() + "价格变动,已做相应调整;";
                                    thing.setPrice(shopCarBean.getData().get(i).getNewprice() + "");
                                    break;
                                case 4://库存不足价格变动
                                    msg = msg + thing.getTitle() + "库存不足,价格变动,已调整到最大库存,价格也做相应调整;";
                                    thing.setCount(shopCarBean.getData().get(i).getNewcount() + "");
                                    thing.setPrice(shopCarBean.getData().get(i).getNewprice() + "");
                                    thing.setMaxNum(shopCarBean.getData().get(i).getNewcount() + "");
                                    break;
                                case 5://下架删除  不会出现
                                    break;
                            }
                            if (!msg.isEmpty()) {
                                isAdd = true;
                                DialogUtil.getDialog("提示", msg, "知道了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                            }
                            ShopCar.mergeButNotAdd(thing, isAdd);
                            isAdd = false;
                        }
                    }
                }
                kingData.sendBroadCast("CAR");
                Post(ActionKey.CLEARCAR, new Token(), BaseBean.class);
                break;
            case ActionKey.CLEARCAR:
                baseBean = (BaseBean) result;
                if (baseBean.getCode() == 2001) {
                    ToastInfo(baseBean.getMsg());
                    openActivity(LoginActivity.class);
                } else {
                    Post(ActionKey.CARSAVE, new CarSave(ShopCar.commit()), BaseBean.class);
                }
                break;
            case ActionKey.CAREQUA:
                shopCarBean = (ShopCarBean) result;
                if (shopCarBean.getCode() == 200) {
                    if (shopCarBean.getData() != null && shopCarBean.getData().size() != 0) {
                        for (int i = 0; i < shopCarBean.getData().size(); i++) {
                            thing.setId(shopCarBean.getData().get(i).getId());
                            thing.setImage(shopCarBean.getData().get(i).getImage());
                            thing.setCount(shopCarBean.getData().get(i).getStock());
                            thing.setTitle(shopCarBean.getData().get(i).getTitle());
                            thing.setSubTitle(shopCarBean.getData().get(i).getSubtitled());
                            thing.setPrice(shopCarBean.getData().get(i).getPrice());
                            String msg = "";
                            switch (shopCarBean.getData().get(i).getErr()) {
                                case 2://库存不足
                                    msg = msg + thing.getTitle() + "库存不足,已调整到最大库存;";
                                    thing.setCount(shopCarBean.getData().get(i).getNewcount() + "");
                                    thing.setMaxNum(shopCarBean.getData().get(i).getNewcount() + "");
                                    break;
                                case 3://价格变动
                                    msg = msg + thing.getTitle() + "价格变动,已做相应调整;";
                                    thing.setPrice(shopCarBean.getData().get(i).getNewprice() + "");
                                    break;
                                case 4://库存不足价格变动
                                    msg = msg + thing.getTitle() + "库存不足,价格变动,已调整到最大库存,价格也做相应调整;";
                                    thing.setCount(shopCarBean.getData().get(i).getNewcount() + "");
                                    thing.setPrice(shopCarBean.getData().get(i).getNewprice() + "");
                                    thing.setMaxNum(shopCarBean.getData().get(i).getNewcount() + "");
                                    break;
                                case 5://下架删除  不会出现
                                    break;
                            }
                            if (!msg.isEmpty()) {
                                DialogUtil.getDialog("提示", msg, "知道了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                            }
                            ShopCar.add(thing);
                        }
                    }
                }
                kingData.sendBroadCast("CAR");
                break;
        }
    }

    private CarAdapter adapter;

    class CarViewHolder {
        String TAG = "car";
        ImageView mIconIv;
        TextView mNameTv;
        TextView mWeightTv;
        TextView mPriceTv;
        TextView mNumTv;
        CheckBox mTickIv;
        View mMinusTv;
        View mAddTv;
        TextView mShopTv;
    }

    private boolean isCheckedRefresh = false;

    class CarAdapter extends SlideBaseAdapter {
        private int size;


        public void setSize(int size) {
            this.size = size;
        }

        public CarAdapter(int size) {
            super(CarFragment.this.mContext);
            this.size = size;
        }

        public SlideListView.SlideMode getSlideModeInPosition(int position) {
            if (position == 0) {
                return SlideListView.SlideMode.NONE;
            }
            return SlideListView.SlideMode.RIGHT;
        }

        public int getFrontViewId(int position) {
            if (position == 0) {
                return R.layout.item_car_title_list;
            }
            return R.layout.item_car_list;
        }

        public int getLeftBackViewId(int position) {
            return 0;
        }

        public int getRightBackViewId(int position) {
            return R.layout.slide_right_delete;
        }

        public int getCount() {
            return size;
        }

        public Object getItem(int i) {
            return Integer.valueOf(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(final int i, View view, ViewGroup viewGroup) {
            CarViewHolder viewHolder;
            if (view == null) {
                viewHolder = new CarViewHolder();
                view = createConvertView(i);
                viewHolder.mTickIv = UIUtil.findViewById(view, R.id.item_car_tick_iv);
                try {
                    if (i == 0) {
                        viewHolder.mShopTv = UIUtil.findViewById(view, R.id.item_car_shop_tv);
                    } else {
                        ((CarViewHolder) (viewHolder)).mNameTv = UIUtil.findViewById(view, R.id.item_car_name_tv);
                        ((CarViewHolder) (viewHolder)).mIconIv = UIUtil.findViewById(view, R.id.item_car_icon_iv);
                        ((CarViewHolder) (viewHolder)).mWeightTv = UIUtil.findViewById(view, R.id.item_car_weight_tv);
                        ((CarViewHolder) (viewHolder)).mPriceTv = UIUtil.findViewById(view, R.id.item_car_price_tv);
                        ((CarViewHolder) (viewHolder)).mNumTv = UIUtil.findViewById(view, R.id.item_car_num_tv);
                        ((CarViewHolder) (viewHolder)).mMinusTv = UIUtil.findViewById(view, R.id.item_car_minus_tv);
                        ((CarViewHolder) (viewHolder)).mAddTv = UIUtil.findViewById(view, R.id.item_car_add_tv);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                view.setTag(viewHolder);
            } else {
                viewHolder = (CarViewHolder) view.getTag();
            }
            if (i == 0) {
                ((CarViewHolder) (viewHolder)).mTickIv.setChecked(ShopCar.isValidCar());
                viewHolder.mShopTv.setText(((IndexBean) GsonUtil.Str2Bean(SPrefUtil.Function.getData(SPKey.INDEX, ""), IndexBean.class)).getData().getShop().getName());
            } else {
                ((CarViewHolder) (viewHolder)).mTickIv.setChecked(((Goods) GsonUtil.Str2Bean(ShopCar.getMap().get(ShopCar.getKeys().get(i - 1)), Goods.class)).isValid());
            }
            try {

                if (isCheckedRefresh) {
                    isCheckedRefresh = false;
                    return view;
                }
                Glide(((Goods) GsonUtil.Str2Bean(ShopCar.getMap().get(ShopCar.getKeys().get(i - 1)), Goods.class)).getImage(), ((CarViewHolder) (viewHolder)).mIconIv);
                ((CarViewHolder) (viewHolder)).mNameTv.setText(((Goods) GsonUtil.Str2Bean(ShopCar.getMap().get(ShopCar.getKeys().get(i - 1)), Goods.class)).getTitle());
                ((CarViewHolder) (viewHolder)).mWeightTv.setText(((Goods) GsonUtil.Str2Bean(ShopCar.getMap().get(ShopCar.getKeys().get(i - 1)), Goods.class)).getSubTitle());
                ((CarViewHolder) (viewHolder)).mNumTv.setText(((Goods) GsonUtil.Str2Bean(ShopCar.getMap().get(ShopCar.getKeys().get(i - 1)), Goods.class)).getCount());
                ((CarViewHolder) (viewHolder)).mPriceTv.setText("￥" + ((Goods) GsonUtil.Str2Bean(ShopCar.getMap().get(ShopCar.getKeys().get(i - 1)), Goods.class)).getPrice());

                SpannableString msp = new SpannableString(((CarViewHolder) (viewHolder)).mPriceTv.getText().toString());
                msp.setSpan(new RelativeSizeSpan(0.8f), ((CarViewHolder) (viewHolder)).mPriceTv.getText().toString().indexOf(".") + 1, ((CarViewHolder) (viewHolder)).mPriceTv.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
                ((CarViewHolder) (viewHolder)).mPriceTv.setText(msp);


                ((CarViewHolder) (viewHolder)).mAddTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Goods goods = GsonUtil.Str2Bean(ShopCar.getMap().get(ShopCar.getKeys().get(i - 1)), Goods.class);
                            ShopCar.isNotice = false;
                            goods.setCount("1");
                            ShopCar.add(goods);
                            ShopCar.isNotice = true;
                            mPriceTv.setText("  ￥" + ShopCar.allPrice());
                            ((TextView) ((LinearLayout) ((view).getParent())).getChildAt(1)).setText(Integer.valueOf(((TextView) ((LinearLayout) ((view).getParent())).getChildAt(1)).getText().toString()) + 1 + "");
                        } catch (Exception e) {
                            mListLv.setAdapter(adapter);
                        }

                    }
                });

                ((CarViewHolder) (viewHolder)).mMinusTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Goods goods = GsonUtil.Str2Bean(ShopCar.getMap().get(ShopCar.getKeys().get(i - 1)), Goods.class);
                            goods.setCount("-1");
                            ShopCar.isNotice = false;
                            ShopCar.add(goods);
                            ShopCar.isNotice = true;
                            ((TextView) ((LinearLayout) ((view).getParent())).getChildAt(1)).setText(Integer.valueOf(((TextView) ((LinearLayout) ((view).getParent())).getChildAt(1)).getText().toString()) - 1 + "");
                            if (Integer.valueOf(((TextView) ((LinearLayout) ((view).getParent())).getChildAt(1)).getText().toString()) == 0) {
                                adapter.setSize(ShopCar.getMap().size() + 1);
                                if (ShopCar.getMap().size() == 0) {
                                    mListLv.setAdapter(null);
                                    //// TODO: 2016/10/10 购物车没有数据
                                } else {
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            mPriceTv.setText("  ￥" + ShopCar.allPrice());
                            ShopCar.print();
                        } catch (Exception e) {
                            adapter.notifyDataSetChanged();
                        }

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            ((CarViewHolder) (viewHolder)).mTickIv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    isCheckedRefresh = true;
                    temp = ShopCar.isInValid();
                    if (i == 0) {
                        if (b) {
                            ShopCar.Valid();
                        } else {
                            ShopCar.Invalid();
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Goods thing = GsonUtil.Str2Bean(ShopCar.getMap().get(ShopCar.getKeys().get(i - 1)), Goods.class);
                        thing.setValid(b);
                        ShopCar.isNotice = false;
                        ShopCar.merge(thing, true);
                        ShopCar.isNotice = true;
                        if (ShopCar.isInValid() == ShopCar.getMap().size()) {
                            ShopCar.setIsValidCar(true);
                            adapter.notifyDataSetChanged();
                        } else if (temp != 0 && ShopCar.isInValid() == 0) {
                            ShopCar.setIsValidCar(false);
                            adapter.notifyDataSetChanged();
                        }
                    }

                }
            });
            //数据填充
            return view;
        }
    }

    private int temp = 0;

}
