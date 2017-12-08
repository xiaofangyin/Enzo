package com.ifenglian.commonlib.utils.updateversion;

import android.os.Parcel;
import android.os.Parcelable;

public class VersionInfo implements Parcelable {

    private String id;
    private String versionName;//版本名
    private int versionCode;//版本号
    private String versionDesc;//版本描述信息内容
    private String downloadUrl;//新版本的下载路径
    private String versionSize;//版本大小

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersionSize() {
        return versionSize;
    }

    public void setVersionSize(String versionSize) {
        this.versionSize = versionSize;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.versionName);
        dest.writeInt(this.versionCode);
        dest.writeString(this.versionDesc);
        dest.writeString(this.downloadUrl);
        dest.writeString(this.versionSize);
    }

    public VersionInfo() {
    }

    protected VersionInfo(Parcel in) {
        this.id = in.readString();
        this.versionName = in.readString();
        this.versionCode = in.readInt();
        this.versionDesc = in.readString();
        this.downloadUrl = in.readString();
        this.versionSize = in.readString();
    }

    public static final Parcelable.Creator<VersionInfo> CREATOR = new Parcelable.Creator<VersionInfo>() {
        @Override
        public VersionInfo createFromParcel(Parcel source) {
            return new VersionInfo(source);
        }

        @Override
        public VersionInfo[] newArray(int size) {
            return new VersionInfo[size];
        }
    };
}
