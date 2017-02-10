package com.mybuckstory.core.event;

import java.util.HashMap;
import java.util.Map;

public class VideoEncodingFailedEvent extends EmailEvent{	
	
	private final String xml;
	private final String message;
	
	public VideoEncodingFailedEvent(Object source, String xml, String message){
		super(source, "admin@mybuckstory.com", "com/mybuckstory/core/service/videoEncodingFailedNotification.vm");
		this.xml = xml;
		this.message = message;
	}

	@Override
	public String getMessageSubject() {
		return "Video Encoding Failed";
	}

	@Override
	public Map<String, Object> getMessageModel() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("xml", xml);
		model.put("message", message);
		return model;
	}
	
}
