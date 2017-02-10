package com.mybuckstory.core.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.BuyerInformation;
import com.mybuckstory.model.Order;
import com.mybuckstory.model.OrderItem;
import com.mybuckstory.model.Payment;
import com.mybuckstory.model.SellableItem;
import com.mybuckstory.model.User;
import com.mybuckstory.util.UserUtil;


/**
 * 
 * @author Dave Malone
 * 
 * Uses the PayPal Express NVP APIs
 * 
 * @See {@linkplain https://cms.paypal.com/us/cgi-bin/?cmd=_render-content&content_ID=developer/e_howto_api_NVPAPIBasics}
 * @See {@linkplain https://cms.paypal.com/cms_content/US/en_US/files/developer/PayflowGateway_Guide.pdf}
 *
 */
@Service
@Transactional
public class PaymentService extends GenericMbsService<Payment> /*implements ApplicationListener<PaymentEvent>*/ {

	private String payPalServerUrl;
	private String payPalUsername;	
	private String serverUrl;
	
	
	public PaymentService(){
		this(null, null, null, null);
	}
	
	@Autowired
	public PaymentService(@Qualifier("paymentDao") GenericHibernateDao<Payment, Long> dao, @Value("${mbs.server.url}") String mbsServerUrl, @Value("${paypal.payment.server.url}") String payPalServerUrl, @Value("${paypal.payment.server.username}") String payPalUsername){
		super(Payment.class, dao);
		this.serverUrl = mbsServerUrl;
		this.payPalServerUrl = payPalServerUrl;
		this.payPalUsername = payPalUsername;
	}
	
	public String getCheckoutOnPayPalUrl(Order order) {
		Payment payment = order.getPayment();
		
		
		if (StringUtils.isBlank(this.payPalServerUrl) || StringUtils.isBlank(this.payPalUsername)) throw new IllegalStateException("Paypal misconfigured! You need to specify the Paypal server URL and/or account email. Refer to documentation.");

		Map<String, Object> commonParams = new HashMap<String, Object>();
		commonParams.put("buyerId", payment.getBuyerId());
		commonParams.put("transactionId", payment.getTransactionId());
		
		String notifyUrl = this.serverUrl + "paypal/ipnNotify";
		String successUrl = this.serverUrl + "paypal/success";
		String cancelUrl = this.serverUrl + "paypal/cancel";
		

		StringBuffer url = new StringBuffer(this.payPalServerUrl);
		url.append("?cmd=_xclick&");
		url.append("business=").append(this.payPalUsername).append("&");
		
		int itemNumber = 0;
		for(OrderItem orderItem: order.getOrderItems()){
			url.append("L_item_name").append(itemNumber).append("=").append(orderItem.getItem().getName()).append("&");
			url.append("L_item_number").append(itemNumber).append("=").append(orderItem.getItem().getPayPalItemNumber()).append("&");
			url.append("L_quantity").append(itemNumber).append("=").append(orderItem.getQuantity()).append("&");
			url.append("L_amount").append(itemNumber).append("=").append(orderItem.getTotal()).append("&");
			itemNumber++;
		}
		
		url.append("tax=").append(order.getTax()).append("&");
		url.append("currency_code=").append("USD").append("&");
		url.append("notify_url=").append(notifyUrl).append("&");
		url.append("return=").append(successUrl).append("&");
		url.append("cancel_return=").append(cancelUrl);
		return url.toString();
	}
	
	public Payment getPaymentByTransactionId(String transactionId){
		Payment payment = (Payment) super.getCurrentSession().createCriteria(Payment.class).add(Restrictions.eq("transactionId", transactionId)).uniqueResult();
		return payment;
	}
	
	public SellableItem getPaymentItemByItemNumber(String itemNumber){
		SellableItem paymentItem = (SellableItem) super.getCurrentSession().createCriteria(SellableItem.class).add(Restrictions.eq("itemNumber", itemNumber)).uniqueResult();
		return paymentItem;
	}
	
	public Payment processPayPalSuccessNotification(String transactionId, String itemNumber){
		User currentUser = UserUtil.getCurrentUser();
		
		if(currentUser == null){
			throw new IllegalStateException("You must login to use this action");
		}
		
		Payment payment = null;
		if (StringUtils.isNotBlank(transactionId)) {
			payment = this.getPaymentByTransactionId(transactionId);
		}else {
			//TODO - need all params to set on payment
			payment = new Payment();
			payment.setBuyerId(currentUser.getId());
			SellableItem paymentItem = this.getPaymentItemByItemNumber(itemNumber);
			if(paymentItem == null){
				throw new IllegalArgumentException(itemNumber + " is not a valid item number");
			}
			//TODO - calculate tax at the order level
//			payment.setTax(paymentItem.getAmount().multiply(taxRate).doubleValue());
			payment.setTax(payment.getTax());
			logger.debug("tax: " + payment.getTax());
//			payment.getPaymentItems().add(paymentItem);
		}		

		if (payment.getId() != null) logger.debug("Resuming existing transaction " + payment);
		
		return payment;
	}
	
