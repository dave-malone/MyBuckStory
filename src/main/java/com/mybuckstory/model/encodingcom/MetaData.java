package com.mybuckstory.model.encodingcom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MetaData{

	@XmlElement
	private String title;
	@XmlElement
	private String copyright;
	@XmlElement
	private String author;
	@XmlElement
	private String description;
	@XmlElement
	private String album;

	public MetaData() {}

	public String getTitle() {
		return title;
	}

	public String getCopyright() {
		return copyright;
	}

	public String getAuthor() {
		return author;
	}

	public String getDescription() {
		return description;
	}

	public String getAlbum() {
		return album;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

}
