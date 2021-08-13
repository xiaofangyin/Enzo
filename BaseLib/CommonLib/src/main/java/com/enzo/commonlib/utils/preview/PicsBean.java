package com.enzo.commonlib.utils.preview;

import java.io.Serializable;

/**
 * 文 件 名: PicsBean
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/7/13
 * 邮   箱: xiaofangyinwork@163.com
 */
public class PicsBean implements Serializable {
    /**
     * original : https://xinrui-shianxia-client.oss-cn-qingdao.aliyuncs.com/back/2018-07-12/10-13-19-5b46b93ff2ed6.jpg
     * small : https://xinrui-shianxia-client.oss-cn-qingdao.aliyuncs.com/back/2018-07-12/10-13-19-5b46b93ff2ed6.jpg?x-oss-process=image/resize,h_420,w_640
     */

    private String original;
    private String small;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }
}
