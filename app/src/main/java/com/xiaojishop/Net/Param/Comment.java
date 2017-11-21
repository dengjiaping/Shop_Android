package com.xiaojishop.Net.Param;

/**
 * Created by admin on 2016/10/11.
 */
public class Comment extends Token {
    private String content;
    private String interact_id = InteractDetail.interact_id;

    public Comment(String content) {
        this.content = content;
    }
}
