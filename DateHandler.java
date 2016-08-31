package com.huice.uitl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHandler {
	
	public static String getTimeStamp(){
		
		return getFormatDate(new Date(),"yyyyMMddHHmmssSS");
	}
	
	public static String getNowDay(){
		return getFormatDate(new Date(),"yyyy-MM-dd");
	}
	
	public static String getForwardDay(String date, int increment){
		Calendar AddDay = Calendar.getInstance();
		AddDay.setTime(ParseDate(date,"yyyy-MM-dd"));
		AddDay.add(Calendar.DATE, increment);
		return getFormatDate(AddDay.getTime(), "yyyy-MM-dd");
	}
	
	public static String getFormatDate(Date date, String reg){
		SimpleDateFormat dataFormat = new SimpleDateFormat(reg);
		return dataFormat.format(date);
	}
	
	public static Date ParseDate(String date, String reg){
		SimpleDateFormat dateFormat = new SimpleDateFormat(reg);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args){
		System.out.println(getTimeStamp());
		System.out.println(getNowDay());
		System.out.println(getForwardDay(getNowDay(), 3));
	}

}
