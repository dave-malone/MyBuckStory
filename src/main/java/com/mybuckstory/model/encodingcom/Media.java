package com.mybuckstory.model.encodingcom;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//Date format: YYYY-MM-DD HH:MM:SS
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Media{

	@XmlElement(name="mediafile")
	private String mediaFile;
	@XmlElement(name="mediaid")
	private String mediaId;
	@XmlElement(name="mediastatus")
	private String mediaStatus;
	@XmlElement(name="createdate")
	private Date createDate;
	@XmlElement(name="startdate")
	private Date startDate;
	@XmlElement(name="finishdate")
	private Date finishDate;
	
	public Media(){}

	public String getMediaFile(){
		return mediaFile;
	}

	public String getMediaId(){
		return mediaId;
	}

	public String getMediaStatus(){
		return mediaStatus;
	}

	public Date getCreateDate(){
		return createDate;
	}

	public Date getStartDate(){
		return startDate;
	}

	public Date getFinishDate(){
		return finishDate;
	}

	public void setMediaFile(String mediaFile){
		this.mediaFile = mediaFile;
	}

	public void setMediaId(String mediaId){
		this.mediaId = mediaId;
	}

	public void setMediaStatus(String mediaStatus){
		this.mediaStatus = mediaStatus;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}

	public void setFinishDate(Date finishDate){
		this.finishDate = finishDate;
	}
}
