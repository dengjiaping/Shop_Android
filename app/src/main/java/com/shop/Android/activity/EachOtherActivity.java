package com.shop.Android.activity;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.king.Utils.GsonUtil;
import com.king.Utils.SPrefUtil;
import com.king.View.slidelistview.KingSlideAdapter;
import com.king.View.slidelistview.SlideListView;
import com.king.View.slidelistview.wrap.SlideItemWrapLayout;
import com.shop.Android.DataKey;
import com.shop.Android.SPKey;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.Android.widget.AnimRefreshListView;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.EachOtherBean;
import com.shop.Net.Bean.IndexBean;
import com.shop.Net.Param.InteractDetail;
import com.shop.R;

/**
 * Created by admin on 2016/9/26.
 */
public class EachOtherActivity extends BaseActvity {

    private String TAG = "eachother";
    private AnimNoLineRefreshListView mListLv;

    @Override
    protected int loadLayout() {
        Get(ActionKey.INTERACTINDEX, EachOtherBean.class);
        return R.layout.activity_eachother;
    }

    @Override
    protected void initTitleBar() {
        initTitle("小方互动");
    }

    private EachOtherBean eachOtherBean;

    @Override
    public void onSuccess(String what, Object result) {
        switch (what){
            case ActionKey.INTERACTINDEX:
                mListLv.onRefreshComplete();
                eachOtherBean = (EachOtherBean) result;
                if(eachOtherBean.getCode() == 200){
                    if(eachOtherBean.getData() == null || eachOtherBean.getData().size() == 0){
                        noData();
                    }else {
                        if (adapter == null) {
                            adapter = new EachAdapter(eachOtherBean.getData().size(), R.layout.item_eachother_list, new EachViewHolder());
                            mListLv.setAdapter(adapter);
                        } else {
                            adapter.setSize(eachOtherBean.getData().size());
                            mListLv.setAdapter(adapter);
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void init() {
        F();
        mListLv.setPullLoadEnable(false);
        mListLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InteractDetail.interact_id = eachOtherBean.getData().get(i).getId();
                kingData.putData(DataKey.CONTENT,eachOtherBean.getData().get(i).getContent());
                kingData.putData(DataKey.TIME,eachOtherBean.getData().get(i).getCreated_time());
                kingData.putData(DataKey.TITLE,eachOtherBean.getData().get(i).getTitle());
                openActivity(EvalueActivity.class);
            }
        });

        mListLv.setListener(new AnimNoLineRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                Get(ActionKey.INTERACTINDEX, EachOtherBean.class);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListLv.onLoadComplete();
                    }
                },1000);
            }
        });
    }

    @Override
    protected void onClickSet(int i) {

    }

    private EachAdapter adapter;

    class EachViewHolder {
        String TAG = "inteact";
        TextView mNameTv;
        TextView mTitleTv;
        TextView mContentTv;
        TextView mNumTv;
        TextView mTimeTv;

    }

    class EachAdapter extends KingSlideAdapter {

        public EachAdapter(int size, int layout, Object viewHolder) {
            super(size, layout, 0, 0, viewHolder);
        }

        @Override
        public void padData(int i, SlideItemWrapLayout slideItemWrapLayout, Object o) {
            EachViewHolder viewHolder = (EachViewHolder) o;
            viewHolder.mTitleTv.setText(eachOtherBean.getData().get(i).getTitle());
            viewHolder.mContentTv.setText(eachOtherBean.getData().get(i).getContent());
            viewHolder.mTimeTv.setText(eachOtherBean.getData().get(i).getCreated_time());
            viewHolder.mNumTv.setText(eachOtherBean.getData().get(i).getComment_num());
            viewHolder.mNameTv.setText(((IndexBean) GsonUtil.Str2Bean(SPrefUtil.Function.getData(SPKey.INDEX,""),IndexBean.class)).getData().getShop().getName());
        }

        @Override
        public SlideListView.SlideMode padMode(int i) {
            return SlideListView.SlideMode.NONE;
        }
    }
}
