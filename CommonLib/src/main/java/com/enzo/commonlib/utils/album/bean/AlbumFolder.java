package com.enzo.commonlib.utils.album.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片文件夹实体类
 */
public class AlbumFolder implements Parcelable{

    private String name;
    private List<AlbumImage> images;

    public AlbumFolder(String name) {
        this.name = name;
        images = new ArrayList<>();
    }

    protected AlbumFolder(Parcel in) {
        name = in.readString();
        images = in.createTypedArrayList(AlbumImage.CREATOR);
    }

    public static final Creator<AlbumFolder> CREATOR = new Creator<AlbumFolder>() {
        @Override
        public AlbumFolder createFromParcel(Parcel in) {
            return new AlbumFolder(in);
        }

        @Override
        public AlbumFolder[] newArray(int size) {
            return new AlbumFolder[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AlbumImage> getImages() {
        return images;
    }

    public void setImages(ArrayList<AlbumImage> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "AlbumFolder{" +
                "name='" + name + '\'' +
                ", images=" + images +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeTypedList(images);
    }
}
