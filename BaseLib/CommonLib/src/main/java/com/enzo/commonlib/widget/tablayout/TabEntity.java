package com.enzo.commonlib.widget.tablayout;

/**
 * 文 件 名: TabEntity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/5/25
 * 邮   箱: xiaofangyinwork@163.com
 */
public class TabEntity {

    private String title;
    private int normalColor;
    private int selectedColor;
    private int normalImage;
    private int selectedImage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNormalColor() {
        return normalColor;
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public int getNormalImage() {
        return normalImage;
    }

    public void setNormalImage(int normalImage) {
        this.normalImage = normalImage;
    }

    public int getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(int selectedImage) {
        this.selectedImage = selectedImage;
    }
}
