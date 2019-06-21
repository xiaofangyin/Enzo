package com.enzo.commonlib.widget.banner.normal;

import java.io.Serializable;

/**
 * 文 件 名: BannerBean
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/15
 * 邮   箱: xiaofangyinwork@163.com
 */
public class BannerBean implements Serializable {
    /**
     * id : 1
     * link : http://weibo.com
     * pic : https://ww4.sinaimg.cn/thumb180/0069kQGBgy1fs5z3skd6tj31kw2tfu12.jpg
     * title : 轮播1
     * type : 1
     */

    private String id;
    private String link;
    private String pic;
    private String title;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
