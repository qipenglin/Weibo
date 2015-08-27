package com.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import android.R.integer;
import android.util.Log;

/**
 * 时间处理类 网络获得的源代码
 */
public class DateUtil {

	/**
	 * 将未指定格式的日期字符串转化成java.util.Date类型日期对象 <br>
	 * 
	 * @param date
	 *            ,待转换的日期字符串
	 * @return
	 * @throws ParseException
	 */
	public static Date parseStringToDate(String dateString) {
		Date result = new Date();
		StringTokenizer tokenizer = new StringTokenizer(dateString, " :");
		String weekday = tokenizer.nextToken();
		int month = GetMonth(tokenizer.nextToken());
		int day = Integer.parseInt(tokenizer.nextToken());
		int hour = Integer.parseInt(tokenizer.nextToken());
		int minute = Integer.parseInt(tokenizer.nextToken());
		int second = Integer.parseInt(tokenizer.nextToken());
		String timezone = tokenizer.nextToken();
		int year = Integer.parseInt(tokenizer.nextToken());

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, hour, minute, second);
		// calendar.set(Calendar.YEAR, year);
		// calendar.set(Calendar.MONTH, month);
		// calendar.set(Calendar.DATE, day);
		// calendar.set(Calendar.HOUR, hour);
		// calendar.set(Calendar.MINUTE, minute);
		// calendar.set(Calendar.SECOND, second);
		result = calendar.getTime();
		return result;
	}

	private static int GetMonth(String token) {
		String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		for (int i = 0; i < 12; i++) {
			if (token.equals(months[i])) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * 计算两个日期型的时间相差多少时间
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public static String twoDateDistance(Date startDate, Date endDate) {

		if (startDate == null || endDate == null) {
			return null;
		}
		long timeLong = endDate.getTime() - startDate.getTime();
		long year = timeLong / (24 * 60 * 60 * 1000 * 365);
		String yearStr = String.valueOf(year);
		long month = timeLong % (24 * 60 * 60 * 1000 * 365) / (24 * 60 * 60 * 1000 * 30);
		String monthStr = String.valueOf(month);
		long day = timeLong % (24 * 60 * 60 * 1000 * 365) % (24 * 60 * 60 * 1000 * 30) / (24 * 60 * 60 * 1000);
		String dayStr = String.valueOf(day);
		long hour = timeLong % (24 * 60 * 60 * 1000 * 365) % (24 * 60 * 60 * 1000 * 30) % (24 * 60 * 60 * 1000)
				/ (60 * 60 * 1000);
		String hourStr = String.valueOf(hour);
		long minute = timeLong % (24 * 60 * 60 * 1000 * 365) % (24 * 60 * 60 * 1000 * 30) % (24 * 60 * 60 * 1000)
				% (60 * 60 * 1000) / (60 * 1000);
		String minuteStr = String.valueOf(minute);
		String returnStr = null;
		if (year != 0) {
			returnStr = yearStr + "年";
		} else if (month != 0) {
			returnStr = monthStr + "月";
		} else if (day != 0) {
			returnStr = dayStr + "天";
		} else if (hour != 0) {
			returnStr = hourStr + "小时";
		} else if (minute != 0) {
			returnStr = minuteStr + "分钟";
		}
		return returnStr == null ? "1秒 前" : returnStr + " 前";
	}
}
