package com.enzo.commonlib.utils.album.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 图片实体类
 */
public class AlbumImage implements Parcelable {

    private int imageId;
    private String imagePath;
    private long date;
    private boolean isSelected;
    private String bucket;

    public AlbumImage(){

    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    protected AlbumImage(Parcel in) {
        imageId = in.readInt();
        imagePath = in.readString();
        date = in.readLong();
        isSelected = in.readByte() != 0;
        bucket = in.readString();
    }

    public static final Creator<AlbumImage> CREATOR = new Creator<AlbumImage>() {
        @Override
        public AlbumImage createFromParcel(Parcel in) {
            return new AlbumImage(in);
        }

        @Override
        public AlbumImage[] newArray(int size) {
            return new AlbumImage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(imageId);
        parcel.writeString(imagePath);
        parcel.writeLong(date);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
        parcel.writeString(bucket);
    }
}
