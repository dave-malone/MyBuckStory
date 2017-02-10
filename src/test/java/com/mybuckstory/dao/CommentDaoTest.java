package com.mybuckstory.dao;

import java.util.List;

import com.mybuckstory.model.Comment;

public class CommentDaoTest extends AbstractMbsDaoTest {

	public void testCreate(){
		Comment cmt = new Comment();		
		cmt.setText("This is a test comment");
		
		commentDao.save(cmt);
		
		assertNotNull(cmt.getId());
		assertNotNull(cmt.getDateCreated());
		assertNotNull(cmt.getLastUpdated());
		assertNotNull(cmt.getCreatedBy());
		assertNotNull(cmt.getLastUpdatedBy());
	}
		
	public void testFindAll(){		
		List all = commentDao.findAll();
		assertNotNull(all);
		logger.debug("Find all found " + all.size());
	}
	
	public void testGetById(){
		Comment cmt = new Comment();		
		cmt.setText("This is a test comment");
		
		commentDao.save(cmt);
		
		assertNotNull(cmt.getId());
		assertNotNull(cmt.getDateCreated());
		assertNotNull(cmt.getLastUpdated());
		assertNotNull(cmt.getCreatedBy());
		assertNotNull(cmt.getLastUpdatedBy());
		
		assertNotNull(commentDao.getById(cmt.getId()));
	}
	
	public void testLoadById(){
		Comment cmt = new Comment();		
		cmt.setText("This is a test comment");
		
		commentDao.save(cmt);
		
		assertNotNull(cmt.getId());
		assertNotNull(cmt.getDateCreated());
		assertNotNull(cmt.getLastUpdated());
		assertNotNull(cmt.getCreatedBy());
		assertNotNull(cmt.getLastUpdatedBy());
		
		assertNotNull(commentDao.loadById(cmt.getId()));
	}
	
	public void testDelete(){
		Comment cmt = new Comment();		
		cmt.setText("This is a test comment");
		
		commentDao.save(cmt);
		
		assertNotNull(cmt.getId());
		assertNotNull(cmt.getDateCreated());
		assertNotNull(cmt.getLastUpdated());
		assertNotNull(cmt.getCreatedBy());
		assertNotNull(cmt.getLastUpdatedBy());
		
		assertNotNull(commentDao.getById(cmt.getId()));
		
		commentDao.delete(cmt);
		
		assertNull(commentDao.getById(cmt.getId()));
	}
	
}
