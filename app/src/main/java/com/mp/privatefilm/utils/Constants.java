/**   
 * @【文件名称】     Constants.java   
 * @【版本信息】       
 * @【创建时间】     2012-7-31   
 * @【Copyright】    郑州华粮科技股份有限公司 Corporation 2012    
 * 版权所有   
 *   
 */
package com.mp.privatefilm.utils;

public interface Constants {

	/**
	 * 获得sd卡位置
	 */
	String AppName = "PrivateFilm";
	String Platform = "android";
	String DataPath = AppName;
	String SHARED_PREFERENCE_NAME = "com_mobile_" + AppName + "_preferenc";
	String PAGE_SIZE = "10";
	int PAGESIZE = 10;
	String secretKey = "com.mp.privatefilm";
	String secretIv = "01234567";
	String URLHead = "";
	
	interface LongTime {
		int volley_timeout = 10000;
	}

	interface TextDefault {
		String Nothave = "--";
	}

	/** 请求远程数据时的固定字段 */
	interface reqhead {

	}

	interface cmd {
		String welcome = "welcome";
		String home_advertisement = "home_advertisement";
		String home_film = "home_film";
	}

	interface netstate {
		String readfail = "网络读取失败!";
	}

	interface jsName {
		String code = "Code";
		String content = "Content";
		String state = "State";
		String code_success = "001";
		String code_error = "002";
		String code_none = "003";
		String code_timeout = "004";
		/** 总页数 */
		String pagetotal = "pagetotal";
		/** 总记录数 */
		String totlePageSize = "totlePageSize";

		String code_url = "url";
		String code_host = "host";
		String code_port = "port";

		String request = "request";
		String summary = "summary";
		String sectionid = "sectionid";
		String sectiontype = "sectiontype";
		String summaryinfo = "summaryinfo";
		String nexttime = "nexttime";
		String specialno = "specialno";
		String specialname = "specialname";

		/** 操作员信息 **/
		String op = "op";
		/** 安全码 **/
		String safetycode = "safetycode";
		
		/** 当前页数 **/
		String pageNo = "pageNo";
		/** 总行数 **/
		String totalRows = "totalRows";
		/** 数据行 **/
		String rows = "rows";
	}

	interface keywords {

		String InitiBoot = SHARED_PREFERENCE_NAME + "_mInitiBoot";

		// 进行程度信息
		String Initialed = SHARED_PREFERENCE_NAME + "_mInitialed";

		// 服务器socket信息
		String Url = SHARED_PREFERENCE_NAME + "_url";
		String Host = SHARED_PREFERENCE_NAME + "_host";
		String Port = SHARED_PREFERENCE_NAME + "_port";

		String Deviceid = SHARED_PREFERENCE_NAME + "_deviceid";
		String Platform = SHARED_PREFERENCE_NAME + "_platform";
		String Version = SHARED_PREFERENCE_NAME + "_version";

		String Autoupdate = SHARED_PREFERENCE_NAME + "_autoupdate";

		String Ring = SHARED_PREFERENCE_NAME + "_settingRing";
		String Message = SHARED_PREFERENCE_NAME + "_settingMessage";

		String LoginID = SHARED_PREFERENCE_NAME + "_loginid";

		/** 会员图像地址 **/
		String MemberIcon = SHARED_PREFERENCE_NAME + "_memberIcon";
		/** 登录状态 **/
		String AlreadyLogin = SHARED_PREFERENCE_NAME + "_alreadyLogin";
	}

	interface TrueOrFalse {
		String True = "true";
		String False = "false";
	}

}
