package com.mybuckstory.model.encodingcom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Logo {	
	
	@XmlElement(name="logo_source")
	private String logoSource;
	@XmlElement(name="logo_x")
	private String logoX;
	@XmlElement(name="logo_y")
	private String logoY;
	@XmlElement(name="logo_mode")
	private String logoMode;
	@XmlElement(name="logo_threshold")
	private String logoThreshold;
	@XmlElement(name="logo_transparent")
	private String logoTransparent;
	
	public Logo(){}

	public String getLogoSource(){
		return logoSource;
	}

	public String getLogoX(){
		return logoX;
	}

	public String getLogoY(){
		return logoY;
	}

	public String getLogoMode(){
		return logoMode;
	}

	public String getLogoThreshold(){
		return logoThreshold;
	}

	public String getLogoTransparent(){
		return logoTransparent;
	}

	public void setLogoSource(String logoSource){
		this.logoSource = logoSource;
	}

	public void setLogoX(String logoX){
		this.logoX = logoX;
	}

	public void setLogoY(String logoY){
		this.logoY = logoY;
	}

	public void setLogoMode(String logoMode){
		this.logoMode = logoMode;
	}

	public void setLogoThreshold(String logoThreshold){
		this.logoThreshold = logoThreshold;
	}

	public void setLogoTransparent(String logoTransparent){
		this.logoTransparent = logoTransparent;
	}	
	
}