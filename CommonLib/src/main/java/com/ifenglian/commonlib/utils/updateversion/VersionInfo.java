package com.ifenglian.commonlib.utils.updateversion;

import java.io.Serializable;

/**
 * @项目名: 	gdmsaec-app
 * @包名:	com.winfo.gdmsaec.app.domain
 * @类名:	VersionInfo
 * @创建者:	wenjie
 * @创建时间:	2015-10-14	上午11:06:08 
 * @描述:	app版本信息封装类
 * 
 * @svn版本:	$Rev: 1304 $
 * @更新人:	$Author: wenjie $
 * @更新时间:	$Date: 2016-02-27 14:58:42 +0800 (Sat, 27 Feb 2016) $
 * @更新描述:	TODO
 */
public class VersionInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String	versionName;//版本名
	private int		versionCode;//版本号
	private String	versionDesc;//版本描述信息内容
	private String	downloadUrl;//新版本的下载路径
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
	
}
