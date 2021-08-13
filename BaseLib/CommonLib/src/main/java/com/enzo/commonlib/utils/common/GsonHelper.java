package com.enzo.commonlib.utils.common;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: GsonHelper
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/26
 * 邮   箱: xiaofangyinwork@163.com
 */
public class GsonHelper {

    private static Gson gson = new Gson();

    /**
     * 把json string 转化成类对象
     */
    public static <T> T toType(String str, Class<T> t) {
        try {
            if (str != null && !TextUtils.isEmpty(str.trim())) {
                return gson.fromJson(str.trim(), t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把类对象转化成json string
     */
    public static <T> String toJson(T t) {
        return gson.toJson(t);
    }

    /**
     * 把json 字符串转化成list
     */
    public static <T> List<T> toList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }
}
