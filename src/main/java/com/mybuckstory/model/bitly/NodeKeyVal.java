package com.mybuckstory.model.bitly;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="nodeKeyVal")
@XmlAccessorType(XmlAccessType.FIELD)
public class NodeKeyVal{

	@XmlElement(name = "userHash")	
	private String userHash;
	
	@XmlElement(name = "shortKeywordUrl")
	private String shortKeywordUrl;
	
	@XmlElement(name = "hash")
	private String hash;
	
	@XmlElement(name = "nodeKey")
	private String nodeKey;
	
	@XmlElement(name = "shortUrl")
	private String shortUrl;
	
	@XmlElement(name = "shortCNAMEUrl")
	private String shortCNAMEUrl;
	
	public String getShortCNAMEUrl() {
		return shortCNAMEUrl;
	}
	public String getUserHash(){
		return userHash;
	}
	public String getShortKeywordUrl(){
		return shortKeywordUrl;
	}
	public String getHash(){
		return hash;
	}
	public String getNodeKey(){
		return nodeKey;
	}
	public String getShortUrl(){
		return shortUrl;
	}
	
	
}
