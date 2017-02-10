/**
 * 
 */
package com.mybuckstory.core.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.util.StopWatch;

import com.mybuckstory.dao.GenericHibernateDao;


/**
 * @author David Malone
 *
 */
public class DaoMethodInvocationTimer implements MethodInterceptor {

	private static final Logger logger = Logger.getLogger(DaoMethodInvocationTimer.class);
	/* (non-Javadoc)
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Object retVal = null;
		if(logger.isInfoEnabled()){
			String className = methodInvocation.getMethod().getDeclaringClass().getName();
			if(methodInvocation.getThis() instanceof GenericHibernateDao){
				GenericHibernateDao dao = (GenericHibernateDao)methodInvocation.getThis();
				className = "beanName(" + dao.getBeanName() + "):GenericHibernateDao";
			}
			String methodName = methodInvocation.getMethod().getName();
			Object[] args = methodInvocation.getArguments();
			
			
			String arguments = "";
			arguments += "(";
			if(args.length > 0){				
				for(int i = 0; i < args.length; i++){
					if(args[i] instanceof String){
						arguments += "\"";
						arguments += String.valueOf(args[i]);
						arguments += "\"";
					}else{
						arguments += String.valueOf(args[i]);
					}
					if((i + 1) != args.length){
						arguments += ", ";
					}
				}				
			}
			arguments += ")";
			
			String invocationName = className + "." + methodName + arguments;
			
			logger.debug("timing DAO method invocation"
					   + "\n\t - Class Name: " + className
					   + "\n\t - Method Name: " + methodName
					   + "\n\t - Argument values: " + arguments);
			
			StopWatch stopWatch = new StopWatch();			
			try{
				stopWatch.start("Timing DAO call; " + invocationName);
				retVal = methodInvocation.proceed();
			}finally{
				stopWatch.stop();
				logger.info("\n\t" + stopWatch.getTotalTimeMillis() + " ms to complete " + invocationName);
			}					
			
		}else{
			retVal = methodInvocation.proceed();
		}
	    return retVal;
		
	}

}
