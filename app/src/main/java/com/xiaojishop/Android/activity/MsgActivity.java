package com.xiaojishop.Android.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.xiaojishop.Android.base.BaseActvity;
import com.xiaojishop.Android.widget.AnimNoLineRefreshListView;
import com.xiaojishop.Net.ActionKey;
import com.xiaojishop.Net.Bean.MessageBean;
import com.xiaojishop.Net.Param.Token;
import com.xiaojishop.R;

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
        Post(ActionKey.MESSAGE_INDEX, new Token(), MessageBean.class);
        mContentRlv.setListener(new AnimNoLineRefreshListView.onListener() {
            @Override
            public void onRefresh() {
                Post(ActionKey.MESSAGE_INDEX, new Token(), MessageBean.class);
            }

            @Override
            public void onLoadMore() {

            }
        });
        mContentRlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext,MessageContentActivity.class);
                intent.putExtra("id",messageBean.getData().get(i).getId());
                startActivity(intent);
                overridePendingTransition(com.king.R.anim.in_from_right, com.king.R.anim.out_to_left);
            }
        });
    }

    @Override
    public void onSuccess(String what, Object result) {
        mContentRlv.onRefreshComplete();
        switch (what) {
            case ActionKey.MESSAGE_INDEX:
                messageBean = (MessageBean) result;
                if (messageBean.getCode() == 200) {
                    messageAdapter = new MessageAdapter(messageBean.getData().size(), R.layout.item_ay_msg, new MessageViewHolder());
                    mContentRlv.setAdapter(messageAdapter);
                } else if (messageBean.getCode()==2001){
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                }else {
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
