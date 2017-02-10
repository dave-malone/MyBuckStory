package com.mybuckstory.dao;

import java.util.List;

import com.mybuckstory.model.Profile;
import com.mybuckstory.model.User;

public class UserDaoTest extends AbstractMbsDaoTest {	
	
	public void testSave(){	
		User p = new User();
		
		p.setProfile(new Profile());
		
		p.setUsername("dave");
		p.setPassword("admin");		
		
		userDao.save(p);
		userDao.refresh(p);
		assertNotNull(p);
		assertNotNull(p.getId());
		assertNotNull(p.getProfile());
		assertNotNull(p.getProfile().getId());
		
	}
	
	public void testLoadById(){	
		User p = new User();
		
		p.setProfile(new Profile());
		
		p.setUsername("dave");
		p.setPassword("admin");		
		
		userDao.save(p);
		userDao.refresh(p);
		assertNotNull(p);
		assertNotNull(p.getId());
		assertNotNull(p.getProfile());
		assertNotNull(p.getProfile().getId());
		
		assertNotNull(userDao.loadById(p.getId()));
	}
	
	public void testGetById(){	
		User p = new User();
		
		p.setProfile(new Profile());
		
		p.setUsername("dave");
		p.setPassword("admin");		
		
		userDao.save(p);
		userDao.refresh(p);
		assertNotNull(p);
		assertNotNull(p.getId());
		assertNotNull(p.getProfile());
		assertNotNull(p.getProfile().getId());
		
		assertNotNull(userDao.getById(p.getId()));
	}	
	
	public void testDelete(){	
		User p = new User();
		
		p.setProfile(new Profile());
		
		p.setUsername("dave");
		p.setPassword("admin");		
		
		userDao.save(p);
		userDao.refresh(p);
		assertNotNull(p);
		assertNotNull(p.getId());
		assertNotNull(p.getProfile());
		assertNotNull(p.getProfile().getId());
		
		assertNotNull(userDao.getById(p.getId()));
		
		userDao.delete(p);
		
		assertNull(userDao.getById(p.getId()));
	}
	
	public void testFindAll(){
		List all = userDao.findAll();
		assertNotNull(all);
		logger.debug("Find all found " + all.size());
	}
	
}