package com.mybuckstory.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SellableItem extends AbstractSingleImageContainer{

	private BigDecimal salePrice;
	private Set<Image> images = Collections.synchronizedSet(new HashSet<Image>());
	private String name;
	private String description;
	private String payPalItemNumber;
	
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	
	public Set<Image> getImages() {
		return images;
	}
	public void setImages(Set<Image> images) {
		this.images = images;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPayPalItemNumber() {
		return payPalItemNumber;
	}
	public void setPayPalItemNumber(String payPalItemNumber) {
		this.payPalItemNumber = payPalItemNumber;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((payPalItemNumber == null) ? 0 : payPalItemNumber.hashCode());
		result = prime * result
				+ ((salePrice == null) ? 0 : salePrice.hashCode());
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
		SellableItem other = (SellableItem) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (payPalItemNumber == null) {
			if (other.payPalItemNumber != null)
				return false;
		} else if (!payPalItemNumber.equals(other.payPalItemNumber))
			return false;
		if (salePrice == null) {
			if (other.salePrice != null)
				return false;
		} else if (!salePrice.equals(other.salePrice))
			return false;
		return true;
	}
	
	
	
}
