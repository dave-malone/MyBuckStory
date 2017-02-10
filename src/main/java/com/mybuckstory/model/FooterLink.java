package com.mybuckstory.model;

import org.apache.commons.lang.StringUtils;

public class FooterLink extends AbstractAuditable {

	private String name;
	private String url;
	private String category;	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FooterLink other = (FooterLink) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	

	public int compareTo(Object o) {
		if(!(o instanceof FooterLink)){
			return -1;
		}
		
		FooterLink link = (FooterLink)o;
		
		if(StringUtils.isBlank(link.name) && StringUtils.isBlank(this.name)){
			return 0;
		}else if(StringUtils.isBlank(link.name) && StringUtils.isNotBlank(this.name)){
			return -1;
		}else if(StringUtils.isNotBlank(link.name) && StringUtils.isBlank(this.name)){
			return 1;
		}
		
		return link.name.compareToIgnoreCase(this.name);
	}
	
	
}
