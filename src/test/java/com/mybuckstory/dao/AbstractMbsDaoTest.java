package com.mybuckstory.dao;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.mybuckstory.core.service.AbstractSecureContextTest;
import com.mybuckstory.model.Comment;
import com.mybuckstory.model.Image;
import com.mybuckstory.model.Profile;
import com.mybuckstory.model.Role;
import com.mybuckstory.model.Story;
import com.mybuckstory.model.User;

public abstract class AbstractMbsDaoTest extends AbstractSecureContextTest {

	protected final Logger logger = Logger.getLogger(getClass());
	
	protected GenericHibernateDao<Comment, Long> commentDao;
	protected GenericHibernateDao<Image, Long> imageDao;
	protected GenericHibernateDao<Profile, Long> profileDao;
	protected GenericHibernateDao<Role, Long> roleDao;
	protected GenericHibernateDao<Story, Long> storyDao;
	protected GenericHibernateDao<User, Long> userDao;
	
	public AbstractMbsDaoTest(){
		setPopulateProtectedVariables(true);
		setDefaultRollback(true);
	}
	
	protected User findByUsername(final String username){
		return (User)userDao.getCurrentSession().createQuery("from User u where u.username = :username")
			.setParameter("username", username)
			.uniqueResult();
	}
	
	protected Role findByName(final String name){
		return (Role)roleDao.getCurrentSession().createQuery("from Role role where role.name = :name")
			.setParameter("name", name)
			.uniqueResult();
	}
	
	protected byte[] getFile(String file) throws IOException{
		InputStream in = null;
		ByteArrayOutputStream out = null;
		byte[] bytes = null;
		try{
			in = new FileInputStream(file);
			out = new ByteArrayOutputStream();
			int i;		 
			while ((i = in.read()) != -1){
			   out.write(i);
			}	
			
			bytes = out.toByteArray();
			
		}finally{
			if(in != null){
				in.close();
			}
			if(out != null){
				out.close();
			}
		}
		return bytes;
	}
	
}