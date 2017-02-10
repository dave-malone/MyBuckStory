package com.mybuckstory.web.filter;

import org.hibernate.FlushMode;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class AuditInterceptorOpenSessionInViewFilter extends org.springframework.orm.hibernate3.support.OpenSessionInViewFilter {
	
	private String entityInterceptorBeanName;	
	
	public void setEntityInterceptorBeanName(String entityInterceptor) {
		this.entityInterceptorBeanName = entityInterceptor;
	}

	protected Interceptor getEntityInterceptor() {
		if (logger.isDebugEnabled()) {
			logger.debug("Using Interceptor '" + entityInterceptorBeanName + "' for Entity Interceptor");
		}
		WebApplicationContext wac =	WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		return (Interceptor) wac.getBean(entityInterceptorBeanName, Interceptor.class);
	}

	@Override
	protected Session getSession(SessionFactory sessionFactory)	throws DataAccessResourceFailureException {
		Session session = SessionFactoryUtils.getSession(sessionFactory, getEntityInterceptor(), null);
		FlushMode flushMode = getFlushMode();
		if (flushMode != null) {
			session.setFlushMode(flushMode);
		}		
		return session;
	}

}
