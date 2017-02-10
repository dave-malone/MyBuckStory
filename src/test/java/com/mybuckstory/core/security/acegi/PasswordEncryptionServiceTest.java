package com.mybuckstory.core.security.acegi;

import org.apache.log4j.Logger;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.mybuckstory.core.security.PasswordEncryptionService;
import com.mybuckstory.core.service.RoleService;
import com.mybuckstory.core.service.UserService;
import com.mybuckstory.model.User;



public class PasswordEncryptionServiceTest extends AbstractDependencyInjectionSpringContextTests {

	private static final Logger logger = Logger.getLogger(PasswordEncryptionServiceTest.class);
	
	protected PasswordEncryptionService passwordEncryptionService;
	protected UserService userService;
	protected RoleService roleService;
	
	public PasswordEncryptionServiceTest(){
		setPopulateProtectedVariables(true);		
	}
	
	public void testEncryptPassword(){
		String password = "admin";
		User user = new User();		
		user.setPassword(password);
		logger.debug("Password: " + user.getPassword());
		this.passwordEncryptionService.encodePassword(user);
		
		logger.debug("Encoded password: " + user.getPassword());
		
	}
	
	// specifies the Spring configurations to load for this test
    protected String[] getConfigLocations() {
        return new String[] { 
        			"classpath:dao-context.xml",
        			"classpath:core-context.xml"
        	   };
    }
	
}
