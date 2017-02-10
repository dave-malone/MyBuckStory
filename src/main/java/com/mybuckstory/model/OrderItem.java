package com.mybuckstory.model;

import java.math.BigDecimal;


public class OrderItem extends AbstractAuditable {

	private Order order;
	private SellableItem item;
	private Integer quantity = 1;
	
	public OrderItem(){}
	
	public OrderItem(SellableItem item, Integer quantity) {
		this.item = item;
		this.quantity = quantity;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public SellableItem getItem() {
		return item;
	}
	public void setItem(SellableItem item) {
		this.item = item;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public BigDecimal getTotal(){
		BigDecimal total = new BigDecimal(0);
		
		if(quantity != null && item != null){
			total = item.getSalePrice().multiply(new BigDecimal(quantity));
		}
		
		return total;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result
				+ ((quantity == null) ? 0 : quantity.hashCode());
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
		OrderItem other = (OrderItem) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}

	@Override
	public int compareTo(Object obj) {
		OrderItem that = (OrderItem)obj;
		return that.dateCreated.compareTo(this.dateCreated);
	}

	
}
