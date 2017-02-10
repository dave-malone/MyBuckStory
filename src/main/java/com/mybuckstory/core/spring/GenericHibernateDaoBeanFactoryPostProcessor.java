package com.mybuckstory.core.spring;

import java.util.Map;

import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.hibernate.EntityMode;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import com.mybuckstory.dao.GenericHibernateDao;

@Component
public class GenericHibernateDaoBeanFactoryPostProcessor implements BeanFactoryPostProcessor{

	private static final Logger logger = Logger.getLogger(GenericHibernateDaoBeanFactoryPostProcessor.class);
	
	@Override
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException{
		final SessionFactory sessionFactory = beanFactory.getBean(SessionFactory.class);
		final Map<String, ClassMetadata> allClassMetaData = sessionFactory.getAllClassMetadata();
		
		for(ClassMetadata classMetadata : allClassMetaData.values()){
			final Class<?> mappedClass = classMetadata.getMappedClass(EntityMode.POJO);
			logger.debug("mapped class " + mappedClass.getName());
			
			final String beanNamePrefix = WordUtils.uncapitalize(mappedClass.getSimpleName());
			
			final String daoBeanName = beanNamePrefix + "Dao";
			final GenericHibernateDao dao = new GenericHibernateDao(mappedClass, sessionFactory);
			logger.debug("registering singleton " + daoBeanName);
			beanFactory.registerSingleton(daoBeanName, dao);
			beanFactory.initializeBean(dao, daoBeanName);			
		}
	}

}