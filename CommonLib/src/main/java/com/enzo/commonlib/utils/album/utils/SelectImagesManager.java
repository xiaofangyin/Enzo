package com.enzo.commonlib.utils.album.utils;

import com.enzo.commonlib.utils.album.bean.AlbumImage;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: SelectImagesManager
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/3
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SelectImagesManager {

    private List<AlbumImage> images;
    private static SelectImagesManager manager;

    private SelectImagesManager() {
        images = new ArrayList<>();
    }

    public static SelectImagesManager getManager() {
        if (manager == null) {
            synchronized (SelectImagesManager.class) {
                if (manager == null) {
                    manager = new SelectImagesManager();
                }
            }
        }
        return manager;
    }

    public int size() {
        return images.size();
    }

    public void add(AlbumImage image) {
        boolean contains = false;
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i).getImagePath().equals(image.getImagePath())) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            images.add(image);
        }
    }

    public void remove(AlbumImage image) {
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i).getImagePath().equals(image.getImagePath())) {
                images.remove(i);
                break;
            }
        }
    }

    public void clear() {
        images.clear();
    }

    public List<AlbumImage> getImages() {
        return images;
    }
}
