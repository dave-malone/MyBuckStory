package com.mybuckstory.dao;

import java.util.List;

import com.mybuckstory.model.Role;

public class RoleDaoTest extends AbstractMbsDaoTest {

	
	public void testSave(){		
		Role role = new Role();
		role.setName("ROLE_TEST");
		this.roleDao.save(role);
		assertNotNull(role.getId());		
	}
	
	public void testLoadById(){	
		Role role = new Role();
		role.setName("ROLE_TEST");
		this.roleDao.save(role);
		assertNotNull(role.getId());
		
		assertNotNull(roleDao.loadById(role.getId()));		
	}
	
	public void testGetById(){	
		Role role = new Role();
		role.setName("ROLE_TEST");
		this.roleDao.save(role);
		assertNotNull(role.getId());
		
		assertNotNull(roleDao.getById(role.getId()));		
	}
	
	public void testDelete(){	
		Role role = new Role();
		role.setName("ROLE_TEST");
		this.roleDao.save(role);
		assertNotNull(role.getId());
		
		assertNotNull(roleDao.getById(role.getId()));
		
		roleDao.delete(role);
		
		assertNull(roleDao.getById(role.getId()));
	}
	
	public void testFindAll(){
		List all = roleDao.findAll();
		assertNotNull(all);
		logger.debug("Find all found " + all.size());
	}
}
