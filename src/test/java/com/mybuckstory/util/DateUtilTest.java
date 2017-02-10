package com.mybuckstory.util;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class DateUtilTest extends TestCase {


	public void testIsToday() {
		assertTrue(DateUtil.isToday(new Date()));
	}
	
	public void testIsNotToday() {
		assertTrue(DateUtil.isToday(DateUtil.getLastMonthsDate()));
	}
	
	public void testGetLastMonthsDate(){
		Date lastMonth = DateUtil.getLastMonthsDate();
		assertNotNull(lastMonth);
		assertTrue(lastMonth.before(new Date()));
		
		Calendar lastMonthCal = Calendar.getInstance();
		lastMonthCal.setTime(lastMonth);
		
		Calendar nowCal = Calendar.getInstance();
		assertNotSame(lastMonthCal.get(Calendar.MONTH), nowCal.get(Calendar.MONTH));
		
		assertEquals(lastMonthCal.get(Calendar.MONTH) + 1, nowCal.get(Calendar.MONTH));
	}

	public void testClearTime() {
		Date date = DateUtil.clearTime(new Date());
		assertNotNull(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		assertEquals(0, cal.get(Calendar.HOUR));
		assertEquals(0, cal.get(Calendar.MINUTE));
		assertEquals(0, cal.get(Calendar.SECOND));
		assertEquals(0, cal.get(Calendar.MILLISECOND));			
	}

	public void testMaxTime() {
		Date date = DateUtil.maxTime(new Date());
		assertNotNull(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		assertEquals(cal.getActualMaximum(Calendar.MILLISECOND), cal.get(Calendar.MILLISECOND));
		assertEquals(cal.getActualMaximum(Calendar.SECOND), cal.get(Calendar.SECOND));
		assertEquals(cal.getActualMaximum(Calendar.MINUTE), cal.get(Calendar.MINUTE));
		assertEquals(cal.getActualMaximum(Calendar.HOUR), cal.get(Calendar.HOUR));
	}

	public void testIsUnder18() {
		assertTrue(DateUtil.isUnder18(new Date()));
		
		Calendar cal = Calendar.getInstance();
	      
		for(int i = 0; i < 20; i++){	
			cal.roll(Calendar.YEAR, false);						
		}
		cal.roll(Calendar.DATE, true);
		cal.roll(Calendar.MONTH, true);
		assertFalse(DateUtil.isUnder18(cal.getTime()));
		
		cal = Calendar.getInstance();
	      
		for(int i = 0; i < 18; i++){	
			cal.roll(Calendar.YEAR, false);						
		}
		cal.roll(Calendar.DATE, true);
		cal.roll(Calendar.MONTH, true);
		
		assertTrue(DateUtil.isUnder18(cal.getTime()));
		
		cal = Calendar.getInstance();
	      
		for(int i = 0; i < 18; i++){	
			cal.roll(Calendar.YEAR, false);						
		}
		cal.roll(Calendar.DATE, false);
		
		assertFalse(DateUtil.isUnder18(cal.getTime()));
		
		cal = Calendar.getInstance();
	      
		for(int i = 0; i < 18; i++){	
			cal.roll(Calendar.YEAR, false);						
		}
		cal.roll(Calendar.DATE, false);
		cal.roll(Calendar.MONTH, true);
		
		assertTrue(DateUtil.isUnder18(cal.getTime()));
		
		cal = Calendar.getInstance();
	      
		for(int i = 0; i < 18; i++){	
			cal.roll(Calendar.YEAR, false);						
		}
		cal.roll(Calendar.DATE, false);
		cal.roll(Calendar.MONTH, false);
		
		assertFalse(DateUtil.isUnder18(cal.getTime()));
	}

}
