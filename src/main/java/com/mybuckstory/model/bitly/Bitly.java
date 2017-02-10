package com.mybuckstory.model.bitly;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="bitly")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bitly{

	@XmlElement(name = "errorCode")
	private String errorCode;
	
	@XmlElement(name = "errorMessage")
	private String errorMessage;
	
	@XmlElement(name = "statusCode")
	private String statusCode;
	
	@XmlElementWrapper(name="results")
	@XmlElement(name="nodeKeyVal",type=NodeKeyVal.class)
	private final List<NodeKeyVal> results;

	
	public Bitly(){
		results = null;
	}
	
	public String getErrorCode(){
		return errorCode;
	}

	public String getErrorMessage(){
		return errorMessage;
	}

	public String getStatusCode(){
		return statusCode;
	}

	public List<NodeKeyVal> getResults(){
		return results;
	}
	
	
}
