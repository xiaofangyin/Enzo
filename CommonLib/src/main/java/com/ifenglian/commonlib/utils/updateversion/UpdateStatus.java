package com.ifenglian.commonlib.utils.updateversion;

/**
 * 
 * @author wenjie
 *	检测版本的状态类
 */
public interface UpdateStatus {
	/**
	 * 没有新版本
	 */
	public static int NO = 1;
	
	/**
	 * 有新版本
	 */
	public static int YES = 2;
	
	/**
	 * 链接超时
	 */
	public static int TIMEOUT = 3;
	
	/**
	 * 没有wifi
	 */
	public static int NOWIFI = 4;
	
	/**
	 * 数据解析出错
	 */
	public static int ERROR = -1;
}
