package com.shop.Android.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Base.KingActivity;
import com.king.FlycoTableLayout.SlidingTabLayout;
import com.king.Utils.GsonUtil;
import com.shop.Android.fragment.StorageFragment;
import com.shop.Android.fragment.TimeSpecialFragment;
import com.shop.Android.widget.ClassView.adapter.ShopAdapter;
import com.shop.Android.widget.ClassView.mode.ShopProduct;
import com.shop.R;
import com.shop.ShopCar.Goods;
import com.shop.ShopCar.TMShopCar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/9/26.
 */
public class SpecialSellActivity extends KingActivity {


    private String TAG = "special";
    private SlidingTabLayout mTabStl;
    private ViewPager mContentVp;

    public static LinearLayout mCarLl;
    private TextView mDefaultTv;
    private FrameLayout mCardFl;
    private LinearLayout mCardshopLl;
    private View mBgV;
    private ListView mShoplistLv;
    private TextView mOrderTv;

    public static TextView mRedTv;
    public static RelativeLayout mBgRl;


    @Override
    protected int loadLayout() {
        return R.layout.activity_special;
    }

    @Override
    protected void initTitleBar() {
        initTitle("今日特卖");
        mTitleBgRl.setBackgroundColor(Color(R.color.my_color));
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    private String[] titles = new String[]{
            "时间特卖", "库存特卖"
    };

    @Override
    public void onBackPressed() {
        if(mCardFl.isShown()){
            mCardFl.setVisibility(View.GONE);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TMShopCar.getNum() > 0) {
            mRedTv.setVisibility(View.VISIBLE);
        } else {
            mRedTv.setVisibility(View.GONE);
        }
        mRedTv.setText(TMShopCar.getNum() + "");

    }

    @Override
    protected void init() {
        F();
        mContentVp.setAdapter(new MyFragmentPagerAdapter(fragmentManager));
        mTabStl.setViewPager(mContentVp, titles);
        setOnClicks(mCarLl, mBgV, mOrderTv);

    }

    private ShopAdapter shopAdapter;
    /**
     * 保存购物车对象到List
     * TODO:考虑保存购物车缓存
     */
    private List<ShopProduct> productList;

    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ay_special_car_ll:
                if (TMShopCar.getMap().size() == 0) {
                    mDefaultTv.setVisibility(View.VISIBLE);
                } else {
                    mDefaultTv.setVisibility(View.GONE);
                    productList = new ArrayList<>();
                    for (String key : TMShopCar.getKeys()) {
                        ShopProduct shopProduct = new ShopProduct();
                        shopProduct.setId(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getId());
                        shopProduct.setGoods(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getTitle());
                        shopProduct.setPrice(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getPrice());
                        shopProduct.setPicture(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getImage());
                        shopProduct.setType(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getSubTitle());
                        shopProduct.setNumber(Integer.parseInt(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getCount()));
                        productList.add(shopProduct);
                    }
                    shopAdapter = new ShopAdapter(mContext, productList);
                    mShoplistLv.setAdapter(shopAdapter);
                }
                if (mCardFl.getVisibility() == View.GONE) {
                    mCardFl.setVisibility(View.VISIBLE);

                    // 加载动画
                    Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.push_bottom_in);
                    // 动画开始
                    mCardshopLl.setVisibility(View.VISIBLE);
                    mCardshopLl.startAnimation(animation);
                    mBgV.setVisibility(View.VISIBLE);

                } else {
                    mCardFl.setVisibility(View.GONE);
                    mBgV.setVisibility(View.GONE);
                    mCardshopLl.setVisibility(View.GONE);
                }
                break;
            case R.id.ay_special_add_tv:
                productList = new ArrayList<>();
                for (String key : TMShopCar.getKeys()) {
                    ShopProduct shopProduct = new ShopProduct();
                    shopProduct.setId(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getId());
                    shopProduct.setGoods(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getTitle());
                    shopProduct.setPrice(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getPrice());
                    shopProduct.setPicture(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getImage());
                    shopProduct.setType(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getSubTitle());
                    shopProduct.setNumber(Integer.parseInt(((Goods) GsonUtil.Str2Bean(TMShopCar.getMap().get(key), Goods.class)).getCount()));
                    productList.add(shopProduct);
                }
                shopAdapter = new ShopAdapter(mContext, productList);
                mShoplistLv.setAdapter(shopAdapter);

                if (TMShopCar.getMap().size() != 0) {
                    mDefaultTv.setVisibility(View.GONE);
                }
                break;

            case R.id.ay_special_bg_v:
                mCardFl.setVisibility(View.GONE);
                mBgV.setVisibility(View.GONE);
                mCardshopLl.setVisibility(View.GONE);
                break;
            case R.id.ay_special_order_tv:
                if (TMShopCar.getNum() == 0) {
                    ToastInfo("您还未选择任何商品");
                    return;
                }
                SubmitOrderActivity.TYPE = 0;
                openActivity(SubmitOrderActivity.class);
                break;
        }

    }


    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new TimeSpecialFragment();
            } else {
                return new StorageFragment();
            }
        }

    }


}
