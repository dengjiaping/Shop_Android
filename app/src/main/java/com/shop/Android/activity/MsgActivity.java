package com.shop.Android.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.base.TestAdapter;
import com.shop.Android.widget.AnimNoLineRefreshListView;
import com.shop.Android.widget.RefreshListView;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.MessageBean;
import com.shop.Net.Param.MessageParam;
import com.shop.R;

/**
 * Created by admin on 2016/9/12.
 */
public class MsgActivity extends BaseActvity {

    private String TAG = "msg";
    private AnimNoLineRefreshListView mContentRlv;
    private MessageAdapter messageAdapter;
    private MessageBean messageBean;

    @Override
    protected int loadLayout() {
        return R.layout.activity_msg;
    }

    @Override
    protected void initTitleBar() {
        initTitle("消息中心");
        mTitleLeftIv.setImageResource(R.mipmap.back);
        mTitleBgRl.setBackgroundColor(Color(R.color.my_color));
    }

    @Override
    protected void init() {
        F();
        mContentRlv.setPullLoadEnable(false);
        Post(ActionKey.MESSAGE_INDEX, new MessageParam("02dd2b6cf803dfa77f2dd5cc95e69651"), MessageBean.class);
        mContentRlv.setListener(new AnimNoLineRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                Post(ActionKey.MESSAGE_INDEX, new MessageParam("02dd2b6cf803dfa77f2dd5cc95e69651"), MessageBean.class);
            }

            @Override
            public void onLoadMore() {

            }
        });
        mContentRlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToastInfo("123");
                Intent intent = new Intent(mContext,MessageContentActivity.class);
                intent.putExtra("id",messageBean.getData().get(i).getId());
                startActivity(intent);
                overridePendingTransition(com.king.R.anim.in_from_right, com.king.R.anim.out_to_left);
            }
        });
    }

    @Override
    public void onSuccess(String what, Object result) {
        switch (what) {
            case ActionKey.MESSAGE_INDEX:
                messageBean = (MessageBean) result;
                if (messageBean.getCode() == 200) {
                    messageAdapter = new MessageAdapter(messageBean.getData().size(), R.layout.item_ay_msg, new MessageViewHolder());
                    mContentRlv.setAdapter(messageAdapter);
                } else {
                    ToastInfo(messageBean.getMsg());
                }
                break;
        }
    }

    @Override
    protected void onClickSet(int i) {

    }

    class MessageAdapter extends KingAdapter {


        public MessageAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            MessageViewHolder viewHolder = (MessageViewHolder) o;
            viewHolder.mDesTv.setText(messageBean.getData().get(i).getTitle());
            viewHolder.mTimeTv.setText(messageBean.getData().get(i).getCreated_time());
        }
    }

    class MessageViewHolder {
        String TAG = "msg";
        TextView mDesTv;
        TextView mTimeTv;

    }
}
