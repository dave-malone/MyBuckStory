package com.mybuckstory.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Constants {

	public static final String STORIES = "/stories/";
	public static final String PROFILES = "/members/";
	public static final String NEWS_ARTICLES = "/articles/";
	public static final String EVENTS = "/events/";
	public static final String CONTESTS = "/contests/";
	
	public static final String BASIC_DATE_FORMAT = "MMMM/d/yyyy";
	public static final DateFormat BASIC_DATE_FORMATTER = new SimpleDateFormat(BASIC_DATE_FORMAT);
	public static final String DEFAULT_USER_ROLE = "ROLE_USER";	
}
