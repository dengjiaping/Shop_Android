package com.xiaojishop.Android.widget.ClassView.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.Utils.PictureUtil;
import com.xiaojishop.Android.activity.GoodsDetailActivity;
import com.xiaojishop.Android.widget.ClassView.assistant.onCallBackListener;
import com.xiaojishop.Android.widget.ClassView.mode.ProductType;
import com.xiaojishop.Net.Param.GoodsDetail;
import com.xiaojishop.R;
import com.xiaojishop.ShopCar.Goods;
import com.xiaojishop.ShopCar.ShopCar;

import java.util.List;

public class TestSectionedAdapter extends SectionedBaseAdapter {

    List<ProductType> pruductCagests;
    private HolderClickListener mHolderClickListener;
    private Context context;
    private LayoutInflater mInflater;

    private Goods thing = new Goods();


    private onCallBackListener callBackListener;

    public void setCallBackListener(onCallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }


    public TestSectionedAdapter(Context context, List<ProductType> pruductCagests) {
        this.context = context;
        this.pruductCagests = pruductCagests;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public Object getItem(int section, int position) {
        return pruductCagests.get(section).getProduct().get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return pruductCagests.size();
    }

    @Override
    public int getCountForSection(int section) {
        return pruductCagests.get(section).getProduct().size();
    }

    @Override
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.product_item, null);
            viewHolder = new ViewHolder();
            viewHolder.head = (ImageView) convertView.findViewById(R.id.head);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.prise = (TextView) convertView.findViewById(R.id.prise);
            viewHolder.increase = (ImageView) convertView.findViewById(R.id.increase);
            viewHolder.reduce = (ImageView) convertView.findViewById(R.id.reduce);
            viewHolder.shoppingNum = (TextView) convertView.findViewById(R.id.shoppingNum);
            viewHolder.type = (TextView) convertView.findViewById(R.id.type);
            viewHolder.mBg = (LinearLayout)convertView.findViewById(R.id.bg);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(pruductCagests.get(section).getProduct().get(position).getGoods() + " " + pruductCagests.get(section).getProduct().get(position).getType());


