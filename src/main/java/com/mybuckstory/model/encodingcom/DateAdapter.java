package com.mybuckstory.model.encodingcom;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date>{
	public static final String DATE_FORMAT = "YYYY-MM-DD HH:mm:ss";
	public static final DateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);	
	@Override
	public String marshal(Date date) throws Exception {
		return DATE_FORMATTER.format(date);
	}
	@Override
	public Date unmarshal(String date) throws Exception {
		return DATE_FORMATTER.parse(date);
	}
}
