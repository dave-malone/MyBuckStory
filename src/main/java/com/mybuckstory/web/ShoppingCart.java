package com.mybuckstory.web;


import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.mybuckstory.core.service.ShoppingCartService;
import com.mybuckstory.model.OrderItem;
import com.mybuckstory.model.SellableItem;
import com.mybuckstory.util.UserUtil;

public class ShoppingCart {

	private static final int EXPIRY_14_DAYS_AS_SECONDS = 60 * 60 * 24 * 14;
	private static final String SERIALIZED_CART_DELIMITER = ";";
	private static final String CART_SESSION_KEY = "cart";
	private static final String CART_COOKIE_NAME = "mbs_shopping_cart";
	
	private final ShoppingCartService shoppingCartService;
	private final HttpServletRequest request;
	private final HttpSession session;
	private final HttpServletResponse response;
	
	public ShoppingCart(ShoppingCartService shoppingCartService, HttpServletRequest request, HttpServletResponse response){
		this.shoppingCartService = shoppingCartService;
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
	

	public void addOrUpdate(Long itemId, Integer quantity) {
		SortedSet<OrderItem> cart = getOrderItems();
		
		//check to see if an order item already exists 
		OrderItem orderItem = getOrderItemByItemId(itemId);
		
		if(orderItem == null){
			SellableItem item = shoppingCartService.getItemById(itemId);
			orderItem = new OrderItem(item, quantity);
		}else{
			orderItem.setQuantity(quantity);
		}	
		
		cart.add(orderItem);	
		updateOrderItems(cart);
	}


	public void remove(Long itemId) {
		SortedSet<OrderItem> cart = getOrderItems();
		
		OrderItem orderItem = getOrderItemByItemId(itemId);
		
		if(orderItem != null){
			cart.remove(orderItem);
		}
		
		updateOrderItems(cart);
	}
	
	public BigDecimal getSubtotal(){
		BigDecimal subTotal = new BigDecimal(0);
		
		for(OrderItem orderItem : getOrderItems()){
			subTotal = subTotal.add(orderItem.getTotal());
		}
		
		return subTotal;
	}
	
	public OrderItem getOrderItemByItemId(Long itemId) {
		OrderItem orderItem = null;
		for(OrderItem orderItemInCart : getOrderItems()){
			if(itemId.equals(orderItemInCart.getItem().getId())){
				orderItem = orderItemInCart;
				break;
			}
		}
		return orderItem;
	}
	
	private void updateOrderItems(SortedSet<OrderItem> orderItems) {
		if(!UserUtil.isAuthenticated()){
			//if the current user isn't authenticated, we should also persist their cart into a cookie so when they revisit the site they will be able to continue checkout, in case they abandoned their session
			setCartCookie(orderItems);
		}else{
			//if the current user is authenticated, then we can persist their cart, and continue to email them with reminders or ask why they didn't check out
			saveShoppingCartInDB(orderItems);
		}
		
		//always set the value in session
		session.setAttribute(CART_SESSION_KEY, orderItems);
	}

	public SortedSet<OrderItem> getOrderItems() {
		final Object sessionAttr = session.getAttribute(CART_SESSION_KEY);
		final SortedSet<OrderItem> orderItems = sessionAttr != null ? (SortedSet<OrderItem>)sessionAttr : new TreeSet<OrderItem>();
		return orderItems;
	}
	
	public void loadPersistedCart() {
		if(getOrderItems().isEmpty()){
			if(!UserUtil.isAuthenticated()){
				loadCartFromCartCookie();
			}else{
				loadCartFromDB();
			}
		}
	}
	
	private void saveShoppingCartInDB(SortedSet<OrderItem> orderItems) {
		final String serializedCart = serializeCart(orderItems);
		this.shoppingCartService.save(serializedCart);
	}
	
	private void loadCartFromDB(){
		final String serializedCartValue = this.shoppingCartService.getCart().getSerializedCart();	
		final SortedSet<OrderItem> orderItems = deserializeCart(serializedCartValue);
		
		session.setAttribute(CART_SESSION_KEY, orderItems);		
	}
	
	private void loadCartFromCartCookie(){		
		final Cookie cartCookie = getCartCookie();		
		final String serializedCartValue = cartCookie.getValue();		
		final SortedSet<OrderItem> orderItems = deserializeCart(serializedCartValue);
		
		session.setAttribute(CART_SESSION_KEY, orderItems);		
	}
	
	
	private String serializeCart(final Collection<OrderItem> orderItems) {
		final StringBuffer serializedCart = new StringBuffer();
		for(Iterator<OrderItem> iter = orderItems.iterator(); iter.hasNext();){
			final OrderItem orderItem = iter.next();
			serializedCart.append(orderItem.getItem().getId()).append("=").append(orderItem.getQuantity());
			if(iter.hasNext()){
				serializedCart.append(SERIALIZED_CART_DELIMITER);
			}
		}
		
		String serializedCartValue = serializedCart.toString();
		return serializedCartValue;
	}
	
	private SortedSet<OrderItem> deserializeCart(final String serializedCartValue) {
		final SortedSet<OrderItem> orderItems = new TreeSet<OrderItem>();
		if(StringUtils.isNotBlank(serializedCartValue)){
			final String serializedCart = serializedCartValue;
			final String[] itemQuantities = serializedCart.split(SERIALIZED_CART_DELIMITER);
			for(String itemQuantity : itemQuantities){
				final OrderItem orderItem = new OrderItem();
				final String[] splitOrderItem = itemQuantity.split("=");
				orderItem.setItem(shoppingCartService.getItemById(Long.valueOf(splitOrderItem[0])));
				orderItem.setQuantity(Integer.valueOf(splitOrderItem[1]));
				orderItems.add(orderItem);
			}			
		}
		return orderItems;
	}
	
	
	private void setCartCookie(final Collection<OrderItem> orderItems) {
		final Cookie cartCookie = getCartCookie();		
		final String serializedCartValue = serializeCart(orderItems);		
		cartCookie.setValue(serializedCartValue);
		//14 days max value
		cartCookie.setMaxAge(EXPIRY_14_DAYS_AS_SECONDS);
		response.addCookie(cartCookie);
	}

	private Cookie getCartCookie() {
		final Cookie[] cookies = request.getCookies();
		
		Cookie cartCookie = null;
		
		if(cookies != null){
			for(Cookie cookie : cookies){
				if(CART_COOKIE_NAME.equals(cookie.getName())){
					cartCookie = cookie;
					break;
				}
			}
		}
		
		if(cartCookie == null){
			cartCookie = new Cookie(CART_COOKIE_NAME, "");			
		}
		
		return cartCookie;
	}
	
}