        SpannableString msp = new SpannableString(pruductCagests.get(section).getProduct().get(position).getPre_price());
        msp.setSpan(new RelativeSizeSpan(0.8f), pruductCagests.get(section).getProduct().get(position).getPre_price().indexOf(".") + 1, pruductCagests.get(section).getProduct().get(position).getPre_price().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
        SpannableString msp1 = new SpannableString("￥" + pruductCagests.get(section).getProduct().get(position).getPrice() + " ");
        msp1.setSpan(new RelativeSizeSpan(0.8f), 0, pruductCagests.get(section).getProduct().get(position).getPrice().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
        msp1.setSpan(new StrikethroughSpan(), 0, pruductCagests.get(section).getProduct().get(position).getPrice().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp1.setSpan(new ForegroundColorSpan(Color.rgb(0x98, 0x98, 0x98)), 0, pruductCagests.get(section).getProduct().get(position).getPrice().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.prise.setText("");
        viewHolder.prise.append("￥");
        viewHolder.prise.append(msp);
        viewHolder.prise.append("  ");
        viewHolder.prise.append(msp1);
        viewHolder.prise.append(" ");
        
        viewHolder.shoppingNum.setText(String.valueOf(pruductCagests.get(section).getProduct().get(position).getNumber()));
        PictureUtil.Glide(pruductCagests.get(section).getProduct().get(position).getPicture(),viewHolder.head);
        viewHolder.type.setText(pruductCagests.get(section).getProduct().get(position).getUnit());
        if (pruductCagests.get(section).getProduct().get(position).getNumber() == 0) {
            viewHolder.reduce.setVisibility(View.INVISIBLE);
            viewHolder.shoppingNum.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.reduce.setVisibility(View.VISIBLE);
            viewHolder.shoppingNum.setVisibility(View.VISIBLE);
            if (callBackListener != null) {
                callBackListener.updateProduct(pruductCagests.get(section).getProduct().get(position), "1");
            }
        }
        viewHolder.mBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsDetail.type = "1";
                GoodsDetail.goods_id = pruductCagests.get(section).getProduct().get(position).getId();
                Intent intent = new Intent(context, GoodsDetailActivity.class);
                context.startActivity(intent);
            }
        });
        viewHolder.head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsDetail.type = "1";
                GoodsDetail.goods_id = pruductCagests.get(section).getProduct().get(position).getId();
                Intent intent = new Intent(context, GoodsDetailActivity.class);
                context.startActivity(intent);
            }
        });
        viewHolder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = pruductCagests.get(section).getProduct().get(position).getNumber();
                num++;
                if (num >= 1) {
                    ((LinearLayout) (v.getParent())).getChildAt(0).setVisibility(View.VISIBLE);
                    ((LinearLayout) (v.getParent())).getChildAt(1).setVisibility(View.VISIBLE);
                }
                pruductCagests.get(section).getProduct().get(position).setNumber(num);
                viewHolder.shoppingNum.setText(pruductCagests.get(section).getProduct().get(position).getNumber() + "");
                if (mHolderClickListener != null) {
                    int[] start_location = new int[2];
                    viewHolder.shoppingNum.getLocationInWindow(start_location);//获取点击商品图片的位置
                    Drawable drawable = ((ImageView) ((RelativeLayout) (v.getParent()).getParent()).getChildAt(0)).getDrawable();//复制一个新的商品图标
                    //TODO:解决方案，先监听到左边ListView的Item中，然后在开始动画添加
                    mHolderClickListener.onHolderClick(drawable, start_location);
                }

                thing.setId(pruductCagests.get(section).getProduct().get(position).getId());
                thing.setCount("1");
                thing.setImage(pruductCagests.get(section).getProduct().get(position).getPicture());
                thing.setTitle(pruductCagests.get(section).getProduct().get(position).getGoods());
                thing.setSubTitle(pruductCagests.get(section).getProduct().get(position).getType());
                thing.setPrice(pruductCagests.get(section).getProduct().get(position).getPrice());

                ShopCar.add(thing);
                ShopCar.print();

                if (callBackListener != null) {
                    callBackListener.updateProduct(pruductCagests.get(section).getProduct().get(position), "1");
                }
            }
        });

        viewHolder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = pruductCagests.get(section).getProduct().get(position).getNumber();
                if (num > 0) {
                    num--;
                    if (num == 0) {
                        ((LinearLayout) (v.getParent())).getChildAt(0).setVisibility(View.INVISIBLE);
                        ((LinearLayout) (v.getParent())).getChildAt(1).setVisibility(View.INVISIBLE);
                    }
                    pruductCagests.get(section).getProduct().get(position).setNumber(num);
                    viewHolder.shoppingNum.setText(pruductCagests.get(section).getProduct().get(position).getNumber() + "");
                }

                thing.setId(pruductCagests.get(section).getProduct().get(position).getId());
                thing.setCount("-1");
                thing.setImage(pruductCagests.get(section).getProduct().get(position).getPicture());
                thing.setTitle(pruductCagests.get(section).getProduct().get(position).getGoods());
                thing.setSubTitle(pruductCagests.get(section).getProduct().get(position).getType());
                thing.setPrice(pruductCagests.get(section).getProduct().get(position).getPrice());

                ShopCar.add(thing);
                ShopCar.print();

                if (callBackListener != null) {
                    callBackListener.updateProduct(pruductCagests.get(section).getProduct().get(position), "2");
                }
            }
        });

        viewHolder.shoppingNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    int shoppingNum = Integer.parseInt(viewHolder.shoppingNum.getText().toString());
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        /**
         * 商品图片
         */
        public TextView type;
        /**
         * 商品图片
         */
        public ImageView head;
        /**
         * 商品名称
         */
        public TextView name;
        /**
         * 商品价格
         */
        public TextView prise;
        /**
         * 增加
         */
        public ImageView increase;
        /**
         * 商品数目
         */
        public TextView shoppingNum;
        /**
         * 减少
         */
        public ImageView reduce;

        public LinearLayout mBg;
    }

    public void SetOnSetHolderClickListener(HolderClickListener holderClickListener) {
        this.mHolderClickListener = holderClickListener;
    }

    public interface HolderClickListener {
        public void onHolderClick(Drawable drawable, int[] start_location);
    }


    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.header_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        layout.setClickable(false);
        ((TextView) layout.findViewById(R.id.textItem)).setText(pruductCagests.get(section).getType());
        return layout;
    }

}
