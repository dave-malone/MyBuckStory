package com.mybuckstory.core.service;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.SellableItem;
import com.mybuckstory.model.ShoppingCart;
import com.mybuckstory.model.User;
import com.mybuckstory.util.UserUtil;

@Service
@Transactional
public class ShoppingCartService extends GenericMbsService<ShoppingCart> /*implements ApplicationListener<PaymentEvent>*/ {

	private final SellableItemService itemService;
	
	public ShoppingCartService(){
		this(null, null);
	}
	
	@Autowired
	public ShoppingCartService(@Qualifier("shoppingCartDao") GenericHibernateDao<ShoppingCart, Long> dao, SellableItemService itemService){
		super(ShoppingCart.class, dao);
		this.itemService = itemService;
	}
	
	public SellableItem getItemById(Long id){
		return this.itemService.getById(id);
	}

	public void save(String serializedCart) {
		if(UserUtil.isAuthenticated()){
			ShoppingCart cart = getCart();
			cart.setSerializedCart(serializedCart);
			super.saveOrUpdate(cart);
		}
	}

	public ShoppingCart getCart() {
		ShoppingCart shoppingCart = new ShoppingCart();
		if(UserUtil.isAuthenticated()){
			User currentUser = UserUtil.getCurrentUser();
			ShoppingCart persistedShoppingCart = (ShoppingCart) getCurrentSession().createCriteria(ShoppingCart.class)
				.add(Restrictions.eq("createdBy", currentUser))
				.uniqueResult();
			
			if(persistedShoppingCart != null) shoppingCart = persistedShoppingCart;
		}
		
		return shoppingCart;
	}	
	
}
