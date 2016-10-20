package com.shop.Android.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.king.Base.KingAdapter;
import com.king.KingApplication;
import com.king.Service.Network;
import com.king.Utils.GsonUtil;
import com.king.Utils.LogCat;
import com.king.Utils.SPrefUtil;
import com.king.Utils.UIUtil;
import com.king.View.gradationscroll.GradationScrollView;
import com.king.View.refreshview.XRefreshView;
import com.king.View.refreshview.listener.OnBottomLoadMoreTime;
import com.king.View.refreshview.listener.OnTopRefreshTime;
import com.king.View.refreshview.ui.smileyloadingview.SmileyHeaderView;
import com.shop.Android.DataKey;
import com.shop.Android.SPKey;
import com.shop.Android.activity.ClassActivity;
import com.shop.Android.activity.DisplayActivity;
import com.shop.Android.activity.EachOtherActivity;
import com.shop.Android.activity.GoodsClassActivity;
import com.shop.Android.activity.GoodsDetailActivity;
import com.shop.Android.activity.IntegralActivity;
import com.shop.Android.activity.MainActivity;
import com.shop.Android.activity.MsgActivity;
import com.shop.Android.activity.SearchActivity;
import com.shop.Android.activity.SpecialSellActivity;
import com.shop.Android.activity.UserHelperActivity;
import com.shop.Android.base.BaseFragment;
import com.shop.Android.base.TestAdapter;
import com.shop.Android.widget.MineView;
import com.shop.Android.widget.NoScrollListView;
import com.shop.Android.widget.PlayViewPager;
import com.shop.Android.widget.RefreshView;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.BaseBean;
import com.shop.Net.Bean.IndexBean;
import com.shop.Net.Param.GoodsDetail;
import com.shop.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by admin on 2016/9/9.
 */
public class IndexFragment extends BaseFragment {

    private String TAG = "index";
    private GradationScrollView mGradutionGsv;
    private PlayViewPager mPlayviewPvp;
    private LinearLayout mTitleLl;
    private RecyclerView mRecycleRv;
    private NoScrollListView mListNlv;
    private ImageView mUpIv;
    private ImageView mMsgIv;
    private ImageView mSearchIv;
    private LinearLayout mSpecialLl;

    private XRefreshView mRefreshXrv;
    private ImageView mArrowIv;


    private String[] banUrls;

    @Override
    protected int loadLayout() {
        return R.layout.fragment_index;
    }

    private int height;
    private final int mPinnedTime = 0;

    private IndexBean indexBean;
    private TextView mTitleoneTv;


