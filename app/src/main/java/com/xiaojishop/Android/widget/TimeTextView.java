package com.xiaojishop.Android.widget;

/**
 * Created by apple on 16/7/12.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

import com.xiaojishop.R;


/**
 * 自定义倒计时文本控件
 *
 * @author Administrator
 */
public class TimeTextView extends TextView implements Runnable {

    Paint mPaint; //画笔,包含了画几何图形、文本等的样式和颜色信息

    private long[] times;

    private long mhour;
    private long mmin;
    private long msecond;//天，小时，分钟，秒

    private boolean run = false; //是否启动了

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimeTextView);

        array.recycle(); //一定要调用，否则这次的设定会对下次的使用造成影响
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimeTextView);

        array.recycle(); //一定要调用，否则这次的设定会对下次的使用造成影响
    }

    public TimeTextView(Context context) {
        super(context);
    }

    public long[] getTimes() {
        return times;
    }

    public void setTimes(long[] times) {
        this.times = times;
        mhour = times[0];
        mmin = times[1];
        msecond = times[2];

    }

    /**
     * 倒计时计算
     */
    private void ComputeTime() {
        msecond--;
        if (msecond < 0) {
            mmin--;
            msecond = 59;
            if (mmin < 0) {
                mmin = 59;
                mhour--;
                if (mhour < 0) {
                    // 倒计时结束
                    mhour = 59;
                }
            }

        }

    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    @Override
    public void run() {
        //标示已经启动
        run = true;

        ComputeTime();
        String mseconds = "";
        String mmins = "";
        String mhours = "";

        if (msecond < 10) {
            mseconds = "0" + msecond;
        } else {
            mseconds = msecond + "";
        }

        if (mmin < 10) {
            mmins = "0" + mmin;
        } else {
            mmins = mmin + "";
        }

        if (mhour < 10) {
            mhours = "0" + mhour;
        } else {
            mhours = mhour + "";
        }

        String strTime = "<span style='color: #fff;'>" + mhours + "</span>" +
                "<pre>:</pre>" +
                "<span style='color: #fff;'>" + mmins + "</span>" +
                "<pre>:</pre>" +
                "<span style='color: #fff;display:none;'>" + mseconds + "</span><pre>";
        this.setText(Html.fromHtml(strTime));
        postDelayed(this, 1000);

    }

}
