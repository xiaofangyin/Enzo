package com.ifenglian.commonlib.widget.danmu;

import java.io.Serializable;

/**
 * 文 件 名: FLDanMuBean
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/9/25
 * 邮   箱: xiaofy@ifenglian.com
 */
public class FLDanMuBean implements Serializable {

    /**
     * {
     * "avatar" : "http://netcore-test-221.oss-cn-hangzhou.aliyuncs.com/avatar/2017/04/19/201704191328453911504993741494574487.jpg",
     * "content" : "这个是发布的弹幕",
     * "isme" : "0"
     * }
     */

    private String avatar;
    private String content;
    private String isme;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsme() {
        return isme;
    }

    public void setIsme(String isme) {
        this.isme = isme;
    }
}
