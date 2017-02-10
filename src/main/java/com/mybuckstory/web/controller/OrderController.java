package com.mybuckstory.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mybuckstory.core.service.OrderService;
import com.mybuckstory.core.service.SellableItemService;
import com.mybuckstory.core.service.ShoppingCartService;
import com.mybuckstory.model.Order;
import com.mybuckstory.web.ShoppingCart;


@Controller
@RequestMapping(value="/order")
public class OrderController {

	private static final Logger log = Logger.getLogger(ImageController.class);
	
	private String payPalServerUrl;
	private String payPalUsername;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SellableItemService itemService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;

	@RequestMapping(value="/cart/{itemId}", method = RequestMethod.PUT)
	@ResponseBody
	public String addOrUpdateOrderItemInCart(@PathVariable(value="itemId") Long itemId, @RequestParam(value="quantity", required=false, defaultValue="1") Integer quantity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		final ShoppingCart cart = new ShoppingCart(this.shoppingCartService, request, response);
		cart.addOrUpdate(itemId, quantity);
		//TODO - need to forward the request and response
		return "success";
	}

	
	@RequestMapping(value="/cart/{itemId}", method = RequestMethod.DELETE)
	@ResponseBody
	public String removeOrderItemFromCart(@PathVariable(value="itemId") Long itemId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		final ShoppingCart cart = new ShoppingCart(this.shoppingCartService, request, response);
		cart.remove(itemId);
		//TODO - need to forward the request and response
		return "success";
	}
	
	@RequestMapping(value="/cart/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCart(@RequestParam(value="itemId") Long itemId, @RequestParam(value="quantity", required=false, defaultValue="1") Integer quantity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		final ShoppingCart cart = new ShoppingCart(this.shoppingCartService, request, response);
		cart.addOrUpdate(itemId, quantity);
		
		final Map<String, Object> responseObject = new HashMap<String, Object>();
		responseObject.put("orderItemTotal", cart.getOrderItemByItemId(itemId).getTotal());
		responseObject.put("orderSubTotal", cart.getSubtotal());
		
		return responseObject;		
	}
	
	@RequestMapping(value="/cart", method = RequestMethod.GET)
	public ModelAndView viewCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView("cart");		
		
		final ShoppingCart cart = new ShoppingCart(this.shoppingCartService, request, response);		
		mav.addObject("cart", cart.getOrderItems());
		mav.addObject("subTotal", cart.getSubtotal());
		
		return mav;
	}
	
	@RequestMapping(value="/checkout", method = RequestMethod.GET)
	public void checkout(final BindingResult result, HttpServletRequest request, HttpServletResponse response) throws IOException {
		//TODO - continue to checkout view
	}
	
	@RequestMapping(value="/submit", method = RequestMethod.POST)
	public void submitOrder(final BindingResult result, HttpServletRequest request, HttpServletResponse response) throws IOException {
		//TODO - save order
		//TODO - forward to paypal controller
		//TODO - even after the order goes through paypal, we need to fulfill the order and add tracking numbers to the order so the user can track it
	}
	
	@RequestMapping(value="/cancel/{orderId}", method = RequestMethod.POST)
	public void cancel(@PathVariable(value="orderId") Long orderId, final BindingResult result, HttpServletRequest request, HttpServletResponse response) throws IOException {
		orderService.cancel(orderId);
		//TODO - continue to 'canceled' view
	}
	
	@RequestMapping(value="/track/{orderId}", method = RequestMethod.GET)
	public void track(@PathVariable(value="orderId") Long orderId, final BindingResult result, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Order order = orderService.getById(orderId);
		//TODO - continue to 'track' view
	}
	
	@RequestMapping(value="/history", method = RequestMethod.GET)
	public void orderHistory(final BindingResult result, HttpServletRequest request, HttpServletResponse response) throws IOException {
		//TODO - get all orders for the current user and display them to the user
	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public void list() throws IOException {
		//TODO - get all orders 
	}	
	
}
