package com.mybuckstory.model;

import org.apache.commons.lang.StringUtils;


public class ShoppingCart extends AbstractAuditable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4458622157130790028L;
	private String serializedCart = "";
	
	public ShoppingCart(){}
	
	public ShoppingCart(String serializedCart) {
		this.serializedCart = serializedCart;
	}

	public String getSerializedCart() {
		return serializedCart;
	}

	public void setSerializedCart(String serializedCart) {
		this.serializedCart = StringUtils.trimToEmpty(serializedCart);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((serializedCart == null) ? 0 : serializedCart.hashCode());
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
		ShoppingCart other = (ShoppingCart) obj;
		if (serializedCart == null) {
			if (other.serializedCart != null)
				return false;
		} else if (!serializedCart.equals(other.serializedCart))
			return false;
		return true;
	}
	
	
	
}
