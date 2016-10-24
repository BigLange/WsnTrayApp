package com.example.retrofitliber.utils;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class Util {
	// 把Stream转换成String


	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;

		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "/n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}


	// 修改整个界面所有控件的字体
	public static void changeFonts(ViewGroup root, String path, Activity act) {
		// path是字体路径
		Typeface tf = Typeface.createFromAsset(act.getAssets(), path);
		for (int i = 0; i < root.getChildCount(); i++) {
			View v = root.getChildAt(i);
			if (v instanceof TextView) {
				((TextView) v).setTypeface(tf);
			} else if (v instanceof Button) {
				((Button) v).setTypeface(tf);
			} else if (v instanceof EditText) {
				((EditText) v).setTypeface(tf);
			} else if (v instanceof ViewGroup) {
				changeFonts((ViewGroup) v, path, act);
			}
		}
	}
	public static int getIntValue(Object obj) {
		String s;
		if (obj == null)
			s = "0";
		else
			s = obj.toString();
		if (checkNULL(s))
			s = "0";
		if (s.equals("不限"))
			return Integer.MAX_VALUE;
		try {
			return Integer.valueOf(s);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	// 修改整个界面所有控件的字体大小
	public static void changeTextSize(ViewGroup root, int size, Activity act) {
		for (int i = 0; i < root.getChildCount(); i++) {
			View v = root.getChildAt(i);
			if (v instanceof TextView) {
				((TextView) v).setTextSize(size);
			} else if (v instanceof Button) {
				((Button) v).setTextSize(size);
			} else if (v instanceof EditText) {
				((EditText) v).setTextSize(size);
			} else if (v instanceof ViewGroup) {
				changeTextSize((ViewGroup) v, size, act);
			}
		}
	}

	// 不改变控件位置，修改控件大小
	public static void changeWH(View v, int W, int H) {
		LayoutParams params = v.getLayoutParams();
		params.width = W;
		params.height = H;
		v.setLayoutParams(params);
	}

	// 修改控件的高
	public static void changeH(View v, int H) {
		LayoutParams params = v.getLayoutParams();
		params.height = H;
		v.setLayoutParams(params);
	}

	// 替换手机号中间为*
	public static String HidePhone(String s1) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s1.length(); i++) {
			char c = s1.charAt(i);
			if (i < 3 || i > 6)
				sb.append(c);
			else
				sb.append('*');
		}
		return sb.toString();
	}

	/**
	 * 描述：获取指定日期时间的字符串,用于导出想要的格式.
	 * 
	 * @param strDate
	 *            String形式的日期时间，必须为yyyy-MM-dd HH:mm:ss格式
	 * @param format
	 *            输出格式化字符串，如："yyyy-MM-dd HH:mm:ss"
	 * @return String 转换后的String类型的日期时间
	 */
	public static String Format(String strDate, String format) {
		String mDateTime = null;
		try {
			Calendar c = new GregorianCalendar();
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			c.setTime(mSimpleDateFormat.parse(strDate));
			SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat(format);
			mDateTime = mSimpleDateFormat2.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mDateTime;
	}

	/**
	 * 描述：获取指定日期时间的字符串,用于导出想要的格式.
	 * 
	 * @param strDate
	 *            String形式的日期时间，必须为yyyy-MM-dd HH:mm:ss格式

	 *            输出格式化字符串，如："yyyy-MM-dd HH:mm:ss"
	 * @return String 转换后的String类型的日期时间
	 */
	public static Date getDate(String strDate) {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = null;
		try {
			date = mSimpleDateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getTimeNow() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		df.format(new Date());// new Date()为获取当前系统时间
		return df.format(new Date());
	}

	// 从路径获取文件名
	public static String getFileName(String pathandname) {
		int start = pathandname.lastIndexOf("/");
		int end = pathandname.lastIndexOf(".");
		if (start != -1 && end != -1) {
			return pathandname.substring(start + 1, end);
		} else {
			return null;
		}
	}

	// 通过路径生成Base64文件
	public static String getBase64FromPath(String path) {
		String base64 = "";
		try {
			File file = new File(path);
			byte[] buffer = new byte[(int) file.length() + 100];
			@SuppressWarnings("resource")
			int length = new FileInputStream(file).read(buffer);
			base64 = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return base64;
	}

	// 判断是否为11位手机号,目前只支持3、5、8、网段，当有新网段时按格式添加
	public static boolean checkPHONE(String str) {
		// String regex = "[1][3|5|8|4][\\d]{9}";
		String regex = "[1][\\d]{10}";
		// String regex = "\\d{11}";// 11位数字
		return str.matches(regex);
	}

	// 判断是否为6位邮编
	public static boolean checkSEND_CODE(String str) {
		String regex = "[\\d]{6}";
		return str.matches(regex);
	}
	public static String getDecimal(float f){
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(f);
	}
	// 判断是否NULL
	public static boolean checkNULL(String str) {
		return str == null || "null".equals(str) || "".equals(str);

	}

	// 判断是否NULL
	public static String HideNULL(String str) {
		if (str == null || "null".equals(str)) {
			return "";
		} else {
			return str;
		}

	}

	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	// 判断连接是否可用

	public static String loadUrl(String url) throws Exception {
		StringBuilder sb = new StringBuilder();
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setUseCaches(true);
		connection.setConnectTimeout(30000);
		connection.setReadTimeout(30000);
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}

	/**
	 * 返回2015年5月2日23:57:54格式的字符串
	 * 
	 * @param text
	 * @return
	 */
	public static String formatShortDate(String text) {
		if (text.length() < 8) {
			return "";
		} else {
			String date = text.substring(0, 4) + "年" + text.substring(4, 6) + "月" + text.substring(6, 8) + "日";
			return date;
		}
	}

	/**
	 * 返回2015年5月2日23:57:54格式的字符串
	 * 
	 * @param text
	 * @return
	 */
	public static String formatShortDate2(String text) {
		if (text.length() < 8) {
			return "";
		} else {
			String date = text.substring(0, 4) + "-" + text.substring(4, 6) + "-" + text.substring(6, 8);
			return date;
		}
	}

	/**
	 * 返回2015年5月2日23:57:54格式的字符串
	 * 
	 * @param text
	 * @return
	 */
	public static String formatLongDate(String text) {
		while (text.length() < 12) {
			text = text + "0";
		}
		String date = text.substring(0, 4) + "年" + text.substring(4, 6) + "月" + text.substring(6, 8) + "日" + text.substring(8, 10) + ":"
				+ text.substring(10, 12);
		return date;
	}

	/**
	 * 返回2015年5月2日23:57:54格式的字符串
	 * 
	 * @param text
	 * @return
	 */
	public static String formatLongDate2(String text) {
		while (text.length() < 12) {
			text = text + "0";
		}
		String date = text.substring(0, 4) + "/" + text.substring(4, 6) + "/" + text.substring(6, 8) + " " + text.substring(8, 10) + ":"
				+ text.substring(10, 12);
		return date;
	}

	public static  String formatPrice(String abc) {
		DecimalFormat format = new DecimalFormat("0.00");
		return format.format(new BigDecimal(abc));
	}


	public static boolean isEmpty(String args) {
		if (args == null || args.equals("") || args.trim().equals("")||args.equals("null")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 消息界面，如果时间是今天就显示小时和分钟，否则显示年月日 2016-10-01
	 *
	 * @param dateString
	 * @return
	 */
	public static String formatMessageDateString(String dateString) {
		 DateFormat dateForamt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = dateForamt.parse(dateString);
			if (dateForamt.format(date).equals(dateForamt.format(new Date()))) {
				return (new SimpleDateFormat("HH:mm")).format((new SimpleDateFormat("yyyy-MM-dd HH:mm")).parse(dateString)).toString();
			} else {
				return (new SimpleDateFormat("yyyy-MM-dd")).format((new SimpleDateFormat("yyyy-MM-dd HH:mm")).parse(dateString)).toString();
			}
		} catch (Exception e) {
			return dateString;
		}

	}

	public   static  boolean checkIsNormalColor(String color){
		 List<String> carColorList = new ArrayList<>(Arrays.asList("米色", "白色", "灰色", "红色", "棕色", "蓝色", "黄色", "紫色", "黑色", "橙色", "银色", "金色", "绿色"));
		return  carColorList.contains(color);
	}

	public static   List<String> getCarColorList(){
		return    new ArrayList<>(Arrays.asList("米色", "白色", "灰色", "红色", "棕色", "蓝色", "黄色", "紫色", "黑色", "橙色", "银色", "金色", "绿色"));
	}
}
