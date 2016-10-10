package com.shop.Android.fragment;

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
import com.king.KingApplication;
import com.king.Utils.FUtil;
import com.king.Utils.GsonUtil;
import com.king.Utils.LogCat;
import com.king.View.slidelistview.KingSlideAdapter;
import com.king.View.slidelistview.SlideBaseAdapter;
import com.king.View.slidelistview.SlideListView;
import com.king.View.slidelistview.wrap.SlideItemWrapLayout;
import com.shop.Android.BaseApplication;
import com.shop.Android.base.BaseFragment;
import com.shop.Android.base.TestAdapter;
import com.shop.Android.base.TestTwoAdapter;
import com.shop.Android.widget.AnimRefreshListView;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.BaseBean;
import com.shop.Net.Param.CarSave;
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
        kingData.registerWatcher("CAR", new KingData.KingCallBack() {
            @Override
            public void onChange() {
                if (ShopCar.getMap().size() > 0) {
                    if (adapter == null) {
                        adapter = new CarAdapter(ShopCar.getMap().size() + 1, new CarViewHolder());
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
                adapter = new CarAdapter(ShopCar.getMap().size() + 1, new CarViewHolder());
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
                Post(ActionKey.CARSAVE, new CarSave(ShopCar.commit()), BaseBean.class);
                break;
        }
    }

    @Override
    public void onSuccess(String what, Object result) {
        switch (what) {
            case ActionKey.CARSAVE:
                BaseBean baseBean = (BaseBean) result;
                
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

    }

    class CarAdapter extends SlideBaseAdapter {
        private int size;
        private Object viewHolder;

        public void setSize(int size) {
            this.size = size;
        }

        public CarAdapter(int size, Object viewHolder) {
            super(CarFragment.this.mContext);
            this.size = size;
            this.viewHolder = viewHolder;
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
            if (view == null) {
                view = createConvertView(i);
                FUtil.findViewInItem(viewHolder, view);
                view.setTag(viewHolder);
            } else {
                viewHolder = view.getTag();
            }

            try {
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
                        Goods goods = GsonUtil.Str2Bean(ShopCar.getMap().get(ShopCar.getKeys().get(i - 1)), Goods.class);
                        ShopCar.isNotice = false;
                        goods.setCount("1");
                        ShopCar.add(goods);
                        ShopCar.isNotice = true;
                        mPriceTv.setText("  ￥" + ShopCar.allPrice());
                        ((TextView) ((LinearLayout) ((view).getParent())).getChildAt(1)).setText(Integer.valueOf(((TextView) ((LinearLayout) ((view).getParent())).getChildAt(1)).getText().toString()) + 1 + "");
                    }
                });

                ((CarViewHolder) (viewHolder)).mMinusTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                                mListLv.setAdapter(adapter);
                            }
                        }
                        mPriceTv.setText("  ￥" + ShopCar.allPrice());
                        ShopCar.print();
                    }
                });

                ((CarViewHolder) (viewHolder)).mTickIv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
            //数据填充
            return view;
        }
    }


}
