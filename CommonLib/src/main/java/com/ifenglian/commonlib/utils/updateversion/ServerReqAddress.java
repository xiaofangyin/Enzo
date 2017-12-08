package com.ifenglian.commonlib.utils.updateversion;

public class ServerReqAddress {
	public static String UPDATA_VERSION_REQ = "版本更新地址";
	/**
	 * 可以模拟测试  服务  
	 * 服务器上随便放个apk文件  versionCode > 2 就行  然后 写个请求地址  返回的数据为下面格式的类容即可 
	 * 也可以 本地模拟测试
	 */
	
	/**
	 * 版本更新的文件类型
	 * 
	 * {
    	"data": [
        	{
            	"downloadUrl": "apk的下载地址",
            	"versionCode": 2,
            	"versionDesc": "更新内容：1、增加xxxxx功能2、增加xxxx显示！3、用户界面优化！4、xxxxxx！",
            	"versionName": "V2.3.0",
            	"versionSize": "22.19M"
        	}
    	],
    	"result": 1
		}
	 * 
	 * 
	 * 
	 * 				
	 * 
	 */
}
