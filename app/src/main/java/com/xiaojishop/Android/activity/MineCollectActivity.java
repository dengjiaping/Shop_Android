package com.xiaojishop.Android.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.king.Base.KingData;
import com.king.View.slidelistview.KingSlideAdapter;
import com.king.View.slidelistview.SlideListView;
import com.king.View.slidelistview.wrap.SlideItemWrapLayout;
import com.xiaojishop.Android.Config;
import com.xiaojishop.Android.base.BaseActvity;
import com.xiaojishop.Android.widget.AnimNoLineRefreshListView;
import com.xiaojishop.Net.ActionKey;
import com.xiaojishop.Net.Bean.BaseBean;
import com.xiaojishop.Net.Bean.CollectBean;
import com.xiaojishop.Net.Param.CancelCollectParam;
import com.xiaojishop.Net.Param.GoodsDetail;
import com.xiaojishop.Net.Param.Token;
import com.xiaojishop.R;

/**
 * Created by admin on 2016/9/20.
 */
public class MineCollectActivity extends BaseActvity {
    private String TAG = "collect";
    private AnimNoLineRefreshListView mListRv;
    private MineCollectAdapter mineCollectAdapter;
    private CollectBean collectBean;

    @Override
    protected int loadLayout() {
        return R.layout.activity_mine_collect;
    }

    @Override
    protected void initTitleBar() {
        initTitle("我的收藏");
        mTitleBgRl.setBackgroundColor(Color(R.color.my_color));
        mTitleLeftIv.setImageResource(R.mipmap.back);
    }

    @Override
    protected void init() {
        F();
        Post(ActionKey.COLLECT_INDEX, new Token(), CollectBean.class);
        kingData.registerWatcher(Config.COLLECT, new KingData.KingCallBack() {
            @Override
            public void onChange() {
                Post(ActionKey.COLLECT_INDEX, new Token(), CollectBean.class);
            }
        });
        mListRv.setPullLoadEnable(false);
        mListRv.setListener(new AnimNoLineRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                Post(ActionKey.COLLECT_INDEX, new Token(), CollectBean.class);
            }

            @Override
            public void onLoadMore() {

            }
        });
        mListRv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 GoodsDetail.type = "1";
                 GoodsDetail.goods_id = collectBean.getData().get(i).getGoods_id();
                 openActivity(GoodsDetailActivity.class);
            }
        });


    }

    @Override
    public void onSuccess(String what, Object result) {
        mListRv.onRefreshComplete();
        switch (what) {
            case ActionKey.COLLECT_INDEX:
                collectBean = (CollectBean) result;
                if (collectBean.getCode() == 200) {
                    mineCollectAdapter = new MineCollectAdapter(collectBean.getData().size(), R.layout.activity_mine_collect_item, R.layout.slide_right_collect, new CollectViewHolder());
                    mListRv.setAdapter(mineCollectAdapter);
                } else if (collectBean.getCode() == 2001) {
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                } else {
                    ToastInfo(collectBean.getMsg());
                }

                break;
            case ActionKey.CANCEL_COLLECT:
                BaseBean bean = (BaseBean) result;
                if (bean.getCode()==200){
                    ToastInfo("取消收藏成功");
                    kingData.sendBroadCast(Config.COLLECT);
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

    class MineCollectAdapter extends KingSlideAdapter {


        public MineCollectAdapter(int size, int layout, int rightLayout, Object viewHolder) {
            super(size, layout, 0, rightLayout, viewHolder);
        }

        @Override
        public void padData(final int i, SlideItemWrapLayout slideItemWrapLayout, Object o) {
            CollectViewHolder viewHolder = (CollectViewHolder) o;
            viewHolder.mNameTv.setText(collectBean.getData().get(i).getTitle()+collectBean.getData().get(i).getSubtitled());
            viewHolder.mNumTv.setText(collectBean.getData().get(i).getSubtitled());
            Glide(collectBean.getData().get(i).getImage(), viewHolder.mPhotoIv);

            slideItemWrapLayout.getRightBackView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    kingData.sendBroadCast(Config.COLLECT);
                    Post(ActionKey.CANCEL_COLLECT, new CancelCollectParam(collectBean.getData().get(i).getGoods_id()), CollectBean.class);
                }
            });

        }

        @Override
        public SlideListView.SlideMode padMode(int i) {
            return SlideListView.SlideMode.RIGHT;
        }
    }

    class CollectViewHolder {
        String TAG = "collect";
        ImageView mPhotoIv;
        TextView mNameTv;
        TextView mNumTv;
    }
}
