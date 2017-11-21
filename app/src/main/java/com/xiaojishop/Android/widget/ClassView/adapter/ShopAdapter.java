package com.xiaojishop.Android.widget.ClassView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.king.Utils.GsonUtil;
import com.xiaojishop.Android.DataKey;
import com.xiaojishop.Android.base.BaseActvity;
import com.xiaojishop.Android.widget.ClassView.assistant.ShopToDetailListener;
import com.xiaojishop.Android.widget.ClassView.mode.ShopProduct;
import com.xiaojishop.R;
import com.xiaojishop.ShopCar.Goods;
import com.xiaojishop.ShopCar.TMShopCar;

import java.util.List;

/**
 * Created by caobo on 2016/7/20.
 * 购物车适配器
 */

public class ShopAdapter extends BaseAdapter {

    private ShopToDetailListener shopToDetailListener;
    private double systemtime = 0;

    public void setShopToDetailListener(ShopToDetailListener callBackListener) {
        this.shopToDetailListener = callBackListener;
    }

    private List<ShopProduct> shopProducts;
    private LayoutInflater mInflater;

    public ShopAdapter(Context context, List<ShopProduct> shopProducts) {
        this.shopProducts = shopProducts;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return shopProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return shopProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.trade_widget, null);
            viewHolder = new ViewHolder();
            viewHolder.commodityName = (TextView) convertView.findViewById(R.id.commodityName);
            viewHolder.commodityPrise = (TextView) convertView.findViewById(R.id.commodityPrise);
            viewHolder.commodityNum = (TextView) convertView.findViewById(R.id.commodityNum);
            viewHolder.increase = (ImageView) convertView.findViewById(R.id.increase);
            viewHolder.reduce = (ImageView) convertView.findViewById(R.id.reduce);
            viewHolder.shoppingNum = (TextView) convertView.findViewById(R.id.shoppingNum);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.commodityName.setText(shopProducts.get(position).getGoods());
        viewHolder.commodityPrise.setText(shopProducts.get(position).getPrice());
        viewHolder.commodityNum.setText(1 + "");
        viewHolder.shoppingNum.setText(shopProducts.get(position).getNumber() + "");

        viewHolder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(System.currentTimeMillis() - SystemTime > 500) {
                    int num = shopProducts.get(position).getNumber();
                    num++;
                    BaseActvity.listener.putData(DataKey.NUM, 1 + "");
                    shopProducts.get(position).setNumber(num);
                    viewHolder.shoppingNum.setText(shopProducts.get(position).getNumber() + "");

                    Goods goods = GsonUtil.Str2Bean(TMShopCar.getMap().get(shopProducts.get(position).getId()), Goods.class);
                    TMShopCar.isNotice = false;
                    goods.setCount("1");
                    TMShopCar.add(goods);
                    TMShopCar.isNotice = true;
                    BaseActvity.listener.sendBroadCast("NUM");
                }else {
                    SystemTime = System.currentTimeMillis();
                }



            }
        });

        viewHolder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(System.currentTimeMillis() - SystemTime > 500){
                    int num = shopProducts.get(position).getNumber();
                    if (num > 0) {
                        num--;
                        BaseActvity.listener.putData(DataKey.NUM, -1 + "");
                        if (num == 0) {
                            shopProducts.get(position).setNumber(num);
                            BaseActvity.listener.sendBroadCast("REDUCE");
                        } else {
                            shopProducts.get(position).setNumber(num);
                            viewHolder.shoppingNum.setText(shopProducts.get(position).getNumber() + "");
                        }
                        Goods goods = GsonUtil.Str2Bean(TMShopCar.getMap().get(shopProducts.get(position).getId()), Goods.class);
                        TMShopCar.isNotice = false;
                        goods.setCount("-1");
                        TMShopCar.add(goods);
                        TMShopCar.isNotice = true;

                        BaseActvity.listener.sendBroadCast("NUM");
                    }
                }else {
                    SystemTime = System.currentTimeMillis();
                }

            }
        });

        return convertView;
    }
    private double SystemTime = 0;

    class ViewHolder {
        /**
         * 购物车商品名称
         */
        public TextView commodityName;
        /**
         * 购物车商品价格
         */
        public TextView commodityPrise;
        /**
         * 购物车商品数量
         */
        public TextView commodityNum;
        /**
         * 增加
         */
        public ImageView increase;
        /**
         * 减少
         */
        public ImageView reduce;
        /**
         * 商品数目
         */
        public TextView shoppingNum;
    }
}