	public Payment processPayPalIpnNotification(String queryString, String transactionId, String paypalTransactionId, String paymentStatus, String receiverEmail, Map<String, String> paypalArgs){
		logger.debug("Submitting query " + queryString + " to PayPal server " + payPalServerUrl);
		String result = postHttpRequestToPayPal(queryString);

		logger.debug("response received from PayPal IPN " + result);

		Payment payment = this.getPaymentByTransactionId(transactionId);

		if (payment != null && "VERIFIED".equalsIgnoreCase(result)) {
			if (!StringUtils.equals(receiverEmail, this.payPalUsername)) {
				logger.warn("WARNING: receiver_email parameter received from PayPal does not match configured e-mail. This request is possibly fraudulent! REQUEST INFO: ");
			} else {
				//request.setAttribute("payment", payment);
				
				if(!Payment.COMPLETE.equalsIgnoreCase(payment.getStatus()) && !Payment.CANCELLED.equalsIgnoreCase(payment.getStatus())) {
					if (StringUtils.isNotBlank(payment.getPaypalTransactionId()) && StringUtils.equals(payment.getPaypalTransactionId(), paypalTransactionId)) {
						logger.warn("WARNING: Request tried to re-use and old PayPal transaction id. This request is possibly fraudulent! REQUEST INFO: ");						
					} else if ("Completed".equalsIgnoreCase(paymentStatus)) {
						payment.setPaypalTransactionId(paypalTransactionId);
						payment.setStatus(Payment.COMPLETE);
						updateBuyerInformation(payment, paypalArgs);
						logger.info("Verified payment as COMPLETE");
					} else if ("Pending".equalsIgnoreCase(paymentStatus)) {
						payment.setPaypalTransactionId(paypalTransactionId);
						payment.setStatus(Payment.PENDING);
						updateBuyerInformation(payment, paypalArgs);
						logger.info("Verified payment as PENDING");
					} else if ("Failed".equalsIgnoreCase(paymentStatus)) {
						payment.setPaypalTransactionId(paypalTransactionId);
						payment.setStatus(Payment.FAILED);
						updateBuyerInformation(payment, paypalArgs);
						logger.info("Verified payment as FAILED");
					}
				}
				super.update(payment);
				
				return payment;
			}
		}else {
			logger.debug("Error with PayPal IPN response: " + result + " and Payment: " + payment.getTransactionId());
		}
		
		return null;
	}
	
	public void updateBuyerInformation(Payment payment, Map<String, String> parameters) {
		BuyerInformation buyerInfo = payment.getBuyerInformation() != null ? payment.getBuyerInformation(): new BuyerInformation();
		buyerInfo.populateFromPaypal(parameters);
		payment.setBuyerInformation(buyerInfo);
	}
	
	//TODO - these should probably live in the payment service
		private String postHttpRequestToPayPal(String queryString){
			try {
				URL url = new URL(payPalServerUrl);
				HttpsURLConnection  conn = (HttpsURLConnection) url.openConnection();
				httpPostRequest(queryString, conn, 6000);
				String result = getResponseAsString(conn);
				return result;
			} catch (MalformedURLException e) {
				logger.error("bad url: " + payPalServerUrl, e);
			} catch (IOException e) {
				logger.error("failed to post request to pay pal server: " + queryString, e);
			}
			
			return "";
		}

		//TODO - these should probably live in the payment service
		private String getResponseAsString(HttpURLConnection urlConnection) throws IOException {
			final InputStream is = urlConnection.getInputStream();
			final StringBuffer strbuf = new StringBuffer();
			final byte[] buffer = new byte[1024 * 4];
			try {
				int n = 0;
				while (-1 != (n = is.read(buffer))) {
					strbuf.append(new String(buffer, 0, n));
				}			
			} catch (IOException e) {
				logger.error("An error occurred while reading the response from the url connection", e);			
			}finally{
				is.close();
			}
			
			String response = strbuf.toString();
			return response;
		}

		//TODO - these should probably live in the payment service
		private void httpPostRequest(final String xml, final HttpURLConnection urlConnection, final int connectionTimeoutInMillis) throws IOException {
			final String xmlRequest = "xml=" + URLEncoder.encode(xml, "UTF8");
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);
			urlConnection.setConnectTimeout(connectionTimeoutInMillis);
			urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
			out.write(xmlRequest);
			out.flush();
			out.close();
		}
	
	/*
	@Override
	public void onApplicationEvent(PaymentEvent event) {
		logger.debug("Receivedpayment event " + event);
		
		try{
			create(event.getMemberFeedItem());
		}catch(Exception e){
			logger.error("excpetion caught while creating member feed item", e);
		}catch(Throwable t){
			logger.error("throwable caught while creating member feed item", t);
		}
	}
	*/
	
	
}
