package com.mybuckstory.core.interceptor;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

public class MethodCachingInterceptor implements MethodInterceptor {

	private static final Logger log = Logger.getLogger(MethodCachingInterceptor.class);
	private static final String CACHE_NAME = "serviceCache";
		
	private CacheManager cacheManager;
	

	public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
		final Cache cache = cacheManager.getCache(CACHE_NAME);
		
		final StringBuffer elementKey = new StringBuffer(methodInvocation.getMethod().getName());		

		final Object[] methodArgs = methodInvocation.getArguments();
		
		if(methodArgs != null){			
			elementKey.append("(");
			for(int i = 0; i < methodArgs.length; i++){
				elementKey.append(methodArgs[i] != null ? methodArgs[i].toString() : "null");
				if(i + 1 != methodArgs.length){
					elementKey.append(",");
				}
			}
			elementKey.append(")");						
		}

		log.debug("cache element key: " + elementKey);
		
		boolean methodInvocationProceed = false;
		boolean methodInvocationCache = false;

		Object methodReturn = null;

		if(cache != null){			
			final Element cacheElement = cache.get(elementKey.toString());
			if(cacheElement == null){
				methodInvocationProceed = true;
			}else{
				log.info("Cache Element Found");
				methodReturn = cacheElement.getValue();
				log.info("Using Cached Element for methodReturn.");
			}
			methodInvocationCache = true;
		}

		if(methodInvocationProceed){
			methodReturn = methodInvocation.proceed();
			if(methodInvocationCache){
				final Element newCacheElement = new Element(elementKey.toString(),	(Serializable) methodReturn);
				cache.put(newCacheElement);
				log.info("Created new CacheElement entry and stored in cache.");
			}
		}

		return methodReturn;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

}
