package com.enzo.commonlib.utils.album.constant;

/**
 * 文 件 名: SelectImageConstants
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/3
 * 邮   箱: xiaofangyinwork@163.com
 */
public interface SelectImageConstants {

    //是否是选择单张图片模式
    String IS_SINGLE = "is_SINGLE";
    //是否需要截图
    String NEED_CROP = "need_crop";
    //图片集合
    String IMAGES = "images";
    //最大的图片选择数
    String MAX_SELECT_COUNT = "max_select_count";
    //相册
    String BUCKET = "bucket";
    //初始位置
    String POSITION = "position";
    //预览图片时是否可以选择
    String CAN_SELECT = "can_select";

    int IMAGES_SELECT_REQUEST_CODE = 1000;

    int AVATAR_CROP_REQUEST_CODE = 1001;

    int PREVIEW_REQUEST_CODE = 1002;

    int PICK_IMAGE_REQUEST_CODE = 1003;
}
