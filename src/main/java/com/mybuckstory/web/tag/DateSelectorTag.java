package com.mybuckstory.web.tag;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.jsp.JspException;

public class DateSelectorTag extends MbsTagSupport {

	private Date selectedDate;
	private String selectNamePrefix;
	private int beginYear;
	
	
	public int doStartTag() throws JspException {
		if(selectedDate == null){
			selectedDate = new Date();
		}		
		
		StringBuffer yearSelect = new StringBuffer(getBeginSelect("Year"));
		StringBuffer monthSelect = new StringBuffer(getBeginSelect("Month"));
		StringBuffer daySelect = new StringBuffer(getBeginSelect("Day"));
		
		logger.debug("Locale: " + pageContext.getRequest().getLocale());
		
		Calendar cal = Calendar.getInstance();
		
		int currentYear = cal.get(Calendar.YEAR);		
		
		cal.setTime(selectedDate);
		int selectedYear = cal.get(Calendar.YEAR);
		int selectedMonth = cal.get(Calendar.MONTH);
		int selectedDate = cal.get(Calendar.DATE);
		
		for(int i = currentYear; i >= beginYear; i--){
			yearSelect.append(getOption(i + "", selectedYear == i));
		}
		yearSelect.append(getEndSelect());
		
		monthSelect.append(getOption("January", selectedMonth == Calendar.JANUARY));
		monthSelect.append(getOption("February", selectedMonth == Calendar.FEBRUARY));
		monthSelect.append(getOption("March", selectedMonth == Calendar.MARCH));
		monthSelect.append(getOption("April", selectedMonth == Calendar.APRIL));
		monthSelect.append(getOption("May", selectedMonth == Calendar.MAY));
		monthSelect.append(getOption("June", selectedMonth == Calendar.JUNE));
		monthSelect.append(getOption("July", selectedMonth == Calendar.JULY));
		monthSelect.append(getOption("August", selectedMonth == Calendar.AUGUST));
		monthSelect.append(getOption("September", selectedMonth == Calendar.SEPTEMBER));
		monthSelect.append(getOption("October", selectedMonth == Calendar.OCTOBER));
		monthSelect.append(getOption("November", selectedMonth == Calendar.NOVEMBER));
		monthSelect.append(getOption("December", selectedMonth == Calendar.DECEMBER));
		monthSelect.append(getEndSelect());
		
		for(int i = 1; i <= 31; i++){
			daySelect.append(getOption(i + "", selectedDate == i));
		}
		daySelect.append(getEndSelect());
		
		try {
			pageContext.getOut().write(daySelect.toString());
			pageContext.getOut().write(monthSelect.toString());
			pageContext.getOut().write(yearSelect.toString());
		} catch (IOException e) {
			throw new JspException(e);
		}
		
		return SKIP_BODY;
	}

	private String getOption(String value, boolean selected){
		return getOption(value, value, selected);
	}
	
	private String getOption(String value, String label, boolean selected){
		StringBuffer buffer = new StringBuffer();
		buffer.append("<option value=\"")
			.append(value)
			.append("\"");
			
		if(selected){
			buffer.append(" selected=\"selected\"");
		}		
		
		buffer.append(">")
			.append(label)
			.append("</option>");
		
		return buffer.toString();
	}
	
	private String getBeginSelect(String calField){
		StringBuffer buffer = new StringBuffer();
		buffer.append("<select name=\"")
			.append(selectNamePrefix)
			.append(calField)
			.append("\"")
			.append(">");
		return buffer.toString();
	}
	
	private String getEndSelect(){
		return "</select>";
	}
	
	
	
	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}
	
	public void setBeginYear(int beginYear) {
		this.beginYear = beginYear;
	}
	
	public void setSelectNamePrefix(String selectNamePrefix) {
		this.selectNamePrefix = selectNamePrefix;
	}

}
