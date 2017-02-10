package com.mybuckstory.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Role;
@Service
@Transactional
public class RoleService extends GenericMbsService<Role> {

	public RoleService(){
		this(null);
	}	
	
	@Autowired
	public RoleService(@Qualifier("roleDao") GenericHibernateDao<Role, Long> dao){
		super(Role.class, dao);
	}
	
	@Transactional(readOnly=true)
	public Role findByName(final String roleName){
		return (Role)getCurrentSession().createQuery("from Role role where role.name = :name")
					.setParameter("name", roleName)
					.setCacheable(true)
					.uniqueResult();
	}
}
