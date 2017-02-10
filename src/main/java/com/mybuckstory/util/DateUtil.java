package com.mybuckstory.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
		
	/**
	 * Enforce Non-instantiability
	 */
	private DateUtil(){}
	
	/**
	 * If the date param is null, this method will return true
	 * @param date
	 * @return
	 */
	public static boolean isBefore1900(Date date){
		if(date == null){
			return true;
		}
		
		date = clearTime(date);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(clearTime(cal.getTime()));
		cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		cal.set(Calendar.YEAR, 1900);
		
		return date.compareTo(cal.getTime()) < 0;	
	}
	
	public static boolean isToday(Date date){
		date = clearTime(date);
		Date now = clearTime(date);
		return now.equals(date);
	}
	
	public static Date clearTime(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		for(int field : new int[]{Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND}){
			cal.set(field, cal.getActualMinimum(field));
		}
		
		return cal.getTime();
	}
	
	public static Date maxTime(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		for(int field : new int[]{Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND}){
			cal.set(field, cal.getActualMaximum(field));
		}		
		return cal.getTime();
	}
	
	public static boolean isUnder18(Date date){
		Calendar dob = Calendar.getInstance();
		dob.setTime(date);
		
		Calendar now = Calendar.getInstance();
		
		int dobDay = dob.get(Calendar.DATE);
		int dobMonth = dob.get(Calendar.MONTH);
		int dobYear = dob.get(Calendar.YEAR);
		
		int nowDay = now.get(Calendar.DATE);
		int nowMonth = now.get(Calendar.MONTH);
		int nowYear = now.get(Calendar.YEAR);
		
		int yearDiff = nowYear - dobYear;
		int monthDiff = nowMonth - dobMonth;
		int dayDiff = nowDay - dobDay;		
				
		if(yearDiff > 18){
			return false;
		}else if(yearDiff == 18 && dayDiff >= 0 && monthDiff == 0){
			//today is the users birthday, or the users birthday was earlier this month
			return false;
		}else if(yearDiff == 18 && monthDiff >= 0){
			//the users birthday was earlier this year
			return false;
		}
		
		return true;
	}
	
	public static Date getLastMonthsDate(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		
		return cal.getTime();
	}
	
}
