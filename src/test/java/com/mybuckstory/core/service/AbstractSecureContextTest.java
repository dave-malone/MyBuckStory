package com.mybuckstory.core.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.mybuckstory.model.Image;
import com.mybuckstory.model.MimeType;

public abstract class AbstractSecureContextTest extends AbstractTransactionalDataSourceSpringContextTests {

		protected final Logger logger = Logger.getLogger(getClass());
		
		protected DaoAuthenticationProvider daoAuthenticationProvider;

		protected CategoryService categoryService;	
				
		protected static final String avidmalone_gmail_username = "avidmalone@gmail.com";
		protected static final String avidmalone_gmail_password = "malone82";
		
		protected static final String davemalone_mbs_username = "dave.malone@mybuckstory.com";
		protected static final String davemalone_mbs_password = "malone82";
		
		protected static final String admin_username = "admin";
		protected static final String admin_password = "admin";
		
		protected ImageService imageService;

		protected static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam malesuada dui at nulla porttitor id vehicula leo venenatis. Proin cursus faucibus lectus sit amet lacinia. Suspendisse potenti. Duis mattis mauris nec libero tincidunt auctor. Nam neque enim, condimentum lobortis dictum sed, mollis tincidunt metus. Donec at odio mauris, ac laoreet orci. Phasellus ullamcorper odio sit amet enim rutrum euismod. Aenean consequat tempor congue. Sed nec nulla lorem, sed vulputate lorem. Proin sapien purus, laoreet id cursus nec, luctus dictum nunc. Vivamus purus dui, elementum at imperdiet non, tempus porttitor dolor. Nunc dignissim mollis libero, vitae semper orci tincidunt id. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Cras condimentum eleifend malesuada. Mauris rutrum cursus dignissim. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus risus mi, tempor a pulvinar eget, sagittis in ligula. Aliquam id dignissim risus. Maecenas molestie elit a justo fermentum auctor.";
		
		public AbstractSecureContextTest(){
			setDefaultRollback(true);
			setPopulateProtectedVariables(true);	
		}
		
		// specifies the Spring configurations to load for this test
	    protected String[] getConfigLocations() {
	        return new String[] { 
	        			"classpath:applicationContext.xml",
	        			"springSecurity-applicationContext.xml"
	        	   };
	    }
		
		@Override
		protected void onSetUp() throws Exception {
			super.onSetUp();
			createSecureContext(admin_username, admin_password);
		}


		@Override
		protected void onTearDown() throws Exception {
			super.onTearDown();
			SecurityContextHolder.setContext(new SecurityContextImpl());
		}
		
		protected void createSecureContext(final String username, final String password) {
			Authentication auth = daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	        SecurityContextHolder.getContext().setAuthentication(auth);
	    }

		protected Image getTestImage(final String fileExt, int thumbWidth, int thumbHeight)
				throws Exception {
					File dir = new File("test/com/mybuckstory/core/service");
//					logger.debug("Directory (java.io.File): " + dir);
					File[] files = dir.listFiles(new FilenameFilter(){
						public boolean accept(File dir, String filename) {
							return filename.endsWith(fileExt);
						}
						
					});
//					logger.debug("Found " + files.length + " jpg files");
					assertNotNull(files);
					
					List<File> fileList = Arrays.asList(files);
					Collections.shuffle(fileList);
					File file = fileList.get(0);
					
//					System.out.println("using image " + file);
					InputStream is = null;
					ByteArrayOutputStream baos = null;
					
					try{
						is = new FileInputStream(file);
						baos = new ByteArrayOutputStream();
						IOUtils.copy(is, baos);
					}finally{
						IOUtils.closeQuietly(is);			
					}
					
					byte[] fullSize = baos.toByteArray();
					byte[] thumbnail = imageService.scaleImage(fullSize, MimeType.getByFileExt(fileExt), thumbWidth, thumbHeight);
					
					Image image = new Image();
					image.setContent(fullSize);
					image.setThumbnail(thumbnail);
					image.setMimeType(MimeType.getByFileExt(fileExt));
					image.setOriginalFileName(file.getName());
					image.setTitle("Test image");
					return image;
					
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

		protected double getFreeMemoryInMB(){
			double mem0 = Runtime.getRuntime().freeMemory();		
			return (mem0/1048576.0);
		}

		protected double getTotalMemoryInMB(){
			double mem0 = Runtime.getRuntime().totalMemory();		
			return (mem0/1048576.0);
		}

}
