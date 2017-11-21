package com.xiaojishop.Net.Param;

/**
 * Created by admin on 2016/10/14.
 */
public class SubmitEvaluateParam extends Token {
    private String goodsid;
    private String type;
    private String content;

    public SubmitEvaluateParam(String goodsid, String type, String content) {
        this.goodsid = goodsid;
        this.type = type;
        this.content = content;
    }
}
