/**   
 * @【文件名称】     DataFormat.java   
 * @【版本信息】       
 * @【创建时间】     2012-7-31   
 * @【Copyright】    郑州华粮科技股份有限公司 Corporation 2012    
 * 版权所有   
 *   
 */
package com.mp.privatefilm.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint({ "SimpleDateFormat", "DefaultLocale" })
public class DataFormat {
	/**
	 * 全局变量
	 */
	private static DataFormat dataFormat = null;

	/**
	 * 
	 * @【方法名称】 getInstants
	 * @【方法描述】 返回对象
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 * 
	 */
	public static DataFormat getInstants() {
		if (dataFormat == null) {
			dataFormat = new DataFormat();
		}
		return dataFormat;
	}

	/**
	 * 
	 * @【方法名称】 isNotNull
	 * @【方法描述】 对null的情况处理
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 */
	public String isNotNull(String data) {
		if (data == null || "null".equals(data)) {
			data = "";
		} else {
			data = data.trim();
		}
		return data;
	}

	public Bitmap decodeImg(byte[] b) {
		Bitmap bitmap = null;

		InputStream input = null;
		if (b == null)
			return bitmap;
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 2;
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			input = new ByteArrayInputStream(b);
			SoftReference<Bitmap> softRef = new SoftReference<Bitmap>(
					BitmapFactory.decodeStream(input, null, options));
			bitmap = softRef.get();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (OutOfMemoryError error) {
			error.printStackTrace();
		} finally {
			if (b != null) {
				b = null;
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bitmap;
	}

	public Bitmap Bytes2Bimap(byte[] b) {
		if (null != b && b.length != 0) {
			try {
				return BitmapFactory.decodeByteArray(b, 0, b.length);
			} catch (OutOfMemoryError error) {
				error.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public Bitmap Decode2Bimap(byte[] data, int width, int height, int Rotate) {
		if (null != data && data.length != 0) {
			try {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				options.inTempStorage = new byte[8 * 1024];
				Matrix matrix = new Matrix();
				matrix.postRotate(Rotate);
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,
						options);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
				return bitmap;
			} catch (OutOfMemoryError error) {
				error.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public byte[] Bitmap2Bytes(Bitmap bm) {
		if (null == bm) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	public Bitmap small(Bitmap bitmap) {
		if (null == bitmap) {
			return null;
		}
		Matrix matrix = new Matrix();
		matrix.postScale(0.5f, 0.5f); // 长和宽放大缩小的比例
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return resizeBmp;
	}

	public Bitmap big(Bitmap bitmap) {
		if (bitmap == null)
			return null;
		Matrix matrix = new Matrix();
		matrix.postScale(1.0f, 1.0f); // 长和宽放大缩小的比例
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return resizeBmp;
	}

	/**
	 * 
	 * @【方法名称】 double2String
	 * @【方法描述】 格式化方法：其中fNumber表示要保留的几位小数 但是当 传入的d小数位最后一位不为0并且小数位长度大于fNumber时
	 *         按d的实际小数位为准(例如 d=0.12345 而fNumber=2)，但是 在实际的运算过程中 用double计算时没有按照
	 *         BigDecimal 大数运算法则乘除加减 数据会发生失真情况。例如 18.0可能会 变成
	 *         18.0000000000000001等 在算的时候 ，取 小数点前12位
	 *         来避开这个问题。如果牵扯到大数运算，特别是银行利率的计算时 这种舍位方法不可取。
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 */
	public String double2String(double d, int fNumber) {
		if (fNumber < 0)
			fNumber = 0;
		String temString = d + "";
		BigDecimal bg = new BigDecimal(temString);
		temString = bg.toPlainString();
		if (temString.indexOf(".") > -1) {
			temString = temString.substring(temString.indexOf("."));
			temString = temString.replace(".", "");
			while (temString != null && temString.length() > 0) {
				if (temString.length() > 12) {// 仅取小数位12位
					temString = temString.substring(0, 12);
				}
				if ("0".equals(temString.substring(temString.length() - 1))) {
					temString = temString.substring(0, temString.length() - 1);
				} else {
					break;
				}
			}
			// if (temString.length() > fNumber) {
			// fNumber = temString.length();
			// }
		}
		String pattern = null;
		switch (fNumber) {
		case 0:
			pattern = "#,##0"; //$NON-NLS-1$
			break;
		case 1:
			pattern = "#,##0.0"; //$NON-NLS-1$
			break;
		case 2:
			pattern = "#,##0.00"; //$NON-NLS-1$
			break;
		default:
			if (fNumber > 2) {
				pattern = "#,##0."; //$NON-NLS-1$
				StringBuffer b = new StringBuffer(pattern);
				for (int i = 0; i < fNumber; i++) {
					b.append('0');
				}
				pattern = b.toString();
			}
			break;
		}
		DecimalFormat formatter = new DecimalFormat();
		formatter.applyPattern(pattern);
		String value = formatter.format(d);
		value = value.equals("-0.00") ? "0.00" : value;
		if (d == 0) {
			return "0.00";
		}
		return value;
	}

	public String getDiffValueDate(String beginDate, String endDate) throws Exception {
		Date BDate = getStringToDate(beginDate);
		Date EDate2 = getStringToDate(endDate);
		long temp = EDate2.getTime() - BDate.getTime(); // 相差毫秒数
		long day = temp / 1000 / 3600 / 24; // 相差天
		long temp2 = temp % (1000 * 3600 * 24);
		long hours = temp2 / 1000 / 3600; // 相差小时数
		long temp3 = temp2 % (1000 * 3600);
		long mins = temp3 / 1000 / 60; // 相差分钟数
		return String.format("%s天%s时%s分", day, hours, mins);
	}

	public String getDiffValueNowDate(String beginDate) throws Exception {
		Date BDate = getStringToDate(beginDate);
		Date EDate2 = new Date(System.currentTimeMillis());
		long temp = EDate2.getTime() - BDate.getTime(); // 相差毫秒数
		long day = temp / 1000 / 3600 / 24; // 相差天
		long temp2 = temp % (1000 * 3600 * 24);
		long hours = temp2 / 1000 / 3600; // 相差小时数
		long temp3 = temp2 % (1000 * 3600);
		long mins = temp3 / 1000 / 60; // 相差分钟数
		return String.format("%s天%s时%s分", day, hours, mins);
	}

	public boolean NohaveBoolean(String Value) {
		boolean result = false;
		result = !(Value == null || Value == Constants.TextDefault.Nothave
				|| Value.equals(Constants.TextDefault.Nothave));
		return result;

	}

	/**
	 * 
	 * @【方法名称】 getDouble
	 * @【方法描述】 四舍五入并保留fNumber位小数
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 */
	public String getDouble(double amt, int fNumber) {
		BigDecimal dec1 = BigDecimal.valueOf(amt);
		dec1 = dec1.setScale(fNumber, RoundingMode.HALF_UP);
		return double2String(dec1.doubleValue(), fNumber);
	}

	/**
	 * 
	 * @【方法名称】 getSimpleDate
	 * @【方法描述】 返回的时间格式：yyyy-MM-dd
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 */
	public String getSimpleDateTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date_str = "";
		try {
			date_str = format.format(date);
		} catch (Exception e) {

		}
		return date_str;
	}

	public String getSimpleFullTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		String date_str = "";
		try {
			date_str = format.format(date);
		} catch (Exception e) {

		}
		return date_str;
	}

	public String getSimpleTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String date_str = "";
		try {
			date_str = format.format(date);
		} catch (Exception e) {

		}
		return date_str;
	}

	public String getSimpleTime(String date) {
		if (TextUtils.isEmpty(date))
			return "";
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String date_str = "";
		try {
			Date d = getStringToDate(date);
			date_str = format.format(d);
		} catch (Exception e) {
		}
		return date_str;
	}

	public String getSimpleDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd");
		String date_str = "";
		try {
			Date d = getStringToDate(date);
			date_str = format.format(d);
		} catch (Exception e) {
		}
		return date_str;
	}

	/**
	 * 
	 * @【方法名称】 getSimpleDate
	 * @【方法描述】 返回的时间格式：yyyy-MM-dd
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 */
	public String getDateTime(Date date, String fmt) {
		String date_str = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat(fmt);
			date_str = format.format(date);
		} catch (Exception e) {
		}
		return date_str;
	}

	/**
	 * 
	 * @【方法名称】 getDateTime
	 * @【方法描述】 返回的时间格式：yyyy-MM-dd HH:mm:ss
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 */
	public String getDateTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date_str = "";
		try {
			date_str = format.format(date);
		} catch (Exception e) {
		}
		return date_str;
	}

	public String getLocalDateTime() {
		return getDateTime(new Date(System.currentTimeMillis()));
	}

	/**
	 * 
	 * @【方法名称】 getAllDateTime
	 * @【方法描述】 返回的时间格式：yyyy-MM-dd HH:mm:ss:SSS
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 */
	public String getAllDateTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String date_str = "";
		try {
			date_str = format.format(date);
		} catch (Exception e) {
		}
		return date_str;
	}

	/**
	 * 
	 * @【方法名称】 getStringToDate
	 * @【方法描述】 String转变成Date型
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 */
	public Date getStringToDate(String string) throws Exception {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			return format.parse(string);
		} catch (Exception e) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return format.parse(string);
			} catch (Exception e2) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				return format.parse(string);
			}
		}
	}

	public Date getStringToSimpDate(String string) throws Exception {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			return format.parse(string);
		} catch (Exception e) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				return format.parse(string);
			} catch (Exception e2) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				return format.parse(string);
			}
		}
	}

	/**
	 * 
	 * @【方法名称】 getDateToString
	 * @【方法描述】 Date转变成String型
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 */
	public String getDateToString(Date date) throws Exception {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			return format.format(date);
		} catch (Exception e) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return format.format(date);
			} catch (Exception e2) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				return format.format(date);
			}
		}
	}

	public String getDateStr(long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		Formatter ft = new Formatter(Locale.CHINA);
		String date = ft.format("%1$tY-%1$tm-%1$td %1$tT", cal).toString();
		ft.close();
		return date;
	}

	/**
	 * 
	 * @【方法名称】 secToTime
	 * @【方法描述】 整数"秒"转变成"时:分:秒"
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 */
	public static String secToTime(int time) {
		String timeStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (time <= 0)
			return "00:00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":"
						+ unitFormat(second);
			}
		}
		return timeStr;
	}

	public static String unitFormat(int i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Integer.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}

	/**
	 * 
	 * @【方法名称】 px2dip
	 * @【方法描述】 象素转机器
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 
	 * @【方法名称】 dip2px
	 * @【方法描述】 机器转象素
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	// 身份证号码验证：start
	/**
	 * 功能：身份证的有效验证
	 * 
	 * @param IDStr
	 *            身份证号
	 * @return 有效：返回"" 无效：返回String信息
	 * @throws ParseException
	 */
	public String IDCardValidate(String IDStr) throws ParseException {
		String errorInfo = "";// 记录错误信息
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9",
				"10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			errorInfo = "身份证号码长度应该为15位或18位。";
			return errorInfo;
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return errorInfo;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "身份证生日无效。";
			return errorInfo;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
				|| (gc.getTime().getTime() - s.parse(
						strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
			errorInfo = "身份证生日不在有效范围。";
			return errorInfo;
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "身份证月份无效";
			return errorInfo;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "身份证日期无效";
			return errorInfo;
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		Hashtable<String, String> h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo = "身份证地区编码错误。";
			return errorInfo;
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				errorInfo = "身份证无效，不是合法的身份证号码";
				return errorInfo;
			}
		} else {
			return "";
		}
		// =====================(end)=====================
		return "";
	}

	/**
	 * 功能：判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		return isNum.matches();
	}

	/**
	 * 功能：设置地区编码
	 * 
	 * @return Hashtable 对象
	 */
	private static Hashtable<String, String> GetAreaCode() {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 验证日期字符串是否是YYYY-MM-DD格式
	 * 
	 * @param str
	 * @return
	 */
	public boolean isDataFormat(String str) {
		boolean flag = false;
		// String
		// regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
		String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern pattern1 = Pattern.compile(regxStr);
		Matcher isNo = pattern1.matcher(str);
		if (isNo.matches()) {
			flag = true;
		}
		return flag;
	}

	// 身份证号码验证：end

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 电话号码验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public boolean isPhone(String str) {
		boolean b = false;
		Pattern p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
		if (str.length() > 9) {
			Matcher m = p1.matcher(str);
			b = m.matches();
		}
		return b;
	}

}
