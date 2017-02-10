package com.mybuckstory.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import com.mybuckstory.model.Auditable;
import com.mybuckstory.model.User;
import com.mybuckstory.util.UserUtil;


public class AuditInterceptor extends EmptyInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 790646426607835502L;
	private static final Logger logger = Logger.getLogger(AuditInterceptor.class);
	private int updates;
	private int creates;
	private int loads;

	public AuditInterceptor(){
		logger.debug("Instantiating...");
	}
	
	@Override
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		// do nothing
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
		logger.debug("Calling on flush dirty");
		boolean updated = false;
		if (entity instanceof Auditable) {
			updates++;
			for (int i = 0; i < propertyNames.length; i++) {
				if ("lastUpdated".equals(propertyNames[i])) {
					logger.info("Setting last updated");
					currentState[i] = new Date();
					updated = true;
				}else if("lastUpdatedBy".equalsIgnoreCase(propertyNames[i])){
					logger.info("Setting last updated by");
					try{
						currentState[i] = getCurrentUser();
						updated = true;
					}catch(Exception e){
						logger.warn("Unable to set last updated by: " + e.getMessage());
					}
				}
			}
		}
		return updated;
	}

	private User getCurrentUser() {
		User currentUser = UserUtil.getCurrentUser();
		if(currentUser == null){
			throw new RuntimeException("Current user was null!");
		}
		return currentUser;
	}

	@Override
	public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		logger.debug("Calling onLoad");
		if (entity instanceof Auditable) {
			loads++;
		}
		return false;
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		logger.debug("Calling onSave");
		boolean updated = false;
		if (entity instanceof Auditable) {
			creates++;
			for (int i = 0; i < propertyNames.length; i++) {				
				if ("lastUpdated".equals(propertyNames[i])) {
					logger.info("Setting last updated");
					state[i] = new Date();
					updated = true;
				}else if("dateCreated".equalsIgnoreCase(propertyNames[i])){
					logger.info("Setting date created");
					if(state[i] == null){
						state[i] = new Date();
						updated = true;
					}
				}else if("lastUpdatedBy".equalsIgnoreCase(propertyNames[i])){
					logger.info("Setting last updated by");
					state[i] = getCurrentUser();
					updated = true;
				}else if("createdBy".equalsIgnoreCase(propertyNames[i])){
					logger.info("Setting created by");
					if(state[i] == null){
						state[i] = getCurrentUser();
						updated = true;
					}
				}
			}
		}
		return updated;
	}

	@Override
	public void afterTransactionCompletion(Transaction tx) {
		if (tx.wasCommitted()) {
			logger.info("Creations: " + creates + ", Updates: " + updates + ", Loads: " + loads);
		}
		updates = 0;
		creates = 0;
		loads = 0;
	}
	
	

}
