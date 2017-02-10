package com.mybuckstory.model;

import java.util.Map;

public class BuyerInformation extends AbstractAuditable{

	private Payment payment;
	
	private String uniqueCustomerId;

	private String firstName;
	private String lastName;
	private String companyName;
	private String receiverName; // included when the customer provides a Gift Address
	private String email;
	private String street;
	private String zip;
	private String city;
	private String state;
	private String country;
	private String countryCode;
	private String phoneNumber;

	private boolean addressConfirmed;
	
	public void populateFromPaypal(Map<String, String> paypalArgs) {
		this.uniqueCustomerId = paypalArgs.get("payer_id");
		this.firstName = paypalArgs.get("first_name");
		this.lastName = paypalArgs.get("last_name");
		this.companyName = paypalArgs.get("payer_business_name");
		this.receiverName = paypalArgs.get("address_name");
		this.email = paypalArgs.get("payer_email");
		this.street = paypalArgs.get("address_street");
		this.zip = paypalArgs.get("address_zip");
		this.city = paypalArgs.get("address_city");
		this.state = paypalArgs.get("address_state");
		this.country = paypalArgs.get("address_country");
		this.countryCode = paypalArgs.get("address_country_code");
		this.phoneNumber = paypalArgs.get("contact_phone");
		this.addressConfirmed = ("confirmed".equalsIgnoreCase(paypalArgs.get("address_status")));
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public String getUniqueCustomerId() {
		return uniqueCustomerId;
	}

	public void setUniqueCustomerId(String uniqueCustomerId) {
		this.uniqueCustomerId = uniqueCustomerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isAddressConfirmed() {
		return addressConfirmed;
	}

	public void setAddressConfirmed(boolean addressConfirmed) {
		this.addressConfirmed = addressConfirmed;
	}

	/*static constraints = { // everything nullable - need to store this object even when values are missing!
		uniqueCustomerId nullable: true, blank: true
		firstName nullable: true, blank: true
		lastName nullable: true, blank: true
		companyName nullable: true, blank: true
		receiverName nullable: true, blank: true
		email nullable: true, blank: true
		street nullable: true, blank: true
		zip nullable: true, blank: true
		city nullable: true, blank: true
		state nullable: true, blank: true
		country nullable: true, blank: true
		countryCode nullable: true, blank: true
		phoneNumber nullable: true, blank: true
	}*/	
	
}
