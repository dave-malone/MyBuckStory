package com.mybuckstory.dao;

import java.util.List;

import com.mybuckstory.model.Profile;

public class ProfileDaoTest extends AbstractMbsDaoTest {
	
	public void testFindAll(){
		List<Profile> all = profileDao.findAll();
		assertNotNull(all);
	}
	
}
