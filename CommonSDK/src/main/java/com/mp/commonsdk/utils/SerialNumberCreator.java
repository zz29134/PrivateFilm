/**   
 * @【文件名称】     SerialNumberCreator.java   
 * @【版本信息】       
 * @【创建时间】     2012-7-31   
 * @【Copyright】    郑州华粮科技股份有限公司 Corporation 2012    
 * 版权所有   
 *   
 */
package com.mp.commonsdk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

@SuppressLint("SimpleDateFormat")
public class SerialNumberCreator {

	private static int INIT_NUM = 100000;
	private static final int MIN_NUM = 100000;
	private static final int MAX_NUM = 999999;
	private static String telephone = "";
	private static String deviceId = "";
	private static boolean hasInit = false;
	private static SimpleDateFormat format = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	public static String getSerialNumber() {
		if (hasInit) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateStr = format.format(new Date());
			dateStr += getNum();
			return telephone + deviceId + dateStr;
		} else {
			throw new RuntimeException("SerialNumberCreator none init");
		}
	}

	public static Long getLongSerialNumber() {
		String dateStr = System.currentTimeMillis() + getNum();
		return Long.valueOf(dateStr);
	}

	public static String getSerialNumber20() {
		if (hasInit) {
			String dateStr = format.format(new Date());
			String str = telephone + deviceId + dateStr + getNum();
			return str.substring(str.length() - 20);
		} else {
			throw new RuntimeException("SerialNumberCreator none init");
		}
	}

	public static String getUUID32() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static void init(String telephone, Context context) {
		SerialNumberCreator.telephone = telephone;
		TelephonyManager tm = (TelephonyManager) context
				.getApplicationContext().getSystemService(
						Context.TELEPHONY_SERVICE);
		SerialNumberCreator.deviceId = tm.getDeviceId(); // 取出IMEI
		if (null == SerialNumberCreator.deviceId) {
			SerialNumberCreator.deviceId = Secure.getString(
					context.getContentResolver(), Secure.ANDROID_ID);
		}
		SerialNumberCreator.hasInit = true;
	}

	private static synchronized String getNum() {
		INIT_NUM = INIT_NUM < MAX_NUM ? ++INIT_NUM : MIN_NUM;
		return String.valueOf(INIT_NUM);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 99; i++) {
			long l = System.currentTimeMillis();
			System.out.println(getSerialNumber());
			System.out.println("-->" + i + ":"
					+ (System.currentTimeMillis() - l));
		}
	}
}
