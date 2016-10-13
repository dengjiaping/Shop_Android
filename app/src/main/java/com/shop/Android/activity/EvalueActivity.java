package com.shop.Android.activity;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.king.Base.KingAdapter;
import com.shop.Android.base.BaseActvity;
import com.shop.Android.base.TestAdapter;
import com.shop.Net.ActionKey;
import com.shop.Net.Bean.BaseBean;
import com.shop.Net.Bean.InteractDetailBean;
import com.shop.Net.Param.Comment;
import com.shop.Net.Param.InteractDetail;
import com.shop.R;

/**
 * Created by admin on 2016/9/26.
 */
public class EvalueActivity extends BaseActvity {

    private String TAG = "evalue";
    private ListView mListLv;
    private EditText mContentEt;
    private TextView mSendTv;

    @Override
    protected int loadLayout() {
        Post(ActionKey.INTERACTDETAIL, new InteractDetail(), InteractDetailBean.class);
        return R.layout.activity_evalue;
    }

    private InteractDetailBean bean;

    @Override
    public void onSuccess(String what, Object result) {
        switch (what) {
            case ActionKey.INTERACTDETAIL:
                bean = (InteractDetailBean) result;
                if (bean.getData() == null || bean.getData().size() == 0) {
                    noData();
                } else {
                    if (adapter == null) {
                        adapter = new DetailAdapter(bean.getData().size(), R.layout.item_evalue_list, new DetailViewHolder());
                    } else {
                        adapter.setSize(bean.getData().size());
                    }
                    mListLv.setAdapter(adapter);
                }
                break;
            case ActionKey.INTERACTADD:
                BaseBean baseBean = (BaseBean) result;
                if(baseBean.getCode() == 200){
                    Post(ActionKey.INTERACTDETAIL, new InteractDetail(), InteractDetailBean.class);
                }else if(baseBean.getCode() == 2001){
                    ToastInfo(baseBean.getMsg());
                    openActivity(LoginActivity.class);
                }else {
                    ToastInfo(baseBean.getMsg());
                }
                break;
        }
    }

    private DetailAdapter adapter;


    class DetailViewHolder {
        String TAG = "detail";
        TextView mTimeTv;
        ImageView mPosterIv;
        TextView mNameTv;
        TextView mContentTv;
    }

    class DetailAdapter extends KingAdapter {

        public DetailAdapter(int size, int layoutId, Object viewHolder) {
            super(size, layoutId, viewHolder);
        }

        @Override
        public void padData(int i, Object o) {
            DetailViewHolder viewHolder = (DetailViewHolder) o;
            viewHolder.mTimeTv.setText(bean.getData().get(i).getCreated_time());
            viewHolder.mNameTv.setText(bean.getData().get(i).getNick_name());
            viewHolder.mContentTv.setText(bean.getData().get(i).getContent());
            GlideCircle(bean.getData().get(i).getPoster(),viewHolder.mPosterIv);
        }
    }

    @Override
    protected void initTitleBar() {
        initTitle("评论");
    }

    @Override
    protected void init() {
        F();
        setOnClicks(mSendTv);
    }

    @Override
    protected void onClickSet(int i) {
        switch (i){
            case R.id.ay_evalue_send_tv:
                if(!getText(mContentEt).isEmpty()){
                    Post(ActionKey.INTERACTADD,new Comment(getText(mContentEt)), BaseBean.class);
                }else {
                    ToastInfo("请先输入评论内容");
                }
                break;
        }

    }
}