    public void addData(Object result) {
        SPrefUtil.Function.putData(SPKey.INDEX, GsonUtil.Bean2Str(result));
        indexBean = (IndexBean) result;

        //ban效果
        banUrls = new String[indexBean.getData().getBan().size()];
        for (int i = 0; i < banUrls.length; i++) {
            banUrls[i] = indexBean.getData().getBan().get(i).getImage();
        }
        mPlayviewPvp.setUrls(banUrls);
        for (int i = 0; i < mPlayviewPvp.getmListViews().size(); i++) {
            final int position = i;
            mPlayviewPvp.getmListViews().get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (indexBean.getData().getBan().get(position).getType().equals("1")) {
                        kingData.putData(DataKey.URL, indexBean.getData().getBan().get(position).getUrl());
                        openActivity(DisplayActivity.class);
                    } else {
                        //goods
                        GoodsDetail.goods_id = indexBean.getData().getBan().get(position).getGoods_id();
                        GoodsDetail.type = "1";
                        openActivity(GoodsDetailActivity.class);
                    }
                }
            });
        }
        //四张图片的效果
        for (int i = 0; i < indexBean.getData().getMid().size(); i++) {
            final int position = i;
            switch (indexBean.getData().getMid().get(i).getPosition()) {
                case "2"://左边大图
                    Glide(indexBean.getData().getMid().get(i).getImage(), mPiconeIv);
                    mPiconeIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (indexBean.getData().getMid().get(position).getType().equals("1")) {
                                kingData.putData(DataKey.URL, indexBean.getData().getMid().get(position).getUrl());
                                openActivity(DisplayActivity.class);
                            } else {
                                //goods
                                GoodsDetail.goods_id = indexBean.getData().getMid().get(position).getGoods_id();
                                GoodsDetail.type = "1";
                                openActivity(GoodsDetailActivity.class);
                            }
                        }
                    });
                    break;
                case "3"://右边上图
                    Glide(indexBean.getData().getMid().get(i).getImage(), mPictwoIv);
                    mPictwoIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (indexBean.getData().getMid().get(position).getType().equals("1")) {
                                kingData.putData(DataKey.URL, indexBean.getData().getMid().get(position).getUrl());
                                openActivity(DisplayActivity.class);
                            } else {
                                //goods
                                GoodsDetail.goods_id = indexBean.getData().getMid().get(position).getGoods_id();
                                GoodsDetail.type = "1";
                                openActivity(GoodsDetailActivity.class);
                            }
                        }
                    });
                    break;
                case "4"://右边左底图
                    Glide(indexBean.getData().getMid().get(i).getImage(), mPicthreeIv);
                    mPicthreeIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (indexBean.getData().getMid().get(position).getType().equals("1")) {
                                kingData.putData(DataKey.URL, indexBean.getData().getMid().get(position).getUrl());
                                openActivity(DisplayActivity.class);
                            } else {
                                //goods
                                GoodsDetail.goods_id = indexBean.getData().getMid().get(position).getGoods_id();
                                GoodsDetail.type = "1";
                                openActivity(GoodsDetailActivity.class);
                            }
                        }
                    });
                    break;
                case "5"://右边右底图:
                    Glide(indexBean.getData().getMid().get(i).getImage(), mPicfourIv);
                    mPicfourIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (indexBean.getData().getMid().get(position).getType().equals("1")) {
                                kingData.putData(DataKey.URL, indexBean.getData().getMid().get(position).getUrl());
                                openActivity(DisplayActivity.class);
                            } else {
                                //goods
                                GoodsDetail.goods_id = indexBean.getData().getMid().get(position).getGoods_id();
                                GoodsDetail.type = "1";
                                openActivity(GoodsDetailActivity.class);
                            }
                        }
                    });
                    break;
            }
        }
        //底下的ListView的效果
        if (indexBean.getData().getCgoods() != null && indexBean.getData().getCgoods().size() != 0) {
            if (adapter == null) {
                adapter = new IndexAdapter(indexBean.getData().getCgoods().size(), R.layout.item_index_list, new IndexViewHolder());
                mListNlv.setAdapter(adapter);
            } else {
                adapter.setSize(indexBean.getData().getCgoods().size());
                mListNlv.setAdapter(adapter);
            }
        }
        //今日特卖
        if (indexBean.getData().getActivity() != null && indexBean.getData().getActivity().getGoods() != null && indexBean.getData().getActivity().getGoods().size() != 0) {
            mTitleoneTv.setVisibility(View.INVISIBLE);
            mArrowIv.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(KingApplication.getAppContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRecycleRv.setLayoutManager(layoutManager);
            if (myAdapter == null) {
                myAdapter = new MyAdapter();
                mRecycleRv.setAdapter(myAdapter);
            } else {
                myAdapter.notifyDataSetChanged();
            }
            mRecycleRv.setNestedScrollingEnabled(true);
            mRecycleRv.setVisibility(View.VISIBLE);

        } else {
            mTitleoneTv.setVisibility(View.VISIBLE);
            try {
                mTitleoneTv.setText(indexBean.getData().getActivity().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mArrowIv.setVisibility(View.GONE);
            mRecycleRv.setVisibility(View.GONE);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                has();
            }
        }, 1000);
    }


    @Override
    public void onSuccess(String what, Object result) {
        switch (what) {
            case ActionKey.INDEX:
                //模拟数据加载失败的情况
                mRefreshXrv.stopRefresh();
                mTitleLl.setVisibility(View.VISIBLE);
                if (!SPrefUtil.Function.getData(SPKey.INDEX, "").equals(GsonUtil.Bean2Str(result))) {
                    addData(result);
                } else {
                    LogCat.e("没有数据被更新");
                }
                break;
        }
    }


    @Override
    protected void init() {
        F();
        loading();
        if (SPrefUtil.Function.getData(SPKey.INDEX, "").isEmpty()) {
            Get(ActionKey.INDEX, IndexBean.class);
        } else {
            addData(GsonUtil.Str2Bean(SPrefUtil.Function.getData(SPKey.INDEX, ""), IndexBean.class));
            Get(ActionKey.INDEX, IndexBean.class);
        }
        addTitleSlideChange();

        // 设置是否可以下拉刷新
        mRefreshXrv.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        mRefreshXrv.setPullLoadEnable(false);
        mRefreshXrv.setPinnedTime(mPinnedTime);
        mRefreshXrv.setCustomHeaderView(new RefreshView(mContext));

        mRefreshXrv.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                if (network.getNetworkState() == Network.NETWORN_NONE) {
                    ToastInfo("请检查网络");
                    noWifi();
                    //模拟数据加载失败的情况
                    mRefreshXrv.stopRefresh();
                    mTitleLl.setVisibility(View.VISIBLE);
                    return;
                }
                mTitleLl.setVisibility(View.GONE);
                Get(ActionKey.INDEX, IndexBean.class);

            }

            @Override
            public void onLoadMore(boolean isSilence) {
            }
        });

        setOnClicks(mUpIv, mMsgIv, mSearchIv, mClassMv, mCreditMv, mSpecialLl, mHelpMv, mEachMv);
    }

    private void addTitleSlideChange() {
        ViewTreeObserver vto = mPlayviewPvp.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTitleLl.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = mPlayviewPvp.getHeight();

                mGradutionGsv.setScrollViewListener(new GradationScrollView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(GradationScrollView gradationScrollView, int i, int i1, int i2, int i3) {
                        if (i1 <= 0) {   //设置标题的背景颜色
                            mUpIv.setVisibility(View.GONE);
                            mTitleLl.setBackgroundColor(Color.argb((int) 0, 144, 151, 166));
                        } else if (i1 > 0 && i1 <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                            mUpIv.setVisibility(View.VISIBLE);
                            float scale = (float) i1 / height;
                            float alpha = (255 * scale);
                            if (alpha > 0xE5) {
                                mTitleLl.setBackgroundColor(Color.argb(0xE5, 0xEA, 0x59, 0x3A));
                            } else {
                                mTitleLl.setBackgroundColor(Color.argb((int) alpha, 0xEA, 0x59, 0x3A));
                            }
                        } else {    //滑动到banner下面设置普通颜色
                            mUpIv.setVisibility(View.VISIBLE);
                            mTitleLl.setBackgroundColor(Color.argb(0xE5, 0xEA, 0x59, 0x3A));
                        }
                    }
                });
            }
        });
    }

    private MineView mClassMv;
    private MineView mCreditMv;
    private ImageView mPiconeIv;
    private ImageView mPictwoIv;
    private ImageView mPicthreeIv;
    private ImageView mPicfourIv;
    private MineView mEachMv;
    private MineView mHelpMv;

    @Override
    protected void onClickSet(int i) {
        switch (i) {
            case R.id.ft_index_up_iv:
                mGradutionGsv.smoothScrollTo(0, 0);
                break;
            case R.id.ft_index_msg_iv:
                openActivity(MsgActivity.class);
                break;
            case R.id.ft_index_search_iv:
                openActivity(SearchActivity.class);
                break;
            case R.id.ft_index_class_mv:
                openActivity(ClassActivity.class);
                break;
            case R.id.ft_index_credit_mv:
                openActivity(IntegralActivity.class);
                break;
            case R.id.ft_index_help_mv:
                openActivity(UserHelperActivity.class);
                break;
            case R.id.ft_index_special_ll:
                if (mArrowIv.isShown()) {
                    openActivity(SpecialSellActivity.class);
                }
                break;
            case R.id.ft_index_each_mv:
                openActivity(EachOtherActivity.class);
                break;
        }

    }

    private MyAdapter myAdapter;

    class MyAdapter extends RecyclerView.Adapter<ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_recycle, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            try {
                final IndexBean.DataBean.ActivityBean.GoodsBean bean = indexBean.getData().getActivity().getGoods().get(position);
                Glide(bean.getImage(), holder.mIconIv);
                holder.mNameTv.setText(bean.getTitle());

                SpannableString msp = new SpannableString(bean.getActivity_price());
                msp.setSpan(new RelativeSizeSpan(0.8f), bean.getActivity_price().indexOf(".") + 1, bean.getActivity_price().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
                SpannableString msp1 = new SpannableString("￥" + bean.getPrice() + " ");
                msp1.setSpan(new RelativeSizeSpan(0.8f), 0, bean.getPrice().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
                msp1.setSpan(new StrikethroughSpan(), 0, bean.getPrice().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp1.setSpan(new ForegroundColorSpan(Color.rgb(0x98, 0x98, 0x98)), 0, bean.getPrice().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.mPriceTv.setText("");
                holder.mPriceTv.append("￥");
                holder.mPriceTv.append(msp);
                holder.mPriceTv.append("  ");
                holder.mPriceTv.append(msp1);
                holder.mPriceTv.append(" ");
                holder.mBgLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GoodsDetail.goods_id = bean.getId();
                        GoodsDetail.type = "2";
                        openActivity(GoodsDetailActivity.class);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return indexBean.getData().getActivity().getGoods().size();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mNameTv;
        ImageView mIconIv;
        TextView mPriceTv;
        LinearLayout mBgLl;

        public ViewHolder(View itemView) {
            super(itemView);
            mIconIv = UIUtil.findViewById(itemView, R.id.item_index_icon_iv);
            mNameTv = UIUtil.findViewById(itemView, R.id.item_index_name_tv);
            mPriceTv = UIUtil.findViewById(itemView, R.id.item_index_price_tv);
            mBgLl = UIUtil.findViewById(itemView, R.id.item_index_bg_ll);
        }
    }

    private IndexAdapter adapter;

    class IndexAdapter extends KingAdapter {

        public IndexAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            final IndexBean.DataBean.CgoodsBean bean = indexBean.getData().getCgoods().get(i);
            IndexViewHolder viewHolder = (IndexViewHolder) o;
            try {
                Glide(bean.getImage(), viewHolder.mIconIv);
                viewHolder.mIconIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        kingData.putData(DataKey.TITLE, bean.getName());
                        kingData.putData(DataKey.CATE_ID, bean.getId());
                        kingData.putData(DataKey.IMAGE, bean.getImage());
                        openActivity(GoodsClassActivity.class);
                    }
                });

                Glide(bean.getList().get(0).getImage(), viewHolder.mOneIv);
                viewHolder.mOnenameTv.setText(bean.getList().get(0).getTitle());
                viewHolder.mOnepriceTv.setText("￥" + bean.getList().get(0).getPrice());
                SpannableString msp = new SpannableString(viewHolder.mOnepriceTv.getText().toString());
                msp.setSpan(new RelativeSizeSpan(0.8f), viewHolder.mOnepriceTv.getText().toString().indexOf(".") + 1, viewHolder.mOnepriceTv.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
                viewHolder.mOnepriceTv.setText(msp);
                viewHolder.mOnebgLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GoodsDetail.type = "1";
                        GoodsDetail.goods_id = bean.getList().get(0).getId();
                        openActivity(GoodsDetailActivity.class);
                    }
                });

                Glide(bean.getList().get(1).getImage(), viewHolder.mTwoIv);
                viewHolder.mTwonameTv.setText(bean.getList().get(1).getTitle());
                viewHolder.mTwopriceTv.setText("￥" + bean.getList().get(1).getPrice());
                msp = new SpannableString(viewHolder.mTwopriceTv.getText().toString());
                msp.setSpan(new RelativeSizeSpan(0.8f), viewHolder.mTwopriceTv.getText().toString().indexOf(".") + 1, viewHolder.mTwopriceTv.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
                viewHolder.mTwopriceTv.setText(msp);
                viewHolder.mTwobgLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GoodsDetail.type = "1";
                        GoodsDetail.goods_id = bean.getList().get(1).getId();
                        openActivity(GoodsDetailActivity.class);
                    }
                });

                Glide(bean.getList().get(2).getImage(), viewHolder.mThreeIv);
                viewHolder.mThreenameTv.setText(bean.getList().get(2).getTitle());
                viewHolder.mThreepriceTv.setText("￥" + bean.getList().get(2).getPrice());
                msp = new SpannableString(viewHolder.mThreepriceTv.getText().toString());
                msp.setSpan(new RelativeSizeSpan(0.8f), viewHolder.mThreepriceTv.getText().toString().indexOf(".") + 1, viewHolder.mThreepriceTv.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
                viewHolder.mThreepriceTv.setText(msp);
                viewHolder.mThreebgLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GoodsDetail.type = "1";
                        GoodsDetail.goods_id = bean.getList().get(2).getId();
                        openActivity(GoodsDetailActivity.class);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class IndexViewHolder {
        String TAG = "index";
        ImageView mIconIv;
        ImageView mOneIv;
        TextView mOnenameTv;
        TextView mOnepriceTv;
        ImageView mTwoIv;
        TextView mTwonameTv;
        TextView mTwopriceTv;
        ImageView mThreeIv;
        TextView mThreenameTv;
        TextView mThreepriceTv;
        LinearLayout mOnebgLl;
        LinearLayout mTwobgLl;
        LinearLayout mThreebgLl;
    }

}
