package com.mybuckstory.core.service;

import java.math.BigDecimal;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Order;

@Service
@Transactional
public class OrderService extends GenericMbsService<Order> /*implements ApplicationListener<PaymentEvent>*/ {

	//TODO - calculate tax rate per state?  Or do we verify that we even have to pay tax since we're an online presence only?
	private static final BigDecimal taxRate = BigDecimal.valueOf(0.07275d);
	
	private UserService userService;
	
	public OrderService(){
		this(null);
	}
	
	@Autowired
	public OrderService(@Qualifier("orderDao") GenericHibernateDao<Order, Long> dao){
		super(Order.class, dao);
	}

	public void cancel(Long orderId) {
		Order order = getById(orderId);
		order.setStatus(Order.CANCELLED);
		update(order);
		//TODO - email admins & customer, let them know order has been canceled
		
		//TODO - how will we handle repayment?
	}

	public Order getByTransactionId(String transactionId) {
		return (Order)this.getCurrentSession().createCriteria(Order.class)
				.createCriteria("payment").add(Restrictions.eq("transactionId", transactionId))
				.uniqueResult();
	}	
	
}
