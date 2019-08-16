package com.zx.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
	/**
	 * 根据传入pattern（如：yyyyMMdd）；获得当前日期
	 * @return String
	 */
	public static String getCurrentDate(String pattern){
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter datePattern = DateTimeFormatter.ofPattern(pattern);
		return currentDate.format(datePattern);
	}
	
	public static void main(String[] args) {
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyyMM");
		String format = currentDate.format(pattern);
		System.out.println(format);
	}
}
