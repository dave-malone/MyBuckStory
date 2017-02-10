package com.mybuckstory.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Order extends AbstractAuditable{

	public static final String PENDING = "PENDING";
	public static final String FULFILLED = "FULFILLED";
	public static final String DELIVERED = "DELIVERED";
	public static final String COMPLETE = "COMPLETE";
	public static final String CANCELLED = "CANCELLED";
	public static final String RETURNED = "RETURNED";
	
	private BigDecimal total;
	private BigDecimal tax;
	private BigDecimal shippingAndHandling;
	private Set<OrderItem> orderItems = Collections.synchronizedSet(new HashSet<OrderItem>());
	private Payment payment;
	private BuyerInformation buyerInformation;	
	private String status = PENDING;
	
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(Set<OrderItem> items) {
		this.orderItems = items;
	}
	public BuyerInformation getBuyerInformation() {
		return buyerInformation;
	}
	public void setBuyerInformation(BuyerInformation buyerInformation) {
		this.buyerInformation = buyerInformation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public BigDecimal getShippingAndHandling() {
		return shippingAndHandling;
	}
	public void setShippingAndHandling(BigDecimal shippingAndHandling) {
		this.shippingAndHandling = shippingAndHandling;
	}	
	
}
