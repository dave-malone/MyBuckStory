package com.mybuckstory.web.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mybuckstory.core.service.OrderService;
import com.mybuckstory.core.service.PaymentService;
import com.mybuckstory.model.Order;
import com.mybuckstory.model.Payment;
import com.mybuckstory.web.validator.PaymentValidator;


@Controller
public class PayPalController {

	//static allowedMethods = [buy: 'POST', ipnNotify: 'POST']
	private static final Logger log = Logger.getLogger(PayPalController.class);
	
	@Autowired
	@Value("${paypal.payment.server.url}")
	private String payPalServerUrl;
	
	@Autowired
	@Value("${paypal.payment.server.username}")
	private String payPalUsername;

	
	@Autowired		
	private OrderService orderService;
	
	@Autowired
	private PaymentService paymentService;

	/**
	 * 
	 * @param orderId
	 * @param transactionId - used to resume a transaction canceled or not completed from the PayPal side
	 * @param originatingUrl - used to redirect users back to the screen they came from in case there were failures processing their order
	 * @param result
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/paypal/buy", method = RequestMethod.POST)
	public void buy(
			@RequestParam(value="orderId", required=false) Long orderId,
			@RequestParam(value="transactionId", required=false) String transactionId, 
			@RequestParam(value="originalUrl", required=false) String originatingUrl,			 
			final BindingResult result, HttpServletRequest request, HttpServletResponse response) throws IOException {

		Order order = null;
		
		if(StringUtils.isNotBlank(transactionId)){
			order = this.orderService.getByTransactionId(transactionId);
		}else if(orderId != null){
			order = this.orderService.getById(orderId);
		}
		
		Payment payment = order.getPayment();
		
		PaymentValidator validator = new PaymentValidator();		
		validator.validate(payment, result);
		
		if(!result.hasErrors()){
			request.setAttribute("payment", payment);
			paymentService.create(payment);
			String checkoutOnPayPalUrl = paymentService.getCheckoutOnPayPalUrl(order);
			log.debug("Redirection to PayPal with URL: " + checkoutOnPayPalUrl);

			response.sendRedirect(checkoutOnPayPalUrl.toString());
		}else {
			log.debug("failed to save payment");
			response.sendRedirect(originatingUrl);
		}
	}

	
	
	@RequestMapping(value="/paypal/ipnNotify", method = RequestMethod.POST)
	public String ipnNotify(
			@RequestParam(value="transactionId", required=false) String transactionId, 
			@RequestParam(value="receiver_email", required=false) String receiverEmail,
			@RequestParam(value="payment_status", required=false) String paymentStatus,
			@RequestParam(value="txn_id", required=false) String paypalTransactionId,
			HttpServletRequest request){
		
		log.debug("Received IPN notification from PayPal Server");
		
			
		if (StringUtils.isBlank(payPalServerUrl) || StringUtils.isBlank(this.payPalUsername)) throw new IllegalStateException("Paypal misconfigured! You need to specify the Paypal server URL and/or account email. Refer to documentation.");

		StringBuffer queryString = new StringBuffer("cmd=_notify-validate");
		
		Enumeration enumer = request.getParameterNames();
		while(enumer.hasMoreElements()) {
		    String paramName = (String)enumer.nextElement();
		    String paramValue = request.getParameter(paramName);
		    queryString.append("&").append(paramName).append("=").append(URLEncoder.encode(paramValue));
		}

		log.debug("Submitting query " + queryString.toString() + " to PayPal server " + payPalServerUrl);
		
		Payment payment = paymentService.processPayPalIpnNotification(queryString.toString(), transactionId, paypalTransactionId, paymentStatus, receiverEmail, request.getParameterMap());		
		request.setAttribute("payment", payment);

		return "OK"; // Paypal needs a response, otherwise it will send the notification several times!
	}

	public void success(
			@RequestParam(value="transactionId", required=false) String transactionId,
			@RequestParam(value="returnAction", required=false) String returnAction,
			@RequestParam(value="returnController", required=false) String returnController,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Payment payment = paymentService.getPaymentByTransactionId(transactionId);
		log.debug("Success notification received from PayPal for " + payment + " with transaction id " + transactionId);
		
		if (payment != null){
			request.setAttribute("payment", payment);
			if (!Payment.COMPLETE.equalsIgnoreCase(payment.getStatus())) {
				payment.setStatus(Payment.COMPLETE);
				paymentService.saveOrUpdate(payment);
			}

			if (StringUtils.isNotBlank(returnAction) || StringUtils.isNotBlank(returnController)) {
				Map<String, String> args = new HashMap<String, String>();
				if (StringUtils.isNotBlank(returnAction)) args.put("action", returnAction);
				if (StringUtils.isNotBlank(returnAction)) args.put("controller", returnController);
				
				//TODO - this was meant to redirect to a different controller and action along with all of the params - redirect(args)
			}else {
				//TODO - direct the user to the success screen
				//return [payment: payment]
			}
		}else {
			response.sendError(403);
		}
	}

	
	@RequestMapping(value="/paypal/cancel", method = RequestMethod.GET)
	public void cancel(
			@RequestParam(value="transactionId", required=false) String transactionId,
			@RequestParam(value="cancelAction", required=false) String cancelAction,
			@RequestParam(value="cancelController", required=false) String cancelController,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		Payment payment = paymentService.getPaymentByTransactionId(transactionId);
		log.debug("Cancel notification received from PayPal for " + payment + " with transaction id " + transactionId);
		
		if (payment != null) {
			request.setAttribute("payment", payment);
			if (!Payment.COMPLETE.equalsIgnoreCase(payment.getStatus())) {
				payment.setStatus(Payment.CANCELLED);
				paymentService.saveOrUpdate(payment);
				
				if (StringUtils.isNotBlank(cancelAction) || StringUtils.isNotBlank(cancelController)) {
					Map<String, String> args = new HashMap<String, String>();
					if (StringUtils.isNotBlank(cancelAction)) args.put("action", cancelAction);
					if (StringUtils.isNotBlank(cancelAction)) args.put("controller", cancelController);
					
					//TODO - this was meant to redirect to a different controller and action along with all of the params - redirect(args)
				}else {
					//TODO - direct the user to the cancel screen
					//return [payment: payment]
				}
			}else {
				response.sendError(403);
			}
		}else {
			response.sendError(403);
		}
	}
	
	
	
	
}
