package com.enzo.module_d.model.room.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Anime {
    /**
     * 动漫名
     * 如果是字符串做主键，记得加@NonNull，不然会报错
     */
    @NonNull
    @PrimaryKey
    private String name;
    /**
     * 动漫类型
     */
    private String type;
    /**
     * 放送时间
     */
    private String playDate;
    /**
     * 集数
     */
    private int episode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlayDate() {
        return playDate;
    }

    public void setPlayDate(String playDate) {
        this.playDate = playDate;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    @Override
    public String toString() {
        return "Anime{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", playDate='" + playDate + '\'' +
                ", episode=" + episode +
                '}';
    }
}