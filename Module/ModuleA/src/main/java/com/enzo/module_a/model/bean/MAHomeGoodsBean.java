package com.enzo.module_a.model.bean;

import com.enzo.module_a.ui.adapter.MAHomeAdapter;

import java.io.Serializable;

/**
 * 文 件 名: MAPhotoListBean
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/13
 * 邮   箱: xiaofywork@163.com
 */
public class MAHomeGoodsBean extends MAHomeBaseBean implements Serializable {

    /**
     * id : 0
     * author : Alejandro Escamilla
     * width : 5616
     * height : 3744
     * url : https://unsplash.com/photos/yC-Yzbqy7PY
     * download_url : https://picsum.photos/id/0/5616/3744
     */

    private String id;
    private String author;
    private int width;
    private int height;
    private String url;
    private String download_url;

    public MAHomeGoodsBean() {
        super(MAHomeAdapter.TYPE_GOODS);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }
}
