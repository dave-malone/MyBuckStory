package com.mybuckstory.model.encodingcom;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Query {	
	
	@XmlElement(name="userid")
	private String userId;
	@XmlElement(name="userkey")
	private String apiKey;
	@XmlElement
	private String action;
	@XmlElement(name="mediaid")
	private String mediaId;
	@XmlElement
	private String notify;
	@XmlElement(name="source", required=false, nillable=true, type=String.class)
	private List<String> sources = new ArrayList<String>();
	@XmlElement(name="format", required=false, nillable=true, type=Format.class)
	private List<Format> formats = new ArrayList<Format>();
	
	public Query(){}

	public String getUserId(){
		return userId;
	}

	public String getApiKey(){
		return apiKey;
	}

	public String getAction(){
		return action;
	}

	public String getMediaId(){
		return mediaId;
	}

	public String getNotify(){
		return notify;
	}

	public List<String> getSources(){
		return sources;
	}

	public List<Format> getFormats(){
		return formats;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public void setApiKey(String apiKey){
		this.apiKey = apiKey;
	}

	public void setAction(String action){
		this.action = action;
	}

	public void setMediaId(String mediaId){
		this.mediaId = mediaId;
	}

	public void setNotify(String notify){
		this.notify = notify;
	}

	public void setSources(List<String> sources){
		this.sources = sources;
	}

	public void setFormats(List<Format> formats){
		this.formats = formats;
	}	
	
	public boolean addFormat(Format format){
		return this.formats.add(format);
	}
	
	public boolean addSource(String source){
		return this.sources.add(source);
	}
	
	
}
