package com.xiaojishop.Android.activity;

import android.widget.TextView;

import com.xiaojishop.Android.base.BaseActvity;
import com.xiaojishop.Net.ActionKey;
import com.xiaojishop.Net.Bean.MessageContentBean;
import com.xiaojishop.Net.Param.MessageContentParam;
import com.xiaojishop.R;

/**
 * Created by admin on 2016/10/10.
 */
public class MessageContentActivity extends BaseActvity {
    private String TAG = "message";
    private TextView mTitleTv;
    private TextView mTimeTv;
    private TextView mContentTv;
    private String id;
    private MessageContentBean messageContentBean;
    @Override
    protected int loadLayout() {
        id = getIntent().getStringExtra("id");
        return R.layout.activity_content_message;
    }

    @Override
    protected void initTitleBar() {
        initTitle("消息中心详情");
    }

    @Override
    protected void init() {
        F();
        Post(ActionKey.MESSAGE_CONTENT,new MessageContentParam(id), MessageContentBean.class);
    }

    @Override
    public void onSuccess(String what, Object result) {
        switch (what){
            case ActionKey.MESSAGE_CONTENT:
                messageContentBean = (MessageContentBean) result;
                if (messageContentBean.getCode()==200){
                    mTitleTv.setText(messageContentBean.getData().getTitle());
                    mTimeTv.setText(messageContentBean.getData().getCreated_time());
                    mContentTv.setText(messageContentBean.getData().getContent());
                }else if (messageContentBean.getCode()==2001){
                    ToastInfo("请登录");
                    openActivity(LoginActivity.class);
                }else {
                    ToastInfo(messageContentBean.getMsg());
                }
                break;
        }
    }

    @Override
    protected void onClickSet(int i) {

    }
}
