package com.mybuckstory.model;

import java.util.Currency;

public class Payment extends AbstractAuditable{

	public static final String PENDING = "PENDING";
	public static final String INVALID = "INVALID";
	public static final String FAILED = "FAILED";
	public static final String COMPLETE = "COMPLETE";
	public static final String CANCELLED = "CANCELLED";

	private	String transactionId;
	private String paypalTransactionId;
	private String status = PENDING;

	private Double tax = 0.0; // tax applies to entire payment, not to each item!

	private Currency currency = Currency.getInstance("USD"); // default to USD
	private Long buyerId;

	private Order order;
			

	public String toString() { 
		return "Payment: " + (transactionId != null ? transactionId : "not saved");
	}


	public String getTransactionId() {
		return transactionId;
	}



	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}



	public String getPaypalTransactionId() {
		return paypalTransactionId;
	}



	public void setPaypalTransactionId(String paypalTransactionId) {
		this.paypalTransactionId = paypalTransactionId;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public Double getTax() {
		return tax;
	}



	public void setTax(Double tax) {
		this.tax = tax;
	}



	public Currency getCurrency() {
		return currency;
	}



	public void setCurrency(Currency currency) {
		this.currency = currency;
	}



	public Long getBuyerId() {
		return buyerId;
	}



	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}



	public BuyerInformation getBuyerInformation() {
		return order != null ? order.getBuyerInformation() : null;
	}



	public void setBuyerInformation(BuyerInformation buyerInformation) {
		if(order != null){
			order.setBuyerInformation(buyerInformation);
		}
	}


	public Order getOrder() {
		return order;
	}


	public void setOrder(Order order) {
		this.order = order;
	}

	/*
	transient beforeInsert = {
	//TODO - when do we set this transaction id, used for paypal?
			transactionId = "TRANS-$buyerId-${System.currentTimeMillis()}"
		}
			static constraints = {
				
			}
	*/
}
