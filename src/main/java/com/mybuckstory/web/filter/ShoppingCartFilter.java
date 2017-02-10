package com.mybuckstory.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mybuckstory.core.service.ShoppingCartService;
import com.mybuckstory.web.ShoppingCart;
import com.mybuckstory.web.spring.SpringHelper;

public class ShoppingCartFilter implements Filter {

	private SpringHelper springHelper;
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.springHelper = SpringHelper.getInstance(filterConfig.getServletContext());
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest servletRequest = (HttpServletRequest)request;
		
		final ShoppingCartService shoppingCartService = springHelper.getBean(ShoppingCartService.class);		
		final ShoppingCart shoppingCart = new ShoppingCart(shoppingCartService, servletRequest, (HttpServletResponse)response);
		shoppingCart.loadPersistedCart();
		
		request.setAttribute("cart", shoppingCart.getOrderItems());
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

}
